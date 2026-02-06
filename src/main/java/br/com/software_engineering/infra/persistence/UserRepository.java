package br.com.software_engineering.infra.persistence;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.domain.valueObjects.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository
        extends JpaRepository<User, UUID>,
        JpaSpecificationExecutor<User> {

    Optional<User> findUserByEmail(Email email);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
