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

public class TestExistingCode extends AbstractTest {

	@Test
	public void testGioFileCtor() {
		testCFile("existingCode/testGioFileCtor.c", false);
	}

	@Test
	public void testGioMemoryFileCtorWithAmpersand() {
		testCFile("existingCode/testGioMemoryFileCtorWithAmpersand.c", false);
	}

	@Test
	public void testGioMemoryFileSeek() {
		testCFile("existingCode/testGioMemoryFileSeek.c", false);
	}

	@Test
	public void test_XtTableAddConverter() {
		testCFile("existingCode/test_XtTableAddConverter.c", false);
	}

	@Test
	public void testCoreSetValues() {
		testCFile("existingCode/testCoreSetValues.c", false);
	}

	@Test
	public void testBenchmark() {
		testCFile("existingCode/testBenchmark.c", false);
	}

	@Test
	public void testG_object_notify_queue_thaw() {
		testCFile("existingCode/testG_object_notify_queue_thaw.c", false);
	}
}
