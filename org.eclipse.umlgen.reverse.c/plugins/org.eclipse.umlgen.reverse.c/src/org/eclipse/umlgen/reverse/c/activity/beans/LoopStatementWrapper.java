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

public class LoopStatementWrapper {
	private IASTStatement wrappedStatement;

	private IASTForStatement forStatement;

	private IASTWhileStatement whileStatement;

	private IASTDoStatement doStatement;

	private enum LoopType {
		FOR, DO, WHILE
	}

	private LoopType loopType;

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

	public IASTStatement getInitializerStatement() {
		if (isFor()) {
			return forStatement.getInitializerStatement();
		}
		return null;
	}

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
		}
		return condition;
	}

	public IASTExpression getIterationExpression() {
		if (isFor()) {
			return forStatement.getIterationExpression();
		}
		return null;
	}

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
		}
		return body;
	}

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
