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

import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.beans.LoopStatementWrapper;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/** The loop statement builder. */
public class LoopStatementBuilder extends AbstractBuilder {

    /**
     * Constructor.
     *
     * @param activityBuilder
     *            the activity builder.
     * @param factory
     *            The activity activity.
     * @param commentBuilder
     *            the comment builder.
     */
    public LoopStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
            CommentBuilder commentBuilder) {
        super(activityBuilder, factory, commentBuilder);
    }

    /**
     * This builds activity nodes pins from the given statement and activity context.
     *
     * @param wrapperStmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     * @return Activity nodes pins.
     */
    public ActivityNodesPins buildLoopStatement(LoopStatementWrapper wrapperStmt,
            ActivityContext currentContext) {
        LoopNode loopNode = factory.createLoopNode(wrapperStmt.getLoopNodeName(),
                wrapperStmt.isTestedFirst(), currentContext);
        // Attach comments to the LoopNode
        commentBuilder.buildComment(loopNode, getCommentInfo(wrapperStmt.getWrappedStatement()));

        ActivityContext loopNodeContext = new ActivityContext(loopNode);
        createInitializer(loopNode, wrapperStmt, loopNodeContext);
        createTest(loopNode, wrapperStmt, loopNodeContext);
        createIteration(loopNode, wrapperStmt, loopNodeContext);
        createBody(loopNode, wrapperStmt, loopNodeContext);
        return new ActivityNodesPins(loopNode, loopNode);
    }

    /**
     * This creates the initializer.
     *
     * @param loopNode
     *            The loop node.
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     */
    private void createInitializer(LoopNode loopNode, LoopStatementWrapper stmt,
            ActivityContext currentContext) {
        IASTStatement initializerStmt = stmt.getInitializerStatement();
        if (initializerStmt != null) {
            String initializerBody = initializerStmt.getRawSignature();
            // Remove the trailing ';'
            initializerBody = initializerBody.substring(0, initializerBody.length() - 1);
            OpaqueAction setupAction = factory.createOpaqueAction(initializerBody, currentContext);
            loopNode.getSetupParts().add(setupAction);
        }
    }

    /**
     * This creates a test.
     *
     * @param loopNode
     *            The loop node.
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     */
    private void createTest(LoopNode loopNode, LoopStatementWrapper stmt, ActivityContext currentContext) {
        String testBody = "";
        if (stmt.getConditionExpression() != null) {
            testBody = stmt.getConditionExpression().getRawSignature();
        }

        OpaqueAction testAction = factory.createOpaqueAction(testBody, currentContext);
        OutputPin decider = factory.createOutputPin();
        testAction.getOutputValues().add(decider);
        loopNode.getTests().add(testAction);
        loopNode.setDecider(decider);
    }

    /**
     * This creates an iteration.
     *
     * @param loopNode
     *            The loop node.
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     */
    private void createIteration(LoopNode loopNode, LoopStatementWrapper stmt, ActivityContext currentContext) {
        if (stmt.isFor()) {
            String iterationBody = "";
            if (stmt.getIterationExpression() != null) {
                iterationBody = stmt.getIterationExpression().getRawSignature();
            }

            OpaqueAction iterationAction = factory.createOpaqueAction(iterationBody, currentContext);
            OutputPin loopVariable = factory.createOutputPin();
            iterationAction.getOutputValues().add(loopVariable);
            loopNode.getLoopVariables().add(loopVariable);
        }
    }

    /**
     * This creates a body.
     *
     * @param loopNode
     *            The loop node.
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The activity context.
     */
    private void createBody(LoopNode loopNode, LoopStatementWrapper stmt, ActivityContext currentContext) {
        if (!(stmt.getBody() instanceof IASTNullStatement)) {
            ActivityNodesPins bodyNodes = activityBuilder.buildNodes(stmt.getBody(), currentContext);
            ActivityNode firstNode = bodyNodes.getStartNode();
            ActivityNode lastNode = bodyNodes.getEndNode();

            // Ensure the first node is a subtype of ExecutableNode and attach
            // this node as bodyPart
            ExecutableNode bodyFirstNode = factory.ensureStartNodeIsExecutable(firstNode, currentContext);
            loopNode.getBodyParts().add(bodyFirstNode);

            // Add a ControlFlow towards a FlowFinalNode
            FlowFinalNode flowFinal = factory.getFlowFinalNodeForEndOfLoop(currentContext);
            factory.createControlFlow(lastNode, flowFinal, currentContext);
        }
    }
}
