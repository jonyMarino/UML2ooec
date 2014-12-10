/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.sync.util.providers;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/** A content provider. */
public class CASTContentProvider implements ITreeContentProvider {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof String) {
            return null;
        }
        IASTNode node = (IASTNode)parentElement;
        return node.getChildren();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof String) {
            return null;
        }
        IASTNode node = (IASTNode)element;
        return node.getParent();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        if (element instanceof String) {
            return false;
        }
        IASTNode node = (IASTNode)element;
        return node.getChildren().length > 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof String) {
            return new Object[] {inputElement };
        }
        Object[] elements = getChildren(inputElement);
        // Object[] elements =
        // ((IASTTranslationUnit)inputElement).getDeclarations();
        // Object[] elements =
        // ((IASTTranslationUnit)inputElement).getComments();
        return elements;
    }

}
