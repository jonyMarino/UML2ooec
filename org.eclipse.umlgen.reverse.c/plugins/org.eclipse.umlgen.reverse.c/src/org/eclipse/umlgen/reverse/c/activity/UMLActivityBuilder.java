/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity;

import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.beans.CommentInfo;
import org.eclipse.umlgen.reverse.c.activity.beans.LoopStatementWrapper;
import org.eclipse.umlgen.reverse.c.activity.builder.CommonStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.CompoundStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.FlowControlStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.IfStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.LabelStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.LoopStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.builder.SwitchStatementBuilder;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentsReconciler;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivitySanitizer;

/** An activity builder. */
public class UMLActivityBuilder {

    /** comments attached to nodes. */
    Map<IASTNode, CommentInfo> nodesAndComments;

    /** a UML activity factory. */
    private UMLActivityFactory factory;

    /** a switch statement builder. */
    private SwitchStatementBuilder switchStmtBuilder;

    /** a compound statement builder. */
    private CompoundStatementBuilder compoundStmtBuilder;

    /** an if statement builder. */
    private IfStatementBuilder ifStmtBuilder;

    /** a loop statement builder. */
    private LoopStatementBuilder loopStmtBuilder;

    /** a common statement builder. */
    private CommonStatementBuilder commonStmtBuilder;

    /** a label statement builder. */
    private LabelStatementBuilder labelStmtBuilder;

    /** a flow control statement builder. */
    private FlowControlStatementBuilder flowControlStmtBuilder;

    /** an activity sanitizer. */
    private UMLActivitySanitizer sanitizer;

    /** a comment reconciler. */
    private CommentsReconciler commentsReconciler;

    /** a comment builder. */
    private CommentBuilder commentBuilder;

    /** Constructor. */
    public UMLActivityBuilder() {
        factory = new UMLActivityFactory();
        commentBuilder = new CommentBuilder(factory);
        switchStmtBuilder = new SwitchStatementBuilder(this, factory, commentBuilder);
        compoundStmtBuilder = new CompoundStatementBuilder(this, factory, commentBuilder);
        ifStmtBuilder = new IfStatementBuilder(this, factory, commentBuilder);
        loopStmtBuilder = new LoopStatementBuilder(this, factory, commentBuilder);
        commonStmtBuilder = new CommonStatementBuilder(this, factory, commentBuilder);
        labelStmtBuilder = new LabelStatementBuilder(this, factory, commentBuilder);
        flowControlStmtBuilder = new FlowControlStatementBuilder(this, factory, commentBuilder);

        sanitizer = new UMLActivitySanitizer();

        commentsReconciler = new CommentsReconciler();
    }

    /**
     * Create an UML Activity based on a function definition.
     *
     * @param functionDefinition
     *            a function definition.
     * @return the UML Activity the activity
     */
    public static Activity build(IASTFunctionDefinition functionDefinition) {
        return new UMLActivityBuilder().createActivity(functionDefinition);
    }

    /**
     * Create an UML Activity based on a function definition.
     *
     * @param functionDefinition
     *            a function definition.
     * @return the UML activity the activity.
     */
    private Activity createActivity(IASTFunctionDefinition functionDefinition) {
        // Init comments information
        initComments(functionDefinition);

        // Initialisation of the activity
        String functionName = functionDefinition.getDeclarator().getName().toString();
        Activity activity = factory.createActivity(functionName);
        ActivityContext currentContext = new ActivityContext(activity);

        // Build the activity nodes corresponding to the function body
        IASTStatement body = functionDefinition.getBody();
        ActivityNodesPins bodyNodes = buildNodes(body, currentContext);

        // Creation of the initial node
        InitialNode initialNode = factory.createInitialNode(currentContext);

        // Creation of the flow from initial to final node
        // via built nodes if there are some
        ActivityFinalNode finalNode = null;
        if (bodyNodes.getStartNode() == null && bodyNodes.getEndNode() == null) {
            finalNode = factory.createActivityFinalNode(currentContext);
            factory.createControlFlow(initialNode, finalNode, currentContext);
        } else {
            factory.createControlFlow(initialNode, bodyNodes.getStartNode(), currentContext);
            if (!(bodyNodes.getEndNode() instanceof ActivityFinalNode)) {
                finalNode = factory.createActivityFinalNode(currentContext);
                factory.addFlowTowardsActivityFinalNode(bodyNodes.getEndNode(), finalNode, currentContext);
            } else {
                finalNode = (ActivityFinalNode)bodyNodes.getEndNode();
            }
        }

        // Add comments to the final node
        commentBuilder.buildComment(finalNode, nodesAndComments.remove(functionDefinition.getBody()));

        // Merge all successive opaque actions into just one
        sanitizer.sanitize(activity);
        return activity;
    }

    /**
     * Initialize comments.
     *
     * @param functionDefinition
     *            a function definition.
     */
    private void initComments(IASTFunctionDefinition functionDefinition) {
        // Get comments with their associated statements
        nodesAndComments = commentsReconciler.reconcile(functionDefinition);
        switchStmtBuilder.setStatementsAndComments(nodesAndComments);
        compoundStmtBuilder.setStatementsAndComments(nodesAndComments);
        ifStmtBuilder.setStatementsAndComments(nodesAndComments);
        loopStmtBuilder.setStatementsAndComments(nodesAndComments);
        commonStmtBuilder.setStatementsAndComments(nodesAndComments);
        labelStmtBuilder.setStatementsAndComments(nodesAndComments);
        flowControlStmtBuilder.setStatementsAndComments(nodesAndComments);
    }

    /**
     * Build the nodes.
     *
     * @param stmt
     *            The current statement.
     * @param currentContext
     *            The context.
     * @return ActivityNodesPins
     */
    // CHECKSTYLE:OFF
    public ActivityNodesPins buildNodes(IASTStatement stmt, ActivityContext currentContext) {
        // CHECKSTYLE:ON
        // switch to transfer treament to the appropriate class
        if (stmt instanceof IASTSwitchStatement) {
            return switchStmtBuilder.buildSwitchStatement((IASTSwitchStatement)stmt, currentContext);
        } else if (stmt instanceof IASTForStatement || stmt instanceof IASTWhileStatement
                || stmt instanceof IASTDoStatement) {
            return loopStmtBuilder.buildLoopStatement(new LoopStatementWrapper(stmt), currentContext);
        } else if (stmt instanceof IASTIfStatement) {
            return ifStmtBuilder.buildIfStatement((IASTIfStatement)stmt, currentContext);
        } else if (stmt instanceof IASTCompoundStatement) {
            return compoundStmtBuilder.buildCompoundStatement((IASTCompoundStatement)stmt, currentContext);
        } else if (stmt instanceof IASTCaseStatement) {
            return switchStmtBuilder.buildCaseStatement((IASTCaseStatement)stmt, currentContext);
        } else if (stmt instanceof IASTDefaultStatement) {
            return switchStmtBuilder.buildDefaultStatement((IASTDefaultStatement)stmt, currentContext);
        } else if (stmt instanceof IASTLabelStatement) {
            return labelStmtBuilder.buildLabelStatement((IASTLabelStatement)stmt, currentContext);
        } else if (stmt instanceof IASTContinueStatement) {
            return flowControlStmtBuilder.buildContinueStatement((IASTContinueStatement)stmt, currentContext);
        } else if (stmt instanceof IASTBreakStatement) {
            return flowControlStmtBuilder.buildBreakStatement((IASTBreakStatement)stmt, currentContext);
        } else if (stmt instanceof IASTReturnStatement) {
            return flowControlStmtBuilder.buildReturnStatement((IASTReturnStatement)stmt, currentContext);
        } else {
            return commonStmtBuilder.buildCommonStatement(stmt, currentContext);
        }
    }
}
