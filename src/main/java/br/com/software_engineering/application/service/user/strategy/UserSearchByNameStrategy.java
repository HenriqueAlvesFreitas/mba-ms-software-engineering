package br.com.software_engineering.application.service.user.strategy;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserSearchByNameStrategy implements UserSearchStrategy {

    private final UserRepository repository;

    public UserSearchByNameStrategy(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<User> search(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
