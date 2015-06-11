/*******************************************************************************
 * Copyright (c) 2015 Spacebel SA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Johan Hardy (Spacebel) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class to access externalized Strings throughout the Acceleo user interface.
 */
public final class Messages {

    /** Qualified path to the properties file in which to seek the keys. */
    private static final String BUNDLE_NAME = "org.eclipse.umlgen.embedded.c.messages"; //$NON-NLS-1$

    /** Contains the locale specific {@link String}s needed by this plug-in. */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Utility classes don't need to (and shouldn't) be instantiated.
     */
    private Messages() {
    }

    /**
     * This will return an unformatted String from the resource bundle.
     * 
     * @param key
     *            Key of the String we seek.
     * @return An unformatted String from the bundle.
     */
    private static String internalGetString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "Error: Unable to find key " + key;
        }
    }

    /**
     * Returns the specified {@link String} from the resource bundle.
     * 
     * @param key
     *            Key of the String we seek.
     * @return The String from the resource bundle associated with <code>key</code>.
     *         <code>"Error: Unable to find key " + key </code> will be returned in case we didn't find it in
     *         the bundle.
     */
    public static String getString(String key) {
        return MessageFormat.format(internalGetString(key), new Object[] {});
    }

    /**
     * Returns a String from the resource bundle bound with the given arguments.
     * 
     * @param key
     *            Key of the String we seek.
     * @param arguments
     *            Arguments for the String formatting.
     * @return formatted {@link String}.
     * @see MessageFormat#format(String, Object[])
     */
    public static String getString(String key, Object... arguments) {
        if (arguments == null) {
            return getString(key);
        }
        return MessageFormat.format(internalGetString(key), arguments);
    }
}
