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
package org.eclipse.umlgen.reverse.c.activity.test;

import org.eclipse.umlgen.reverse.c.activity.test.utils.AbstractTest;
import org.junit.Test;

public class TestDoWhileLoop extends AbstractTest {

	@Test
	public void empty() {
		testCFile("doWhileLoop/empty.c", false);
	}

	@Test
	public void standard() {
		testCFile("doWhileLoop/standard.c", false);
	}

	@Test
	public void nested() {
		testCFile("doWhileLoop/nested.c", false);
	}

	@Test
	public void ifAsFirst() {
		testCFile("doWhileLoop/ifAsFirst.c", false);
	}
}
