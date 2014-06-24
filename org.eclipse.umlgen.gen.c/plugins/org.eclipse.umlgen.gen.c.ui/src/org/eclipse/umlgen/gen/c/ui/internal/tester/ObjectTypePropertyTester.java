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
package org.eclipse.umlgen.gen.c.ui.internal.tester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;

/**
 * Tests whether the action into the pop-up menu has to be available for the receiver. Creation : 10 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class ObjectTypePropertyTester extends PropertyTester {

	/**
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String,
	 *      java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;
		if (receiver instanceof EObject) {
			EObject eObject = (EObject)receiver;
			result = expectedValue.equals(eObject.eClass().getInstanceClassName());
		}
		// FIXME MIGRATION reference to modeler
		// if (receiver instanceof Diagram && args.length > 0)
		// {
		// Diagram diagram = (Diagram) receiver;
		// result &=
		// args[0].equals(diagram.getSemanticModel().getPresentation());
		// }
		return result;
	}

}
