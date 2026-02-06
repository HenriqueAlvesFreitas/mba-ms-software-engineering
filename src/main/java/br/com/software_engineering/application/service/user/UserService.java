package br.com.software_engineering.application.service.user;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.domain.valueObjects.Email;
import br.com.software_engineering.application.dtos.mappers.UserMapper;
import br.com.software_engineering.application.dtos.request.UserDTO;
import br.com.software_engineering.application.exceptions.userExceptions.UserAlreadyExistsException;
import br.com.software_engineering.application.exceptions.userExceptions.UserNotFoundException;
import br.com.software_engineering.infra.persistence.UserRepository;
import br.com.software_engineering.application.service.user.filter.UserSearchFilter;
import br.com.software_engineering.application.service.user.strategy.UserSearchStrategy;
import br.com.software_engineering.application.service.user.strategy.UserSearchStrategyResolver;
import br.com.software_engineering.infra.web.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    UserSearchStrategyResolver strategyResolver;

    @Autowired
    UserMapper mapper;

    public ResponseEntity<RestResponse<User>> save(UserDTO dto){

        validateExistingEmail(new Email(dto.getEmail()));

        User savedData = repository.save(mapper.toEntity(dto));

        return RestResponse.success(savedData, "User successfully saved!");
    }

    public ResponseEntity<RestResponse<Page<User>>> getAll(String name, String email, String phone, int page){

        Pageable pageable = PageRequest.of(Math.max(page, 0), 5);

        UserSearchFilter filter = new UserSearchFilter();
        filter.setEmail(email);
        filter.setName(name);
        filter.setPhone(phone);

        UserSearchStrategy strategy = strategyResolver.resolve(filter);

        Page<User> userPage = strategy.search(filter, pageable);

        return RestResponse.success(userPage, "Users found!");
    }

    public ResponseEntity<RestResponse<User>> updateUser(UserDTO user, UUID id){

        User foundUser = findUser(id);

        assertEmailIsUniqueForUpdate(new Email(user.getEmail()), id);

        mapper.updateEntityFromDTO(user, foundUser);

        User savedUser = repository.save(foundUser);

        return RestResponse.success(savedUser, "User updated!");
    }

    public ResponseEntity<RestResponse<Object>> deleteUser(UUID id){

        User foundUser = findUser(id);

        repository.delete(foundUser);

        return RestResponse.success(null, "User Deleted!");
    }

    private User findUser(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    private User findUserByEmail(Email email){
        return repository.findUserByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email.getValue()));
    }

    private void validateExistingEmail(Email email){
        repository.findUserByEmail(email)
                .ifPresent(user -> {
                   throw new UserAlreadyExistsException(email.getValue());
                });

    }

    private void assertEmailIsUniqueForUpdate(Email email, UUID currentUserId) {
        repository.findUserByEmail(email)
                .filter(user -> !user.getId().equals(currentUserId))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(email.getValue());
                });
    }

}
