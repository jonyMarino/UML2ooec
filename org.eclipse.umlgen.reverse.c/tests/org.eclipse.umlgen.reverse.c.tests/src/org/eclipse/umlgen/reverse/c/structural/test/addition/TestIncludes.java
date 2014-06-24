/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.structural.test.addition;

import static org.eclipse.umlgen.reverse.c.structural.test.utils.TestUtils.getResourceInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestIncludes extends AbstractTest {

	@Test
	public void testIncludesStdLibInC() throws CoreException, InterruptedException {
		IProject project = createIProject("testincludesExtC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("file.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);
		setEditorContent(editor, " ");
		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/includes/file.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/includes/includesExtC.uml");
	}

	@Test
	public void testIncludesStdLibInH() throws CoreException, InterruptedException {
		IProject project = createIProject("testincludesExtH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("file.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);
		setEditorContent(editor, " ");
		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/includes/file.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/includes/includesExtH.uml");
	}

	@Test
	public void testsameNameExternalInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testsameNameExternalC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("sameNameExternal.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);
		setEditorContent(editor, " ");
		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/includes/sameNameExternal.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/includes/sameNameExternalC.uml");
	}

	@Test
	public void testsameNameInternalInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testsameNameInternalC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("sameNameInternal.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);
		setEditorContent(editor, " ");
		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/includes/sameNameInternal.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/includes/sameNameInternalC.uml");
	}
}
