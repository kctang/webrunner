package net.big2.webrunner.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static java.util.UUID.randomUUID;

public class BaseController {
    private Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e,
                                  HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("error");

        String message = logExceptionWithId(e, request);
        mav.addObject("message", message);
        mav.addObject("exception", e);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);
        e.printStackTrace(writer);
        writer.close();
        mav.addObject("stackTrace", baos.toString());

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        return mav;

        // TODO: alternative codes for AJAX requests
/*
        boolean USE_HTTP_ERROR_CODES = true;
        if (USE_HTTP_ERROR_CODES) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return new AjaxResponse(AjaxResponse.STATUS_ERROR, message, null);
*/

    }

    private String logExceptionWithId(Exception e, HttpServletRequest request) {
        String message = String.format("Error processing request [Request ID:%s, URI:%s]", randomUUID(), request.getRequestURI());
        log.error(message, e);
        return message;
    }

}
