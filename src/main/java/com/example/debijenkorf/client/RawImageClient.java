package com.example.debijenkorf.client;

import com.example.debijenkorf.ResourceRequest;
import org.springframework.stereotype.Component;

@Component
public class RawImageClient {
    public byte[] download_image(ResourceRequest request){
        // mocking the process of downloading new image
        return new byte[20];
    }
}
