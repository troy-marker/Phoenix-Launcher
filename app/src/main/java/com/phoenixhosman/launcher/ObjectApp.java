package com.phoenixhosman.launcher;

/**
 * The type ObjectApp.
 */
class ObjectApp {
    private final CharSequence label;
    private final CharSequence name;

    /**
     * Instantiates a new ObjectApp.
     *
     * @param label the label
     * @param name  the name
     */
    ObjectApp(CharSequence label, CharSequence name) {
        this.label = label;
        this.name = name;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    CharSequence getLabel() {
        return label;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    CharSequence getName() {
        return name;
    }
}
