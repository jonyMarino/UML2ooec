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

import java.util.Date;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of VisibilityPropertiesInterface.
 * 
 * @author Obeo
 */
public interface VisibilityPropertiesInterface {
	/**
	 * Description of the property publicProperty.
	 */
	public Boolean publicProperty = Boolean.FALSE;

	/**
	 * Description of the property privateProperty.
	 */
	private Byte privateProperty = Byte.valueOf("+0");

	/**
	 * Description of the property protectedProperty.
	 */
	protected Date protectedProperty = new Date();

	/**
	 * Description of the property packageProperty.
	 */
	/*package*/Float packageProperty = Float.valueOf(0F);

	// Start of user code (user defined attributes for VisibilityPropertiesInterface)

	// End of user code

	// Start of user code (user defined methods for VisibilityPropertiesInterface)

	// End of user code

}
