/*******************************************************************************
 * Copyright (c) 2005,2008 AIRBUS FRANCE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jacques LESCOT (Anyware Technologies) - initial API and implementation
 *    Thibault Landré - Fix #889
 *******************************************************************************/
package org.eclipse.umlgen.c.modeler.interactions.templates;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Class that describes a Template.
 */
public class TemplateDescriptor {

    // Constants

    /** The template tag. */
    public static final String TAG_TEMPLATE = "template";

    /** The id attribut. */
    public static final String ATT_ID = "id";

    /** The model attribut. */
    public static final String ATT_MODEL = "model";

    // Values
    /** The id. */
    private String id;

    /** The model. */
    private String model;

    /** The configuration element. */
    private IConfigurationElement configElement;

    /**
     * Initialize the descriptor from the XML fragment of the extension.
     *
     * @param element
     *            The XML Fragment that describes the Template
     * @throws CoreException
     *             if the xml fragment is not valid
     */
    TemplateDescriptor(IConfigurationElement element) throws CoreException {
        super();
        configElement = element;

        load();
    }

    /**
     * Return the IConfigurationElement associated with the TemplateDescriptor.
     *
     * @return the configuration element.
     */
    public IConfigurationElement getConfigurationElement() {
        return configElement;
    }

    /**
     * This loads the configuration.
     * 
     * @throws CoreException
     *             exception.
     */
    private void load() throws CoreException {
        id = configElement.getAttribute(ATT_ID);
        model = configElement.getAttribute(ATT_MODEL);

        // Sanity check.
        if (id == null || model == null) {
            throw new CoreException(new Status(IStatus.ERROR, configElement.getNamespace(), IStatus.OK,
                    "Invalid extension (missing id, name, editorId, model or di attribute): " + id, null));
        }
    }

    /**
     * Get the id of the diagram.
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Get the relativePath to the template of the model file.
     *
     * @return String
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the Template object associated with a template id.
     *
     * @return the Template object associated
     */
    public Template getTemplateModel() {
        return new Template(getFile(model));
    }

    /**
     * Returns the file of the given relativePath.
     *
     * @param relativePath
     *            the relativePath of the template which represent the file to get
     * @return the file
     */
    private File getFile(String relativePath) {
        try {
            Bundle extensionBundle = Platform.getBundle(configElement.getDeclaringExtension().getNamespace());
            URL templateFileURL = extensionBundle.getEntry(relativePath);
            return new File(Platform.asLocalURL(templateFileURL).getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
