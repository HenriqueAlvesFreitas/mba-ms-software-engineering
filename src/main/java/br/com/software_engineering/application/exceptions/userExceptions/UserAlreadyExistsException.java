package br.com.software_engineering.application.exceptions.userExceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private final Object info;
    public UserAlreadyExistsException(Object info) {
        super("This e-mail already is being used");
        this.info = info;
    }


    public Object getUserInfo() {
        return info;
    }
}
