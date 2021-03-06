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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * This listener will allow us to be aware of contribution changes against the synchronizer extension point.
 */
public class SynchronizerListener implements IRegistryEventListener {

    /** Name of the extension point to parse for extensions. */
    public static final String SYNCHRONIZER_EXTENSION_POINT = "org.eclipse.umlgen.c.common.synchronizers"; //$NON-NLS-1$

    /** Name of the extension point's "Synchronizers" tag. */
    private static final String SYNCHRONIZER_TAG_EXTENSION = "synchronizer"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
     */
    public void added(IExtension[] extensions) {
        for (IExtension extension : extensions) {
            parseExtension(extension);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
     */
    public void added(IExtensionPoint[] extensionPoints) {
        // no need to listen to this event
    }

    /**
     * Though this listener reacts to the extension point changes, there could have been contributions before
     * it's been registered. This will parse these initial contributions.
     */
    public void parseInitialContributions() {
        final IExtensionRegistry registry = Platform.getExtensionRegistry();

        for (IExtension extension : registry.getExtensionPoint(SYNCHRONIZER_EXTENSION_POINT).getExtensions()) {
            parseExtension(extension);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
     */
    public void removed(IExtension[] extensions) {
        for (IExtension extension : extensions) {
            final IConfigurationElement[] configElements = extension.getConfigurationElements();
            for (IConfigurationElement elem : configElements) {
                if (SYNCHRONIZER_TAG_EXTENSION.equals(elem.getName())) {
                    final String extensionClassName = elem
                            .getAttribute(SynchronizerDescriptor.SYNCHRONIZER_ATTRIBUTE_EXTENSION);
                    SynchronizerRegistry.removeExtension(extensionClassName);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
     */
    public void removed(IExtensionPoint[] extensionPoints) {
        // no need to listen to this event
    }

    /**
     * Parses a single extension contribution.
     *
     * @param extension
     *            Parses the given extension and adds its contribution to the registry.
     */
    private void parseExtension(IExtension extension) {
        final IConfigurationElement[] configElements = extension.getConfigurationElements();
        for (IConfigurationElement elem : configElements) {
            if (SYNCHRONIZER_TAG_EXTENSION.equals(elem.getName())) {
                SynchronizerRegistry.addExtension(new SynchronizerDescriptor(elem));
            }
        }
    }
}
