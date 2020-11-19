package com.example.debijenkorf;

public class ResourceRequest {
    private String resource_type;
    private String resource_sub_type;
    private String resource_name;
    private String action;

    public ResourceRequest(String resource_type, String resource_sub_type, String resource_name, String action){
        this.resource_type = resource_type;
        this.resource_sub_type = resource_sub_type;
        this.resource_name = resource_name;
        this.action = action;

    }

    public String get_resource_type(){
        return resource_type;
    }

    public String get_resource_sub_type(){
        return resource_sub_type;
    }

    public String get_resource_name(){
        return resource_name;
    }

    public String get_action(){
        return action;
    }
}