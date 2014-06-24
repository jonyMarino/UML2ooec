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

import java.util.List;

import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.StructuredActivityNode;

public class ActivityContext {
	private List<ActivityNode> nodes;

	private List<ActivityEdge> edges;

	public ActivityContext(Activity activity) {
		nodes = activity.getNodes();
		edges = activity.getEdges();
	}

	public ActivityContext(StructuredActivityNode node) {
		nodes = node.getNodes();
		edges = node.getEdges();
	}

	public List<ActivityNode> getNodes() {
		return nodes;
	}

	public void addNode(ActivityNode node) {
		getNodes().add(node);
	}

	public List<ActivityEdge> getEdges() {
		return edges;
	}

	public void addEdge(ActivityEdge edge) {
		getEdges().add(edge);
	}
}
