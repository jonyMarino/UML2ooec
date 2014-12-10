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

public class TestStorageClassExtern5to6AndRegister1 extends AbstractTest {

    /***********************************************/

    @Test
    public void testStorageClassExtern5InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/testextern5C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("extern5.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/extern5.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/extern5C.uml");

    }

    @Test
    public void testStorageClassExtern5InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/testextern5H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("extern5.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/extern5.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/extern5H.uml");

    }

    /***********************************************/

    @Test
    public void testStorageClassExtern6InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/testextern6C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("extern6.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/extern6.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/extern6C.uml");

    }

    @Test
    public void testStorageClassExtern6InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/testextern6H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("extern6.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/extern6.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/extern6H.uml");

    }

    /***********************************************/

    @Test
    public void testStorageClassRegister1InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/testregister1C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("register1.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/register1.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/register1C.uml");

    }

    @Test
    public void testStorageClassRegister1InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/testregister1H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("register1.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/register1.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/register1H.uml");

    }

    /***********************************************/

}
