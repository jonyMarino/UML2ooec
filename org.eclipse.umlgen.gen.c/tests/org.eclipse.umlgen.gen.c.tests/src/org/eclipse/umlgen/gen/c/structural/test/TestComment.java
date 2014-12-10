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
package org.eclipse.umlgen.gen.c.structural.test;

import org.eclipse.umlgen.gen.c.structural.test.util.AbstractTest;
import org.junit.Test;

public class TestComment extends AbstractTest {

    @Test
    public void commentDuplicataClass() {
        testStructuralFiles("comment", "commentDuplicataClass", "commentDuplicataClassE");
    }

    @Test
    public void duplicataOperation() {
        testStructuralFiles("comment", "commentDuplicataOperation", "commentDuplicataOperationN");
    }

    @Test
    public void commentClasseC() {
        testStructuralCFile("comment", "commentClasse", "commentClasseC");
    }

    @Test
    public void commentClasseH() {
        testStructuralHFile("comment", "commentClasse", "commentClasseH");
    }

    @Test
    public void commentIncludeC() {
        testStructuralCFile("comment", "commentInclude", "commentIncludeC");
    }

    @Test
    public void commentIncludeH() {
        testStructuralHFile("comment", "commentInclude", "commentIncludeH");
    }

    @Test
    public void commentOperationC() {
        testStructuralCFile("comment", "CommentOperation", "CommentOperationC");
    }

    @Test
    public void commentOperationH() {
        testStructuralHFile("comment", "CommentOperation", "CommentOperationH");
    }

    @Test
    public void commentInlineParametersC() {
        testStructuralCFile("comment", "inlineParameters", "inlineParametersC");
    }

    @Test
    public void commentInlineParametersH() {
        testStructuralHFile("comment", "inlineParameters", "inlineParametersH");
    }
}
