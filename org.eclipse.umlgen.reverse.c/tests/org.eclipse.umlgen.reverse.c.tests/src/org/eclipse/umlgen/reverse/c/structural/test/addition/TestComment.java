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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestComment extends AbstractTest {
	@Test
	public void testCommentInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentClasseC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("commentClasse.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/commentClasse.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/commentClasseC.uml");
	}

	@Test
	public void testCommentInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentClasseH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("commentClasse.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/commentClasse.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/commentClasseH.uml");
	}

	@Test
	public void testCommentIncludeInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentIncludeC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("commentInclude.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/commentInclude.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/commentIncludeC.uml");
	}

	@Test
	public void testCommentIncludeInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentIncludeH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("commentInclude.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/commentInclude.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/commentIncludeH.uml");
	}

	@Test
	public void testCommentOperationInC() throws CoreException, InterruptedException {

		IProject project = createIProject("/testCommentOperationC", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("CommentOperation.c"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/CommentOperation.c"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/CommentOperationC.uml");
	}

	@Test
	public void testCommentOperationInH() throws CoreException, InterruptedException {

		IProject project = createIProject("/testCommentOperationH", new NullProgressMonitor());

		IFile iFile = createIFile(project, new Path("CommentOperation.h"), new NullProgressMonitor());

		TextEditor editor = openEditor(iFile);

		setEditorContent(editor, " ");

		setEditorContent(editor,
				getResourceInputStream("/resource/structural/addition/comment/CommentOperation.h"));

		closeEditor(editor, true);

		testModel(project, "/resource/structural/addition/comment/CommentOperationH.uml");
	}

	@Test
	public void testCommentDuplicataClasseInAll() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentDuplicataClassE", new NullProgressMonitor());

		IFile iFileC = createIFile(project, new Path("commentDuplicataClass.c"), new NullProgressMonitor());
		IFile iFileH = createIFile(project, new Path("commentDuplicataClass.h"), new NullProgressMonitor());

		TextEditor editorC = openEditor(iFileC);
		TextEditor editorH = openEditor(iFileH);

		setEditorContent(editorC, " ");

		setEditorContent(editorC,
				getResourceInputStream("/resource/structural/addition/comment/commentDuplicataClass.c"));

		closeEditor(editorC, true);

		setEditorContent(editorH, " ");

		setEditorContent(editorH,
				getResourceInputStream("/resource/structural/addition/comment/commentDuplicataClass.h"));

		closeEditor(editorH, true);

		testModel(project, "/resource/structural/addition/comment/commentDuplicataClassE.uml");
	}

	@Test
	public void testCommentDuplicataOperationInAll() throws CoreException, InterruptedException {

		IProject project = createIProject("/testcommentDuplicataOperationN", new NullProgressMonitor());

		IFile iFileC = createIFile(project, new Path("commentDuplicataOperation.c"),
				new NullProgressMonitor());
		IFile iFileH = createIFile(project, new Path("commentDuplicataOperation.h"),
				new NullProgressMonitor());

		TextEditor editorC = openEditor(iFileC);
		TextEditor editorH = openEditor(iFileH);

		setEditorContent(editorC, " ");

		setEditorContent(editorC,
				getResourceInputStream("/resource/structural/addition/comment/commentDuplicataOperation.c"));

		closeEditor(editorC, true);

		setEditorContent(editorH, " ");

		setEditorContent(editorH,
				getResourceInputStream("/resource/structural/addition/comment/commentDuplicataOperation.h"));

		closeEditor(editorH, true);

		testModel(project, "/resource/structural/addition/comment/commentDuplicataOperationN.uml");
	}

}
