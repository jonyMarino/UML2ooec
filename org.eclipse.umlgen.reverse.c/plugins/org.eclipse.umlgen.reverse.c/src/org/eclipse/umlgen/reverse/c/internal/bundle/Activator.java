/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Christophe Le Camus (CS-SI) - initial API and implementation
 *      Mikael Barbero (Obeo) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.bundle;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.umlgen.reverse.c.listener.UMLModelChangeListener;
import org.eclipse.umlgen.reverse.c.reconciler.CASTReconciler;
import org.osgi.framework.BundleContext;

/** Activator of the plug-in. */
public class Activator extends Plugin {

    /** The Constant PLUGIN_ID. */
    public static final String PLUGIN_ID = "org.eclipse.umlgen.reverse.c";

    /** The plugin. */
    private static Activator plugin;

    // private ElementChangedListener elementChangedListener;

    /** The reconciler. */
    private CASTReconciler cASTReconciler;

    /** The model change listener. */
    private UMLModelChangeListener uMLModelChangeListener;

    // public ElementChangedListener getElementChangedListener()
    // {
    // return elementChangedListener;
    // }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);

        plugin = this;

        uMLModelChangeListener = new UMLModelChangeListener();
        // elementChangedListener = new ElementChangedListener();

        cASTReconciler = new CASTReconciler();
        cASTReconciler.addModelChangeListener(uMLModelChangeListener);
        // elementChangedListener.setASTReconciler(cASTReconciler);

        // CoreModel.getDefault().addElementChangedListener(elementChangedListener);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        // CoreModel.getDefault().removeElementChangedListener(elementChangedListener);

        // elementChangedListener.setASTReconciler(null);
        cASTReconciler.removeModelChangeListener(uMLModelChangeListener);
        cASTReconciler = null;

        // elementChangedListener = null;
        uMLModelChangeListener.dispose();
        uMLModelChangeListener = null;

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
            status = ((CoreException)e).getStatus();
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
     *            a throwable
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
        return PLUGIN_ID;
    }

    /**
     * Returns the shared instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the singleton
     * @generated
     */
    public static Activator getDefault() {
        return plugin;
    }

}
