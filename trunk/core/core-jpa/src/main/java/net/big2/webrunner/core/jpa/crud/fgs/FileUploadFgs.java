package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUploadFgs extends AbstractFieldGenerationStrategy {
    public static final String PREFIX_FILE = "file";

    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        Field field = null;

        StringTokenizer st = new StringTokenizer(fieldName, ":");
        if (st.hasMoreTokens()) {

            // token 1 - prefix
            String prefix = st.nextToken();
            if (PREFIX_FILE.equals(prefix)) {

                // token 2 - field name
                String name = st.nextToken();
                field = Field.makeField(name);
                field.setType(Field.Type.FILE);

                field.setUploadedProperty(name + "Uploaded");

                // token 3++ - valid content types
                List<String> contentTypeList = new ArrayList<String>();
                while (st.hasMoreTokens()) {
                    contentTypeList.add(st.nextToken());
                }
                field.setValidContentTypes(contentTypeList.toArray(new String[contentTypeList.size()]));
                field.setLabel(Field.toLabel(name));
            }
        }

        return field;
    }
}
