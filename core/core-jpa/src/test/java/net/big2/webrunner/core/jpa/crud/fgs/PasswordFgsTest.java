package net.big2.webrunner.core.jpa.crud.fgs;

import net.big2.webrunner.core.jpa.crud.Field;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordFgsTest {
    @Test
    public void generatesPasswordField() {
        Field actual = new PasswordFgs().generate("password:myField", null);

        assertEquals(Field.Type.PASSWORD, actual.getType());
        assertEquals("myField", actual.getId());
    }
}
