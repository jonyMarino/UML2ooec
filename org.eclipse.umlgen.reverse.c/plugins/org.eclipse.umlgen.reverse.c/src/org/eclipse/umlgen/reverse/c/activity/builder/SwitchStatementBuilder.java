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
package org.eclipse.umlgen.reverse.c.activity.builder;

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Clause;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.ASTUtilities;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/** The swith statement builder. */
public class SwitchStatementBuilder extends AbstractBuilder {

    /** Constant for "case". */
    private static final String CASE = "case";

    /** Constant for "default". */
    private static final String DEFAULT = "default";

    /**
     * Constructor.
     *
     * @param activityBuilder
     *            The activity builder.
     * @param factory
     *            the activity factory.
     * @param commentBuilder
     *            The comment builder.
     */
    public SwitchStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
            CommentBuilder commentBuilder) {
        super(activityBuilder, factory, commentBuilder);
    }

    /**
     * This builds activity nodes pins from the given statement and activity context.
     *
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     * @return Activity nodes pins.
     */
    public ActivityNodesPins buildSwitchStatement(IASTSwitchStatement stmt, ActivityContext currentContext) {
        ConditionalNode condNode = createConditionnalNode(stmt, currentContext);

        // Attach comments to the ConditionalNode
        commentBuilder.buildComment(condNode, getCommentInfo(stmt));

        ActivityContext condNodeContext = new ActivityContext(condNode);

        // Create an OpaqueAction containing the test for the whole switch
        OpaqueAction switchTestAction = factory.createOpaqueAction(getTestName(stmt), condNodeContext);
        switchTestAction.setName("test");

        Clause previousClause = null;
        ControlFlow pendingFlow = null;
        List<Clause> clausesWithoutBody = Lists.newArrayList();

        // Build nodes and collect info on every case clause (group)
        List<List<IASTNode>> groups = ASTUtilities.getStatementsGroupedByClause(stmt);
        for (List<IASTNode> group : groups) {
            OpaqueAction testAction = buildTestActionForCaseGroup(group, condNodeContext);
            ExecutableNode bodyAction = buildBodyActionForCaseGroup(group, condNodeContext);
            boolean hasBreak = ASTUtilities.hasBreakStatement(group);
            boolean hasReturn = ASTUtilities.hasReturnStatement(group);

            Clause clause = factory.createClause(condNode, testAction);

            // Attach comments to the clause
            commentBuilder.buildComment(clause, getCommentInfo((IASTStatement)group.get(0)));

            if (bodyAction == null) {
                // Store the clause in the clauses without body list (to handle
                // fallthrough)
                clausesWithoutBody.add(clause);
            } else {
                // Add the action into clause's body parts
                clause.getBodies().add(bodyAction);

                // And add the body to the clauses without body
                for (Clause clauseWithoutBody : clausesWithoutBody) {
                    clauseWithoutBody.getBodies().add(bodyAction);
                }
                clausesWithoutBody = Lists.newArrayList();
            }
            if (previousClause != null) {
                clause.getPredecessorClauses().add(previousClause);
            }

            // Complete the pending flow from the previous iteration if there's
            // one
            if (pendingFlow != null) {
                pendingFlow.setTarget(bodyAction);
                pendingFlow = null;
            }

            if (bodyAction != null && !hasBreak && !hasReturn) {
                // We have to build a flow from he body action to the next body
                // action
                pendingFlow = factory.createControlFlow(condNodeContext);
                pendingFlow.setSource(bodyAction);
                // The target will be set at the next iteration
            }

            previousClause = clause;
        }

        // Complete the pending flow if there's still one
        if (pendingFlow != null) {
            // FlowFinalNode
            FlowFinalNode flowFinal = factory.getFlowFinalNodeForEndOfSwitch(condNodeContext);
            pendingFlow.setTarget(flowFinal);
        }

        return new ActivityNodesPins(condNode, condNode);
    }

    /**
     * This builds activity nodes pins from the given statement and activity context.
     *
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     * @return Activity nodes pins.
     */
    public ActivityNodesPins buildCaseStatement(IASTCaseStatement stmt, ActivityContext currentContext) {
        // CHECKSTYLE:OFF
        String actionBody = stmt.getExpression() == null ? "" : stmt.getExpression().getRawSignature();
        // CHECKSTYLE:ON
        String actionName = CASE + " " + actionBody;

        OpaqueAction opaqueAction = buildActionForClauseStatement(actionName, actionBody, currentContext);
        return new ActivityNodesPins(opaqueAction, opaqueAction);
    }

    /**
     * This builds activity nodes pins from the given statement and activity context.
     *
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     * @return Activity nodes pins.
     */
    public ActivityNodesPins buildDefaultStatement(IASTDefaultStatement stmt, ActivityContext currentContext) {
        OpaqueAction opaqueAction = buildActionForClauseStatement(DEFAULT, DEFAULT, currentContext);
        return new ActivityNodesPins(opaqueAction, opaqueAction);
    }

    /**
     * This builds an opaque action for clause statement.
     *
     * @param actionName
     *            The name of the action.
     * @param actionBody
     *            The body of the action.
     * @param currentContext
     *            The activity context.
     * @return The opaque action.
     */
    private OpaqueAction buildActionForClauseStatement(String actionName, String actionBody,
            ActivityContext currentContext) {
        OpaqueAction opaqueAction = factory.createOpaqueAction(actionBody, currentContext);
        opaqueAction.setName(factory.sanitizeString(actionName));
        opaqueAction.getOutputValues().add(factory.createOutputPin());
        return opaqueAction;
    }

    /**
     * This creates a conditional node.
     *
     * @param stmt
     *            The statement.
     * @param currentContext
     *            The activity context.
     * @return The conditional node.
     */
    private ConditionalNode createConditionnalNode(IASTSwitchStatement stmt, ActivityContext currentContext) {
        // Name of the node
        String nodeName = getConditionalNodeName(stmt);

        // Creation of the node
        ConditionalNode condNode = factory.createConditionalNode(nodeName, currentContext);

        // IsAssured and IsDeterminate
        boolean hasDefaultStmt = ASTUtilities.hasDefaultStatement(stmt);
        condNode.setIsDeterminate(!hasDefaultStmt);
        condNode.setIsAssured(hasDefaultStmt);

        return condNode;
    }

    /**
     * Get the name of the conditional node from the current statement.
     *
     * @param stmt
     *            The current statement.
     * @return The name.
     */
    private String getConditionalNodeName(IASTSwitchStatement stmt) {
        String name = stmt.getRawSignature();
        // Get the position of the "{"
        int stmtOffset = stmt.getFileLocation().getNodeOffset();
        int bodyOffset = stmt.getBody().getFileLocation().getNodeOffset();
        return name.substring(0, bodyOffset - stmtOffset).trim();
    }

    /**
     * This builds a test action for case group.
     *
     * @param group
     *            The group.
     * @param currentContext
     *            The activity context.
     * @return The opaque action.
     */
    private OpaqueAction buildTestActionForCaseGroup(List<IASTNode> group, ActivityContext currentContext) {
        IASTStatement stmt = null;
        // A test action must be created for the CaseStatement of the group
        // this statement must be the first
        IASTNode node = group.get(0);
        if (node instanceof IASTCaseStatement) {
            stmt = (IASTCaseStatement)node;
        } else if (node instanceof IASTDefaultStatement) {
            stmt = (IASTDefaultStatement)node;
        }

        ActivityNodesPins nodes = activityBuilder.buildNodes(stmt, currentContext);
        return (OpaqueAction)nodes.getStartNode();
    }

    /**
     * This builds the body of the action for case group.
     *
     * @param group
     *            The group.
     * @param currentContext
     *            The activity context.
     * @return The body action.
     */
    private ExecutableNode buildBodyActionForCaseGroup(List<IASTNode> group, ActivityContext currentContext) {
        // If the group contains only 1 statement it means it's a fallthrough,
        // for example
        // case 1 :
        // case 2 :
        if (group.size() == 1) {
            return null;
        }

        // The body action corresponds to the second statement in group
        ExecutableNode bodyAction = null;
        ActivityNode firstNode = null;
        ActivityNode previousNode = null;
        for (int i = 1; i < group.size(); i++) {
            IASTNode astNode = group.get(i);
            ActivityNodesPins nodes = activityBuilder.buildNodes((IASTStatement)astNode, currentContext);
            if (i == 1) {
                firstNode = nodes.getStartNode();
            }
            if (previousNode != null) {
                factory.createControlFlow(previousNode, nodes.getStartNode(), currentContext);
            }
            previousNode = nodes.getEndNode();
        }
        bodyAction = factory.ensureStartNodeIsExecutable(firstNode, currentContext);

        return bodyAction;
    }

    /**
     * Get the name of the test.
     * 
     * @param stmt
     *            The current statement.
     * @return The name.
     */
    private String getTestName(IASTSwitchStatement stmt) {
        // Name of the node
        String testName = "";
        if (stmt.getControllerExpression() != null) {
            testName = stmt.getControllerExpression().getRawSignature();
        }
        return testName;
    }

}
