package com.iad.courseProject.data.entities.types;

import org.postgresql.geometric.PGpoint;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PGPointConverter implements AttributeConverter<PGpoint, Object> {
    @Override
    public Object convertToDatabaseColumn(PGpoint point) {
        return point;
    }

    @Override
    public PGpoint convertToEntityAttribute(Object object) {
        return (PGpoint) object;
    }
}
