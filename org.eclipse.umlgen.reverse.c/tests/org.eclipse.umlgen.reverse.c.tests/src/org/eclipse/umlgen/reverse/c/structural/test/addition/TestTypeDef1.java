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

public class TestTypeDef1 extends AbstractTest {

    @Test
    public void testSimpleAliasInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testaliasBuiltinTypeC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("aliasBuiltin.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/typedef/alias/aliasBuiltinType.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/typedef/alias/aliasBuiltinTypeC.uml");
    }

    @Test
    public void testSimpleAliasInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testaliasBuiltinTypeH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("aliasBuiltin.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/typedef/alias/aliasBuiltinType.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/typedef/alias/aliasBuiltinTypeH.uml");
    }

    @Test
    public void testPointerInC() throws CoreException, InterruptedException {

        IProject project = createIProject("testTypeDefPointerC", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("pointer.c"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/typedef/pointer/pointer.c"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/typedef/pointer/TypeDefPointerC.uml");
    }

    @Test
    public void testPointerInH() throws CoreException, InterruptedException {

        IProject project = createIProject("testTypeDefPointerH", new NullProgressMonitor());

        IFile iFile = createIFile(project, new Path("pointer.h"), new NullProgressMonitor());

        TextEditor editor = openEditor(iFile);

        setEditorContent(editor, new ByteArrayInputStream(" ".getBytes()));

        setEditorContent(editor,
                getResourceInputStream("/resource/structural/addition/typedef/pointer/pointer.h"));

        closeEditor(editor, true);

        testModel(project, "/resource/structural/addition/typedef/pointer/TypeDefPointerH.uml");
    }

}
