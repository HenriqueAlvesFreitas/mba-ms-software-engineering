package br.com.software_engineering.application.service.user.strategy;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.service.user.filter.UserSearchFilter;
import br.com.software_engineering.infra.persistence.UserRepository;
import br.com.software_engineering.infra.specifications.UserSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;



@Component
public class UserMixedSearch implements UserSearchStrategy {

    private final UserRepository repository;

    public UserMixedSearch(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<User> search(UserSearchFilter filter, Pageable pageable) {

        Specification<User> spec = UserSpecifications.byFilter(filter);

        return repository.findAll(spec, pageable);
    }
}