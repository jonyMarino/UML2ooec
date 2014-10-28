/*******************************************************************************
 * Copyright (c) 2008, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.java.tests.classes.properties;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of StaticPropertyClass.
 * 
 * @author Obeo
 */
public class StaticPropertyClass {
	/**
	 * Description of the property staticProperty.
	 */
	public static Boolean staticProperty = Boolean.FALSE;

	// Start of user code (user defined attributes for StaticPropertyClass)

	// End of user code

	/**
	 * The constructor.
	 */
	public StaticPropertyClass() {
		// Start of user code constructor for StaticPropertyClass)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for StaticPropertyClass)

	// End of user code
	/**
	 * Returns staticProperty.
	 * @return staticProperty 
	 */
	public static Boolean getStaticProperty() {
		return staticProperty;
	}

	/**
	 * Sets a value to attribute staticProperty. 
	 * @param newStaticProperty 
	 */
	public static void setStaticProperty(Boolean newStaticProperty) {
		staticProperty = newStaticProperty;
	}

}
