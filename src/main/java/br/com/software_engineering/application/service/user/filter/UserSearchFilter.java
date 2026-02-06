package br.com.software_engineering.application.service.user.filter;

import lombok.Data;

@Data
public class UserSearchFilter {
    private String name;
    private String email;
    private String phone;

    public boolean isEmpty() {
        return name == null && email == null && phone == null;
    }
}
