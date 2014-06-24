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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestCUnit extends AbstractTest {

	@Test
	public void testEmptyHandC() throws CoreException, InterruptedException {
		IProject project = createIProject("testemptyHandC", new NullProgressMonitor());

		IFile cFile = createIFile(project, new Path("empty.c"), new NullProgressMonitor());
		IFile hFile = createIFile(project, new Path("empty.h"), new NullProgressMonitor());

		testModel(project, "/resource/structural/addition/empty/emptyHandC.uml");
	}

	@Test
	public void testEmptyC() throws CoreException, InterruptedException {
		IProject project = createIProject("testemptyC", new NullProgressMonitor());

		createIFile(project, new Path("empty.c"), new NullProgressMonitor());

		testModel(project, "/resource/structural/addition/empty/emptyC.uml");
	}

	@Test
	public void testEmptyH() throws CoreException, InterruptedException {
		IProject project = createIProject("testemptyH", new NullProgressMonitor());

		createIFile(project, new Path("empty.h"), new NullProgressMonitor());

		testModel(project, "/resource/structural/addition/empty/emptyH.uml");
	}
}
