/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.IFunction;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.FunctionDefinitionAdded;
import org.eclipse.umlgen.reverse.c.event.FunctionDefinitionRemoved;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;
import org.eclipse.umlgen.reverse.c.util.ASTUtil;

/**
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class CFileReconciler extends AbstractFileReconciler {

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent addElement(IASTPreprocessorIfndefStatement statementAdded,
			ITranslationUnit translationUnit) {
		return null;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition,
	 *      org.eclipse.cdt.core.model.IFunction)
	 */
	public CModelChangedEvent addElement(IASTFunctionDefinition functionDef, IFunction element) {
		if (functionDef == null) {
			return null;
		}
		List<FunctionParameter> parameters = ASTUtil.collectParameterInformation(functionDef.getDeclarator());
		String returnType = element.getReturnType();
		String body = functionDef.getBody().getRawSignature();
		boolean isStatic = false;
		try {
			isStatic = element.isStatic();
		} catch (CModelException e) {
			// do nothing
		}
		return FunctionDefinitionAdded.builder().setBody(body).isStatic(isStatic).setReturnType(returnType)
				.setParameters(parameters).currentName(element.getElementName()).translationUnit(
						element.getTranslationUnit()).build();
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition,
	 *      org.eclipse.cdt.core.model.IFunction)
	 */
	public CModelChangedEvent removeElement(IASTFunctionDefinition element, IFunction originalFunction) {
		List<FunctionParameter> parameters = ASTUtil.collectParameterInformation(element.getDeclarator());
		return FunctionDefinitionRemoved.builder().setParameters(parameters).setReturnType(
				originalFunction.getReturnType()).currentName(originalFunction.getElementName())
				.translationUnit(originalFunction.getTranslationUnit()).build();
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent removeElement(IASTPreprocessorIfndefStatement statementAdded,
			ITranslationUnit translationUnit) {
		return null;
	}
}
