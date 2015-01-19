/*******************************************************************************
 * Copyright (c) 2010, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.builder.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.umlgen.gen.c.builder.UML2CBundleConstant;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
@Deprecated
public class UML2CBuilderBundle extends Plugin {

    /** The shared instance. */
    private static UML2CBuilderBundle plugin;

    /**
     * The constructor.
     */
    public UML2CBuilderBundle() {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Log an exception into the Eclipse log file <!-- begin-user-doc --> <!-- end-user-doc -->.
     *
     * @param e
     *            the exception to log
     * @generated
     */
    public static void log(Throwable e) {
        Throwable e2 = e;
        if (e2 instanceof InvocationTargetException) {
            e2 = ((InvocationTargetException)e).getTargetException();
        }

        IStatus status = null;
        if (e2 instanceof CoreException) {
            status = ((CoreException)e2).getStatus();
        } else {
            status = new Status(IStatus.ERROR, getId(), IStatus.OK, "Error", e);
        }

        log(status);
    }

    /**
     * Log a message with given level into the Eclipse log file <!-- begin-user-doc --> <!-- end-user-doc -->.
     *
     * @param message
     *            the message to log
     * @param level
     *            the message priority
     * @param e
     *            the exception
     * @generated
     */
    public static void log(String message, int level, Throwable e) {
        IStatus status = null;
        status = new Status(level, getId(), IStatus.OK, message, e);
        log(status);
    }

    /**
     * Log a message with given level into the Eclipse log file <!-- begin-user-doc --> <!-- end-user-doc -->.
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
     * Log an IStatus <!-- begin-user-doc --> <!-- end-user-doc -->.
     *
     * @param status
     *            the status to log
     * @generated
     */
    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->.
     *
     * @return the Plugin Id
     * @generated
     */
    public static String getId() {
        return UML2CBundleConstant.PLUGIN_ID;
    }

    /**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
    public static UML2CBuilderBundle getDefault() {
        return plugin;
    }

}
