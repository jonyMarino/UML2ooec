/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Christophe Le Camus (CS) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.structural.test.addition;

import static org.eclipse.umlgen.reverse.c.structural.test.utils.TestUtils.getResourceInputStream;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestTypeDefStructNamed extends AbstractTest {
	@Test
	public void testTypeDefStructNamedInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testTypeDefStructNamedC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedStruct.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/typedef/named/struct/namedStruct.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/typedef/named/struct/TypeDefStructNamedC.uml");
	}

	@Test
	public void testTypeDefStructNamedInH() throws CoreException, InterruptedException {

		IProject project = createIProject("testTypeDefStructNamedH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedStruct.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/typedef/named/struct/namedStruct.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/typedef/named/struct/TypeDefStructNamedH.uml");
	}

}
