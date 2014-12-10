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

import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/** The if statement builder. */
public class IfStatementBuilder extends AbstractBuilder {

    /**
     * Constructor.
     *
     * @param activityBuilder
     *            The activity builder.
     * @param factory
     *            The activity factory.
     * @param commentBuilder
     *            The comment builder.
     */
    public IfStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
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
    public ActivityNodesPins buildIfStatement(IASTIfStatement stmt, ActivityContext currentContext) {
        String decisionNodeName = getDecisionNodeName(stmt);
        DecisionNode decisionNode = factory.createDecisionNode(decisionNodeName, currentContext);
        MergeNode mergeNode = factory.createMergeNode(currentContext);

        // Attach comments to the DecisionNode
        commentBuilder.buildComment(decisionNode, getCommentInfo(stmt));

        // Create nodes for the "then" clause
        IASTStatement thenClause = stmt.getThenClause();
        ActivityNodesPins thenNodes = activityBuilder.buildNodes(thenClause, currentContext);

        // Create a flow from decision node to the first node of the "then"
        // clause
        ControlFlow thenFlow = factory.createControlFlow(decisionNode, thenNodes.getStartNode(), stmt
                .getConditionExpression().getRawSignature(), currentContext);
        commentBuilder.buildComment(thenFlow, getCommentInfo(thenClause));
        factory.createControlFlow(thenNodes.getEndNode(), mergeNode, currentContext);

        IASTStatement elseClause = stmt.getElseClause();
        ActivityNodesPins elseNodes = null;
        if (elseClause != null) {
            // Create the nodes for the "else" clause"
            elseNodes = activityBuilder.buildNodes(elseClause, currentContext);
            // Create a flow from the decision node to the first node of the
            // "else" clause"
            ControlFlow elseFlow = factory.createControlFlow(decisionNode, elseNodes.getStartNode(),
                    "[else]", currentContext);
            commentBuilder.buildComment(elseFlow, getCommentInfo(elseClause));

            // Create a flow from the last node of the "else" clause to the
            // merge node
            // if the last node is already a MergeNode, merge the 2 MergeNodes
            // into one
            if (elseNodes.getEndNode() instanceof MergeNode) {
                replaceMergeNode((MergeNode)elseNodes.getEndNode(), mergeNode);
            } else {
                factory.createControlFlow(elseNodes.getEndNode(), mergeNode, currentContext);
            }
        } else {
            // If there's no "else" clause, create a flow directly from the
            // decision node to the merge node
            factory.createControlFlow(decisionNode, mergeNode, "[else]", currentContext);
        }

        if (thenNodes.getEndNode() instanceof ActivityFinalNode && elseNodes != null
                && elseNodes.getEndNode() instanceof ActivityFinalNode) {
            currentContext.getNodes().remove(mergeNode);
            return new ActivityNodesPins(decisionNode, thenNodes.getEndNode());
        }

        return new ActivityNodesPins(decisionNode, mergeNode);
    }

    /**
     * Get the name of the decision node.
     *
     * @param stmt
     *            The current statement.
     * @return The name.
     */
    private String getDecisionNodeName(IASTIfStatement stmt) {
        String name = stmt.getRawSignature();
        // Get the position of the "{"
        int stmtOffset = stmt.getFileLocation().getNodeOffset();
        int thenOffset = stmt.getThenClause().getFileLocation().getNodeOffset();
        return name.substring(0, thenOffset - stmtOffset).trim();
    }

    /**
     * Substitute a merge node with another one - copy all incomings - copy all outgoings - remove all
     * incomings and outgoings on the replaced node - destroy the replaced node.
     *
     * @param toBeReplaced
     *            Merge node to be replaced
     * @param substitute
     *            Merge node used as a replacement
     */
    private void replaceMergeNode(MergeNode toBeReplaced, MergeNode substitute) {
        // Get all incomings and remove them from the node to be replaced
        substitute.getIncomings().addAll(toBeReplaced.getIncomings());
        toBeReplaced.getIncomings().clear();
        // Get all outgoings
        substitute.getOutgoings().addAll(toBeReplaced.getOutgoings());
        toBeReplaced.getOutgoings().clear();
        toBeReplaced.destroy();
    }
}
