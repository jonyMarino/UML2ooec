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

public class TestSimpleEnumNamed extends AbstractTest {
	@Test
	public void TestSimpleEnumNamedInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testwithAllValuedLiteralC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("withAllValuedLiteral.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/withAllValuedLiteral.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/withAllValuedLiteralC.uml");

		/*** ****/
	}

	@Test
	public void testEnumCNamedInC() throws CoreException, InterruptedException {

		IProject project = createIProject("testnamedC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("named.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/enums/named.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedC.uml");

	}

	@Test
	public void testEnumNamedWithVarDeclC() throws CoreException, InterruptedException {

		/*** Named With Var Decl ****/

		IProject project = createIProject("testnamedWithVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithVarDeclC.uml");

	}

	@Test
	public void testEnumNamedWithVarDeclAndInitC() throws CoreException, InterruptedException {

		/*** Named With Var Decl and Init ****/

		IProject project = createIProject("testnamedWithVarDeclAndInitC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDeclAndInit.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithVarDeclAndInitC.uml");

	}

	@Test
	public void testEnumNamedWithArrayVarDeclC() throws CoreException, InterruptedException {

		/*** Named With Array Var Decl ****/

		IProject project = createIProject("testnamedWithArrayVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithArrayVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithArrayVarDeclC.uml");

	}

	@Test
	public void testEnumNamedWithArrayVarDeclAndInitC() throws CoreException, InterruptedException {

		/*** Named With Array Var Decl and Init ****/

		IProject project = createIProject("testnamedWithArrayVarDeclAndInitC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithArrayVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithArrayVarDeclAndInitC.uml");

	}

	@Test
	public void TestSimpleEnumNamedInH() throws CoreException, InterruptedException {

		IProject project = createIProject("testwithAllValuedLiteralH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("withAllValuedLiteral.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/withAllValuedLiteral.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/withAllValuedLiteralH.uml");

	}

	@Test
	public void testEnumHNamedInH() throws CoreException, InterruptedException {

		/*** ****/

		IProject project = createIProject("testnamedH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("named.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/enums/named.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedH.uml");
	}

	@Test
	public void testEnumNamedWithVarDeclH() throws CoreException, InterruptedException {

		/*** Named With Var Decl ****/

		IProject project = createIProject("testnamedWithVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithVarDeclH.uml");
	}

	@Test
	public void testEnumNamedWithVarDeclAndInitH() throws CoreException, InterruptedException {

		/*** Named With Var Decl and Init ****/

		IProject project = createIProject("testnamedWithVarDeclAndInitH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDeclAndInit.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithVarDeclAndInitH.uml");

	}

	@Test
	public void testEnumNamedWithArrayVarDeclH() throws CoreException, InterruptedException {

		/*** Named With Array Var Decl ****/

		IProject project = createIProject("testnamedWithArrayVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithArrayVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithArrayVarDeclH.uml");

	}

	@Test
	public void testEnumNamedWithArrayVarDeclAndInitH() throws CoreException, InterruptedException {

		/*** Named With Array Var Decl and Init ****/

		IProject project = createIProject("testnamedWithArrayVarDeclAndInitH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/namedWithArrayVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/namedWithArrayVarDeclAndInitH.uml");

	}

}
