/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
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

public class TestOperation extends AbstractTest {
    @Test
    public void testOperationInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testoperationC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("operation.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/operation.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/operationC.uml");
    }

    @Test
    public void testOperationInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testoperationH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("operation.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/operation/operation.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/operation/operationH.uml");
    }

}
