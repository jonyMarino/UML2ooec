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

import static com.google.common.collect.Iterables.any;
import static com.google.common.collect.Lists.newArrayList;

import com.google.common.base.Predicate;

import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;

public class ASTUtilities {
	static public boolean hasDefaultStatement(IASTSwitchStatement stmt) {
		if (stmt.getBody() == null) {
			return false;
		}
		final Predicate<IASTNode> breakStatement = new Predicate<IASTNode>() {
			public boolean apply(IASTNode node) {
				return node instanceof IASTDefaultStatement;
			}
		};
		List<IASTNode> nodes = Arrays.asList(stmt.getBody().getChildren());
		return any(nodes, breakStatement);
	}

	static public boolean hasBreakStatement(List<IASTNode> nodes) {
		final Predicate<IASTNode> hasBreakStatement = new Predicate<IASTNode>() {
			public boolean apply(IASTNode node) {
				return node instanceof IASTBreakStatement;
			}
		};
		return any(nodes, hasBreakStatement);
	}

	static public boolean hasReturnStatement(List<IASTNode> nodes) {
		final Predicate<IASTNode> hasReturnStatement = new Predicate<IASTNode>() {
			public boolean apply(IASTNode node) {
				return node instanceof IASTReturnStatement;
			}
		};
		return any(nodes, hasReturnStatement);
	}

	static public List<List<IASTNode>> getStatementsGroupedByClause(IASTSwitchStatement switchStmt) {
		List<List<IASTNode>> groups = newArrayList();

		if (switchStmt.getBody() != null) {
			IASTNode[] nodes = switchStmt.getBody().getChildren();
			List<IASTNode> group = null;
			for (IASTNode astNode : nodes) {
				if (astNode instanceof IASTCaseStatement || astNode instanceof IASTDefaultStatement) {
					// When we meet a Case or default statement we create a new
					// group of statements
					group = newArrayList();
					groups.add(group);
				}
				group.add(astNode);
			}
		}
		return groups;
	}
}
