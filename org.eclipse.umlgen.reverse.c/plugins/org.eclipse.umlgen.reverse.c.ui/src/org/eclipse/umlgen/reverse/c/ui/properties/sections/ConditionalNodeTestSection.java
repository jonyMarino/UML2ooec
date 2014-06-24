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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section for setting the 'test' part of a {@link ConditionalNode}. Creation : 16 june 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class ConditionalNodeTestSection extends AbstractStringWithButtonSection {

	@Override
	protected String getFeatureAsString() {
		OpaqueAction opaqueAction = getTestOpaqueAction();
		if (opaqueAction != null) {
			int index = opaqueAction.getLanguages().indexOf(BundleConstants.C_LANGUAGE);
			if (index > -1) {
				return opaqueAction.getBodies().get(index);
			}
		}
		return null;
	}

	@Override
	protected EStructuralFeature getFeature() {
		return UMLPackage.eINSTANCE.getOpaqueAction_Body();
	}

	@Override
	protected void createCommand(Object oldValue, Object newValue) {
		boolean equals = oldValue == null ? false : oldValue.equals(newValue);
		if (!equals) {
			EditingDomain editingDomain = getEditingDomain();
			Command setCmd = SetCommand.create(editingDomain, getTestOpaqueAction(), getFeature(),
					Collections.singletonList(newValue));
			editingDomain.getCommandStack().execute(setCmd);
		}
	}

	@Override
	protected String getLabelText() {
		return Messages.getString("ConditionalNode.Test"); //$NON-NLS-1$
	}

	@Override
	protected void createModelObject() {
		addOpaqueExpression(createNewOpaqueAction());
	}

	/**
	 * Gets the {@link OpaqueAction} named 'test'.
	 *
	 * @return the corresponding or null if the opaque action does not still exist.
	 */
	public OpaqueAction getTestOpaqueAction() {
		ConditionalNode conditionalNode = (ConditionalNode)getEObject();
		ActivityNode testNode = conditionalNode.getNode(Messages.getString("ConditionalNode.test")); //$NON-NLS-1$

		if (testNode instanceof OpaqueAction) {
			return (OpaqueAction)testNode;
		}
		return null;
	}

	/**
	 * Creates a new opaque action named 'test' with a default C language and an empty body.
	 *
	 * @return The newly opaque action
	 */
	private OpaqueAction createNewOpaqueAction() {
		OpaqueAction opaqueAction = UMLFactory.eINSTANCE.createOpaqueAction();
		opaqueAction.getBodies().add(""); //$NON-NLS-1$
		opaqueAction.getLanguages().add(BundleConstants.C_LANGUAGE);
		opaqueAction.setName(Messages.getString("ConditionalNode.test")); //$NON-NLS-1$
		return opaqueAction;
	}

	/**
	 * Add an {@link OpaqueAction} into the current {@link ConditionalNode}.
	 *
	 * @param newObj
	 *            The new object to add to the model
	 */
	private void addOpaqueExpression(OpaqueAction newObj) {
		EditingDomain editingDomain = getEditingDomain();
		Command addCmd = AddCommand.create(editingDomain, getEObject(), UMLPackage.eINSTANCE
				.getStructuredActivityNode_Node(), newObj);
		editingDomain.getCommandStack().execute(addCmd);
	}

}
