package com.example.debijenkorf;

public class ResponseObject {
    private String message;
    private byte[] content;

    public ResponseObject(String message, byte[] content){
        this.message = message;
        this.content = content;

    }

    public String get_message(){
        return message;
    }

    public byte[] get_content(){
        return content;
    }

}
