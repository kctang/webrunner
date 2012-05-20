package net.big2.webrunner.core.jpa.crud.fg;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.fgs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;

public class DefaultFieldGenerator implements FieldGenerator {
    private Logger log = LoggerFactory.getLogger(DefaultFieldGenerator.class);

    private List<FieldGenerationStrategy> fieldGenerationStrategyList;


    public DefaultFieldGenerator() {
        fieldGenerationStrategyList = new ArrayList<FieldGenerationStrategy>();
        fieldGenerationStrategyList.addAll(asList(
                new JspIncludeFgs(),
                new FileUploadFgs(),
                new TextareaFgs(),
                new PrimitiveTypeFgs(),
                new SelectFgs(),
                new FallbackFgs()
        ));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void populateFieldList(Class<? extends CrudEntity> crudEntityClass, List<String> fieldList) throws FieldGeneratorException {
        checkArgument(fieldList != null);

        // --- hmm..currently not doing anyway, support to reflect from crudEntityClass, useful?
//        fieldList.clear();

        // TODO: detect fields from class via @Crud(...)
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void populateFieldMap(Class<? extends CrudEntity> crudEntityClass, List<String> fieldList, Map<String, Field> fieldMap) throws FieldGeneratorException {
        checkArgument(fieldList != null && fieldMap != null);

        // clear fieldMap
        fieldMap.clear();

        for (String fieldName : fieldList) {
            Field field = null;

            // send fields through registered field generation strategies
            for (FieldGenerationStrategy strategy : fieldGenerationStrategyList) {
                field = strategy.generate(fieldName, crudEntityClass);
                if (field != null) {
                    break;
                }
            }

            if (field != null) {
                fieldMap.put(field.getId(), field);
            } else {
                throw new FieldGeneratorException(String.format("Unable to generate field [%s]", fieldName));
            }
        }

    }

}
