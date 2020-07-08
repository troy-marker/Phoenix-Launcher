package com.phoenixhosman.launcher;

/**
 * The type Object settings.
 */
@SuppressWarnings("ALL")
class ObjectSettings {
    private String mCoName;
    private String mCoAddress;
    private String mCoCity;
    private String mCoState;
    private String mCoZip;
    private String mApiUrl;
    private String mLockPass;

    /**
     * Sets company name.
     *
     * @param mCoName the company name
     */
    void setCoName (String mCoName) {
        this.mCoName = mCoName;
    }

    /**
     * Sets company address.
     *
     * @param mCoAddress the company address
     */
    void setCoAddress (String mCoAddress) {
        this.mCoAddress = mCoAddress;
    }

    /**
     * Sets company city.
     *
     * @param mCoCity the company city
     */
    void setCoCity (String mCoCity) {
        this.mCoCity = mCoCity;
    }

    /**
     * Sets company state.
     *
     * @param mCoState the company state
     */
    void setCoState (String mCoState) {
        this.mCoState = mCoState;
    }

    /**
     * Sets company zip.
     *
     * @param mCoZip the company zip
     */
    void setCoZip (String mCoZip) {
        this.mCoZip = mCoZip;
    }

    /**
     * Sets api url.
     *
     * @param mApiUrl the API URL
     */
    void setApiUrl (String mApiUrl) {
        this.mApiUrl = mApiUrl;
    }

    /**
     * Sets Lock Password
     *
     * @param mLockPass The lock Passeword
     */
    void setLockPass (String mLockPass) { this.mLockPass = mLockPass; }

    /**
     * Gets company name.
     *
     * @return the company name
     */
    String getCoName () {
        return mCoName;
    }

    /**
     * Gets company address.
     *
     * @return the company address
     */
    String getCoAddress () {
        return mCoAddress;
    }

    /**
     * Gets company city.
     *
     * @return the company city
     */
    String getCoCity () {
        return mCoCity;
    }

    /**
     * Gets company state.
     *
     * @return the company state
     */
    String getCoState () {
        return mCoState;
    }

    /**
     * Gets company zip.
     *
     * @return the company zip
     */
    String getCoZip () {
        return mCoZip;
    }

    /**
     * Gets api url.
     *
     * @return the api url
     */
    String getApiUrl () {
        return mApiUrl;
    }

    /**
     * Gets the Lock Password
     *
     * @return the lock password
     */
    String getLockPass () { return mLockPass; }
}
