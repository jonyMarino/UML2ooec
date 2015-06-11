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
package org.eclipse.umlgen.gen.embedded.c.utils;

import java.util.Calendar;

/**
 * List of constants for the UML to Embedded C generator.
 */
public interface IUML2ECConstants {

    /**
     * The extension of the UML files.
     */
    String UML_FILE_EXTENSION = "uml";

    /**
     * The key representing the uml model path.
     */
    String UML_MODEL_PATH = "uml_model_path";

    /**
     * The key representing the output folder path.
     */
    String OUTPUT_FOLDER_PATH = "output_folder_path";

    /**
     * The key indicating if the traceability between specification and detailed design must be generated.
     */
    String GENERATE_TRACEABILITY = "generate_traceability";

    /**
     * The key indicating if the svn:property $Date$ must be generated.
     */
    String GENERATE_SVN_DATE = "generate_svn_date";

    /**
     * The key indicating if the svn:property $Id$ must be generated.
     */
    String GENERATE_SVN_ID = "generate_svn_id";

    /**
     * The key representing the author.
     */
    String AUTHOR = "author";

    /**
     * The key representing the version.
     */
    String VERSION = "version";

    /**
     * The key representing the copyright.
     */
    String COPYRIGHT = "copyright";

    /**
     * The default values.
     */
    public interface Default {

        /**
         * The default author.
         */
        String DEFAULT_AUTHOR = System.getProperty("user.name");

        /**
         * The default version.
         */
        String DEFAULT_VERSION = "1.0.0";

        /**
         * The default license and copyright.
         */
        String DEFAULT_COPYRIGHT = Calendar.getInstance().get(Calendar.YEAR) + " All rights reserved.";

        /**
         * The default value to indicate if the traceability between specification and detailed design must be
         * generated.
         */
        boolean DEFAULT_GENERATE_TRACEABILITY = true;

        /**
         * The default value to indicate if the svn:property $Date$ must be generated.
         */
        boolean DEFAULT_GENERATE_SVN_DATE = true;

        /**
         * The default value to indicate if the svn:property $Id$ must be generated.
         */
        boolean DEFAULT_GENERATE_SVN_ID = true;
    }
}
