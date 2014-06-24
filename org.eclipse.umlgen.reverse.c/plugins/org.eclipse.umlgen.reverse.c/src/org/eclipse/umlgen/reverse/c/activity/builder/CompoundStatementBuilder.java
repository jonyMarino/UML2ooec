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

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

public class CompoundStatementBuilder extends AbstractBuilder {

	public CompoundStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
			CommentBuilder commentBuilder) {
		super(activityBuilder, factory, commentBuilder);
	}

	public ActivityNodesPins buildCompoundStatement(IASTCompoundStatement stmt, ActivityContext currentContext) {
		ActivityNode previousNode = null;
		ActivityNode firstNode = null;

		if (stmt.getStatements().length == 0) {
			firstNode = factory.createOpaqueAction("", currentContext);
			previousNode = firstNode;
		} else {
			IASTStatement[] statements = stmt.getStatements();
			for (int i = 0; i < statements.length; i++) {
				IASTStatement childStmt = statements[i];
				// Create the nodes for the current statement
				ActivityNodesPins nodes = activityBuilder.buildNodes(childStmt, currentContext);

				if (i == 0) {
					firstNode = nodes.getStartNode();
				}

				// link with previous node
				factory.createControlFlow(previousNode, nodes.getStartNode(), currentContext);

				previousNode = nodes.getEndNode();
			}
		}
		return new ActivityNodesPins(firstNode, previousNode);
	}
}
