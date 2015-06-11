/*******************************************************************************
 * Copyright (c) 2015 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *     Johan Hardy (Spacebel) - evolution and adaptation for Embedded C generator
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.common;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.umlgen.gen.embedded.c.services.ConfigurationHolder;
import org.eclipse.umlgen.gen.embedded.c.ui.UML2ECUIActivator;
import org.eclipse.umlgen.gen.embedded.c.ui.launch.IUML2ECUIConstants;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * Utility class to manage launch configurations from menu and properties page. The implementation of this
 * class has been re-used from org.eclipse.umlgen.gen.java.ui. The name of the property key and its value plus
 * the body of the method <b>createConfigurationHolder</b> of the class have been adapted for the sake of the
 * UML to Embedded C generation.
 */
public final class ConfigurationServices {

    /**
     * The properties key to store the selected launch configuration on the current element, for Embedded C
     * generation.
     */
    public static final String PROP_KEY_GEN_EMBEDDED_C_LAUNCH_CONFIG_NAME = "UML2EC_LaunchConfig";

    /** Empty Constructor. */
    private ConfigurationServices() {
    }

    /**
     * Get the stored launch configurations for the current UML model, for Embedded C generation.
     *
     * @return ILaunchConfiguration[]
     */
    public static ILaunchConfiguration[] getStoredEmbeddedCGenerationLaunchConfigurations() {
        ILaunchConfigurationType configurationType = DebugPlugin.getDefault().getLaunchManager()
                .getLaunchConfigurationType(IUML2ECUIConstants.LAUNCH_CONFIGURATION_TYPE);
        try {
            // Get the launch configurations related to a Embedded C generation.
            return DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configurationType);
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }
        return null;
    }

    /**
     * Get the stored launched configuration identified by the given name.
     *
     * @param name
     *            The name of the launch configuration to find.
     * @return The launch configuration.
     */
    public static ILaunchConfiguration getStoredLaunchConfiguration(String name) {
        ILaunchConfiguration[] configs = getStoredEmbeddedCGenerationLaunchConfigurations();
        if (configs != null) {
            for (ILaunchConfiguration config : configs) {
                if (name.equals(config.getName())) {
                    return config;
                }
            }
        }
        return null;
    }

    /**
     * Get the saved launch configuration property name.
     *
     * @param element
     *            The resource element containing the property.
     * @return The configuration name.
     */
    public static String getConfigurationProperty(IResource element) {
        String result = null;
        try {
            result = element.getPersistentProperty(new QualifiedName("",
                    ConfigurationServices.PROP_KEY_GEN_EMBEDDED_C_LAUNCH_CONFIG_NAME));
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    /**
     * Save the given launch configuration property name.
     *
     * @param element
     *            The resource element containing the property.
     * @param name
     *            The configuration name.
     */
    public static void saveConfigurationProperty(IResource element, String name) {
        try {
            element.setPersistentProperty(new QualifiedName("",
                    ConfigurationServices.PROP_KEY_GEN_EMBEDDED_C_LAUNCH_CONFIG_NAME), name);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the launch group.
     *
     * @return The launch group.
     */
    public static ILaunchGroup getLaunchGroup() {
        ILaunchGroup[] launchGroups = DebugUITools.getLaunchGroups();
        for (ILaunchGroup iLaunchGroup : launchGroups) {
            if ("org.eclipse.debug.ui.launchGroup.run".equals(iLaunchGroup.getIdentifier())) {
                return iLaunchGroup;
            }
        }
        return null;
    }

    /**
     * Creates the configuration holder from the launch configuration.
     *
     * @param configuration
     *            The launch configuration
     * @return The configuration holder from the launch configuration.
     */
    public static ConfigurationHolder createConfigurationHolder(ILaunchConfiguration configuration) {
        ConfigurationHolder configurationHolder = new ConfigurationHolder();

        try {
            // General
            configurationHolder.put(IUML2ECConstants.UML_MODEL_PATH, configuration.getAttribute(
                    IUML2ECConstants.UML_MODEL_PATH, ""));
            configurationHolder.put(IUML2ECConstants.OUTPUT_FOLDER_PATH, configuration.getAttribute(
                    IUML2ECConstants.OUTPUT_FOLDER_PATH, ""));

            // Documentation
            configurationHolder.put(IUML2ECConstants.AUTHOR, configuration.getAttribute(
                    IUML2ECConstants.AUTHOR, ""));
            configurationHolder.put(IUML2ECConstants.VERSION, configuration.getAttribute(
                    IUML2ECConstants.VERSION, ""));
            configurationHolder.put(IUML2ECConstants.COPYRIGHT, configuration.getAttribute(
                    IUML2ECConstants.COPYRIGHT, ""));
            configurationHolder.put(IUML2ECConstants.GENERATE_TRACEABILITY, configuration.getAttribute(
                    IUML2ECConstants.GENERATE_TRACEABILITY, false));
            configurationHolder.put(IUML2ECConstants.GENERATE_SVN_DATE, configuration.getAttribute(
                    IUML2ECConstants.GENERATE_SVN_DATE, false));
            configurationHolder.put(IUML2ECConstants.GENERATE_SVN_ID, configuration.getAttribute(
                    IUML2ECConstants.GENERATE_SVN_ID, false));
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }

        return configurationHolder;
    }

}
