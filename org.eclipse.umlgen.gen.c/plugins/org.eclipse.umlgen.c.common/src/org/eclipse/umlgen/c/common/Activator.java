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

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:fabien.toral@c-s.fr">Fabien Toral</a>
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plugin context 
	 */
	private static BundleContext context;
	
	/**
	 * Shared plugin instance
	 */
	private static Activator plugin;

	/**
	 * Return the plugin context.
	 * @return the plugin context
	 */
	static BundleContext getContext() {
		return context;
	}

	/**{@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		plugin = this;
	}

	/**{@inheritDoc}
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		plugin = null;
	}
	
	/**
	 * Get the plugin bundle symbolic name.
	 * 
	 * @return the plugin name
	 */
	public static String getBundleId() {
		return context.getBundle().getSymbolicName();
	}
	
	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	

}
