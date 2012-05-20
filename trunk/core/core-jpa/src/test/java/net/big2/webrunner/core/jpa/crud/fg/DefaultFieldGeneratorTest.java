package net.big2.webrunner.core.jpa.crud.fg;

import net.big2.webrunner.core.jpa.crud.BaseEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultFieldGeneratorTest {
    FieldGenerator fieldGenerator;

    @Before
    public void setUp() throws Exception {
        fieldGenerator = new DefaultFieldGenerator();
    }

    @Test
    public void canGenerateText() throws FieldGeneratorException {
        Map<String, Field> fieldMap = new HashMap<String, Field>();

        fieldGenerator.populateFieldMap(FieldGeneratorTestEntity.class, asList("name"), fieldMap);

        assertEquals(1, fieldMap.size());
        Field nameField = fieldMap.get("name");
        assertTrue(nameField.getType() == Field.Type.TEXT);
    }


    @Test
    public void canGenerateTextArea() throws FieldGeneratorException {
        Map<String, Field> fieldMap = new HashMap<String, Field>();

        fieldGenerator.populateFieldMap(FieldGeneratorTestEntity.class, asList("lobString"), fieldMap);

        assertEquals(1, fieldMap.size());
        Field nameField = fieldMap.get("lobString");
        assertTrue(nameField.getType() == Field.Type.TEXTAREA);
    }


}

@Entity
class FieldGeneratorTestEntity extends BaseEntity {
    private String name;

    @Lob
    private String lobString;

    @Override
    public String getDisplayName() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLobString() {
        return lobString;
    }

    public void setLobString(String lobString) {
        this.lobString = lobString;
    }
}