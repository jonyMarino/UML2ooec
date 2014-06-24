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
public final class FunctionParameter
{
    private String name;

    private String type;

    private String initilizer;

    private boolean isConst;

    private boolean isReadOnly;
    
    private Type umlType;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getInitilizer()
    {
        return initilizer;
    }

    public void setInitilizer(String initilizer)
    {
        this.initilizer = initilizer;
    }

    public boolean isConst()
    {
        return isConst;
    }

    public void setConst(boolean isConst)
    {
        this.isConst = isConst;
    }

    public boolean isReadOnly()
    {
        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly)
    {
        this.isReadOnly = isReadOnly;
    }

    public Type getUMLType()
    {
        return umlType;
    }

    public void setUMLType(Type umlType)
    {
        this.umlType = umlType;
    }
    
    public boolean isPointer()
    {
        return getType().endsWith("*");
    }

}
