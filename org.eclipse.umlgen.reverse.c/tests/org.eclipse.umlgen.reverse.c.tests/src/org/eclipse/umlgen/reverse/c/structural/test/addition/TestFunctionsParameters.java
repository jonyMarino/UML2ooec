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

public class TestFunctionsParameters extends AbstractTest {
    @Test
    public void testwithoutParameterInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testwithoutParameterC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("withoutParameter.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/withoutParameter.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/withoutParameterC.uml");
    }

    @Test
    public void testwithoutParameterInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testwithoutParameterH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("withoutParameter.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/withoutParameter.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/withoutParameterH.uml");
    }

    @Test
    public void testwithoutReturnTypeInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testwithoutReturnTypeC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("withoutReturnType.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/withoutReturnType.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/withoutReturnTypeC.uml");
    }

    @Test
    public void testwithoutReturnTypeInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testwithoutReturnTypeH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("withoutReturnType.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/withoutReturnType.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/withoutReturnTypeH.uml");
    }

    @Test
    public void testsimpleParametersInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testsimpleParametersC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("simpleParameters.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/simpleParameters.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/simpleParametersC.uml");
    }

    @Test
    public void testsimpleParametersInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testsimpleParametersH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("simpleParameters.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/simpleParameters.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/simpleParametersH.uml");
    }

    @Test
    public void testmultipleReferencesCInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testmultipleReferencesC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("multipleReferences.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/multipleReferences.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/multipleReferencesC.uml");
    }

    @Test
    public void testmultipleReferencesCInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testmultipleReferencesH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("multipleReferences.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/multipleReferences.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/multipleReferencesH.uml");
    }

}
