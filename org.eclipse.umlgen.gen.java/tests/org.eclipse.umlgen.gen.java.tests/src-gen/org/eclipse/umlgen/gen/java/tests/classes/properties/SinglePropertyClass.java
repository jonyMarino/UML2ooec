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
 * Description of SinglePropertyClass.
 * 
 * @author Obeo
 */
public class SinglePropertyClass {
	/**
	 * Description of the property firstProperty.
	 */
	public Byte firstProperty = Byte.valueOf("+0");

	// Start of user code (user defined attributes for SinglePropertyClass)

	// End of user code

	/**
	 * The constructor.
	 */
	public SinglePropertyClass() {
		// Start of user code constructor for SinglePropertyClass)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for SinglePropertyClass)

	// End of user code
	/**
	 * Returns firstProperty.
	 * @return firstProperty 
	 */
	public Byte getFirstProperty() {
		return this.firstProperty;
	}

	/**
	 * Sets a value to attribute firstProperty. 
	 * @param newFirstProperty 
	 */
	public void setFirstProperty(Byte newFirstProperty) {
		this.firstProperty = newFirstProperty;
	}

}
