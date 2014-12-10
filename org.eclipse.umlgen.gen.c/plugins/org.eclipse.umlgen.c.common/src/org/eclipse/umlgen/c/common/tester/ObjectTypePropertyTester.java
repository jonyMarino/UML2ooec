/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.c.common.tester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;

/**
 * Tests whether the action into the pop-up menu has to be available for the receiver. Creation : 10 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class ObjectTypePropertyTester extends PropertyTester {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String,
     *      java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        boolean result = false;
        if (receiver instanceof EObject) {
            EObject eObject = (EObject)receiver;
            result = expectedValue.equals(eObject.eClass().getInstanceClassName());
        }
        IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
        if (args.length > 0 && synchronizer instanceof IDiagramSynchronizer
                && ((IDiagramSynchronizer)synchronizer).getPresentation(receiver) != null) {
            result &= args[0].equals(((IDiagramSynchronizer)synchronizer).getPresentation(receiver));
        }
        return result;
    }
}
