package com.iad.courseProject.data.services;

import com.iad.courseProject.data.entities.types.SectorState;
import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.OneToManyMapping;

public class RegionCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
        OneToManyMapping mapping = (OneToManyMapping) classDescriptor.getMappingForAttributeName("sectors");
        ExpressionBuilder eb = new ExpressionBuilder(mapping.getReferenceClass());

        Expression fkExp = eb.getField("regionId").equal(eb.getParameter(new DatabaseField("id", "region")));
        Expression activeExp = eb.getField("state").notEqual(SectorState.DELETED);

        mapping.setSelectionCriteria(fkExp.and(activeExp));
    }
}
