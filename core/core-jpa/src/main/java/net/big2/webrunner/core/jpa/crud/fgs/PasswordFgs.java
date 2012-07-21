package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

/**
 * Generates a password field.
 * <p/>
 * Generates a field based on specified prefix. Supported prefixes are based on
 * Field.Type enum.
 */
public class PasswordFgs extends AbstractFieldGenerationStrategy {
    public static final String PREFIX_PASSWORD = "password:";

    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        Field field = null;

        if (fieldName.startsWith(PREFIX_PASSWORD)) {
            field = Field.makeField(Field.extractFieldName(fieldName));
            field.setType(Field.Type.PASSWORD);
        }

        return field;
    }
}
