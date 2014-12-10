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

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/**
 * The flow control statement builder.
 */
public class FlowControlStatementBuilder extends AbstractBuilder {

    /**
     * Constructor.
     *
     * @param activityBuilder
     *            the activity builder.
     * @param factory
     *            the activity factory.
     * @param commentBuilder
     *            The comment builder.
     */
    public FlowControlStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
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
    public ActivityNodesPins buildBreakStatement(IASTBreakStatement stmt, ActivityContext currentContext) {
        // Create a flow towards a flow final node named "break"
        FlowFinalNode flowFinal = factory.getFlowFinalNodeForBreak(currentContext);
        // Attach comments
        commentBuilder.buildComment(flowFinal, getCommentInfo(stmt));
        return new ActivityNodesPins(flowFinal, flowFinal);
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
    public ActivityNodesPins buildContinueStatement(IASTContinueStatement stmt, ActivityContext currentContext) {
        // Create a flow towards a flow final node named "continue"
        FlowFinalNode flowFinal = factory.getFlowFinalNodeForContinue(currentContext);
        // Attach comments
        commentBuilder.buildComment(flowFinal, getCommentInfo(stmt));
        return new ActivityNodesPins(flowFinal, flowFinal);
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
    public ActivityNodesPins buildReturnStatement(IASTReturnStatement stmt, ActivityContext currentContext) {
        // Create an action for the return statement
        // and a flow towards the activity final node
        OpaqueAction returnAction = factory.createOpaqueAction(stmt.getRawSignature(), currentContext);
        commentBuilder.buildComment(returnAction, getCommentInfo(stmt));

        ActivityFinalNode finalNode = factory.createActivityFinalNode(currentContext);
        factory.addFlowTowardsActivityFinalNode(returnAction, finalNode, currentContext);
        return new ActivityNodesPins(returnAction, finalNode);
    }
}
