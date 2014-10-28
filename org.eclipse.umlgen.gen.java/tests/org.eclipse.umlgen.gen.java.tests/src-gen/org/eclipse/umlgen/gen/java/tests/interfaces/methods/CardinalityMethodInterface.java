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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of CardinalityMethodInterface.
 * 
 * @author Obeo
 */
public interface CardinalityMethodInterface {
	// Start of user code (user defined attributes for CardinalityMethodInterface)

	// End of user code

	/**
	 * Description of the method orderedUniqueMethod.
	 * @return 
	 */
	public LinkedHashSet<Integer> orderedUniqueMethod();

	/**
	 * Description of the method notOrderedUniqueMethod.
	 * @return 
	 */
	public HashSet<Date> notOrderedUniqueMethod();

	/**
	 * Description of the method notOrderedNotUniqueMethod.
	 * @return 
	 */
	public ArrayList<Boolean> notOrderedNotUniqueMethod();

	/**
	 * Description of the method OrderedNotUniqueMethod.
	 * @return 
	 */
	public ArrayList<String> OrderedNotUniqueMethod();

	// Start of user code (user defined methods for CardinalityMethodInterface)

	// End of user code

}
