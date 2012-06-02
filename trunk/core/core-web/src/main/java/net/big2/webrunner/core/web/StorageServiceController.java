package net.big2.webrunner.core.web;

import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.storage.StorageServiceException;
import net.sf.jmimemagic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import static java.lang.String.format;

@Controller
public class StorageServiceController extends BaseController {
    private Logger log = LoggerFactory.getLogger(StorageServiceController.class);

    StorageService storageService;

    @Autowired
    public StorageServiceController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "data/{type}/{id:[a-z0-9\\-\\.]+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getData(WebRequest request, @PathVariable String type, @PathVariable String id) throws StorageServiceException {

        // TODO: this checkNotModified() cache need to be "proven"
        long lastModified = System.currentTimeMillis() - (1000 * 30); // 30 seconds ago
        if (request.checkNotModified(lastModified)) {
            // 2. shortcut exit - no further processing necessary
            return null;
        }

        byte[] data = storageService.load(type, id);

        String mimeType;
        try {
            MagicMatch match = Magic.getMagicMatch(data);
            mimeType = match.getMimeType();
            log.debug(format("Serving data as %s", mimeType));
        } catch (MagicParseException e) {
            mimeType = "application/octet-stream";
            log.debug(format("Serving data as %s (fallback)", mimeType));
        } catch (MagicMatchNotFoundException e) {
            mimeType = "application/octet-stream";
            log.debug(format("Serving data as %s (fallback)", mimeType));
        } catch (MagicException e) {
            mimeType = "application/octet-stream";
            log.debug(format("Serving data as %s (fallback)", mimeType));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));

        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "data/{type}/{id:[a-z0-9\\-\\.]+}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String type, @PathVariable String id) throws StorageServiceException {
        storageService.delete(type, id);

        return "Deleted";
    }
}