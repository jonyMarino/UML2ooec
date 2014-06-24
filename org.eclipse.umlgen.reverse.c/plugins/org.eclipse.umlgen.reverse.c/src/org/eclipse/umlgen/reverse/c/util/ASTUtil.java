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
package org.eclipse.umlgen.reverse.c.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTSignatureUtil;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionStyleMacroParameter;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * AST utility class.<br>
 * 
 * Creation : 10 september 2010<br>
 * 
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class ASTUtil {

	/**
	 * Collects function parameters.
	 * 
	 * @param declarator
	 *            The IASTDeclarator in which function parameter must be
	 *            extracted
	 * @return A list of {@link FunctionParameter}. This list can be empty.
	 */
	public static List<FunctionParameter> collectParameterInformation(
			IASTDeclarator declarator) {
		String[] paramSignature = ASTSignatureUtil
				.getParameterSignatureArray(declarator);
		List<FunctionParameter> fctParams = new ArrayList<FunctionParameter>();
		int nbParams = 0;
		for (IASTNode node : declarator.getChildren()) {
			if (node instanceof IASTParameterDeclaration) {
				IASTParameterDeclaration paramDecl = (IASTParameterDeclaration) node;
				FunctionParameter newParam = new FunctionParameter();

				String type = paramSignature[nbParams];
				if (type.startsWith("const ")) //$NON-NLS-1$
				{
					type = type.replaceFirst(BundleConstants.CONST_REGEXP, ""); //$NON-NLS-1$
					newParam.setConst(true);
				}
				if ("".equals(type)) //$NON-NLS-1$
				{
					newParam.setType(paramDecl.getDeclarator().getName()
							.toString());
				} else {
					newParam.setName(paramDecl.getDeclarator().getName()
							.toString());
					newParam.setType(type.replaceAll("\\s", "")); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if (paramDecl.getDeclarator().getInitializer() != null) {
					// ASTSignatureUtil.getInitializerString(paramDecl.getDeclarator().getInitializer());
					newParam.setInitilizer(paramDecl.getDeclarator()
							.getInitializer().getRawSignature());
				}
				fctParams.add(newParam);
				nbParams++;
			}
		}
		return fctParams;
	}

	/**
	 * Computes the macro name (Function or Object style macro).
	 * 
	 * @param macro
	 *            The function or object macro
	 * @return The computed name
	 */
	public static String computeName(IASTPreprocessorMacroDefinition macro) {
		StringBuilder result = new StringBuilder();
		result.append(macro.getName().getSimpleID());
		if (macro instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
			IASTPreprocessorFunctionStyleMacroDefinition fctMacro = (IASTPreprocessorFunctionStyleMacroDefinition) macro;
			result.append('(');
			boolean needComma = false;
			for (IASTFunctionStyleMacroParameter param : fctMacro
					.getParameters()) {
				if (needComma) {
					result.append(", "); //$NON-NLS-1$
				}
				result.append(param.getParameter());
				needComma = true;
			}
			result.append(')');
		}
		return result.toString();
	}

	/**
	 * Computes a simple name (simple or pointer type)
	 * 
	 * @param declaration
	 *            The simple declaration
	 * @return The computed name
	 */
	public static String computeName(IASTSimpleDeclaration declaration,
			String toMatch) {
		for (IASTDeclarator declarator : declaration.getDeclarators()) {
			if (declarator.getRawSignature().contains(toMatch)) {
				return computeName(declarator);
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Computes a simple name (simple or pointer type)
	 * 
	 * @param declaration
	 *            The simple declaration
	 * @return The computed name
	 */
	public static String computeName(IASTDeclarator declarator) {
		String name = declarator.getRawSignature();
		if (name.indexOf("=") > -1) //$NON-NLS-1$
		{
			name = name.substring(0, name.indexOf("=")); //$NON-NLS-1$
		}
		return name.replaceAll("\\*", "").trim(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Computes a simple name (simple or pointer type)
	 * 
	 * @param declaration
	 *            The simple declaration
	 * @return The computed name
	 */
	public static String computeType(IASTSimpleDeclaration declaration) {
		if (declaration == null) {
			return "";
		}
		if (declaration.getDeclSpecifier() instanceof IASTEnumerationSpecifier) {
			return ((IASTEnumerationSpecifier) declaration.getDeclSpecifier())
					.getName().toString();
		}
		return ASTUtil.computeType(declaration.getDeclarators()[0]);
	}

	/**
	 * Computes a simple type (simple or pointer type)
	 * 
	 * @param declaration
	 *            The simple declaration
	 * @return The computed type
	 */
	private static String computeType(IASTDeclarator declarator) {
		String type = ASTSignatureUtil.getSignature(declarator);
		type = type.replaceAll(BundleConstants.SQUARE_BRAKETS_REGEXP, ""); // remove 0 or several square brakets //$NON-NLS-1$
		type = type.replaceFirst(BundleConstants.STATIC_REGEXP, ""); // remove the static keyword //$NON-NLS-1$
		type = type.replaceFirst(BundleConstants.REGISTER_REGEXP, ""); // remove the register keyword //$NON-NLS-1$
		type = type.replaceFirst(BundleConstants.VOLATILE_REGEXP, ""); // remove the volatile keyword //$NON-NLS-1$
		type = type.replaceFirst(BundleConstants.EXTERN_REGEXP, ""); // remove the extern keyword //$NON-NLS-1$
		type = type.replaceFirst(BundleConstants.CONST_REGEXP, ""); // remove the const keyword //$NON-NLS-1$
		type = type.replaceAll("\\s+\\*", "*"); // allow to get a formatted type such as 'INT32 **' to 'INT32**' //$NON-NLS-1$ //$NON-NLS-2$
		return type.trim();
	}
}
