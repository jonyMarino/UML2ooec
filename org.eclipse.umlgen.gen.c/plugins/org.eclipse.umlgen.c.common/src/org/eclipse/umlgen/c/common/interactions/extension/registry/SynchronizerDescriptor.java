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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;

/**
 * Describes an extension as contributed to the "org.eclipse.umlgen.c.common.synchronizers" extension point.
 */
public class SynchronizerDescriptor {
    /** Name of the synchronizer extension point's tag "extension" attribute. */
    public static final String SYNCHRONIZER_ATTRIBUTE_EXTENSION = "extension"; //$NON-NLS-1$

    /** Name of the synchronizer extension point's tag "ranking" attribute. */
    public static final String SYNCHRONIZER_ATTRIBUTE_RANKING = "ranking"; //$NON-NLS-1$

    /** Configuration element of this descriptor. */
    private final IConfigurationElement element;

    /**
     * Qualified class name of the synchronizer extension. This will be used as an id to remove contributions.
     */
    private final String extensionClassName;

    /** The ranking of the synchronizer extension. */
    private final String ranking;

    /** We only need to create the instance once, this will keep reference to it. */
    private IModelSynchronizer extension;

    /**
     * Instantiates a descriptor with all information.
     *
     * @param configuration
     *            Configuration element from which to create this descriptor.
     */
    public SynchronizerDescriptor(IConfigurationElement configuration) {
        element = configuration;
        extensionClassName = configuration.getAttribute(SYNCHRONIZER_ATTRIBUTE_EXTENSION);
        ranking = configuration.getAttribute(SYNCHRONIZER_ATTRIBUTE_RANKING);
    }

    /**
     * Returns this descriptor's "extension" class name.
     *
     * @return This descriptor's "extension" class name.
     */
    public String getExtensionClassName() {
        return extensionClassName;
    }

    /**
     * Returns this descriptor's "ranking" class name.
     *
     * @return This descriptor's "ranking" class name.
     */
    public String getRanking() {
        return ranking;
    }

    /**
     * Creates an instance of this descriptor's {@link IModelSynchronizer}.
     *
     * @return A new instance of this descriptor's {@link IModelSynchronizer}.
     */
    public IModelSynchronizer getSynchronizerExtension() {
        if (extension == null) {
            try {
                extension = (IModelSynchronizer)element
                        .createExecutableExtension(SYNCHRONIZER_ATTRIBUTE_EXTENSION);
            } catch (CoreException e) {
                // FIXME log this!
            }
        }
        return extension;
    }
}
