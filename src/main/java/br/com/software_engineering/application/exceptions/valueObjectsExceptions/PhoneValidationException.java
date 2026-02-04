package br.com.software_engineering.application.exceptions.valueObjectsExceptions;


public class PhoneValidationException extends RuntimeException{
    private final String value;

    public PhoneValidationException(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
