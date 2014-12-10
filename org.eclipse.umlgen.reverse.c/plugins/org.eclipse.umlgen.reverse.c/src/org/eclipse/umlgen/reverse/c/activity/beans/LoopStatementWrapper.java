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
package org.eclipse.umlgen.reverse.c.activity.beans;

import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

/**
 * Loop statement wrapper.
 */
public class LoopStatementWrapper {

    /** wrapped statement. */
    private IASTStatement wrappedStatement;

    /** for statement. */
    private IASTForStatement forStatement;

    /** while statement. */
    private IASTWhileStatement whileStatement;

    /** do statement. */
    private IASTDoStatement doStatement;

    /** loop type. */
    private enum LoopType {
        /** for. */
        FOR,
        /** do. */
        DO,
        /** while. */
        WHILE
    }

    /** loop type. */
    private LoopType loopType;

    /**
     * Constructor.
     *
     * @param wrappedStatement
     *            the wrapped statement.
     */
    public LoopStatementWrapper(IASTStatement wrappedStatement) {
        this.wrappedStatement = wrappedStatement;
        if (wrappedStatement instanceof IASTForStatement) {
            this.forStatement = (IASTForStatement)wrappedStatement;
            loopType = LoopType.FOR;
        } else if (wrappedStatement instanceof IASTWhileStatement) {
            this.whileStatement = (IASTWhileStatement)wrappedStatement;
            loopType = LoopType.WHILE;
        } else if (wrappedStatement instanceof IASTDoStatement) {
            this.doStatement = (IASTDoStatement)wrappedStatement;
            loopType = LoopType.DO;
        }
    }

    public IASTStatement getWrappedStatement() {
        return wrappedStatement;
    }

    /**
     * Get the initializer statement.
     *
     * @return The statement
     */
    public IASTStatement getInitializerStatement() {
        if (isFor()) {
            return forStatement.getInitializerStatement();
        }
        return null;
    }

    /**
     * Get the condition expression.
     *
     * @return The expression.
     */
    public IASTExpression getConditionExpression() {
        IASTExpression condition = null;
        switch (loopType) {
            case FOR:
                condition = forStatement.getConditionExpression();
                break;
            case DO:
                condition = doStatement.getCondition();
                break;
            case WHILE:
                condition = whileStatement.getCondition();
                break;
            default:
        }
        return condition;
    }

    /**
     * Get the iteration expression.
     *
     * @return the expression.
     */
    public IASTExpression getIterationExpression() {
        if (isFor()) {
            return forStatement.getIterationExpression();
        }
        return null;
    }

    /**
     * Get body.
     *
     * @return The body statement.
     */
    public IASTStatement getBody() {
        IASTStatement body = null;
        switch (loopType) {
            case FOR:
                body = forStatement.getBody();
                break;
            case DO:
                body = doStatement.getBody();
                break;
            case WHILE:
                body = whileStatement.getBody();
                break;
            default:
        }
        return body;
    }

    /**
     * Get the name of the loop node.
     * 
     * @return The name.
     */
    public String getLoopNodeName() {
        String nodeName = wrappedStatement.getRawSignature();
        int bodyOffset = 0;
        int loopOffset = wrappedStatement.getFileLocation().getNodeOffset();
        switch (loopType) {
            case FOR:
                // Get offset for the loop body
                bodyOffset = forStatement.getBody().getFileLocation().getNodeOffset();
                nodeName = nodeName.substring(0, bodyOffset - loopOffset);
                break;
            case WHILE:
                // Get offset for the loop body
                bodyOffset = whileStatement.getBody().getFileLocation().getNodeOffset();
                nodeName = nodeName.substring(0, bodyOffset - loopOffset);
                break;
            case DO:
                // Get ending offset for the loop body
                bodyOffset = doStatement.getBody().getFileLocation().getNodeOffset()
                + doStatement.getBody().getFileLocation().getNodeLength();
                nodeName = "do ..." + nodeName.substring(bodyOffset - loopOffset);
                break;
            default:
                break;
        }

        return nodeName.trim();
    }

    public boolean isTestedFirst() {
        return loopType == LoopType.FOR || loopType == LoopType.WHILE;
    }

    public boolean isFor() {
        return loopType == LoopType.FOR;
    }

    public boolean isDo() {
        return loopType == LoopType.DO;
    }

    public boolean isWhile() {
        return loopType == LoopType.WHILE;
    }
}
