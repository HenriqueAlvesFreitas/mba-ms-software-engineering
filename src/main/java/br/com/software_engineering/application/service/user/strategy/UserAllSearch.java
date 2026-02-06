package br.com.software_engineering.application.service.user.strategy;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.service.user.filter.UserSearchFilter;
import br.com.software_engineering.infra.persistence.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserAllSearch implements UserSearchStrategy {

    private final UserRepository repository;

    public UserAllSearch(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<User> search(UserSearchFilter filter, Pageable pageable) {
        return repository.findAll(pageable);
    }
}
