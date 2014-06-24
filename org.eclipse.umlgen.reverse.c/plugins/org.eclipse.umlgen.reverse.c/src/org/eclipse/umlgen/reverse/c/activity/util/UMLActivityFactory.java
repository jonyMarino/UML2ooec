/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.util;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Clause;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.FinalNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;

public class UMLActivityFactory {
	private static final String FLOW_FINAL_END_OF_SWITCH = "end of switch";

	private static final String FLOW_FINAL_BREAK = "break";

	private static final String FLOW_FINAL_CONTINUE = "continue";

	private static final String FLOW_FINAL_END_OF_LOOP = "end of loop";

	private UMLFactory umlFactory;

	private UMLPackage umlPackage;

	public UMLActivityFactory() {
		umlFactory = UMLFactory.eINSTANCE;
		umlPackage = UMLPackage.eINSTANCE;
	}

	public Activity createActivity(String activityName) {
		Activity activity = umlFactory.createActivity();
		activity.setName(sanitizeString(activityName));
		return activity;
	}

	public InitialNode createInitialNode(ActivityContext currentContext) {
		InitialNode initialNode = umlFactory.createInitialNode();
		currentContext.addNode(initialNode);
		return initialNode;
	}

	public ActivityFinalNode createActivityFinalNode(ActivityContext currentContext) {
		ActivityFinalNode finalNode = umlFactory.createActivityFinalNode();
		currentContext.addNode(finalNode);
		return finalNode;
	}

	public OutputPin createOutputPin() {
		OutputPin outputPin = umlFactory.createOutputPin();
		outputPin.createUpperBound(null, null, umlPackage.getLiteralNull());
		return outputPin;
	}

	public ConditionalNode createConditionalNode(String nodeName, ActivityContext currentContext) {
		ConditionalNode condNode = umlFactory.createConditionalNode();
		currentContext.addNode(condNode);
		condNode.setName(sanitizeString(nodeName));
		return condNode;
	}

	public DecisionNode createDecisionNode(String nodeName, ActivityContext currentContext) {
		DecisionNode decisionNode = umlFactory.createDecisionNode();
		decisionNode.setName(sanitizeString(nodeName));

		currentContext.addNode(decisionNode);
		return decisionNode;
	}

	public MergeNode createMergeNode(ActivityContext currentContext) {
		MergeNode mergeNode = umlFactory.createMergeNode();
		currentContext.addNode(mergeNode);
		return mergeNode;
	}

	public OpaqueAction createOpaqueAction(String body, ActivityContext currentContext) {
		OpaqueAction opaqueAction = umlFactory.createOpaqueAction();
		currentContext.addNode(opaqueAction);
		opaqueAction.getLanguages().add(BundleConstants.C_LANGUAGE);
		opaqueAction.getBodies().add(sanitizeString(body));
		return opaqueAction;
	}

	public Clause createClause(ConditionalNode condNode, OpaqueAction testAction) {
		Clause clause = umlFactory.createClause();
		condNode.getClauses().add(clause);
		clause.getTests().add(testAction);
		clause.setDecider(testAction.getOutputValues().get(0));
		return clause;
	}

	public String sanitizeString(String value) {
		String sanitizeValue = value;
		if (sanitizeValue != null && !sanitizeValue.equals("")) {
			// Convert all line delimitors
			sanitizeValue = convertLineDelimitors(sanitizeValue);
		}
		return sanitizeValue;
	}

	private String convertLineDelimitors(String code) {
		return code.replaceAll(BundleConstants.WINDOWS_LINE_SEPARATOR, BundleConstants.LINE_SEPARATOR);
	}

	private OpaqueExpression createOpaqueExpression() {
		OpaqueExpression opaqueExpression = umlFactory.createOpaqueExpression();
		opaqueExpression.getLanguages().add(BundleConstants.C_LANGUAGE);
		return opaqueExpression;
	}

	public LoopNode createLoopNode(String nodeName, boolean isTestedFirst, ActivityContext currentContext) {
		LoopNode loopNode = umlFactory.createLoopNode();
		loopNode.setName(sanitizeString(nodeName));
		loopNode.setIsTestedFirst(isTestedFirst);
		currentContext.addNode(loopNode);
		return loopNode;
	}

	private ControlFlow createControlFlow(ValueSpecification guard, ActivityContext currentContext) {
		ControlFlow controlFlow = umlFactory.createControlFlow();
		LiteralInteger weight = umlFactory.createLiteralInteger();
		weight.setValue(1);
		controlFlow.setWeight(weight);
		controlFlow.setGuard(guard);
		currentContext.addEdge(controlFlow);
		return controlFlow;
	}

	public ControlFlow createControlFlow(ActivityContext currentContext) {
		return createControlFlow(trueLiteralBoolean(), currentContext);
	}

	public ControlFlow createControlFlow(ActivityNode fromNode, ActivityNode toNode,
			ActivityContext currentContext) {
		return createControlFlow(fromNode, toNode, trueLiteralBoolean(), currentContext);
	}

	public ControlFlow createControlFlow(ActivityNode fromNode, ActivityNode toNode, String guard,
			ActivityContext currentContext) {
		OpaqueExpression guardExpression = createOpaqueExpression();
		guardExpression.getBodies().add(sanitizeString(guard));
		return createControlFlow(fromNode, toNode, guardExpression, currentContext);
	}

	private ControlFlow createControlFlow(ActivityNode fromNode, ActivityNode toNode,
			ValueSpecification guard, ActivityContext currentContext) {
		ControlFlow controlFlow = null;
		if (fromNode != null && toNode != null && !(fromNode instanceof FinalNode)) {
			controlFlow = createControlFlow(guard, currentContext);
			controlFlow.setSource(fromNode);
			controlFlow.setTarget(toNode);
		}
		return controlFlow;
	}

	public FlowFinalNode getFlowFinalNodeForBreak(ActivityContext currentContext) {
		return getFlowFinalNode(FLOW_FINAL_BREAK, currentContext);
	}

	public FlowFinalNode getFlowFinalNodeForContinue(ActivityContext currentContext) {
		return getFlowFinalNode(FLOW_FINAL_CONTINUE, currentContext);
	}

	public FlowFinalNode getFlowFinalNodeForEndOfLoop(ActivityContext currentContext) {
		return getFlowFinalNode(FLOW_FINAL_END_OF_LOOP, currentContext);
	}

	public FlowFinalNode getFlowFinalNodeForEndOfSwitch(ActivityContext currentContext) {
		return getFlowFinalNode(FLOW_FINAL_END_OF_SWITCH, currentContext);
	}

	private FlowFinalNode getFlowFinalNode(String name, ActivityContext currentContext) {
		return createFlowFinal(name, currentContext);
	}

	public ExecutableNode ensureStartNodeIsExecutable(ActivityNode firstNode, ActivityContext currentContext) {
		ExecutableNode realFirstNode = null;
		if (firstNode instanceof ExecutableNode) {
			return realFirstNode = (ExecutableNode)firstNode;
		} else {
			realFirstNode = createOpaqueAction("", currentContext);
			createControlFlow(realFirstNode, firstNode, currentContext);
		}
		return realFirstNode;
	}

	public void addFlowTowardsActivityFinalNode(final ActivityNode fromNode, ActivityNode finalNode,
			ActivityContext currentContext) {
		if (fromNode instanceof ActivityFinalNode) {
			return;
		}
		createControlFlow(fromNode, finalNode, currentContext);
	}

	private FlowFinalNode createFlowFinal(String name, ActivityContext currentContext) {
		FlowFinalNode flowFinalNode = umlFactory.createFlowFinalNode();
		flowFinalNode.setName(sanitizeString(name));
		currentContext.addNode(flowFinalNode);
		return flowFinalNode;
	}

	private LiteralBoolean trueLiteralBoolean() {
		LiteralBoolean ret = umlFactory.createLiteralBoolean();
		ret.setValue(true);
		return ret;
	}

	public EAnnotation createEAnnotation(String source) {
		EAnnotation ann = EcoreFactory.eINSTANCE.createEAnnotation();
		ann.setSource(source);
		return ann;
	}
}
