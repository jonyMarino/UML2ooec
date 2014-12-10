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

public class TestFunctions extends AbstractTest {
    @Test
    public void testFunctionInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testfunctionDefinitionC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("functionDefinition.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/functionDefinition.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/functionDefinitionC.uml");
    }

    @Test
    public void testFunctionInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testfunctionDefinitionH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("functionDefinition.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/functionDefinition.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/functionDefinitionH.uml");
    }

    @Test
    public void testFunctionBoth() throws CoreException, InterruptedException {

        IProject project = createIProject("testfunctionDefinitionBOTH", new NullProgressMonitor());

        IFile iFileC = createIFile(project, new Path("functionDefinitionBOTH.c"), new NullProgressMonitor());

        TextEditor editorC = openEditor(iFileC);

        setEditorContent(editorC, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editorC,
                getResourceInputStream("/resource/structural/addition/operation/functionDefinitionBOTH.c"));

        closeEditor(editorC, true);

        IFile iFileH = createIFile(project, new Path("functionDefinitionBOTH.h"), new NullProgressMonitor());

        TextEditor editorH = openEditor(iFileH);

        setEditorContent(editorH, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editorH,
                getResourceInputStream("/resource/structural/addition/operation/functionDefinitionBOTH.h"));

        closeEditor(editorH, true);

        testModel(project, "/resource/structural/addition/operation/functionDefinitionBOTH.uml");
    }

}
