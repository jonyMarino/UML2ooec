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

public class TestStorageClassStatic1to3 extends AbstractTest {

	@Test
	public void testStorageClassstatic1InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic1C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static1.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static1.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static1C.uml");

	}

	@Test
	public void testStorageClassstatic1InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic1H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static1.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static1.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static1H.uml");

	}

	/***********************************************/

	@Test
	public void testStorageClassstatic2InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic2C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static2.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static2.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static2C.uml");

	}

	@Test
	public void testStorageClassstatic2InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic2H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static2.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static2.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static2H.uml");

	}

	/***********************************************/

	@Test
	public void testStorageClassstatic3InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic3C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static3.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static3.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static3C.uml");

	}

	@Test
	public void testStorageClassstatic3InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/teststatic3H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("static3.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/static3.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/static3H.uml");

	}

}
