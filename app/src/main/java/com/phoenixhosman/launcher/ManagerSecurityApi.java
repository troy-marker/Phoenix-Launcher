package com.phoenixhosman.launcher;

import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * The type ManagerLauncherApi.
 */
class ManagerSecurityApi {
    private static InterfaceSecurityApi service;
    private static ManagerSecurityApi managerSecurityApi;
    private static String ApiUrl;

    /**
     * Instantiates a new ManagerSecurityApi.
     *
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

    @SuppressWarnings("unused")
    static synchronized ManagerSecurityApi getInstance() {
        if (managerSecurityApi == null) {
            managerSecurityApi = new ManagerSecurityApi(ApiUrl);
        }
        return managerSecurityApi;
    }

    @SuppressWarnings("unused")
    InterfaceSecurityApi getApi() {
        return service;
    }

}
