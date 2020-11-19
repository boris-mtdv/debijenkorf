package com.example.debijenkorf;

import com.example.debijenkorf.service.ImageService;
import com.example.debijenkorf.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceLocator {
    // an abstraction that provides the necessary service for the resource type requested
    @Autowired
    ImageService image_service;

    private Map service_lookup;

    @Autowired
    public ServiceLocator(ImageService image_service){
        Map service_lookup = new HashMap();
        service_lookup.put("image",image_service);
        this.service_lookup = service_lookup;
    }
    public ResourceService get_service(String resource_type){
        return (ResourceService)service_lookup.get(resource_type);
    }

}
