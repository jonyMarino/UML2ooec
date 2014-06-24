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

import org.eclipse.uml2.uml.ActivityNode;

public class ActivityNodesPins {
	private ActivityNode startNode;

	private ActivityNode endNode;

	public ActivityNodesPins() {

	}

	public ActivityNodesPins(ActivityNode startNode, ActivityNode endNode) {
		super();
		this.startNode = startNode;
		this.endNode = endNode;
	}

	public ActivityNode getStartNode() {
		return startNode;
	}

	public void setStartNode(ActivityNode startNode) {
		this.startNode = startNode;
	}

	public ActivityNode getEndNode() {
		return endNode;
	}

	public void setEndNode(ActivityNode endNode) {
		this.endNode = endNode;
	}
}
