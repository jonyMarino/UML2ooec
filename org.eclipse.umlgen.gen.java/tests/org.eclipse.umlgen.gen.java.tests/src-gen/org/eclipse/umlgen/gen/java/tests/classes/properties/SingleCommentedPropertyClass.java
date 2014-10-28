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
 * The comment of SingleCommentedPropertyClass.
 * 
 * @author Obeo
 */
public class SingleCommentedPropertyClass {
	/**
	 * The two lines comment of 
	the first property of SingleCommentedPropertyClass.
	 */
	public Byte firstProperty = Byte.valueOf("+0");

	// Start of user code (user defined attributes for SingleCommentedPropertyClass)

	// End of user code

	/**
	 * The constructor.
	 */
	public SingleCommentedPropertyClass() {
		// Start of user code constructor for SingleCommentedPropertyClass)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for SingleCommentedPropertyClass)

	// End of user code
	/**
	 * Returns firstProperty.
	 * @return firstProperty The two lines comment of 
	 the first property of SingleCommentedPropertyClass.
	 */
	public Byte getFirstProperty() {
		return this.firstProperty;
	}

	/**
	 * Sets a value to attribute firstProperty. 
	 * @param newFirstProperty The two lines comment of 
	 the first property of SingleCommentedPropertyClass.
	 */
	public void setFirstProperty(Byte newFirstProperty) {
		this.firstProperty = newFirstProperty;
	}

}
