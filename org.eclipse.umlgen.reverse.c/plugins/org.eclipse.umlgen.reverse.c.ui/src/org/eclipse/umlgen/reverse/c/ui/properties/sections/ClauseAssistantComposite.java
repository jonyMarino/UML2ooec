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
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Clause;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * This composite provides facilities to create a right switch structure.
 * 
 * Creation : 17 june 2010<br>
 * 
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class ClauseAssistantComposite extends Composite {
	private static String BREAK = "break"; //$NON-NLS-1$

	private static String END_SWITCH = "end of switch"; //$NON-NLS-1$

	private static String DEFAULT = "default"; //$NON-NLS-1$

	private static String CASE = "case "; //$NON-NLS-1$

	/** The selected model object whose type should be 'ConditionalNode'. */
	private EObject parentObj;

	/** The editing domain */
	private EditingDomain domain;

	private Text text;

	private Button breakBtn;

	private Button defaultBtn;

	/**
	 * Constructor.
	 * 
	 * @param widgetFactory
	 *            the widget factory
	 * @param parent
	 *            The parent Composite
	 * @param style
	 *            the style of the composite
	 * @param selected
	 *            The selected model object which should be of kind 'conditional
	 *            node'
	 * @param editingDomain
	 *            the editing domain
	 */
	public ClauseAssistantComposite(
			TabbedPropertySheetWidgetFactory widgetFactory, Composite parent,
			int style) {
		super(parent, style);

		// set the layout
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;

		setLayout(layout);

		// create widgets
		createWidgets(widgetFactory, this);
	}

	protected void createWidgets(
			TabbedPropertySheetWidgetFactory widgetFactory, Composite parent) {
		Group mainGroup = widgetFactory.createGroup(parent,
				Messages.getString("ClauseAssistantComposite.5")); //$NON-NLS-1$
		mainGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mainGroup.setLayout(new GridLayout(3, false));

		// create the first line of the widget
		widgetFactory.createCLabel(mainGroup,
				Messages.getString("ClauseAssistantComposite.6")); //$NON-NLS-1$
		text = widgetFactory.createText(mainGroup, "2", SWT.BORDER); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Button createBtn = widgetFactory.createButton(mainGroup,
				Messages.getString("Create"), SWT.PUSH); //$NON-NLS-1$
		createBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, true, false));
		createBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int nb = Integer.parseInt(text.getText());
					CompoundCommand cc = new CompoundCommand(Messages
							.getString("ClauseAssistantComposite.9")); //$NON-NLS-1$
					createClauseAndOpaqueAction(nb, cc);
					domain.getCommandStack().execute(cc);
				} catch (NumberFormatException nfe) {

				}
			}
		});

		// create the first check box
		breakBtn = widgetFactory.createButton(mainGroup,
				Messages.getString("ClauseAssistantComposite.10"), SWT.CHECK); //$NON-NLS-1$
		breakBtn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 3,
				1));

		// create the second check box
		defaultBtn = widgetFactory.createButton(mainGroup,
				Messages.getString("ClauseAssistantComposite.11"), SWT.CHECK); //$NON-NLS-1$
		defaultBtn.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				3, 1));
	}

	public void setEditingDomain(EditingDomain domain) {
		this.domain = domain;
	}

	public void setParent(EObject eObject) {
		parentObj = eObject;
	}

	private void createClauseAndOpaqueAction(int total, CompoundCommand cc) {
		Clause predecessorClause = null;

		ActivityNode previousNode = null;

		// handles features 'isDeterminate' and 'isAssured' states.
		if (!defaultBtn.getSelection()) {
			setIsDeterminate(true, cc);
			seIsAssured(false, cc);
		} else {
			total++;
		}

		for (int index = 0; index < total; index++) {
			Clause clause = UMLFactory.eINSTANCE.createClause();

			// handles clause succession
			if (predecessorClause != null) {
				clause.getPredecessorClauses().add(predecessorClause);
			}
			predecessorClause = clause;
			addClause(clause, cc);

			OpaqueAction test = createTestOpaqueAction(index, total);
			addActivityNode(test, cc);

			OpaqueAction body = createBodyOpaqueAction();
			addActivityNode(body, cc);

			clause.getTests().add(test);
			clause.getBodies().add(body);
			clause.setDecider(test.getOutputs().get(0));

			if (breakBtn.getSelection()) {
				FlowFinalNode finalNode = createFlowFinalNode(BREAK);
				addActivityNode(finalNode, cc);
				addControlFlow(createControlFlow(body, finalNode), cc);
			} else {
				if (previousNode != null) {
					addControlFlow(createControlFlow(previousNode, body), cc);
				}
				previousNode = body;
			}
		}

		// Create the exit only when no breaks have been generated
		if (!breakBtn.getSelection()) {
			FlowFinalNode finalNode = createFlowFinalNode(END_SWITCH);
			addActivityNode(finalNode, cc);
			addControlFlow(createControlFlow(previousNode, finalNode), cc);
		}
	}

	/**
	 * 
	 * Creates the {@link ControlFlow} between two {@link ActivityNode}s.
	 * 
	 * @param source
	 *            The source activity node.
	 * @param target
	 *            The target activity node.
	 * @return The newly control flow.
	 */
	protected ControlFlow createControlFlow(ActivityNode source,
			ActivityNode target) {
		ControlFlow flow = UMLFactory.eINSTANCE.createControlFlow();
		flow.setSource(source);
		flow.setTarget(target);

		LiteralBoolean literalBool = UMLFactory.eINSTANCE
				.createLiteralBoolean();
		literalBool.setValue(true);
		flow.setGuard(literalBool);

		LiteralInteger literalInt = UMLFactory.eINSTANCE.createLiteralInteger();
		literalInt.setValue(1);
		flow.setWeight(literalInt);

		return flow;
	}

	/**
	 * Creates the {@link FlowFinalNode}.
	 * 
	 * @param label
	 *            The label to display as name
	 * @return the newly flow final node.
	 */
	protected FlowFinalNode createFlowFinalNode(String label) {
		FlowFinalNode finalNode = UMLFactory.eINSTANCE.createFlowFinalNode();
		finalNode.setName(label);
		return finalNode;
	}

	/**
	 * Creates a 'test' {@link OpaqueAction}.
	 * 
	 * @param index
	 *            The index of the <i>case</i>
	 * @return the newly opaque action.
	 */
	protected OpaqueAction createTestOpaqueAction(int index, int total) {
		OpaqueAction action = UMLFactory.eINSTANCE.createOpaqueAction();
		action.setName(defaultBtn.getSelection() && index == total - 1 ? DEFAULT
				: CASE + (index + 1));
		action.getLanguages().add(BundleConstants.C_LANGUAGE);
		action.getBodies().add(
				defaultBtn.getSelection() && index == total - 1 ? DEFAULT
						: String.valueOf(index));

		OutputPin pin = UMLFactory.eINSTANCE.createOutputPin();
		action.getOutputValues().add(pin);

		return action;
	}

	/**
	 * Creates a 'body' {@link OpaqueAction}.
	 * 
	 * @return the newly opaque action.
	 */
	protected OpaqueAction createBodyOpaqueAction() {
		OpaqueAction action = UMLFactory.eINSTANCE.createOpaqueAction();
		action.setName(Messages.getString("ClauseAssistantComposite.12")); //$NON-NLS-1$
		action.getLanguages().add(BundleConstants.C_LANGUAGE);
		action.getBodies().add(
				Messages.getString("ClauseAssistantComposite.13")); //$NON-NLS-1$
		return action;
	}

	/**
	 * Adds a {@link Clause} into the current {@link ConditionalNode}.
	 * 
	 * @param newObj
	 *            The new object to add to the model
	 * @param cc
	 *            The compound command in which all the commands of this
	 *            assistant must be stored
	 */
	private void addClause(Clause newObj, CompoundCommand cc) {
		Command addCmd = AddCommand.create(domain, parentObj,
				UMLPackage.eINSTANCE.getConditionalNode_Clause(), newObj);
		cc.appendIfCanExecute(addCmd);
	}

	/**
	 * Adds an {@link OpaqueAction} into the current {@link ConditionalNode}.
	 * 
	 * @param newObj
	 *            The new object to add to the model
	 * @param cc
	 *            The compound command in which all the commands of this
	 *            assistant must be stored
	 */
	private void addActivityNode(ActivityNode newObj, CompoundCommand cc) {
		Command addCmd = AddCommand.create(domain, parentObj,
				UMLPackage.eINSTANCE.getStructuredActivityNode_Node(), newObj);
		cc.appendIfCanExecute(addCmd);
	}

	/**
	 * Adds an {@link ControlFlow} into the current {@link ConditionalNode}.
	 * 
	 * @param newObj
	 *            The new object to add to the model.
	 * @param cc
	 *            The compound command in which all the commands of this
	 *            assistant must be stored.
	 */
	private void addControlFlow(ControlFlow newObj, CompoundCommand cc) {
		Command addCmd = AddCommand.create(domain, parentObj,
				UMLPackage.eINSTANCE.getStructuredActivityNode_Edge(), newObj);
		cc.appendIfCanExecute(addCmd);
	}

	/**
	 * Changes the value of the <i>IsDeterminate</i> attribute.
	 * 
	 * @param newValue
	 *            The new value to set for this feature
	 * @param cc
	 *            The compound command in which all the commands of this
	 *            assistant must be stored
	 */
	private void setIsDeterminate(boolean newValue, CompoundCommand cc) {
		Command setCmd = SetCommand.create(domain, parentObj,
				UMLPackage.eINSTANCE.getConditionalNode_IsDeterminate(),
				newValue);
		cc.appendIfCanExecute(setCmd);
	}

	/**
	 * Changes the value of the <i>IsAssured/i> attribute.
	 * 
	 * @param newValue
	 *            The new value to set for this feature
	 * @param cc
	 *            The compound command in which all the commands of this
	 *            assistant must be stored
	 */
	private void seIsAssured(boolean newValue, CompoundCommand cc) {
		Command setCmd = SetCommand.create(domain, parentObj,
				UMLPackage.eINSTANCE.getConditionalNode_IsAssured(), newValue);
		cc.appendIfCanExecute(setCmd);
	}
}
