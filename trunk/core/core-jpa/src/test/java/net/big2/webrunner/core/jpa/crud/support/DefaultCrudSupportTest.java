package net.big2.webrunner.core.jpa.crud.support;

import net.big2.webrunner.core.jpa.crud.BaseEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.Lob;

import static org.junit.Assert.assertEquals;

public class DefaultCrudSupportTest {
    DefaultCrudSupport crudSupport;

    @Before
    public void setUp() throws Exception {
        crudSupport = new DefaultCrudSupport(DefaultCrudSupportTestEntity.class, null, null);
    }

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


@Entity
class DefaultCrudSupportTestEntity extends BaseEntity {
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