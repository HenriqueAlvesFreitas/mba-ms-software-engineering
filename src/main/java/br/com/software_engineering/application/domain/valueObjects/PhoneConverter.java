package br.com.software_engineering.application.domain.valueObjects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PhoneConverter implements AttributeConverter<Phone, String> {

    @Override
    public String convertToDatabaseColumn(Phone phone) {
        return phone != null ? phone.getValue() : null;
    }

    @Override
    public Phone convertToEntityAttribute(String dbValue) {
        return dbValue != null ? new Phone(dbValue) : null;
    }
}