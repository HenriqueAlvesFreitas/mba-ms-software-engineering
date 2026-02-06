package br.com.software_engineering.application.service.user.strategy;

import br.com.software_engineering.application.service.user.filter.UserSearchFilter;
import org.springframework.stereotype.Component;

@Component
public class UserSearchStrategyResolver {

    private final UserMixedSearch mixedStrategy;
    private final UserAllSearch simpleStrategy;

    public UserSearchStrategyResolver(
            UserMixedSearch mixedStrategy,
            UserAllSearch simpleStrategy
    ){
        this.mixedStrategy = mixedStrategy;
        this.simpleStrategy = simpleStrategy;
    }

    public UserSearchStrategy resolve(UserSearchFilter filter){
        if(filter.isEmpty()){
            return simpleStrategy;
        }
        return mixedStrategy;
    }
}
