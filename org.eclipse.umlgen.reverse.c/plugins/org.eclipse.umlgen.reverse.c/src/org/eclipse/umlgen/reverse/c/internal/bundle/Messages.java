/*******************************************************************************
 * Copyright (c) 2011, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.bundle;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** Messages for C reverse. */
public final class Messages {

    /** Constant for the bundle name. */
    private static final String BUNDLE_NAME = "org.eclipse.umlgen.reverse.c.internal.bundle.messages"; //$NON-NLS-1$

    /** Resource bundle. */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /** Constructor. */
    private Messages() {
    }

    /**
     * Get the string from the given key.
     *
     * @param key
     *            The key.
     * @return The message.
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
