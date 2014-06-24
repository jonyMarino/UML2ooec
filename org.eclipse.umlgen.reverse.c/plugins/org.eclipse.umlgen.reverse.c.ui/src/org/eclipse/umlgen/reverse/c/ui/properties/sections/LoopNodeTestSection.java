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
package org.eclipse.umlgen.reverse.c.ui.properties.sections;

import java.util.Collections;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section for adding a Test condition to a {@link LoopNode}.<br>
 * Creation : 20 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class LoopNodeTestSection extends AbstractLoopNodeSection {

	@Override
	protected String getLabelText() {
		return Messages.getString("LoopNode.Condition"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#getOpaqueAction()
	 */
	@Override
	protected OpaqueAction getOpaqueAction() {
		LoopNode loopNode = (LoopNode)getEObject();
		// Take the first one by default
		ExecutableNode testNode = !loopNode.getTests().isEmpty() ? loopNode.getTests().get(0) : null;

				if (testNode instanceof OpaqueAction) {
					return (OpaqueAction)testNode;
				}
				return null;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#createNewOpaqueAction()
	 */
	@Override
	protected OpaqueAction createNewOpaqueAction() {
		OpaqueAction opaqueAction = UMLFactory.eINSTANCE.createOpaqueAction();
		opaqueAction.getBodies().add(""); //$NON-NLS-1$
		opaqueAction.getLanguages().add(BundleConstants.C_LANGUAGE);
		opaqueAction.setName(""); //$NON-NLS-1$

		OutputPin pin = UMLFactory.eINSTANCE.createOutputPin();
		opaqueAction.getOutputValues().add(pin);

		LiteralNull literalNull = UMLFactory.eINSTANCE.createLiteralNull();
		pin.setUpperBound(literalNull);

		LoopNode loop = (LoopNode)getEObject();
		loop.setDecider(pin);

		return opaqueAction;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#addOpaqueAction(org.eclipse.uml2.uml.OpaqueAction,
	 *      org.eclipse.emf.common.command.CompoundCommand)
	 */
	@Override
	protected void addOpaqueAction(OpaqueAction newObj, CompoundCommand cc) {
		super.addOpaqueAction(newObj, cc);
		cc.appendIfCanExecute(SetCommand.create(getEditingDomain(), getEObject(), UMLPackage.eINSTANCE
				.getLoopNode_Test(), Collections.singletonList(newObj)));
	}
}
