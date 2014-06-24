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
package org.eclipse.umlgen.gen.c.activity.test;

import org.eclipse.umlgen.gen.c.activity.test.util.AbstractTest;
import org.junit.Test;

public class TestIfElseIf extends AbstractTest {

	@Test
	public void testIfElseIf1() {
		testUmlActivityFile("ifElseIf/testIfElseIf1.uml");
	}

	@Test
	public void testIfElseIf2() {
		testUmlActivityFile("ifElseIf/testIfElseIf2.uml");
	}

	@Test
	public void testIfElseIf3() {
		testUmlActivityFile("ifElseIf/testIfElseIf3.uml");
	}
}
