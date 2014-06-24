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
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import java.util.Collection;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.ICompositeType;
import org.eclipse.cdt.core.dom.ast.IEnumeration;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;

public final class BindingResolver
{

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTSimpleDeclaration(Collection<IASTDeclaration> elements, ICElement coreElt)
    {
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTSimpleDeclaration)
            {
                for (IASTDeclarator declarator : ((IASTSimpleDeclaration) declaration).getDeclarators())
                {
                    IBinding binding = declarator.getName().resolveBinding();
                    if (binding instanceof IVariable && binding.getName().equals(coreElt.getElementName()))
                    {
                        return (IASTSimpleDeclaration) declaration;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTEnumeration(Collection<IASTDeclaration> elements, ICElement coreElt, int anonymousRanking)
    {
        int ranking = 1;
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTSimpleDeclaration)
            {
                IASTDeclSpecifier specifier = ((IASTSimpleDeclaration) declaration).getDeclSpecifier();
                if (specifier instanceof IASTEnumerationSpecifier)
                {
                    IBinding binding = ((IASTEnumerationSpecifier) specifier).getName().resolveBinding();
                    if (binding instanceof IEnumeration && binding.getName().equals(coreElt.getElementName()) && ranking == anonymousRanking)
                    {
                        return (IASTSimpleDeclaration) declaration;
                    }
                    else if ("".equals(binding.getName()))
                    {
                        ranking++;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTEnumeration(Collection<IASTDeclaration> elements, ICElement coreElt, ITranslationUnit tu) throws CoreException
    {
        int anonymousRanking = 1;
        List<ICElement> childrenOfType = tu.getChildrenOfType(ICElement.C_ENUMERATION);
        for (int i = 0; i < childrenOfType.indexOf(coreElt); i++)
        {
            if ("".equals(childrenOfType.get(i).getElementName()))
            {
                anonymousRanking++;
            }
        }
        return resolveBindingIASTEnumeration(elements, coreElt, anonymousRanking);
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTStructure(Collection<IASTDeclaration> elements, ICElement coreElt, int anonymousRanking)
    {
        int ranking = 1;
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTSimpleDeclaration)
            {
                IASTDeclSpecifier specifier = ((IASTSimpleDeclaration) declaration).getDeclSpecifier();
                if (specifier instanceof IASTCompositeTypeSpecifier)
                {
                    IBinding binding = ((IASTCompositeTypeSpecifier) specifier).getName().resolveBinding();
                    if (binding instanceof ICompositeType && binding.getName().equals(coreElt.getElementName()) && ranking == anonymousRanking)
                    {
                        return (IASTSimpleDeclaration) declaration;
                    }
                    else if ("".equals(binding.getName()))
                    {
                        ranking++;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTStructure(Collection<IASTDeclaration> elements, ICElement coreElt, ITranslationUnit tu) throws CoreException
    {
        int anonymousRanking = 1;
        List<ICElement> childrenOfType = tu.getChildrenOfType(ICElement.C_STRUCT);
        for (int i = 0; i < childrenOfType.indexOf(coreElt); i++)
        {
            if ("".equals(childrenOfType.get(i).getElementName()))
            {
                anonymousRanking++;
            }
        }
        return resolveBindingIASTStructure(elements, coreElt, anonymousRanking);
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTypeDefDeclaration(Collection<IASTDeclaration> elements, ICElement coreElt)
    {
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTSimpleDeclaration)
            {
                for (IASTDeclarator declarator : ((IASTSimpleDeclaration) declaration).getDeclarators())
                {
                    IBinding binding = declarator.getName().resolveBinding();
                    if (binding instanceof ITypedef && binding.getName().equals(coreElt.getElementName()))
                    {
                        return (IASTSimpleDeclaration) declaration;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Resolve binding for an IASTSimpleDeclaration for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTSimpleDeclaration resolveBindingIASTFunctionDeclarator(Collection<IASTDeclaration> elements, ICElement coreElt)
    {
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTSimpleDeclaration)
            {
                for (IASTDeclarator declarator : ((IASTSimpleDeclaration) declaration).getDeclarators())
                {
                    if (declarator instanceof IASTFunctionDeclarator)
                    {
                        IBinding binding = declarator.getName().resolveBinding();
                        if (binding instanceof IFunction && binding.getName().equals(coreElt.getElementName()))
                        {
                            return (IASTSimpleDeclaration) declaration;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Resolve binding for an IASTFunctionDefinition for which the Declarator is the name binding
     * 
     * @param elements collection of declarations
     * @param coreElement the ICElement identified by the CDT
     * @return the element bounded
     */
    public static IASTFunctionDefinition resolveBindingIASTFunctionDefinition(Collection<IASTDeclaration> elements, ICElement coreElement)
    {
        for (IASTDeclaration declaration : elements)
        {
            if (declaration instanceof IASTFunctionDefinition)
            {
                IASTDeclarator declarator = ((IASTFunctionDefinition) declaration).getDeclarator();
                IBinding binding = declarator.getName().resolveBinding();
                if (binding instanceof IFunction && binding.getName().equals(coreElement.getElementName()))
                {
                    return (IASTFunctionDefinition) declaration;
                }
            }
        }
        return null;
    }

}
