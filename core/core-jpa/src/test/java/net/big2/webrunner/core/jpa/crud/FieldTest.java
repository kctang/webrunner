package net.big2.webrunner.core.jpa.crud;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldTest {
    @Test
    public void testSlug() {
        assertEquals("item", Field.toSlug("Item"));
        assertEquals("apple-berry-candy", Field.toSlug("AppleBerryCandy"));
        assertEquals("cooler-master", Field.toSlug("CoolerMaster"));
        assertEquals("big2-web-runner", Field.toSlug("Big2WebRunner"));
        assertEquals("j-d-b-c-driver", Field.toSlug("JDBCDriver"));
    }

    @Test
    public void testLabel() {
        assertEquals("Item", Field.toLabel("Item"));
        assertEquals("Item", Field.toLabel("item"));
        assertEquals("Item Two", Field.toLabel("itemTwo"));
        assertEquals("Item Two Three", Field.toLabel("itemTwoThree"));
        assertEquals("Item J D B C", Field.toLabel("itemJDBC"));
    }
}
