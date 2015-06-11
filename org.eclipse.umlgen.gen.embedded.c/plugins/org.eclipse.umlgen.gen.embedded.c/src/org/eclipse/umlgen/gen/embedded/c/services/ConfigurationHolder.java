/*******************************************************************************
 * Copyright (c) 2015 Spacebel SA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Johan Hardy (Spacebel) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.umlgen.gen.embedded.c.main.Uml2ec;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * This class contains the properties of the generation.
 */
public class ConfigurationHolder extends AdapterImpl {

    /**
     * The map containing the parameters of the generation.
     */
    private Map<String, Object> configuration = new HashMap<String, Object>();

    /**
     * Puts a new entry into the configuration.
     *
     * @param key
     *            The key of the entry
     * @param value
     *            The value of the entry
     */
    public void put(String key, Object value) {
        this.configuration.put(key, value);
    }

    /**
     * Returns the output folder path.
     *
     * @param eObject
     *            The model element
     * @return The output folder path.
     */
    public static String getOutputFolderPath(EObject eObject) {
        return ConfigurationHolder.getStringValue(eObject, IUML2ECConstants.OUTPUT_FOLDER_PATH);
    }

    /**
     * Indicates if we should generate the traceability.
     *
     * @param eObject
     *            The model element
     * @return <code>true</code> if traceability must be generated, <code>false</code> otherwise.
     */
    public static boolean isTraceabilityEnabled(EObject eObject) {
        return ConfigurationHolder.getBooleanValue(eObject, IUML2ECConstants.GENERATE_TRACEABILITY);
    }

    /**
     * Returns the author.
     *
     * @param eObject
     *            The model element
     * @return The author.
     */
    public static String getAuthor(EObject eObject) {
        return ConfigurationHolder.getStringValue(eObject, IUML2ECConstants.AUTHOR);
    }

    /**
     * Returns the version.
     *
     * @param eObject
     *            The model element
     * @return The version.
     */
    public static String getVersion(EObject eObject) {
        return ConfigurationHolder.getStringValue(eObject, IUML2ECConstants.VERSION);
    }

    /**
     * Returns the copyright.
     *
     * @param eObject
     *            The model element
     * @return The copyright and the license.
     */
    public static String getCopyright(EObject eObject) {
        return ConfigurationHolder.getStringValue(eObject, IUML2ECConstants.COPYRIGHT);
    }

    /**
     * Returns a string value from the given key.
     *
     * @param eObject
     *            The model element
     * @param key
     *            The key
     * @return A string value from the given key.
     */
    private static String getStringValue(EObject eObject, String key) {
        ConfigurationHolder configurationHolder = Uml2ec.getConfigurationHolder();
        if (configurationHolder != null) {
            Object object = configurationHolder.getValue(key);
            if (object instanceof String) {
                return (String)object;
            }
        }
        return "";
    }

    /**
     * Returns a boolean value from the given key.
     *
     * @param eObject
     *            The model element
     * @param key
     *            The key
     * @return A boolean value from the given key.
     */
    private static boolean getBooleanValue(EObject eObject, String key) {
        boolean result = false;
        ConfigurationHolder configurationHolder = Uml2ec.getConfigurationHolder();
        if (configurationHolder != null) {
            Object object = configurationHolder.getValue(key);
            if (object instanceof String) {
                result = Boolean.valueOf((String)object).booleanValue();
            } else if (object instanceof Boolean) {
                result = ((Boolean)object).booleanValue();
            }
        }
        return result;
    }

    /**
     * Returns a list of strings from the given key.
     *
     * @param eObject
     *            The model element
     * @param key
     *            The key
     * @return A list of strings from the given key.
     */
    private static List<String> getStringListValue(EObject eObject, String key) {
        List<String> packages = new ArrayList<String>();
        ConfigurationHolder configurationHolder = Uml2ec.getConfigurationHolder();
        if (configurationHolder != null) {
            Object object = configurationHolder.getValue(key);
            if (object instanceof String) {
                String str = (String)object;

                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    String nextToken = stringTokenizer.nextToken();
                    String trim = nextToken.trim();
                    if (trim.length() > 0) {
                        packages.add(trim);
                    }
                }
            }
        }
        return packages;
    }

    /**
     * Returns a value for the given key.
     *
     * @param key
     *            The key
     * @return A value for the given key.
     */
    public Object getValue(String key) {
        return this.configuration.get(key);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
     */
    @Override
    public boolean isAdapterForType(Object type) {
        return type == ConfigurationHolder.class;
    }

    /**
     * Indicates if we should generate the svn:property $Date$.
     *
     * @param eObject
     *            The model element
     * @return <code>true</code> if svn:property $Date$ must be generated, <code>false</code> otherwise.
     */
    public static boolean isSvnDateEnabled(EObject eObject) {
        return ConfigurationHolder.getBooleanValue(eObject, IUML2ECConstants.GENERATE_SVN_DATE);
    }

    /**
     * Indicates if we should generate the svn:property $Id$.
     *
     * @param eObject
     *            The model element
     * @return <code>true</code> if svn:property $Id$ must be generated, <code>false</code> otherwise.
     */
    public static boolean isSvnIdEnabled(EObject eObject) {
        return ConfigurationHolder.getBooleanValue(eObject, IUML2ECConstants.GENERATE_SVN_ID);
    }

}
