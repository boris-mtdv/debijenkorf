package com.example.debijenkorf.client;

import com.example.debijenkorf.ResourceRequest;
import com.example.debijenkorf.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AmazonBucketClient {
    // simulating access to S3 Bucket

    private Map image_map;

    @Autowired
    public AmazonBucketClient(){
        Map image_map = new HashMap<String, HashMap<String, byte[]>>();
        Map thumbnail_map = new HashMap<String, byte[]>();

        thumbnail_map.put("first_image.jpg", new byte[20]);
        thumbnail_map.put("second_image.jpg", new byte[20]);
        thumbnail_map.put("third_image.jpg", new byte[20]);
        thumbnail_map.put("test.jpg", new byte[20]);

        Map original_map = new HashMap<String, byte[]>();
        original_map.put("test.jpg", new byte[20]);
        image_map.put("thumbnail", thumbnail_map);
        image_map.put("original", original_map);

        this.image_map = image_map;
    }
    public byte[] get_image(String type, String name){
        Map type_map = (Map) image_map.get(type);
        return (byte[]) type_map.get(name);
    }

    public void add_image(ResourceRequest request, byte[] image){
        Map type_map = (Map) image_map.get(request.get_resource_sub_type());
        if (type_map == null){
            Map new_type_map = new HashMap();
            new_type_map.put(request.get_resource_name(), image);
            image_map.put(request.get_resource_sub_type(), new_type_map);
        } else {
            type_map.put(request.get_resource_name(), image);
            image_map.put(request.get_resource_sub_type(), type_map);
        }
    }

    public ResponseObject flush(ResourceRequest request){
        if (! request.get_resource_sub_type().equals("original")) {
            HashMap<String, byte[]> type_map = (HashMap<String, byte[]>) image_map.get(request.get_resource_sub_type());
            if (type_map != null && type_map.containsKey(request.get_resource_name())) {
                type_map.remove(request.get_resource_name());
                image_map.put(request.get_resource_sub_type(), type_map);
                ResponseObject response = new ResponseObject("image flushed", null);
            } else {
                // remove original and all optimized images
                for (Object entry : image_map.keySet()) {
                    HashMap<String, byte[]> sub_map = (HashMap<String, byte[]>)image_map.get((String)entry);
                    sub_map.remove(request.get_resource_name());
                }
            }
        }
        return new ResponseObject("image removed", null);
    }
    public Map get_image_map(){
        return image_map;
    }
}
