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
package org.eclipse.umlgen.gen.java.tests.interfaces.methods;

import java.util.Date;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of VisibilityMethodsInterface.
 * 
 * @author Obeo
 */
public interface VisibilityMethodsInterface {
	// Start of user code (user defined attributes for VisibilityMethodsInterface)

	// End of user code

	/**
	 * Description of the method publicMethod.
	 * @return 
	 */
	public Boolean publicMethod();

	/**
	 * Description of the method privateMethod.
	 * @return 
	 */
	private Byte privateMethod();

	/**
	 * Description of the method protectedMethod.
	 * @return 
	 */
	protected Date protectedMethod();

	/**
	 * Description of the method packageMethod.
	 * @return 
	 */
	/*package*/String packageMethod();

	// Start of user code (user defined methods for VisibilityMethodsInterface)

	// End of user code

}
