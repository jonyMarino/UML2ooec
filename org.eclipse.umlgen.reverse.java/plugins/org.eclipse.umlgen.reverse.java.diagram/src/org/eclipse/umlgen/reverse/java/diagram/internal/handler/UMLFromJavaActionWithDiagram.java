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
package org.eclipse.umlgen.reverse.java.diagram.internal.handler;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.umlgen.reverse.java.diagram.PrototypeJava2UMLWizard;
import org.eclipse.umlgen.reverse.java.internal.handler.UMLFromJavaAction;

/**
 * Extension of UML From Java Action that also creates diagrams.
 */
public class UMLFromJavaActionWithDiagram extends UMLFromJavaAction {

    @Override
    protected Wizard createWizard() throws CoreException {
        PrototypeJava2UMLWizard wizard = new PrototypeJava2UMLWizard();
        wizard.setJavaElement(javaElement);
        return wizard;
    }

}
