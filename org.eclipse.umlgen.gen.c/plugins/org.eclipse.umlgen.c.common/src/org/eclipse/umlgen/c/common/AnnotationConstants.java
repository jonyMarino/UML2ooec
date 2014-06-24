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
package org.eclipse.umlgen.c.common;

/**
 * Set of constants related to annotation management.<br>
 * Creation : 30 september 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public final class AnnotationConstants {

	/**
	 * Key to identify annotations for ifndef statements in .h file
	 */
	public static final String REVERSE_PROCESS = "http://www.eclipse.org/umlgen/annotation/c";

	/**
	 * Key to identify the project charset.
	 */
	public static final String PROJECT_CHARSET = "PROJECT_CHARSET";

	/**
	 * Key to identify annotations for ifndef statements in .h file
	 */
	public static final String IFNDEF_CONDITION = "IFNDEF";

	/**
	 * Key to identy details entry of the C filename of an UML!Class
	 */
	public static final String C_FILENAME = "C_FILENAME";

	/**
	 * Key to identy details entry of the H filename of an UML!Class
	 */
	public static final String H_FILENAME = "H_FILENAME";

	/**
	 * Key to identy details entry for a standard include (enclosed in angle brackets).
	 */
	public static final String STD_LIBRARY = "STD_LIBRARY";

	/**
	 * Key to identify an Volatile Variable
	 */
	public static final String VOLATILE = "VOLATILE";

	/**
	 * Key to identify a Register Variable
	 */
	public static final String REGISTER = "REGISTER";

	/**
	 * Key to identify an inline comment in C source file before its parent
	 */
	public static final String C_INLINE_BEFORE = "C_INLINE_COMMENT_BEFORE_DECLARATION";

	/**
	 * Key to identify an inline comment in C source file after its parent
	 */
	public static final String C_INLINE_AFTER = "C_INLINE_COMMENT_AFTER_DECLARATION";

	/**
	 * Key to identify an inline comment in C source file before its parent
	 */
	public static final String H_INLINE_BEFORE = "H_INLINE_COMMENT_BEFORE_DECLARATION";

	/**
	 * Key to identify an inline comment in C source file after its parent
	 */
	public static final String H_INLINE_AFTER = "H_INLINE_COMMENT_AFTER_DECLARATION";

}
