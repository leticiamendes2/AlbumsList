package com.sushmobile.albumslist.interfaces;

import com.sushmobile.albumslist.network.ResponseInfo;

public interface AsyncResponse {
	void processFinish(ResponseInfo result);
}