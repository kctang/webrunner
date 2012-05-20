package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

public class FallbackFgs implements FieldGenerationStrategy {
    @Override
    public Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) {
        return Field.makeField(fieldName);
    }
}
