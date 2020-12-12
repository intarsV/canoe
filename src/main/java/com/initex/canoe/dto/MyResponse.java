package com.initex.canoe.dto;

public class MyResponse {

    private Object object;

    public MyResponse(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
