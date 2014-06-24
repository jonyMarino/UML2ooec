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

public class TestStruct1 extends AbstractTest {

	@Test
	public void testNamedStructDeclarationInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructNamedC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("named.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/struct/named.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructNamedC.uml");

	}

	@Test
	public void testNamedStructDeclarationInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructNamedH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("named.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/struct/named.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructNamedH.uml");

	}

	@Test
	public void testNamedWithArrayVarDeclInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithArrayVarDeclC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDecl.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithArrayVarDecl.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithArrayVarDeclC.uml");

	}

	@Test
	public void testNamedWithArrayVarDeclInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithArrayVarDeclH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDecl.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithArrayVarDecl.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithArrayVarDeclH.uml");

	}

	@Test
	public void testNamedWithArrayVarDeclAndInitInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithArrayVarDeclAndInitC",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDeclAndInit.c"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithArrayVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithArrayVarDeclAndInitC.uml");

	}

	@Test
	public void testNamedWithArrayVarDeclAndInitInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithArrayVarDeclAndInitH",
				new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithArrayVarDeclAndInit.h"),
				new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithArrayVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithArrayVarDeclAndInitH.uml");

	}

	@Test
	public void testNamedWithVarDeclAndInitInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithVarDeclAndInitC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDeclAndInit.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithVarDeclAndInit.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithVarDeclAndInitC.uml");

	}

	@Test
	public void testNamedWithVarDeclAndInitInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructnamedWithVarDeclAndInitH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("namedWithVarDeclAndInit.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/namedWithVarDeclAndInit.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructnamedWithVarDeclAndInitH.uml");

	}

	@Test
	public void testpointersMembersInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testpointersMembersC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("pointersMembers.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/pointersMembers.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/pointersMembersC.uml");

	}

	@Test
	public void testpointersMembersInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testpointersMembersH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("pointersMembers.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/pointersMembers.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/pointersMembersH.uml");

	}

	@Test
	public void teststructMembersInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststructMembersC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("structMembers.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/structMembers.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/structMembersC.uml");

	}

	@Test
	public void teststructMembersInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststructMembersH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("structMembers.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/structMembers.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/structMembersH.uml");

	}

	@Test
	public void testtypeDefMembersInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testtypeDefMembersC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("typeDefMembers.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/typeDefMembers.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/typeDefMembersC.uml");

	}

	@Test
	public void testtypeDefMembersInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testtypeDefMembersH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("typeDefMembers.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/struct/typeDefMembers.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/typeDefMembersH.uml");

	}

	@Test
	public void testanonymousInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymous.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/struct/anonymous.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousC.uml");

	}

	@Test
	public void testanonymousInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testStructanonymousH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("anonymous.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor, getResourceInputStream("/resource/structural/addition/struct/anonymous.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/struct/StructanonymousH.uml");

	}

}
