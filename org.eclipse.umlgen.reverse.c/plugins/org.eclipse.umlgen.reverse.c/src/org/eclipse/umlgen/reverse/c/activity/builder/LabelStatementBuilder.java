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

import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

public class LabelStatementBuilder extends AbstractBuilder {
	public LabelStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
			CommentBuilder commentBuilder) {
		super(activityBuilder, factory, commentBuilder);
	}

	public ActivityNodesPins buildLabelStatement(IASTLabelStatement stmt, ActivityContext currentContext) {
		// Extract only the label and the ":"
		String raw = stmt.getRawSignature();
		int colonPos = raw.indexOf(':', stmt.getName().toString().length());
		String body = raw.substring(0, colonPos + 1);

		OpaqueAction labelAction = factory.createOpaqueAction(body, currentContext);
		commentBuilder.buildComment(labelAction, getCommentInfo(stmt));

		// Build the nodes for the nested statement
		ActivityNodesPins nestedNodes = activityBuilder.buildNodes(stmt.getNestedStatement(), currentContext);

		// Create the flow between the nodes
		factory.createControlFlow(labelAction, nestedNodes.getStartNode(), currentContext);

		return new ActivityNodesPins(labelAction, nestedNodes.getEndNode());
	}
}
