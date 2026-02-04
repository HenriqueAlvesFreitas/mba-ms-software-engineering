package br.com.software_engineering.application.domain.valueObjects;


import br.com.software_engineering.application.exceptions.valueObjectsExceptions.EmailValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    public Email(String value) {
        if (!EMAIL_REGEX.matcher(value).matches()) {
            throw new EmailValidationException("Invalid e-mail format!", value);
        }
        this.value = value.trim().toLowerCase();
    }
    
    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
