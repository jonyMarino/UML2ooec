/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.beans;

import org.eclipse.uml2.uml.Type;

/**
 * Represents a function parameters. This beans is built from information provided by the CDT.<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public final class FunctionParameter {
    /** The name. */
    private String name;

    /** The type. */
    private String type;

    /** The initializer. */
    private String initializer;

    /** The flag const. */
    private boolean isConst;

    /** The flag read only. */
    private boolean isReadOnly;

    /** The UML type. */
    private Type umlType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInitializer() {
        return initializer;
    }

    public void setInitializer(String initilizer) {
        this.initializer = initilizer;
    }

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean pIsConst) {
        this.isConst = pIsConst;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean pIsReadOnly) {
        this.isReadOnly = pIsReadOnly;
    }

    public Type getUMLType() {
        return umlType;
    }

    public void setUMLType(Type pUmlType) {
        this.umlType = pUmlType;
    }

    public boolean isPointer() {
        return getType().endsWith("*");
    }

}
