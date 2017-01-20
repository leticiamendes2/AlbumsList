package com.sushmobile.albumslist.network;

import com.sushmobile.albumslist.enums.Service;

import java.util.HashMap;

public class RequestSettings {

	private final Service service;
	private final HashMap<String, String> uriParameters;
	private final String uri;
	private String controller;

	public RequestSettings(Service service, HashMap<String, String> uriParameters) {
		super();
		
		this.service = service;
		this.uriParameters = uriParameters;
		this.controller = "";
		this.uri = "http://jsonplaceholder.typicode.com/";

		switch (service) {
			case ALBUMS:
				this.controller = "albums";
				break;
			case USERS:
				this.controller = "users";
				break;
			case PHOTOS:
				this.controller = "photos";
				break;
		default:
			break;
		}
	}
	
	Service getService() {
		return service;
	}
	HashMap<String, String> getUriParameters() {
		return uriParameters;
	}
	String getUri() {
		return uri;
	}
	String getController() {
		return controller;
	}
}