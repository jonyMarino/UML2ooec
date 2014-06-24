/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.tester;

import org.eclipse.core.expressions.PropertyTester;

/**
 * Tests whether the <b>C2UML Sync</b> nature is set on the current C project.<br>
 * Creation : 10 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class HasNaturePropertyTester extends PropertyTester {
	/**
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String,
	 *      java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		// FIXME MIGRATION reference to modeler
		// if (Modeler.getCurrentIFile() != null)
		// {
		// IFile file = Modeler.getCurrentIFile();
		// IProject project = file.getProject();
		// try
		// {
		// return project.getNature((String) expectedValue) != null ? true :
		// false;
		// }
		// catch (CoreException e)
		// {
		// Activator.log(e);
		// }
		// }
		return false;
	}

}
