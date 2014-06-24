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

public class TestStruct2 extends AbstractTest {

	@Test
	public void testanonymousWithArrayVarDeclAndInitInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithArrayVarDeclAndInitC",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(
				editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithArrayVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithArrayVarDeclAndInitC.uml");

	}

	@Test
	public void testanonymousWithArrayVarDeclAndInitInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithArrayVarDeclAndInitH",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(
				editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithArrayVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithArrayVarDeclAndInitH.uml");

	}

	@Test
	public void testanonymousWithArrayVarDeclInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithArrayVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithArrayVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithArrayVarDeclC.uml");

	}

	@Test
	public void testanonymousWithArrayVarDeclInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithArrayVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithArrayVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithArrayVarDeclH.uml");

	}

	@Test
	public void testanonymousWithVarDeclC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithVarDeclC.uml");

	}

	@Test
	public void testanonymousWithVarDeclInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithVarDeclH.uml");

	}

	@Test
	public void testanonymousWithVarDeclAndInitInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithVarDeclAndInitC",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithVarDeclAndInitC.uml");

	}

	@Test
	public void testanonymousWithVarDeclAndInitInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousWithVarDeclAndInitH",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/anonymousWithVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousWithVarDeclAndInitH.uml");

	}

}
