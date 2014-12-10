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

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Iterables.transform;

import com.google.common.base.Function;

import java.util.Arrays;

import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.internal.ui.CPluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings("restriction")
public class CASTLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        if (element instanceof String) {
            return (String)element;
        }

        StringBuilder ret = new StringBuilder();

        IASTNode node = (IASTNode)element;
        if (node instanceof IASTName) {
            ret.append("\"").append(node.toString()).append("\" ");
        }
        ret.append("[" + node.getPropertyInParent().getName() + "] ");

        Iterable<String> ifacesName = transform(Arrays.asList(node.getClass().getInterfaces()),
                new Function<Class, String>() {
                    public String apply(Class from) {
                        return from.getSimpleName();
                    }
                });
        ret.append(on(", ").join(ifacesName));

        return ret.toString();
    }

    @Override
    public Image getImage(Object element) {
        if (element instanceof String) {
            return null;
        }
        return CPluginImages.get(CPluginImages.IMG_OBJS_FUNCTION);
    }
}
