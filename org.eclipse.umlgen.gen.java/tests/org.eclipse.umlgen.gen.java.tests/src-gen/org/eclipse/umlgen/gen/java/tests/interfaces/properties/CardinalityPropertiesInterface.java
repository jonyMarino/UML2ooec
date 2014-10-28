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
package org.eclipse.umlgen.gen.java.tests.interfaces.properties;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of CardinalityPropertiesInterface.
 * 
 * @author Obeo
 */
public interface CardinalityPropertiesInterface {
	/**
	 * Description of the property singleElementProperty.
	 */
	public String singleElementProperty = "";

	/**
	 * Description of the property multipleElementsProperty.
	 */
	public ArrayList<Integer> multipleElementsProperty = new ArrayList<Integer>();

	/**
	 * Description of the property multipleOrderedElementsProperty.
	 */
	public ArrayList<String> multipleOrderedElementsProperty = new ArrayList<String>();

	/**
	 * Description of the property multipleUniqueElementsProperty.
	 */
	public HashSet<Date> multipleUniqueElementsProperty = new HashSet<Date>();

	/**
	 * Description of the property multipleUniqueOrderedElementsProperty.
	 */
	public LinkedHashSet<Boolean> multipleUniqueOrderedElementsProperty = new LinkedHashSet<Boolean>();

	// Start of user code (user defined attributes for CardinalityPropertiesInterface)

	// End of user code

	// Start of user code (user defined methods for CardinalityPropertiesInterface)

	// End of user code

}
