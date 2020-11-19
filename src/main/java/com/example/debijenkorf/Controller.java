package com.example.debijenkorf;
import com.example.debijenkorf.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@RestController
public class Controller {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    @Autowired
    ServiceLocator service_locator;

    @RequestMapping(value = "/{resource_type}/{action}/{resource_sub_type}/{dummy_seo_name}", method = RequestMethod.GET)
    ResponseObject request_resource_with_dummy_seo_name(
            @RequestParam(required = true, value = "reference") String reference,
            @PathVariable(required = true) String resource_sub_type,
            @PathVariable(required = true) String action,
            @PathVariable(required = true) String resource_type) {

        return this.process(resource_type, resource_sub_type, reference, action);
    }

    @RequestMapping(value = "/{resource_type}/{action}/{resource_sub_type}", method = RequestMethod.GET)
    ResponseObject request_resource_without_dummy_seo_name(
            @RequestParam(required = true, value = "reference") String reference,
            @PathVariable(required = true) String resource_sub_type,
            @PathVariable(required = true) String action,
            @PathVariable(required = true) String resource_type) {

        return this.process(resource_type, resource_sub_type, reference, action);

    }
    private ResponseObject process(String resource_type, String resource_sub_type, String reference, String action) {
        ResourceRequest resource_request = new ResourceRequest(resource_type, resource_sub_type, reference, action);
        ResourceService relevant_service = service_locator.get_service(resource_type);
        if (relevant_service == null) {
            LOGGER.info("resource type not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "resource type not found"
            );

        }
        return relevant_service.process_request(resource_request);
    }

}
