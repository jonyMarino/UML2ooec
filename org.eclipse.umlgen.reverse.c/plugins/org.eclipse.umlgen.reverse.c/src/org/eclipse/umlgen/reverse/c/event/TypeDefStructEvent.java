/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * Abstract representation of an event related to a structure.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class TypeDefStructEvent extends StructEvent {

	private String redefinedStructName;

	private IASTNode source;

	public String getRedefinedStructName() {
		return this.redefinedStructName;
	}

	protected void setRedefinedStructName(String redefinedStructName) {
		this.redefinedStructName = redefinedStructName;
	}

	public IASTNode getSource() {
		return source;
	}

	public void setSource(IASTNode source) {
		this.source = source;
	}

	public static abstract class Builder<T extends TypeDefStructEvent> extends StructEvent.Builder<T> {

		public Builder<T> setRedefinedStruct(String name) {
			getEvent().setRedefinedStructName(name);
			return this;
		}

		public Builder<T> setSource(IASTNode source) {
			getEvent().setSource(source);
			return this;
		}
	}

}
