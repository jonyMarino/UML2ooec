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
package org.eclipse.umlgen.gen.java.tests.classes.methods;

import java.util.Date;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of VisibilityMethodsClass.
 * 
 * @author Obeo
 */
public class VisibilityMethodsClass {
	// Start of user code (user defined attributes for VisibilityMethodsClass)

	// End of user code

	/**
	 * The constructor.
	 */
	public VisibilityMethodsClass() {
		// Start of user code constructor for VisibilityMethodsClass)
		super();
		// End of user code
	}

	/**
	 * Description of the method publicMethod.
	 * @return 
	 */
	public Boolean publicMethod() {
		// Start of user code for method publicMethod
		Boolean publicMethod = Boolean.FALSE;
		return publicMethod;
		// End of user code
	}

	/**
	 * Description of the method privateMethod.
	 * @return 
	 */
	private Date privateMethod() {
		// Start of user code for method privateMethod
		Date privateMethod = new Date();
		return privateMethod;
		// End of user code
	}

	/**
	 * Description of the method protectedMethod.
	 * @return 
	 */
	protected Boolean protectedMethod() {
		// Start of user code for method protectedMethod
		Boolean protectedMethod = Boolean.FALSE;
		return protectedMethod;
		// End of user code
	}

	/**
	 * Description of the method packageMethod.
	 * @return 
	 */
	/*package*/Float packageMethod() {
		// Start of user code for method packageMethod
		Float packageMethod = Float.valueOf(0F);
		return packageMethod;
		// End of user code
	}

	// Start of user code (user defined methods for VisibilityMethodsClass)

	// End of user code

}
