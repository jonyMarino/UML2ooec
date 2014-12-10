/*******************************************************************************
 * Copyright (c) 2014 CS Systèmes d'Information (CS-SI) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Fabien Toral (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.common;

import org.eclipse.osgi.util.NLS;

/**
 * Messages.
 */
public final class Messages extends NLS {
    /** Bundle name. */
    private static final String BUNDLE_NAME = "org.eclipse.umlgen.c.common.messages"; //$NON-NLS-1$

    /** model manager. */
    private static String modelManager;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    /** Default constructor. */
    private Messages() {
    }

    public static String getModelManager() {
        return modelManager;
    }

}
