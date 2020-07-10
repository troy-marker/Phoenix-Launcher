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

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * The InterfaceSecurityApi interface.
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 */
public interface InterfaceSecurityApi {

    /**
     * Logon call.
     * @param username the username
     * @param password the password
     * @return the call
     */
    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("username") String username,
                       @Field("password") String password);
}
