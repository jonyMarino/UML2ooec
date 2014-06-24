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

public class TestReturn extends AbstractTest {

	@Test
	public void testReturnInElse() {
		testUmlActivityFile("returnStatement/returnInElse.uml");
	}

	@Test
	public void testReturnInIf() {
		testUmlActivityFile("returnStatement/returnInIf.uml");
	}

	@Test
	public void testReturnInNestedForAndIf() {
		testUmlActivityFile("returnStatement/returnInNestedForAndIf.uml");
	}

	@Test
	public void testReturnInNestedForAndIf2() {
		testUmlActivityFile("returnStatement/returnInNestedForAndIf2.uml");
	}

	@Test
	public void testReturnInSwitch() {
		testUmlActivityFile("returnStatement/returnInSwitch.uml");
	}

}
