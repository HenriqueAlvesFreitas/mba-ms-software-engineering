package br.com.software_engineering.application.exceptions.valueObjectsExceptions;


public class EmailValidationException extends RuntimeException{
    private final String value;

    public EmailValidationException(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
