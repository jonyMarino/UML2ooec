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

public class TestExistingCode extends AbstractTest {

	@Test
	public void testBenchmark() {
		testUmlActivityFile("existingCode/testBenchmark.uml");
	}

	@Test
	public void testCoreSetValues() {
		testUmlActivityFile("existingCode/testCoreSetValues.uml");
	}

	@Test
	public void testGioFileCtor() {
		testUmlActivityFile("existingCode/testGioFileCtor.uml");
	}

	@Test
	public void testGioMemoryFileCtorWithAmpersand() {
		testUmlActivityFile("existingCode/testGioMemoryFileCtorWithAmpersand.uml");
	}

	@Test
	public void testGioMemoryFileSeek() {
		testUmlActivityFile("existingCode/testGioMemoryFileSeek.uml");
	}

	@Test
	public void testG_object_notify_queue_thaw() {
		testUmlActivityFile("existingCode/testG_object_notify_queue_thaw.uml");
	}

	@Test
	public void test_XtTableAddConverter() {
		testUmlActivityFile("existingCode/test_XtTableAddConverter.uml");
	}
}
