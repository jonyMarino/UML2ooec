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

public class TestSwitchConditional extends AbstractTest {

	@Test
	public void standard() {
		testCFile("switchConditional/standard.c", false);
	}

	@Test
	public void fallthrough() {
		testCFile("switchConditional/fallthrough.c", false);
	}

	@Test
	public void noDefault() {
		testCFile("switchConditional/noDefault.c", false);
	}

	@Test
	public void withoutBreak() {
		testCFile("switchConditional/withoutBreak.c", false);
	}

	@Test
	public void caseBreak() {
		testCFile("switchConditional/caseBreak.c", false);
	}

	@Test
	public void special() {
		testCFile("switchConditional/special.c", false);
	}

	@Test
	public void bigCaseClause() {
		testCFile("switchConditional/bigCaseClause.c", false);
	}

	@Test
	public void withBreakAndContinue() {
		testCFile("switchConditional/withBreakAndContinue.c", false);
	}
}
