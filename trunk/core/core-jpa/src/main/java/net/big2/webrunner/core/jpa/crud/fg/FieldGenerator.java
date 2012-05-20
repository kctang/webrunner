package net.big2.webrunner.core.jpa.crud.fg;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;

import java.util.List;
import java.util.Map;

public interface FieldGenerator {
    void populateFieldList(Class<? extends CrudEntity> crudEntityClass, List<String> fieldList) throws FieldGeneratorException;
    void populateFieldMap(Class<? extends CrudEntity> crudEntityClass, List<String> fieldList, Map<String, Field> fieldMap) throws FieldGeneratorException;
}
