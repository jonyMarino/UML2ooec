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

public class TestReturn extends AbstractTest {

	@Test
	public void returnInNestedForAndIf() {
		testCFile("returnStatement/returnInNestedForAndIf.c", false);
	}

	@Test
	public void returnInNestedForAndIf2() {
		testCFile("returnStatement/returnInNestedForAndIf2.c", false);
	}

	@Test
	public void returnInSwitch() {
		testCFile("returnStatement/returnInSwitch.c", false);
	}

	@Test
	public void returnInIf() {
		testCFile("returnStatement/returnInIf.c", false);
	}

	@Test
	public void returnInElse() {
		testCFile("returnStatement/returnInElse.c", false);
	}
}
