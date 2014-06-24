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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section for adding an <i>Iteration</i> field to the edited {@link LoopNode}.<br>
 * Creation : 21 june 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class LoopNodeIterationSection extends AbstractLoopNodeSection {

	@Override
	protected String getLabelText() {
		return Messages.getString("LoopNode.Iteration"); //$NON-NLS-1$
	}

	/**
	 * Gets the {@link OpaqueAction} corresponding to the iteration.
	 *
	 * @return the corresponding or null if the opaque action does not still exist.
	 */
	@Override
	public OpaqueAction getOpaqueAction() {
		LoopNode loopNode = (LoopNode)getEObject();
		// Take the first one by default
		OutputPin outputPin = !loopNode.getLoopVariables().isEmpty() ? loopNode.getLoopVariables().get(0)
				: null;

				if (outputPin != null && outputPin.eContainer() instanceof OpaqueAction) {
					return (OpaqueAction)outputPin.eContainer();
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
		loop.getLoopVariables().add(pin);

		return opaqueAction;
	}

	/**
	 * Removes an {@link OpaqueAction} into the current {@link LoopNode}.
	 *
	 * @param newObj
	 *            The new object to add to the model
	 */
	private void removeOpaqueExpression(OpaqueAction newObj) {
		if (newObj != null) {
			EditingDomain editingDomain = getEditingDomain();
			Command removeCmd = DeleteCommand.create(editingDomain, newObj);
			editingDomain.getCommandStack().execute(removeCmd);
		}
	}

	@Override
	protected void handleModelChanged(Notification msg) {
		if (msg.getEventType() == LoopNodeKindSection.UI_CHANGE_KIND) {
			if (msg.getOldValue() != msg.getNewValue()) {
				removeOpaqueExpression(getOpaqueAction());
			}
			refresh();
			getButton().setEnabled(msg.getNewStringValue().equals(LoopNodeKindSection.FOR));
		}
	}

}
