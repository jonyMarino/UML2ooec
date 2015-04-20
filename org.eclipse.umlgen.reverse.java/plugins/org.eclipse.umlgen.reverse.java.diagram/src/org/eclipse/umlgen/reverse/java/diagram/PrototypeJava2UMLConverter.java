/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Philippe Roland (Atos) - initialize API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.umlgen.reverse.java.Java2UMLConverter;
import org.eclipse.umlgen.reverse.java.logging.LogUtils;

/**
 * This class converts objects from the JDT model to a UML2 model.
 */
public class PrototypeJava2UMLConverter extends Java2UMLConverter {
    @Override
    protected Package findOrCreateRootPackage() {
        Package model = UMLFactory.eINSTANCE.createModel();
        model.setName(modelName);
        emfResource.getContents().add(model);
        LogUtils.logCreation(null, null, model, null);
        return model;
    }
}
