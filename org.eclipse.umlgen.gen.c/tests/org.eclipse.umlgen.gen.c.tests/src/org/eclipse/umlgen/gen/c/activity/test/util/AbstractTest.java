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
package org.eclipse.umlgen.gen.c.activity.test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import junit.framework.TestCase;

import org.eclipse.umlgen.gen.c.files.activity.GenerateActivity;

public class AbstractTest extends TestCase {
	private static final String C_EXTENSION = "c";

	private static final String TEST_ROOT = "resource/activity/";

	public void testUmlActivityFile(String umlFilePath) {

		int dotPos = umlFilePath.lastIndexOf(".");
		String cFilePath = TEST_ROOT + umlFilePath.substring(0, dotPos + 1) + C_EXTENSION;
		String initialCFilePath = TEST_ROOT + umlFilePath.substring(0, dotPos) + "_expected." + C_EXTENSION;

		int slashPos = umlFilePath.lastIndexOf("/");
		String outputDir = TEST_ROOT + umlFilePath.substring(0, slashPos);

		// Delete the old file if it already exists
		File generatedFile = new File(cFilePath);
		if (generatedFile.exists()) {
			generatedFile.delete();
			assertFalse("File " + generatedFile.getPath() + " can not be removed before generation.",
					generatedFile.exists());
		}

		// Call generator
		GenerateActivity.main(new String[] {TEST_ROOT + umlFilePath, outputDir });

		assertTrue("File " + generatedFile.getPath() + " doesn't exist", generatedFile.exists());

		// Compare file contents
		assertEquals(getFileContents(initialCFilePath).replaceAll("\r\n", "\n"), getFileContents(cFilePath)
				.replaceAll("\r\n", "\n"));
	}

	private String getFileContents(String filename) {
		String contents = null;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename)).useDelimiter("\\Z");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found", e);
		}
		if (scanner != null) {
			try {
				contents = scanner.next();
			} catch (NoSuchElementException e) {
				contents = "";
			}
			scanner.close();
		}
		return contents;
	}
}
