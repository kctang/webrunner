package net.big2.webrunner.core.web;

import org.junit.Test;

import static net.big2.webrunner.core.web.AjaxResponse.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AjaxResponseTest {
    @Test
    public void pojo_ok() {
        AjaxResponse ajaxResponse = new AjaxResponse("data");
        assertEquals("data", ajaxResponse.getData());
        assertEquals(STATUS_OK, ajaxResponse.getStatus());
        assertNull(ajaxResponse.getMessage());
        
        ajaxResponse.setStatus(STATUS_ERROR);
        ajaxResponse.setMessage("number");
        ajaxResponse.setData(123);
        assertEquals(STATUS_ERROR, ajaxResponse.getStatus());
        assertEquals("number", ajaxResponse.getMessage());
        assertEquals(123, ajaxResponse.getData());

        ajaxResponse = new AjaxResponse(STATUS_ERROR, "bad", "bad-data");
        assertEquals("bad-data", ajaxResponse.getData());
        assertEquals(STATUS_ERROR, ajaxResponse.getStatus());
        assertEquals("bad", ajaxResponse.getMessage());
    }
}
