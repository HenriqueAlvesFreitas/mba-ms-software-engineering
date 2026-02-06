package br.com.software_engineering.application.service.user.strategy;

import br.com.software_engineering.application.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSearchStrategy {
    Page<User> search(String name, Pageable pageable);
}
