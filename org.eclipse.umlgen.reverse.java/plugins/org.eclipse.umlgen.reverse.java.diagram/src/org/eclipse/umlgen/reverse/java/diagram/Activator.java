/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    JF Rolland (Atos) - initial API and implementation
 *    P Roland (Atos) philippe.roland@atos.net
 *    F Vivares (Atos) florence.vivares@atos.net
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "org.eclipse.umlgen.reverse.java.diagram"; //$NON-NLS-1$

    /** The deleted image ID. */
    public static final String DELETED_DECORATOR_ID = "org.eclipse.umlgen.reverse.java.diagram.deleted_decorator";

    /** The deleted image ID. */
    public static final String ADDED_DECORATOR_ID = "org.eclipse.umlgen.reverse.java.diagram.added_decorator";

    /** The shared instance. */
    private static Activator plugin;

    /** The constructor. */
    public Activator() {
    }

    /**
     * (non-Javadoc).
     *
     * @param context
     * @throws Exception
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /**
     * (non-Javadoc).
     *
     * @param context
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     * @throws Exception
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        super.initializeImageRegistry(reg);
        Bundle bundle = Platform.getBundle(PLUGIN_ID);

        ImageDescriptor deletedImage = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(
                "icons/Delete_16x16.gif"), null));
        ImageDescriptor addedImage = ImageDescriptor.createFromURL(FileLocator.find(bundle, new Path(
                "icons/Add_blue_16x16.png"), null));
        reg.put(DELETED_DECORATOR_ID, deletedImage);
        reg.put(ADDED_DECORATOR_ID, addedImage);
    }

}
