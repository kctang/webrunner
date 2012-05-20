package net.big2.webrunner.core.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrudSupportControllerTest {
    private CrudSupportController crudSupportController;

    @Before
    public void setUp() throws Exception {
        crudSupportController = new CrudSupportController(null, null);
    }

    @Test
    public void getTypeForEdit_Match() {
        String uri;
        String contextPath;
        String actual;

        uri = "/obj/item/1";
        contextPath = "";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals("item", actual);

        uri = "/context/obj/item/12";
        contextPath = "/context";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals("item", actual);

        uri = "/context/obj/another-item/123";
        contextPath = "/context";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals("another-item", actual);
    }

    @Test
    public void getTypeForEdit_NoMatch() {
        String expected = null;
        String uri;
        String contextPath;
        String actual;

        uri = "/obj/item";
        contextPath = "";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

        uri = "/obj/item/";
        contextPath = "";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

        uri = "/obj/item/abc";
        contextPath = "";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

        uri = "/context/obj/item";
        contextPath = "/context";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

        uri = "/context/obj/item/";
        contextPath = "/context";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

        uri = "/context/obj/item/abc";
        contextPath = "/context";
        actual = crudSupportController.getTypeForEdit(uri, contextPath);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getTypeForNew() {
        String uri;
        String contextPath;
        String actual;

        uri = "/obj/item/new";
        contextPath = "";
        actual = crudSupportController.getTypeForNew(uri, contextPath);
        Assert.assertEquals("item", actual);

        uri = "my-context/obj/item/new";
        contextPath = "my-context";
        actual = crudSupportController.getTypeForNew(uri, contextPath);
        Assert.assertEquals("item", actual);
    }

    @Test
    public void getIdForEdit() {
        String uri;
        String contextPath;
        Long actual;

        uri = "/obj/item/123";
        contextPath = "";
        actual = crudSupportController.getIdForEdit(uri, contextPath);
        Assert.assertEquals(new Long(123), actual);

        uri = "/obj/item/abc123";
        contextPath = "";
        actual = crudSupportController.getIdForEdit(uri, contextPath);
        Assert.assertNull(actual);

        uri = "/obj/item/-123";
        contextPath = "";
        actual = crudSupportController.getIdForEdit(uri, contextPath);
        Assert.assertNull(actual);

    }

}
