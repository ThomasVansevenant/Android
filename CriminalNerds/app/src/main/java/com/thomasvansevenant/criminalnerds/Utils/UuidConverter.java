package com.thomasvansevenant.criminalnerds.Utils;

import java.util.UUID;

import de.greenrobot.dao.converter.PropertyConverter;

public class UuidConverter implements PropertyConverter<UUID, String> {
    @Override
    public UUID convertToEntityProperty(String databaseValue) {
        return UUID.fromString(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(UUID entityProperty) {
        return entityProperty.toString();
    }
}