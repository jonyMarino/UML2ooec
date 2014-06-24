/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Christophe Le Camus (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;

public abstract class EnumerationEvent extends AbstractTypedEvent {

	private IASTEnumerator[] enumerators;

	public IASTEnumerator[] getEnumerators() {
		return this.enumerators;
	}

	protected void setEnumerators(IASTEnumerator[] enumerators) {
		this.enumerators = enumerators;
	}

	public static abstract class Builder<T extends EnumerationEvent> extends AbstractTypedEvent.Builder<T> {

		public Builder<T> setEnumerators(IASTEnumerator[] enumerators) {
			getEvent().setEnumerators(enumerators);
			return this;
		}
	}

}
