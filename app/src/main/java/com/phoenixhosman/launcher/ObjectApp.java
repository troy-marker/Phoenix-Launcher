/*
    The Phoenix Hospitality Management System
    Launcher App Source
    ObjectApp type Code File
    Copyright (c) 2020 By Troy Marker Enterprises
    All Rights Under Copyright Reserved

    The code in this file was created for use with the Phoenix Hospitality Management System (PHMS).
    Use of this code outside the PHMS is strictly prohibited.
 */
package com.phoenixhosman.launcher;

class ObjectApp {
    private CharSequence mLabel;
    private CharSequence mName;

    /**
     * Instantiates a new ObjectApp.
     * @param label the app label
     * @param name  the app name
     */
    ObjectApp(CharSequence label, CharSequence name) {
        this.mLabel = label;
        this.mName = name;
    }

    /**
     * Gets the app label.
     * @return the app label
     */
    CharSequence getLabel() {
        return mLabel;
    }

    /**
     * Set Label
     * @param lable the app label
     */
    void setLabel(CharSequence lable) {
        this.mLabel = lable;
    }

    /**
     * Gets the app name.
     * @return the name
     */
    CharSequence getName() {
        return mName;
    }

    /**
     * Sets the app name
     * @param name the app name
     */
    void setName(CharSequence name) {
        this.mName = name;
    }
}
