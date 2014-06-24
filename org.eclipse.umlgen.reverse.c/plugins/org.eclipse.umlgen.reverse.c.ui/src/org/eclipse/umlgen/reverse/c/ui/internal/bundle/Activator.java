/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.bundle;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	// The plug-in ID
	/** The Constant PLUGIN_ID. */
	public static final String PLUGIN_ID = "org.eclipse.umlgen.reverse.c.ui";

	// The shared instance
	/** The plugin. */
	private static Activator plugin;

	public void start(BundleContext context) throws Exception {
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
	}

	/**
	 * Log an exception into the Eclipse log file <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param e
	 *            the exception to log
	 * @generated
	 */
	public static void log(Throwable e) {
		if (e instanceof InvocationTargetException) {
			e = ((InvocationTargetException) e).getTargetException();
		}

		IStatus status = null;
		if (e instanceof CoreException) {
			status = ((CoreException) e).getStatus();
		} else {
			status = new Status(IStatus.ERROR, getId(), IStatus.OK, "Error", e);
		}

		log(status);
	}

	/**
	 * Log a message with given level into the Eclipse log file <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param message
	 *            the message to log
	 * @param level
	 *            the message priority
	 * @generated
	 */
	public static void log(String message, int level) {
		IStatus status = null;
		status = new Status(level, getId(), IStatus.OK, message, null);
		log(status);
	}

	/**
	 * Log a message with given level into the Eclipse log file <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param message
	 *            the message to log
	 * @param level
	 *            the message priority
	 * @generated
	 */
	public static void log(String message, int level, Throwable e) {
		IStatus status = null;
		status = new Status(level, getId(), IStatus.OK, message, e);
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
		ResourcesPlugin.getPlugin().getLog().log(status);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the Plugin Id
	 * @generated
	 */
	public static String getId() {
		return PLUGIN_ID;
	}

	/**
	 * Returns the shared instance. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the singleton
	 * @generated
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
