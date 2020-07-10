/*
    The Phoenix Hospitality Management System
    Launcher App Source Code
    Security API Interface Code File
    Copyright (c) 2020 By Troy Marker Enterprises
    All Rights Under Copyright Reserved

    The code in this file was created for use with the Phoenix Hospitality Management System (PHMS).
    Use of this code outside the PHMS is strictly prohibited.
 */
package com.phoenixhosman.launcher;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * The ManagerSecurityApi manager.
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 */
class ManagerSecurityApi {
    private static InterfaceSecurityApi service;
    private static ManagerSecurityApi managerSecurityApi;
    private static String ApiUrl;

    /**
     * Instantiates a new ManagerSecurityApi.
     * @param strApiUrl the strApiUrl
     */
    ManagerSecurityApi(String strApiUrl) {
        ApiUrl = strApiUrl;
        String BASE_URL = ApiUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        service = retrofit.create(InterfaceSecurityApi.class);
    }

    /**
     * Method to return the ManagerSecurityApi instance
     * @return ManagerSecurityApi
     */
    static synchronized ManagerSecurityApi getInstance() {
        if (managerSecurityApi == null) {
            managerSecurityApi = new ManagerSecurityApi(ApiUrl);
        }
        return managerSecurityApi;
    }

    /**
     * Method to return the Api service
     * @return InterfaceSecurityApi
     */
    InterfaceSecurityApi getApi() {
        return service;
    }

}
