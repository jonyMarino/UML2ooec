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

/** The activity factory. */
public class UMLActivityFactory {

    /** A constant for empty strings. */
    private static final String EMPTY_STRING = "";

    /** Constant for "end of switch". */
    private static final String FLOW_FINAL_END_OF_SWITCH = "end of switch";

    /** Constant for break. */
    private static final String FLOW_FINAL_BREAK = "break";

    /** Constant for continue. */
    private static final String FLOW_FINAL_CONTINUE = "continue";

    /** Constant for "end of loop". */
    private static final String FLOW_FINAL_END_OF_LOOP = "end of loop";

    /** UML Factory. */
    private UMLFactory umlFactory;

    /** UML Package. */
    private UMLPackage umlPackage;

    /**
     * Constructor.
     */
    public UMLActivityFactory() {
        umlFactory = UMLFactory.eINSTANCE;
        umlPackage = UMLPackage.eINSTANCE;
    }

    /**
     * Create and return an activity with a sanitized name.
     *
     * @param activityName
     *            The activity name.
     * @return The activity.
     */
    public Activity createActivity(String activityName) {
        Activity activity = umlFactory.createActivity();
        activity.setName(sanitizeString(activityName));
        return activity;
    }

    /**
     * Create and return an initial node and add this the given context.
     *
     * @param currentContext
     *            The current context
     * @return The initial node.
     */
    public InitialNode createInitialNode(ActivityContext currentContext) {
        InitialNode initialNode = umlFactory.createInitialNode();
        currentContext.addNode(initialNode);
        return initialNode;
    }

    /**
     * Create and return an activity final node and add this the given context.
     *
     * @param currentContext
     *            The current context
     * @return The initial node.
     */
    public ActivityFinalNode createActivityFinalNode(ActivityContext currentContext) {
        ActivityFinalNode finalNode = umlFactory.createActivityFinalNode();
        currentContext.addNode(finalNode);
        return finalNode;
    }

    /**
     * Create and return an output pin.
     *
     * @return The output pin.
     */
    public OutputPin createOutputPin() {
        OutputPin outputPin = umlFactory.createOutputPin();
        outputPin.createUpperBound(null, null, umlPackage.getLiteralNull());
        return outputPin;
    }

    /**
     * Create and return a conditional node with a sanitized name and add to the context.
     *
     * @param nodeName
     *            The name.
     * @param currentContext
     *            The context.
     * @return The conditional node.
     */
    public ConditionalNode createConditionalNode(String nodeName, ActivityContext currentContext) {
        ConditionalNode condNode = umlFactory.createConditionalNode();
        currentContext.addNode(condNode);
        condNode.setName(sanitizeString(nodeName));
        return condNode;
    }

    /**
     * Create and return a decision node with a sanitized name and add to the context.
     *
     * @param nodeName
     *            The name.
     * @param currentContext
     *            The context.
     * @return The decision node.
     */
    public DecisionNode createDecisionNode(String nodeName, ActivityContext currentContext) {
        DecisionNode decisionNode = umlFactory.createDecisionNode();
        decisionNode.setName(sanitizeString(nodeName));

        currentContext.addNode(decisionNode);
        return decisionNode;
    }

    /**
     * Create and return a merge node and add to the context.
     *
     * @param currentContext
     *            The context.
     * @return The merge node.
     */
    public MergeNode createMergeNode(ActivityContext currentContext) {
        MergeNode mergeNode = umlFactory.createMergeNode();
        currentContext.addNode(mergeNode);
        return mergeNode;
    }

    /**
     * Create and return an opaque action with a sanitized name and add to the context...
     *
     * @param body
     *            The body name of the action.
     * @param currentContext
     *            The context.
     * @return The opaque action.
     */
    public OpaqueAction createOpaqueAction(String body, ActivityContext currentContext) {
        OpaqueAction opaqueAction = umlFactory.createOpaqueAction();
        currentContext.addNode(opaqueAction);
        opaqueAction.getLanguages().add(BundleConstants.C_LANGUAGE);
        opaqueAction.getBodies().add(sanitizeString(body));
        return opaqueAction;
    }

    /**
     * Create and return a clause.
     *
     * @param condNode
     *            The conditional node to update.
     * @param testAction
     *            The opaque action to add to the clause.
     * @return The clause.
     */
    public Clause createClause(ConditionalNode condNode, OpaqueAction testAction) {
        Clause clause = umlFactory.createClause();
        condNode.getClauses().add(clause);
        clause.getTests().add(testAction);
        clause.setDecider(testAction.getOutputValues().get(0));
        return clause;
    }

    /**
     * Sanitize the given string.
     *
     * @param value
     *            The string.
     * @return The sanitized string.
     */
    public String sanitizeString(String value) {
        String sanitizeValue = value;
        if (sanitizeValue != null && !sanitizeValue.equals(EMPTY_STRING)) {
            // Convert all line delimitors
            sanitizeValue = convertLineDelimitors(sanitizeValue);
        }
        return sanitizeValue;
    }

    /**
     * This converts windows line delimiters to the specified one (Unix by default).
     *
     * @param code
     *            The code to convert.
     * @return The converted code.
     */
    private String convertLineDelimitors(String code) {
        return code.replaceAll(BundleConstants.WINDOWS_LINE_SEPARATOR, BundleConstants.LINE_SEPARATOR);
    }

    /**
     * Create and return an opaque expression.
     *
     * @return The opaque expression.
     */
    private OpaqueExpression createOpaqueExpression() {
        OpaqueExpression opaqueExpression = umlFactory.createOpaqueExpression();
        opaqueExpression.getLanguages().add(BundleConstants.C_LANGUAGE);
        return opaqueExpression;
    }

    /**
     * Create and return a loop node.
     *
     * @param nodeName
     *            The name of the loop.
     * @param isTestedFirst
     *            The "testedFirst" loop property.
     * @param currentContext
     *            The currentContext.
     * @return The loop node.
     */
    public LoopNode createLoopNode(String nodeName, boolean isTestedFirst, ActivityContext currentContext) {
        LoopNode loopNode = umlFactory.createLoopNode();
        loopNode.setName(sanitizeString(nodeName));
        loopNode.setIsTestedFirst(isTestedFirst);
        currentContext.addNode(loopNode);
        return loopNode;
    }

    /**
     * Create and return a control flow.
     *
     * @param guard
     *            the guard of the control flow.
     * @param currentContext
     *            The context.
     * @return The control flow.
     */
    private ControlFlow createControlFlow(ValueSpecification guard, ActivityContext currentContext) {
        ControlFlow controlFlow = umlFactory.createControlFlow();
        LiteralInteger weight = umlFactory.createLiteralInteger();
        weight.setValue(1);
        controlFlow.setWeight(weight);
        controlFlow.setGuard(guard);
        currentContext.addEdge(controlFlow);
        return controlFlow;
    }

    /**
     * Create and return a control flow.
     *
     * @param currentContext
     *            The context.
     * @return The control flow.
     */
    public ControlFlow createControlFlow(ActivityContext currentContext) {
        return createControlFlow(trueLiteralBoolean(), currentContext);
    }

    /**
     * Create and return a control flow.
     *
     * @param fromNode
     *            The activity node from which it has to be attached.
     * @param toNode
     *            The activity node to which it has to be attached.
     * @param currentContext
     *            The context.
     * @return The control flow.
     */
    public ControlFlow createControlFlow(ActivityNode fromNode, ActivityNode toNode,
            ActivityContext currentContext) {
        return createControlFlow(fromNode, toNode, trueLiteralBoolean(), currentContext);
    }

    /**
     * Create and return a control flow.
     *
     * @param fromNode
     *            The activity node from which it has to be attached.
     * @param toNode
     *            The activity node to which it has to be attached.
     * @param guard
     *            The guard.
     * @param currentContext
     *            The context.
     * @return The control flow.
     */
    public ControlFlow createControlFlow(ActivityNode fromNode, ActivityNode toNode, String guard,
            ActivityContext currentContext) {
        OpaqueExpression guardExpression = createOpaqueExpression();
        guardExpression.getBodies().add(sanitizeString(guard));
        return createControlFlow(fromNode, toNode, guardExpression, currentContext);
    }

    /**
     * Create and return a control flow.
     *
     * @param fromNode
     *            The activity node from which it has to be attached.
     * @param toNode
     *            The activity node to which it has to be attached.
     * @param guard
     *            The guard.
     * @param currentContext
     *            The context.
     * @return The control flow.
     */
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

    /**
     * Get the flow final node for break.
     *
     * @param currentContext
     *            The context.
     * @return The flow final node.
     */
    public FlowFinalNode getFlowFinalNodeForBreak(ActivityContext currentContext) {
        return getFlowFinalNode(FLOW_FINAL_BREAK, currentContext);
    }

    /**
     * Get the flow final node for continue.
     *
     * @param currentContext
     *            The context.
     * @return The flow final node.
     */
    public FlowFinalNode getFlowFinalNodeForContinue(ActivityContext currentContext) {
        return getFlowFinalNode(FLOW_FINAL_CONTINUE, currentContext);
    }

    /**
     * Get the flow final node for loop.
     *
     * @param currentContext
     *            The context.
     * @return The flow final node.
     */
    public FlowFinalNode getFlowFinalNodeForEndOfLoop(ActivityContext currentContext) {
        return getFlowFinalNode(FLOW_FINAL_END_OF_LOOP, currentContext);
    }

    /**
     * Get the flow final node for end of switch.
     *
     * @param currentContext
     *            The context.
     * @return The flow final node.
     */
    public FlowFinalNode getFlowFinalNodeForEndOfSwitch(ActivityContext currentContext) {
        return getFlowFinalNode(FLOW_FINAL_END_OF_SWITCH, currentContext);
    }

    /**
     * Get the flow final node.
     *
     * @param name
     *            The name of the flow.
     * @param currentContext
     *            The context.
     * @return The flow final node.
     */
    private FlowFinalNode getFlowFinalNode(String name, ActivityContext currentContext) {
        return createFlowFinal(name, currentContext);
    }

    /**
     * Return the first executable node.
     *
     * @param firstNode
     *            The current activity node.
     * @param currentContext
     *            The context.
     * @return the executable node.
     */
    public ExecutableNode ensureStartNodeIsExecutable(ActivityNode firstNode, ActivityContext currentContext) {
        ExecutableNode realFirstNode = null;
        if (firstNode instanceof ExecutableNode) {
            realFirstNode = (ExecutableNode)firstNode;
        } else {
            realFirstNode = createOpaqueAction(EMPTY_STRING, currentContext);
            createControlFlow(realFirstNode, firstNode, currentContext);
        }
        return realFirstNode;
    }

    /**
     * Create a flow between the given <code>fromNode</code> to the <code>finalNode</code>.
     *
     * @param fromNode
     *            The start node.
     * @param finalNode
     *            The end node.
     * @param currentContext
     *            the context.
     */
    public void addFlowTowardsActivityFinalNode(final ActivityNode fromNode, ActivityNode finalNode,
            ActivityContext currentContext) {
        if (fromNode instanceof ActivityFinalNode) {
            return;
        }
        createControlFlow(fromNode, finalNode, currentContext);
    }

    /**
     * Create and return the flow with a sanitized name.
     *
     * @param name
     *            The name to set.
     * @param currentContext
     *            The context.
     * @return The flow.
     */
    private FlowFinalNode createFlowFinal(String name, ActivityContext currentContext) {
        FlowFinalNode flowFinalNode = umlFactory.createFlowFinalNode();
        flowFinalNode.setName(sanitizeString(name));
        currentContext.addNode(flowFinalNode);
        return flowFinalNode;
    }

    /**
     * Create and return a literal boolean.
     *
     * @return a literal boolean.
     */
    private LiteralBoolean trueLiteralBoolean() {
        LiteralBoolean ret = umlFactory.createLiteralBoolean();
        ret.setValue(true);
        return ret;
    }

    /**
     * Create an annotation.
     *
     * @param source
     *            The source of the annotation.
     * @return The annotation.
     */
    public EAnnotation createEAnnotation(String source) {
        EAnnotation ann = EcoreFactory.eINSTANCE.createEAnnotation();
        ann.setSource(source);
        return ann;
    }
}
