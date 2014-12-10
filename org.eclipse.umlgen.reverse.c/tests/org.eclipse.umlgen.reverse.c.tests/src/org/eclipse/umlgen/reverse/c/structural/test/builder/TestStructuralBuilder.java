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
package org.eclipse.umlgen.reverse.c.structural.test.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.umlgen.reverse.c.structural.test.utils.AbstractTest;
import org.junit.Test;

public class TestStructuralBuilder extends AbstractTest {

    @Test
    public void testIncludes() throws CoreException, InterruptedException {
        IProject project = createIProject("structuralBuilder.testIncludes", new NullProgressMonitor());

        createIFile(project, new Path("acq.c"), "#include \"acq.h\"\n" + "#include \"define.h\"\n"
                + "#include \"timer.h\"\n" + "#include \"bbbbbb.h\"\n" + "#include \"lice.h\"\n"
                + "#include \"sysLib.h\"\n" + "#include \"intLib.h\"\n" + "#include \"taskLib.h\"",
                new NullProgressMonitor());

        createIFile(project, new Path("acq.h"), "#ifndef __ACQ_H__\n" + "#define __ACQ_H__\n\n"
                + "#include \"define.h\"\n" + "#include \"lib.h\"\n\n\n" + "#endif",
                new NullProgressMonitor());

        createIFile(project, new Path("ccc.c"), "#include \"works.h\"\n" + "#include \"define.h\"\n"
                + "#include \"ccc.h\"\n" + "#include \"bbbbbb.h\"\n" + "#include \"acq.h\"\n"
                + "#include \"donnees.h\"\n", new NullProgressMonitor());

        createIFile(project, new Path("ccc.h"), "#ifndef __CCC_H__\n" + "#define __CCC_H__\n\n"
                + "#include \"define.h\"\n\n" + "#endif", new NullProgressMonitor());

        testModel(project, "/resource/structural/builder/includes/includes.uml");
    }
}
