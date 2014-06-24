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

import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.CommentInfo;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

abstract public class AbstractBuilder {
	protected UMLActivityBuilder activityBuilder;

	protected UMLActivityFactory factory;

	protected CommentBuilder commentBuilder;

	protected Map<IASTNode, CommentInfo> nodesAndComments;

	public AbstractBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
			CommentBuilder commentBuilder) {
		this.activityBuilder = activityBuilder;
		this.factory = factory;
		this.commentBuilder = commentBuilder;
	}

	public void setStatementsAndComments(Map<IASTNode, CommentInfo> nodesAndComments) {
		this.nodesAndComments = nodesAndComments;
	}

	public CommentInfo getCommentInfo(IASTNode node) {
		return nodesAndComments.remove(node);
	}
}
