package com.example.debijenkorf.service;

import com.example.debijenkorf.client.AmazonBucketClient;
import com.example.debijenkorf.client.RawImageClient;
import com.example.debijenkorf.ResourceRequest;
import com.example.debijenkorf.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Service
public class ImageService implements ResourceService {
    private static final Logger LOGGER = Logger.getLogger(ImageService.class.getName());
    @Autowired
    RawImageClient raw_image_client;
    @Autowired
    AmazonBucketClient amazon_bucket_client;

    @Override
    public ResponseObject process_request(ResourceRequest request) {
        if (request.get_action().equals("show")){
            ResponseObject response = retrieve_from_storage(request);

            if (response.get_content() == null){
                this.acquire_new_image(request);
                response = retrieve_from_storage(request);
            }

            return response;

        } else if (request.get_action().equals("flush")){
            return this.flush_from_storage(request);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "service action not found"
            );
        }
    }

    @Override
    public ResponseObject  retrieve_from_storage(ResourceRequest request) {
        byte[] image = amazon_bucket_client.get_image(request.get_resource_sub_type(), request.get_resource_name());

        return new ResponseObject("content_retrieved", image);
    }

    public byte[] resize_image(ResourceRequest request, byte[] image) {
        // consults configuration containing properties for this image type
        // and resizes the image accordingly
        return image;
    }
    public void acquire_new_image(ResourceRequest request) {
        // check for original image
        byte[] image = amazon_bucket_client.get_image("original", request.get_resource_name());
        // download image if it's not found in S3
        if (image == null){
            image = raw_image_client.download_image(request);
            if (image == null){
                LOGGER.info("could not download image from source url");
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "could not download image from source url"
                );
            }
        }

        byte[] processed_image = resize_image(request, image);
        amazon_bucket_client.add_image(request, processed_image);
    }

    @Override
    public byte[] download(ResourceRequest request) {
        return raw_image_client.download_image(request);
    }

    @Override
    public ResponseObject flush_from_storage(ResourceRequest request) {
        return amazon_bucket_client.flush(request);
    }
}
