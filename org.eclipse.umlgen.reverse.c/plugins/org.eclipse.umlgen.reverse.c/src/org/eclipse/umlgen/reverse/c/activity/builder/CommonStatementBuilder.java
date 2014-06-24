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

import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityContext;
import org.eclipse.umlgen.reverse.c.activity.beans.ActivityNodesPins;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

public class CommonStatementBuilder extends AbstractBuilder {

	public CommonStatementBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
			CommentBuilder commentBuilder) {
		super(activityBuilder, factory, commentBuilder);
	}

	public ActivityNodesPins buildCommonStatement(IASTStatement stmt, ActivityContext currentContext) {
		OpaqueAction opaqueAction = factory.createOpaqueAction(stmt.getRawSignature(), currentContext);
		commentBuilder.buildComment(opaqueAction, getCommentInfo(stmt));
		return new ActivityNodesPins(opaqueAction, opaqueAction);
	}
}
