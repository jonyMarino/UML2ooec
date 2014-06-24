/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public final class SameFileLocation implements Predicate<IASTNode>
{
    private final IASTTranslationUnit astTranslationUnit;

    public SameFileLocation(IASTTranslationUnit astTranslationUnit)
    {
        this.astTranslationUnit = astTranslationUnit;
    }

    public boolean apply(IASTNode input)
    {
        if (input != null && astTranslationUnit != null) {
            IASTFileLocation fileLocation = input.getFileLocation();
            String filePath = astTranslationUnit.getFilePath();
            if (fileLocation != null) {
                return Objects.equal(filePath, fileLocation.getFileName());
            }
        }
        
        return false;
    }
}