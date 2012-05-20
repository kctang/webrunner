package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

import static net.big2.webrunner.core.jpa.crud.fg.ReflectionUtils.isFieldType;


public class PrimitiveTypeFgs extends AbstractFieldGenerationStrategy {

    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        Field field = null;

        if (isFieldType(fieldName, crudEntityClass, boolean.class)) {
            field = Field.makeField(fieldName);
            field.setType(Field.Type.CHECKBOX);
        }

        return field;
    }

}