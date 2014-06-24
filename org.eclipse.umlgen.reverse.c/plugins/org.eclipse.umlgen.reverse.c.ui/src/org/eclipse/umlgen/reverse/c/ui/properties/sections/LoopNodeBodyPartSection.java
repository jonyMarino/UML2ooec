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
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section for creating or referencing a <i>'body part'</i> section to a {@link LoopNode}.<br>
 * Creation : 21 june 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class LoopNodeBodyPartSection extends AbstractLoopNodeSection {

	@Override
	protected void createCommand(Object oldValue, Object newValue) {
		boolean equals = oldValue == null ? false : oldValue.equals(newValue);
		if (!equals) {
			EditingDomain editingDomain = getEditingDomain();
			CompoundCommand cc = new CompoundCommand(Messages
					.getString("AbstractLoopNodeSection.PropertyValueChange")); //$NON-NLS-1$
			cc.appendIfCanExecute(SetCommand.create(editingDomain, getOpaqueAction(), getFeature(),
					Collections.singletonList(newValue)));

			String formattedName = "";
			if (newValue.toString().length() > 0) {
				int length = Math.min(BundleConstants.OPAQUE_ACTION_NAME_MAX_LENGTH, newValue.toString()
						.length());
				formattedName = newValue.toString().substring(0, length)
						+ (newValue.toString().length() > BundleConstants.OPAQUE_ACTION_NAME_MAX_LENGTH ? "..."
								: "");
			}

			cc.appendIfCanExecute(SetCommand.create(editingDomain, getOpaqueAction(), UMLPackage.eINSTANCE
					.getNamedElement_Name(), formattedName));
			editingDomain.getCommandStack().execute(cc);
		}
	}

	@Override
	protected String getLabelText() {
		return Messages.getString("LoopNode.BodyPart"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#createModelObject()
	 */
	@Override
	protected void createModelObject() {
		OpaqueAction body = createNewOpaqueAction();
		FlowFinalNode finalNode = createNewFlowFinalNode();
		ControlFlow controlFlow = createControlFlow(body, finalNode);
		// use a compound command to manage creation of OpaqueAction,
		// ControlFlow and FlowFinalFlow
		CompoundCommand cc = new CompoundCommand(Messages.getString("AbstractLoopNodeSection.CmdTitle")); //$NON-NLS-1$
		addOpaqueAction(body, cc);
		addFlowFinalNode(finalNode, cc);
		addControlFlow(controlFlow, cc);
		getEditingDomain().getCommandStack().execute(cc);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#getOpaqueAction()
	 */
	@Override
	protected OpaqueAction getOpaqueAction() {
		LoopNode loopNode = (LoopNode)getEObject();
		// Take the first one by default
		ExecutableNode node = !loopNode.getBodyParts().isEmpty() ? loopNode.getBodyParts().get(0) : null;

				if (node instanceof OpaqueAction) {
					return (OpaqueAction)node;
				}
				return null;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#createNewOpaqueAction()
	 */
	@Override
	protected OpaqueAction createNewOpaqueAction() {
		OpaqueAction opaqueAction = UMLFactory.eINSTANCE.createOpaqueAction();
		opaqueAction.getBodies().add("Enter your code here"); //$NON-NLS-1$
		opaqueAction.getLanguages().add(BundleConstants.C_LANGUAGE);
		opaqueAction.setName(""); //$NON-NLS-1$
		return opaqueAction;
	}

	/**
	 * Creates a new flow final node named "continue".
	 *
	 * @return The newly flow final node.
	 */
	protected FlowFinalNode createNewFlowFinalNode() {
		FlowFinalNode finalNode = UMLFactory.eINSTANCE.createFlowFinalNode();
		finalNode.setName("continue"); //$NON-NLS-1$
		return finalNode;
	}

	/**
	 * Creates the {@link ControlFlow} between two {@link ActivityNode}s.
	 *
	 * @param source
	 *            The source activity node.
	 * @param target
	 *            The target activity node.
	 * @return The newly control flow.
	 */
	protected ControlFlow createControlFlow(ActivityNode source, ActivityNode target) {
		ControlFlow flow = UMLFactory.eINSTANCE.createControlFlow();
		flow.setSource(source);
		flow.setTarget(target);

		LiteralBoolean literalBool = UMLFactory.eINSTANCE.createLiteralBoolean();
		literalBool.setValue(true);
		flow.setGuard(literalBool);

		LiteralInteger literalInt = UMLFactory.eINSTANCE.createLiteralInteger();
		literalInt.setValue(1);
		flow.setWeight(literalInt);

		return flow;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractLoopNodeSection#addOpaqueAction(org.eclipse.uml2.uml.OpaqueAction,
	 *      org.eclipse.emf.common.command.CompoundCommand)
	 */
	@Override
	protected void addOpaqueAction(OpaqueAction newObj, CompoundCommand cc) {
		super.addOpaqueAction(newObj, cc);
		cc.appendIfCanExecute(AddCommand.create(getEditingDomain(), getEObject(), UMLPackage.eINSTANCE
				.getLoopNode_BodyPart(), Collections.singletonList(newObj)));
	}

	/**
	 * Adds a {@link FlowFinalNode} into the current {@link LoopNode}.
	 *
	 * @param newObj
	 *            The new object to add to the model
	 * @param cc
	 *            The compound command in which all the commands of this assistant must be stored.
	 */
	protected void addFlowFinalNode(FlowFinalNode newObj, CompoundCommand cc) {
		cc.appendIfCanExecute(AddCommand.create(getEditingDomain(), getEObject(), UMLPackage.eINSTANCE
				.getStructuredActivityNode_Node(), newObj));
	}

	/**
	 * Adds a {@link ControlFlow} into the current {@link LoopNode}.
	 *
	 * @param newObj
	 *            The new object to add to the model.
	 * @param cc
	 *            The compound command in which all the commands of this assistant must be stored.
	 */
	protected void addControlFlow(ControlFlow newObj, CompoundCommand cc) {
		cc.appendIfCanExecute(AddCommand.create(getEditingDomain(), getEObject(), UMLPackage.eINSTANCE
				.getStructuredActivityNode_Edge(), newObj));
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	@Override
	protected int getStyle() {
		return SWT.MULTI;
	}
}
