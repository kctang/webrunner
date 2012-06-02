package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

/**
 * // TODO: change this to a PrefixFgs
 *
 * Generates a field based on specified prefix. Supported prefixes are based on
 * Field.Type enum.
 */
public class JspIncludeFgs extends AbstractFieldGenerationStrategy {
    public static final String PREFIX_JSP = "jsp:";

    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        Field field = null;

        if (fieldName.startsWith(PREFIX_JSP)) {

            field = Field.makeField(Field.extractFieldName(fieldName));
            field.setType(Field.Type.JSP);

            /*
            String filename = fieldName.substring(PREFIX_JSP.length());

            if(idIndex != -1) {
                // derive field ID from fieldName
                field.setId(filename.substring(idIndex + 1));
                filename = filename.substring(0, idIndex);
            }
            field.setType(Field.Type.JSP);

            field.setFilename(filename);
            field.setLabel(Field.toLabel(field.getId()));*/
        }

        return field;
    }
}
