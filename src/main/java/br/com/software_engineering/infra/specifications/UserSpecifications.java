package br.com.software_engineering.infra.specifications;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.service.user.filter.UserSearchFilter;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class UserSpecifications {

    public static Specification<User> byFilter(UserSearchFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmail() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + filter.getEmail().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getPhone() != null) {
                predicates.add(
                        cb.like(
                                root.get("phone"),
                                "%" + filter.getPhone() + "%"
                        )
                );
            }

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                    )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
