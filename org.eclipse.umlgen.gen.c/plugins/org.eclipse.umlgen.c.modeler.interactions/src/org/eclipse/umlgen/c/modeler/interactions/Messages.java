/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.modeler.interactions;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages.
 */
public final class Messages {

    /** The bundle name. */
    private static final String BUNDLE_NAME = "org.eclipse.umlgen.c.modeler.interactions.messages"; //$NON-NLS-1$

    /** The resource bundle. */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /** Default constructor. */
    private Messages() {
    }

    /**
     * Get the message from its key.
     * 
     * @param key
     *            the key.
     * @return the message.
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
