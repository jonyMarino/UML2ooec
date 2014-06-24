/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS) - initial API and implementation
 *     Christophe Le Camus, Sebastien Gabel (CS) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.util.CommentFormatter;

public abstract class CommentEvent extends CModelChangedEvent {
	private String body;

	private IASTNode parent;

	private IASTComment source;

	public String getBody() {
		return body;
	}

	protected void setBody(String body) {
		this.body = CommentFormatter.unwrap(body);
	}

	public IASTNode getParent() {
		return parent;
	}

	protected void setParent(IASTNode parent) {
		this.parent = parent;
	}

	public IASTComment getSource() {
		return source;
	}

	public void setSource(IASTComment source) {
		this.source = source;
	}

	public String getKey() {
		if (!(parent instanceof IASTTranslationUnit)) {
			if (source.getFileLocation().getStartingLineNumber() == source.getFileLocation()
					.getEndingLineNumber()) {
				if (source.getFileLocation().getStartingLineNumber() == parent.getFileLocation()
						.getStartingLineNumber()) {
					if (source.getFileLocation().getNodeOffset() < parent.getFileLocation().getNodeOffset()) {
						return getTranslationUnit().isHeaderUnit() ? AnnotationConstants.H_INLINE_BEFORE
								: AnnotationConstants.C_INLINE_BEFORE;
					} else {
						return getTranslationUnit().isHeaderUnit() ? AnnotationConstants.H_INLINE_AFTER
								: AnnotationConstants.C_INLINE_AFTER;
					}
				}
			}
		}

		return AnnotationConstants.DOCUMENTATION_KEY;
	}

	public abstract static class Builder<T extends CommentEvent> extends CModelChangedEvent.Builder<CommentEvent> {

		public Builder<T> setBody(String body) {
			getEvent().setBody(body);
			return this;
		}

		public Builder<T> setParent(IASTNode parent) {
			getEvent().setParent(parent);
			return this;
		}

		public Builder<T> setSource(IASTComment comment) {
			getEvent().setSource(comment);
			return this;
		}
	}
}
