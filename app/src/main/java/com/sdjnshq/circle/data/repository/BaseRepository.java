package com.sdjnshq.circle.data.repository;

import com.sdjnshq.circle.data.http.APIManager;
import com.sdjnshq.circle.data.http.api.API;

public class BaseRepository {

    protected API apiService;


    public BaseRepository() {
        if (null == apiService) {
            apiService = APIManager.getAPI();
        }
    }


}
