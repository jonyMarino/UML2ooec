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

public class TestSimpleEnumUnNamed extends AbstractTest {
	@Test
	public void testEnumwithSomeValuedLiteralInC() throws CoreException, InterruptedException {

		/*** With Some Valued Literals ***/
		IProject project = createIProject("testEnumwithSomeValuedLiteralC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("withSomeValuedLiteral.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/withSomeValuedLiteral.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumwithSomeValuedLiteralC.uml");
	}

	@Test
	public void testEnumAnonymousC() throws CoreException, InterruptedException {

		/*** UnNamed Enum ****/

		IProject project = createIProject("testEnumanonymousC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymous.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/enums/anonymous.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousC.uml");

	}

	@Test
	public void testAnonymousWithVarDeclC() throws CoreException, InterruptedException {

		/*** UnNamed With Var Decl ****/

		IProject project = createIProject("testEnumanonymousWithVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithVarDeclC.uml");

	}

	@Test
	public void testEnumAnonymousWithVarDeclAndInitC() throws CoreException, InterruptedException {

		/*** UnNamed With Var Decl and Init ****/

		IProject project = createIProject("testEnumanonymousWithVarDeclAndInitC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithVarDeclAndInitC.uml");

	}

	@Test
	public void testEnumAnonymousWithArrayVarDeclC() throws CoreException, InterruptedException {

		/*** UnNamed With Array Var Decl ****/
		IProject project = createIProject("testEnumanonymousWithArrayVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithArrayVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithArrayVarDeclC.uml");

	}

	@Test
	public void testAnonymousWithArrayVarDeclAndInitC() throws CoreException, InterruptedException {

		/*** UnNamed With Array Var Decl and Init ****/

		IProject project = createIProject("testEnumanonymousWithArrayVarDeclAndInitC",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(
				editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithArrayVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithArrayVarDeclAndInitC.uml");

	}

	@Test
	public void testEnumwithSomeValuedLiteralInH() throws CoreException, InterruptedException {

		/*** With Some Valued Literals ***/
		IProject project = createIProject("testEnumwithSomeValuedLiteralH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("withSomeValuedLiteral.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/withSomeValuedLiteral.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumwithSomeValuedLiteralH.uml");

	}

	@Test
	public void testEnumAnonymousH() throws CoreException, InterruptedException {

		/*** UnNamed Enum ****/

		IProject project = createIProject("testEnumanonymousH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymous.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/enums/anonymous.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousH.uml");

	}

	@Test
	public void testAnonymousWithVarDeclH() throws CoreException, InterruptedException {

		/*** UnNamed With Var Decl ****/

		IProject project = createIProject("testEnumanonymousWithVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithVarDeclH.uml");

	}

	@Test
	public void testEnumAnonymousWithVarDeclAndInitH() throws CoreException, InterruptedException {

		/*** UnNamed With Var Decl and Init ****/

		IProject project = createIProject("testEnumanonymousWithVarDeclAndInitH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithVarDeclAndInitH.uml");

	}

	@Test
	public void testEnumAnonymousWithArrayVarDeclH() throws CoreException, InterruptedException {

		/*** UnNamed With Array Var Decl ****/

		IProject project = createIProject("testEnumanonymousWithArrayVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithArrayVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithArrayVarDeclH.uml");

	}

	@Test
	public void testAnonymousWithArrayVarDeclAndInitH() throws CoreException, InterruptedException {

		/*** UnNamed With Array Var Decl and Init ****/

		IProject project = createIProject("testEnumanonymousWithArrayVarDeclAndInitH",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymousWithArrayVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(
				editor,
				getResourceInputStream("/resource/structural/addition/enums/anonymousWithArrayVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/enums/EnumanonymousWithArrayVarDeclAndInitH.uml");

	}

}
