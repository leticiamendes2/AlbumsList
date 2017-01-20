package com.sushmobile.albumslist.network;

import com.sushmobile.albumslist.enums.Service;

public class ResponseInfo {

    private Service service;
    private String response;

    public ResponseInfo(Service service, String response) {
        this.service = service;
        this.response = response;
    }

    public Service getService() {
        return service;
    }

    public String getResponse() {
        return response;
    }
}
