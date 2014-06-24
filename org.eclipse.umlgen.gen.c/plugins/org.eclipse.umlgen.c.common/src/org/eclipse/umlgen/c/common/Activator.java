/*******************************************************************************
 * Copyright (c) 2014 CS Systèmes d'Information (CS-SI) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Fabien Toral (CS-SI) - initial API and implementation
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.c.common;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.umlgen.c.common.interactions.extension.registry.SynchronizerListener;
import org.eclipse.umlgen.c.common.interactions.extension.registry.SynchronizerRegistry;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author <a href="mailto:fabien.toral@c-s.fr">Fabien Toral</a>
 */
public class Activator extends Plugin {

	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.eclipse.umlgen.c.common";

	/**
	 * The plugin context
	 */
	private static BundleContext context;

	/**
	 * Shared plugin instance
	 */
	private static Activator plugin;

	/**
	 * Listener registry for the synchronizers extension
	 */
	private SynchronizerListener registryListener = new SynchronizerListener();

	/**
	 * Return the plugin context.
	 *
	 * @return the plugin context
	 */
	static BundleContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		plugin = this;
		super.start(bundleContext);

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(registryListener, SynchronizerListener.SYNCHRONIZER_EXTENSION_POINT);
		registryListener.parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		Activator.context = null;
		plugin = null;

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeListener(registryListener);
		SynchronizerRegistry.clearRegistry();
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

	/**
	 * Log an exception into the Eclipse log file <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param e
	 *            the exception to log
	 * @generated
	 */
	public static void log(Throwable e) {
		if (e instanceof InvocationTargetException) {
			e = ((InvocationTargetException)e).getTargetException();
		}

		IStatus status = null;
		if (e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			status = new Status(IStatus.ERROR, getId(), IStatus.OK, "Error", e);
		}

		log(status);
	}

	/**
	 * Log an IStatus <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param status
	 *            the status to log
	 * @generated
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	/**
	 * Get the ID of this bundle.
	 * 
	 * @return id.
	 */
	public static String getId() {
		return PLUGIN_ID;
	}

}
