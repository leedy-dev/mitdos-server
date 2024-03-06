package com.dydev.mitd.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Convert
public record BCryptoConverter(PasswordEncoder passwordEncoder) implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Optional.ofNullable(attribute)
                .map(passwordEncoder::encode)
                .orElse(null);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }

}
