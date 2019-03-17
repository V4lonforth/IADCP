package com.iad.courseProject.data.entities.types;

import org.postgresql.geometric.PGpolygon;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PGPolygonConverter implements AttributeConverter<PGpolygon, Object> {
    @Override
    public Object convertToDatabaseColumn(PGpolygon point) {
        return point;
    }

    @Override
    public PGpolygon convertToEntityAttribute(Object object) {
        return (PGpolygon) object;
    }
}