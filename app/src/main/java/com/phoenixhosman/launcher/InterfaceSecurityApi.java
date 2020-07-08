package com.phoenixhosman.launcher;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *
 * The InterfaceSecurityApi interface.
 *
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 *
 */
public interface InterfaceSecurityApi {

    /**
     *
     * Logon call.
     *
     * @param username the username
     * @param password the password
     * @return the call
     *
     */
    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("username") String username,
                       @Field("password") String password);
}
