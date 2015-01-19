/*******************************************************************************
 * Copyright (c) 2007, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Topcased contributors and others - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;

/**
 * The stored properties used in the property page to configure the generation.
 */
@Deprecated
// No use ?
public final class CodeGenerationProperties {

    /** Key for property "Generate author". */
    private static final QualifiedName GEN_AUTHOR_PROPERTY = new QualifiedName("", "PROPERTY_GEN_AUTHOR");

    /** Key for property "Generate header". */
    private static final QualifiedName GEN_HEADER_PROPERTY = new QualifiedName("", "PROPERTY_GEN_HEADER");

    /** Key for property "Generate Accessors". */
    private static final QualifiedName GEN_ACCESSORS_PROPERTY = new QualifiedName("",
            "PROPERTY_GEN_ACCESSORS");

    /** Key for property "Generate add/remove item for arrays". */
    private static final QualifiedName GEN_ARRAY_METHODS_PROPERTY = new QualifiedName("",
            "PROPERTY_GEN_ARRAY_METHODS");

    /** Key for property "Generate <Main> method". */
    private static final QualifiedName GEN_MAIN_PROPERTY = new QualifiedName("", "PROPERTY_GEN_MAIN");

    /** Key for property "Generate code conform to coding style". */
    private static final QualifiedName USE_CHKST_PROPERTY = new QualifiedName("", "PROPERTY_USE_CHKST");

    /** Key for property "Author Name". */
    private static final QualifiedName AUTHOR_NAME_PROPERTY = new QualifiedName("", "PROPERTY_AUTHOR_NAME");

    /** Key for property "Header". */
    private static final QualifiedName HEADER_TEXT_PROPERTY = new QualifiedName("", "PROPERTY_HEADER_TEXT");

    /** Key for property "Output Folder". */
    private static final QualifiedName OUTPUT_PATH_PROPERTY = new QualifiedName("", "PROPERTY_OUTPUT_PATH");

    /** Key for property "Make all static". */
    private static final QualifiedName FORCE_STATIC_PROPERTY = new QualifiedName("", "PROPERTY_FORCE_STATIC");

    /** Key for property "Stop if warning". */
    private static final QualifiedName STOP_IF_WARNING_PROPERTY = new QualifiedName("",
            "STOP_IF_WARNING_PROPERTY");

    /* String property "Output Folder" */

    /** Default Constructor. */
    private CodeGenerationProperties() {
    }

    /**
     * Returns the default value for the property "Output Folder".
     *
     * @param model
     *            The model.
     * @return the default value for the property "Output Folder"
     */
    protected static String getDefaultOutputPathProperty(IResource model) {
        String s;
        IPath modelPath = model.getLocation();
        modelPath = modelPath.removeLastSegments(1);
        IPath workspacePath = model.getWorkspace().getRoot().getLocation();
        if (modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice() != null) {
            s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString().substring(
                    modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice().toString()
                            .length());
        } else {
            s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString();
        }
        return '/' + s;
    }

    /**
     * Returns the value of the property "Output Folder" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Output Folder"
     * @throws CoreException
     *             exception.
     */
    public static String getOutputPathProperty(IResource model) throws CoreException {
        String value = model.getPersistentProperty(OUTPUT_PATH_PROPERTY);
        if (value == null) {
            value = getDefaultOutputPathProperty(model);
        }
        return value;
    }

    /**
     * Sets the value of the property "Output Folder" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setOutputPathProperty(IResource model, String value) throws CoreException {
        model.setPersistentProperty(OUTPUT_PATH_PROPERTY, value);
    }

    /* String property "Author Name" */

    /**
     * Returns the default value for the property "Author Name".
     *
     * @param model
     * @return the default value for the property "Author Name"
     */
    protected static String getDefaultAuthorNameProperty() {
        return "your_name_here";
    }

    /**
     * Returns the value of the property "Author Name" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Author Name"
     * @throws CoreException
     *             exception
     */
    public static String getAuthorNameProperty(IResource model) throws CoreException {
        String value = model.getPersistentProperty(AUTHOR_NAME_PROPERTY);
        if (value == null) {
            value = getDefaultAuthorNameProperty();
        }
        return value;
    }

    /**
     * Sets the value of the property "Author Name" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setAuthorNameProperty(IResource model, String value) throws CoreException {
        model.setPersistentProperty(AUTHOR_NAME_PROPERTY, value);
    }

    /* String property "Header" */

    /**
     * Returns the default value for the property "Header".
     *
     * @param model
     * @return the default value for the property "Header"
     */
    protected static String getDefaultHeaderProperty() {
        StringBuffer sbHeader = new StringBuffer(
                "/*******************************************************************************\n");
        sbHeader.append(" * Copyright (c) XXXX TODO and others. All rights reserved. This program\n");
        sbHeader.append(" * and the accompanying materials are made available under the terms of the\n");
        sbHeader.append(" * Eclipse Public License v1.0 which accompanies this distribution, and is\n");
        sbHeader.append(" * available at http://www.eclipse.org/legal/epl-v10.html\n");
        sbHeader.append(" *\n");
        sbHeader.append(" * Contributors: TODO - initial API and implementation\n");
        sbHeader.append("*******************************************************************************/\n");
        return sbHeader.toString();
    }

    /**
     * Returns the value of the property "Header" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Header"
     * @throws CoreException
     *             exception
     */
    public static String getHeaderProperty(IResource model) throws CoreException {
        String value = model.getPersistentProperty(HEADER_TEXT_PROPERTY);
        if (value == null) {
            value = getDefaultHeaderProperty();
        }
        return value;
    }

    /**
     * Sets the value of the property "Header" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setHeaderProperty(IResource model, String value) throws CoreException {
        model.setPersistentProperty(HEADER_TEXT_PROPERTY, value);
    }

    /* Boolean property "Generate author" */

    /**
     * Returns the default value for the property "Generate author".
     *
     * @param model
     * @return the default value for the property "Generate author"
     */
    protected static boolean getDefaultGenAuthorProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Generate author" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate author"
     * @throws CoreException
     *             exception
     */
    public static boolean getGenAuthorProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(GEN_AUTHOR_PROPERTY);
        if (stringValue == null) {
            value = getDefaultGenAuthorProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate author" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setGenAuthorProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(GEN_AUTHOR_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Generate header" */

    /**
     * Returns the default value for the property "Generate header".
     *
     * @param model
     * @return the default value for the property "Generate header"
     */
    protected static boolean getDefaultGenHeaderProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Generate header" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate header"
     * @throws CoreException
     *             exception
     */
    public static boolean getGenHeaderProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(GEN_HEADER_PROPERTY);
        if (stringValue == null) {
            value = getDefaultGenHeaderProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate header" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setGenHeaderProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(GEN_HEADER_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Generate accessors" */

    /**
     * Returns the default value for the property "Generate accessors".
     *
     * @param model
     * @return the default value for the property "Generate accessors"
     */
    protected static boolean getDefaultGenAccessorsProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Generate accessors" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate accessors"
     * @throws CoreException
     *             exception
     */
    public static boolean getGenAccessorsProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(GEN_ACCESSORS_PROPERTY);
        if (stringValue == null) {
            value = getDefaultGenAccessorsProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate accessors" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setGenAccessorsProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(GEN_ACCESSORS_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Generate add/remove item for arrays" */

    /**
     * Returns the default value for the property "Generate add/remove item for arrays".
     *
     * @param model
     * @return the default value for the property "Generate add/remove item for arrays"
     */
    protected static boolean getDefaultGenArrayMethodsProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Generate add/remove item for arrays" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate add/remove item for arrays""
     * @throws CoreException
     *             exception
     */
    public static boolean getGenArrayMethodsProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(GEN_ARRAY_METHODS_PROPERTY);
        if (stringValue == null) {
            value = getDefaultGenArrayMethodsProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate add/remove item for arrays" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setGenArrayMethodsProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(GEN_ARRAY_METHODS_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Generate <Main> method" */

    /**
     * Returns the default value for the property "Generate <Main> method".
     *
     * @param model
     * @return the default value for the property "Generate <Main> method"
     */
    protected static boolean getDefaultGenMainProperty() {
        return false;
    }

    /**
     * Returns the value of the property "Generate <Main> method" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate <Main> method""
     * @throws CoreException
     *             exception
     */
    public static boolean getGenMainProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(GEN_MAIN_PROPERTY);
        if (stringValue == null) {
            value = getDefaultGenMainProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate <Main> method" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setGenMainProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(GEN_MAIN_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Generate code conform to coding style" */

    /**
     * Returns the default value for the property "Generate code conform to coding style".
     *
     * @param model
     * @return the default value for the property "Generate code conform to coding style"
     */
    protected static boolean getDefaultUseChkstProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Generate code conform to coding style" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Generate code conform to coding style""
     * @throws CoreException
     *             exception
     */
    public static boolean getUseChkstProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(USE_CHKST_PROPERTY);
        if (stringValue == null) {
            value = getDefaultUseChkstProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Generate code conform to coding style" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setUseChkstProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(USE_CHKST_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Make all static" */

    /**
     * Returns the default value for the property "Make all static".
     *
     * @param model
     * @return the default value for the property "Make all static"
     */
    protected static boolean getDefaultForceStaticProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Make all static" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Make all static""
     * @throws CoreException
     *             exception
     */
    public static boolean getForceStaticProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(FORCE_STATIC_PROPERTY);
        if (stringValue == null) {
            value = getDefaultForceStaticProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Make all static" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setForceStaticProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(FORCE_STATIC_PROPERTY, Boolean.toString(value));
    }

    /* Boolean property "Stop if warning" */

    /**
     * Returns the default value for the property "Stop if warning".
     *
     * @param model
     * @return the default value for the property "Stop if warning"
     */
    protected static boolean getDefaultStopIfWarningProperty() {
        return true;
    }

    /**
     * Returns the value of the property "Stop if warning" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @return the value of the property "Stop if warning""
     * @throws CoreException
     *             exception
     */
    public static boolean getStopIfWarningProperty(IResource model) throws CoreException {
        boolean value;
        String stringValue = model.getPersistentProperty(STOP_IF_WARNING_PROPERTY);
        if (stringValue == null) {
            value = getDefaultStopIfWarningProperty();
        } else if (stringValue.equals(Boolean.TRUE.toString())) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    /**
     * Sets the value of the property "Stop if warning" for this <code>model</code>.
     *
     * @param model
     *            The model.
     * @param value
     *            The value.
     * @throws CoreException
     *             exception
     */
    protected static void setStopIfWarningProperty(IResource model, boolean value) throws CoreException {
        model.setPersistentProperty(STOP_IF_WARNING_PROPERTY, Boolean.toString(value));
    }
}
