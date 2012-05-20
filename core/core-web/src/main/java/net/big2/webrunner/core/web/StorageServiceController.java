package net.big2.webrunner.core.web;

import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.storage.StorageServiceException;
import net.sf.jmimemagic.*;
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

@Controller
public class StorageServiceController {
    StorageService storageService;

    @Autowired
    public StorageServiceController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "data/{type}/{id:[a-z0-9\\-\\.]+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getData(@PathVariable String type, @PathVariable String id) throws StorageServiceException {
        byte[] data = storageService.load(type, id);

        String mimeType;
        try {
            MagicMatch match = Magic.getMagicMatch(data);
            mimeType = match.getMimeType();
        } catch (MagicParseException e) {
            mimeType = "application/octet-stream";
        } catch (MagicMatchNotFoundException e) {
            mimeType = "application/octet-stream";
        } catch (MagicException e) {
            mimeType = "application/octet-stream";
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
