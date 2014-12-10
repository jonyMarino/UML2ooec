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

public class TestStorageClassStatic4to6 extends AbstractTest {

    @Test
    public void testStorageClassstatic4InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic4C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static4.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static4.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static4C.uml");

    }

    @Test
    public void testStorageClassstatic4InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic4H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static4.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static4.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static4H.uml");

    }

    /***********************************************/

    @Test
    public void testStorageClassstatic5InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic5C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static5.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static5.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static5C.uml");

    }

    @Test
    public void testStorageClassstatic5InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic5H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static5.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static5.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static5H.uml");

    }

    /***********************************************/

    @Test
    public void testStorageClassstatic6InC() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic6C", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static6.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static6.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static6C.uml");

    }

    @Test
    public void testStorageClassstatic6InH() throws CoreException, InterruptedException {

        IProject project = createIProject("/teststatic6H", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("static6.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/storageClasses/static6.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/storageClasses/static6H.uml");

    }

}
