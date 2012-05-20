package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

import javax.persistence.Lob;

import static net.big2.webrunner.core.jpa.crud.fg.ReflectionUtils.isFieldAnnotatedWith;

/**
 * Algorithm:
 * <ul>
 * <li>if fieldName exists and is annotated with @Lob, that's what i want!</li>
 * </ul>
 */
public class TextareaFgs extends AbstractFieldGenerationStrategy {
    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        Field field = null;

        if (isFieldAnnotatedWith(fieldName, crudEntityClass, Lob.class)) {
            field = Field.makeField(fieldName);
            field.setType(Field.Type.TEXTAREA);
            field.setRows(10);
        }

        return field;
    }

}