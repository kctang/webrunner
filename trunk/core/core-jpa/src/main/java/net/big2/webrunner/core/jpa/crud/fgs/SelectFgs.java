package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.fg.FieldGeneratorException;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import static net.big2.webrunner.core.jpa.crud.fg.ReflectionUtils.isFieldAnnotatedWith;

public class SelectFgs extends AbstractFieldGenerationStrategy {
    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) throws FieldGeneratorException {
        Field field = null;

        if (isFieldAnnotatedWith(fieldName, crudEntityClass, ManyToMany.class)) {
            field = Field.makeField(fieldName);
            field.setType(Field.Type.SELECT);

        } else if (isFieldAnnotatedWith(fieldName, crudEntityClass, OneToOne.class)) {
            field = Field.makeField(fieldName);
            field.setType(Field.Type.SELECT);
            field.setMultiple(false);

            try {
                OneToOne oneToOne = null;
                oneToOne = crudEntityClass.getDeclaredField(fieldName).getAnnotation(OneToOne.class);
                if (oneToOne.optional()) {
                    field.setOptional(true);
                }

            } catch (NoSuchFieldException e) {
                throw new FieldGeneratorException(String.format("Error generating field [%s]", fieldName), e);
            }

        } else if (isFieldAnnotatedWith(fieldName, crudEntityClass, ManyToOne.class)) {
            field = Field.makeField(fieldName);
            field.setType(Field.Type.SELECT);
            field.setMultiple(false);

            try {
                ManyToOne manyToOne;
                manyToOne = crudEntityClass.getDeclaredField(fieldName).getAnnotation(ManyToOne.class);
                if (manyToOne.optional()) {
                    field.setOptional(true);
                }

            } catch (NoSuchFieldException e) {
                throw new FieldGeneratorException(String.format("Error generating field [%s]", fieldName), e);
            }

        }

        return field;
    }

}