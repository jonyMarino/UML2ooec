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
package org.eclipse.umlgen.c.common.interactions.extension.registry;

import java.util.ArrayList;
import java.util.List;

/**
 * This will contain all synchronizers extension that have been parsed from the extension point.
 */
public final class SynchronizerRegistry {
    /** List of extensions created from the extension point contributions. */
    private static final List<SynchronizerDescriptor> EXTENSIONS = new ArrayList<SynchronizerDescriptor>();

    /**
     * Utility classes don't need a default constructor.
     */
    private SynchronizerRegistry() {
        // hides constructor
    }

    /**
     * Adds an extension to the registry.
     *
     * @param extension
     *            The extension that is to be added to the registry.
     */
    public static void addExtension(SynchronizerDescriptor extension) {
        EXTENSIONS.add(extension);
    }

    /**
     * Removes all extensions from the registry. This will be called at plugin stopping.
     */
    public static void clearRegistry() {
        EXTENSIONS.clear();
    }

    /**
     * Returns a copy of the registered extensions list.
     *
     * @return A copy of the registered extensions list.
     */
    public static List<SynchronizerDescriptor> getRegisteredExtensions() {
        return new ArrayList<SynchronizerDescriptor>(EXTENSIONS);
    }

    /**
     * Removes a phantom from the registry.
     *
     * @param extensionClassName
     *            Qualified class name of the sync element which corresponding phantom is to be removed from
     *            the registry.
     */
    public static void removeExtension(String extensionClassName) {
        for (SynchronizerDescriptor extension : getRegisteredExtensions()) {
            if (extension.getExtensionClassName().equals(extensionClassName)) {
                EXTENSIONS.remove(extension);
            }
        }
    }
}
