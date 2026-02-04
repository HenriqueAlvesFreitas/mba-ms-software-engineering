package br.com.software_engineering.application.domain.valueObjects;

import br.com.software_engineering.application.exceptions.valueObjectsExceptions.PhoneValidationException;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public final class Phone {

    private static final Set<String> DDDs = Set.of(
            "11","12","13","14","15","16","17","18","19",
            "21","22","24","27","28","31","32","33","34",
            "35","36","37","38","39","41","42","43","44",
            "45","46","47","48","49","51","53","54","55",
            "56","57","58","59","61","62","63","64","65",
            "66","67","68","69","71","73","74","75","77",
            "78","79","81","82","83","84","85","86","87",
            "88","89","91","92","93","94","95","96","97",
            "98","99");

    private static final Pattern E164_PATTERN =
            Pattern.compile("^\\+?(55)?([1-9]{2})?(\\d{4,5})(\\d{4})$");

    private final String value;

    public Phone(String value) {
        validate(value);
        this.value = value.trim().toLowerCase();
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
        return value.equals(phone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private void validate(String value) {
        if (!E164_PATTERN.matcher(value).matches() || value.length() != 14) {
            throw new PhoneValidationException("Invalid Format, ex: +5511999998888", value);
        }

        if (!value.startsWith("+55")) {
            throw new PhoneValidationException("Number must be brazilian.", value);
        }


        if (value.chars().distinct().count() == 1) {
            throw new PhoneValidationException("Invalid number (repeated digits)", value);
        }

        String ddd = value.substring(3,5);
        if (!DDDs.contains(ddd)) {
            throw new PhoneValidationException("Invalid DDD", value);
        }

        char ninithDigit = value.charAt(5);
        if (ninithDigit != '9') {
            throw new PhoneValidationException("Brazilian numbers must start with number 9!", value);
        }

    }

}
