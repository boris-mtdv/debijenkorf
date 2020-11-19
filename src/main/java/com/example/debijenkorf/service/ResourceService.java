package com.example.debijenkorf.service;

import com.example.debijenkorf.ResourceRequest;
import com.example.debijenkorf.ResponseObject;

public interface ResourceService {
    public abstract ResponseObject process_request(ResourceRequest request);
    public abstract ResponseObject retrieve_from_storage(ResourceRequest request);
    public abstract byte[] download(ResourceRequest request);
    public abstract ResponseObject flush_from_storage(ResourceRequest request);


}
