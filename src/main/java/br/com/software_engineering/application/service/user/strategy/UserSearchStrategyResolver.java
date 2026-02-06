package br.com.software_engineering.application.service.user.strategy;

import org.springframework.stereotype.Component;

@Component
public class UserSearchStrategyResolver {

    private final UserSearchByNameStrategy byName;

    private final UserSearchAllStrategy all;

    public UserSearchStrategyResolver(
            UserSearchByNameStrategy byName,
            UserSearchAllStrategy all
    ){
        this.byName = byName;
        this.all = all;
    }

    public UserSearchStrategy resolve(String name){
        if(name != null && !name.trim().isEmpty()){
            return byName;
        }
        return all;
    }
}
