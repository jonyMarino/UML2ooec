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

import java.util.Date;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of MultiplePropertiesClass.
 * 
 * @author Obeo
 */
public class MultiplePropertiesClass {
	/**
	 * Description of the property firstProperty.
	 */
	public Date firstProperty = new Date();

	/**
	 * Description of the property secondProperty.
	 */
	public Integer secondProperty = Integer.valueOf(0);

	/**
	 * Description of the property thirdProperty.
	 */
	public Float thirdProperty = Float.valueOf(0F);

	// Start of user code (user defined attributes for MultiplePropertiesClass)

	// End of user code

	/**
	 * The constructor.
	 */
	public MultiplePropertiesClass() {
		// Start of user code constructor for MultiplePropertiesClass)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for MultiplePropertiesClass)

	// End of user code
	/**
	 * Returns firstProperty.
	 * @return firstProperty 
	 */
	public Date getFirstProperty() {
		return this.firstProperty;
	}

	/**
	 * Sets a value to attribute firstProperty. 
	 * @param newFirstProperty 
	 */
	public void setFirstProperty(Date newFirstProperty) {
		this.firstProperty = newFirstProperty;
	}

	/**
	 * Returns secondProperty.
	 * @return secondProperty 
	 */
	public Integer getSecondProperty() {
		return this.secondProperty;
	}

	/**
	 * Sets a value to attribute secondProperty. 
	 * @param newSecondProperty 
	 */
	public void setSecondProperty(Integer newSecondProperty) {
		this.secondProperty = newSecondProperty;
	}

	/**
	 * Returns thirdProperty.
	 * @return thirdProperty 
	 */
	public Float getThirdProperty() {
		return this.thirdProperty;
	}

	/**
	 * Sets a value to attribute thirdProperty. 
	 * @param newThirdProperty 
	 */
	public void setThirdProperty(Float newThirdProperty) {
		this.thirdProperty = newThirdProperty;
	}

}
