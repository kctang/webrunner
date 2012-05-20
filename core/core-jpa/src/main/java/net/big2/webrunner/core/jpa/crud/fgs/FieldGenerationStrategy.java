package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.fg.FieldGeneratorException;

public interface FieldGenerationStrategy {
    Field generate(String fieldName, Class<? extends CrudEntity> crudEntityClass) throws FieldGeneratorException;
}
