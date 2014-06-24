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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestDefine extends AbstractTest {

	@Test
	public void testDefineEmptyInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testdefineemptyC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/defineemptyC.uml");
	}

	@Test
	public void testDefineIntInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testintDefineC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE 45");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/intDefineC.uml");
	}

	@Test
	public void testDefineStringInC() throws CoreException, InterruptedException {

		IProject project = createIProject("teststringDefineC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE \"A STRING\"");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/stringDefineC.uml");
	}

	@Test
	public void testDefineEmptyInH() throws CoreException, InterruptedException {

		IProject project = createIProject("testdefineemptyH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/defineemptyH.uml");
	}

	@Test
	public void testDefineIntInH() throws CoreException, InterruptedException {

		IProject project = createIProject("testintDefineH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE 45");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/intDefineH.uml");
	}

	@Test
	public void testDefineStringInH() throws CoreException, InterruptedException {

		IProject project = createIProject("teststringDefineH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("aFile.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor, "#define MY_DEFINE \"A STRING\"");

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/define/stringDefineH.uml");
	}
}
