package br.com.software_engineering.application.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException{
    private final Object info;
    public UserNotFoundException(Object info) {
        super("User not found");
        this.info = info;
    }


    public Object getUserInfo() {
        return info;
    }
}
