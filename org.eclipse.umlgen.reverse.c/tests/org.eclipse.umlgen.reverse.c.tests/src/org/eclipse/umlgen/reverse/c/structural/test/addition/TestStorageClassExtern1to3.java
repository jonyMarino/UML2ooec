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

public class TestStorageClassExtern1to3 extends AbstractTest {

	@Test
	public void testStorageClassExtern1InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern1C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern1.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern1.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern1C.uml");

	}

	@Test
	public void testStorageClassExtern1InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern1H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern1.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern1.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern1H.uml");

	}

	/***********************************************/

	@Test
	public void testStorageClassExtern2InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern2C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern2.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern2.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern2C.uml");

	}

	@Test
	public void testStorageClassExtern2InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern2H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern2.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern2.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern2H.uml");

	}

	/***********************************************/

	@Test
	public void testStorageClassExtern3InC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern3C", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern3.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern3.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern3C.uml");

	}

	@Test
	public void testStorageClassExtern3InH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testextern3H", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("extern3.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/storageClasses/extern3.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/storageClasses/extern3H.uml");

	}

}
