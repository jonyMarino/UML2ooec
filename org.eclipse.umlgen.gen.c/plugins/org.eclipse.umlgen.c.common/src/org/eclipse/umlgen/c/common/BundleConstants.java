/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) - evolutions
 *     Sebastien GABEL (CS-SI) - evolutions
 *     Stephane Thibaudeau (Obeo) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.c.common;

import org.eclipse.uml2.uml.OpaqueAction;

public final class BundleConstants {

	public static final String BUNDLE_ID = "org.eclipse.umlgen.reverse.c";

	public static final String NATURE_ID = "org.eclipse.umlgen.reverse.c.syncNature";

	public static final String C_NATURE_ID = "org.eclipse.cdt.core.cnature";

	public static final String C2UML_BUILDER_ID = "org.eclipse.umlgen.reverse.c.builder";

	public static final String UML2C_BUILDER_ID = "org.eclipse.umlgen.gen.c.builder";

	public static final String TEMPLATE_ID = "org.eclipse.umlgen.reverse.c.ui.template";

	public static final String UML_EXTENSION = "uml";

	public static final String UMLDI_EXTENSION = "umldi";

	public static final String C_EXTENSION = "c";

	public static final String H_EXTENSION = "h";

	public static final String SOURCE_PACKAGE_NAME = "Source Artefacts";

	public static final String TYPE_PACKAGE_NAME = "Types";

	public static final String LIB_PACKAGE_NAME = "Libs";

	public static final String MODELS_FOLDER = "Models";

	/**
	 * Key to use to know nature of the synchronization when new project begins.
	 */
	public static final String SYNC_AT_STARTING = "SyncAtStarting";

	/**
	 * Value associated with a synchronization done from an existing model
	 */
	public static final String SYNC_MODEL_VALUE = "model";

	/**
	 * Value associated with a synchronization done from a C source folder
	 */
	public static final String SYNC_SOURCE_VALUE = "source";

	/**
	 * Key to use to retrieve value of the UML model path stored inside the
	 * preference store
	 */
	public static final String UML_MODEL_PATH = "UMLModelPath";

	/**
	 * Key to use to retrieve value of the UML model path stored inside the
	 * preference store
	 */
	public static final String UMLDI_MODEL_PATH = "UMLDIModelPath";

	/**
	 * Key to use to retrieve value of the 'src' package name where source code
	 * should be generated.
	 */
	public static final String SRC_PCK_NAME = "srcPackageName";

	/**
	 * Key to use to retrieve value of the 'type' package name where types
	 * should be defined.
	 */
	public static final String TYPE_PCK_NAME = "typePackageName";

	/**
	 * Key to use to retrieve value of the 'type' package name where external
	 * modules should be placed.
	 */
	public static final String EXT_PCK_NAME = "extPackageName";

	/**
	 * Max length of the name of all {@link OpaqueAction}s.
	 */
	public static final int OPAQUE_ACTION_NAME_MAX_LENGTH = 12;

	/**
	 * Default line separator
	 */
	public static final String LINE_SEPARATOR = "\n";

	/**
	 * Line separator for files written by Windows
	 */
	public static final String WINDOWS_LINE_SEPARATOR = "\r\n";

	/**
	 * Constant representing the C language
	 */
	public static final String C_LANGUAGE = "C"; //$NON-NLS-1$

	/**
	 * Constant representing private visibility of UML elements
	 */
	public static final String PRIVATE_VISIBILITY = "Private";

	/**
	 * Constant representing public visibility of UML elements
	 */
	public static final String PUBLIC_VISIBILITY = "Public";

	/**
	 * Constant associating the type to a macro definition
	 */
	public static final String MACRO_TYPE = "Macro";

	/**
	 * Key used to model array dimension
	 */
	public static final String ARRAY_SIZE = "size";

	/**
	 * Key used to model default value associated to variable definition
	 */
	public static final String DEFAULT_VALUE = "defaultValue";

	/**
	 * Key used to model read-only value associated to constant definition
	 */
	public static final String READONLY_VALUE = "readOnlyValue";

	/**
	 * Key representing the regular expression matching the 'struct' keyword
	 */
	public static final String STRUCT_REGEXP = "^struct\\s*";

	/**
	 * Key representing the regular expression matching the square brakets
	 */
	public static final String SQUARE_BRAKETS_REGEXP = "\\[\\]";

	/**
	 * Key representing the regular expression matching the 'enum' keyword
	 */
	public static final String ENUM_REGEXP = "^enum\\s*";

	/**
	 * Key representing the regular expression matching the 'static' keyword
	 */
	public static final String STATIC_REGEXP = "static\\s+";

	/**
	 * Key representing the regular expression matching the 'register' keyword
	 */
	public static final String REGISTER_REGEXP = "register\\s+";

	/**
	 * Key representing the regular expression matching the 'volatile' keyword
	 */
	public static final String VOLATILE_REGEXP = "volatile\\s+";

	/**
	 * Key representing the regular expression matching the 'extern' keyword
	 */
	public static final String EXTERN_REGEXP = "extern\\s+";

	/**
	 * Key representing the regular expression matching the 'const' keyword
	 */
	public static final String CONST_REGEXP = "const\\s+";

}
