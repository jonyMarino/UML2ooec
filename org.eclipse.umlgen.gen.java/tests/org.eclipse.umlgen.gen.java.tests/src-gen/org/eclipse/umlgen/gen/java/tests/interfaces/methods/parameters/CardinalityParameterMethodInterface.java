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
package org.eclipse.umlgen.gen.java.tests.interfaces.methods.parameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of CardinalityParameterMethodInterface.
 * 
 * @author Obeo
 */
public interface CardinalityParameterMethodInterface {
	// Start of user code (user defined attributes for CardinalityParameterMethodInterface)

	// End of user code

	/**
	 * Description of the method cardinalityParameterMethod.
	 * @param orderedUniqueParameter 
	 * @param notOrderedUniqueParameter 
	 * @param notOrderedNotUniqueParameter 
	 * @param orderedNotUniqueParameter 
	 * @return 
	 */
	public Date cardinalityParameterMethod(
			LinkedHashSet<Boolean> orderedUniqueParameter,
			HashSet<Integer> notOrderedUniqueParameter,
			ArrayList<String> notOrderedNotUniqueParameter,
			ArrayList<Date> orderedNotUniqueParameter);

	// Start of user code (user defined methods for CardinalityParameterMethodInterface)

	// End of user code

}
