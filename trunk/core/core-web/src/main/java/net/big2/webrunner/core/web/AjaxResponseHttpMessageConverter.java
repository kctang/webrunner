package net.big2.webrunner.core.web;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AjaxResponseHttpMessageConverter extends MappingJacksonHttpMessageConverter {
    protected Logger log = LoggerFactory.getLogger(AjaxResponseHttpMessageConverter.class);

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof AjaxResponse) {
            // writes AjaxResponse directly
            super.writeInternal(o, outputMessage);

        } else if (o instanceof BindingResult) {
            Map<String, String> bindingResultMap = new LinkedHashMap<String, String>();
            for (FieldError error : ((BindingResult) o).getFieldErrors()) {
                bindingResultMap.put(error.getField(), error.getDefaultMessage());
            }
            AjaxResponse response = new AjaxResponse(AjaxResponse.STATUS_ERROR, "Validation error", bindingResultMap);
            super.writeInternal(response, outputMessage);

        } else if (o instanceof String) {
            // writes String as message

            if (((String) o).startsWith("{") && ((String) o).endsWith("}")) {
                // String values that starts with "{" and ends with "}" are assumed to be be JSON formats
                // write out directly
                IOUtils.write(o.toString(), outputMessage.getBody());

            } else {
                // other String values will be wrapped in an OK status AjaxResponse object
                AjaxResponse response = new AjaxResponse(o.toString());
                super.writeInternal(response, outputMessage);
            }

        } else {
            AjaxResponse response = new AjaxResponse(o);
            super.writeInternal(response, outputMessage);
        }
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (byte[].class.isAssignableFrom(clazz)) {
            // do not handle byte[]
            return false;
        } else {
            return super.canWrite(clazz, mediaType);
        }
    }
}
