/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *      Christophe Le Camus, Sebastien GABEL, Fabien Toral  (CS) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IEnumeration;
import org.eclipse.cdt.core.model.IFunction;
import org.eclipse.cdt.core.model.IFunctionDeclaration;
import org.eclipse.cdt.core.model.IStructure;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.ITypeDef;
import org.eclipse.cdt.core.model.IVariable;
import org.eclipse.cdt.core.model.IVariableDeclaration;
import org.eclipse.cdt.core.model.IWorkingCopy;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.CUnitAdded;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.internal.reconciler.BindingResolver;
import org.eclipse.umlgen.reverse.c.internal.reconciler.CFileReconciler;
import org.eclipse.umlgen.reverse.c.internal.reconciler.HFileReconciler;
import org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler;
import org.eclipse.umlgen.reverse.c.internal.reconciler.SameFileLocation;
import org.eclipse.umlgen.reverse.c.listener.UMLModelChangeListener;
import org.eclipse.umlgen.reverse.c.reconciler.ASTCommentReconciler;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;

public class StructuralBuilder {
	private UMLModelChangeListener umlModelChangeListener;

	private final HFileReconciler hFileReconciler;

	private final CFileReconciler cFileReconciler;

	private final ASTCommentReconciler commentReconciler;

	private final boolean includeInactiveNode;

	private final IResource resource;

	private Collection<IASTDeclaration> declarations;

	public StructuralBuilder(IResource modelFile) {
		includeInactiveNode = true;
		cFileReconciler = new CFileReconciler();
		hFileReconciler = new HFileReconciler();
		commentReconciler = new ASTCommentReconciler();
		resource = modelFile;
	}

	private int filesCount(IResource[] members, int count) {
		for (IResource rsc : members) {
			if (rsc instanceof IFile
					&& (BundleConstants.C_EXTENSION.equalsIgnoreCase(((IFile)rsc).getFileExtension()) || BundleConstants.H_EXTENSION
							.equalsIgnoreCase(((IFile)rsc).getFileExtension()))) {
				count = count + 1;
			} else {
				if (rsc instanceof IFolder) {
					try {
						count = filesCount(((IFolder)rsc).members(), count);
					} catch (CoreException e) {
						Activator.log(e);
					}
				}
			}
		}
		return count;
	}

	public void build() throws CoreException {
		final ICProject cProject = CoreModel.getDefault().getCModel().getCProject(
				resource.getProject().getName());
		final IWorkspaceRoot wsRoot = cProject.getResource().getWorkspace().getRoot();
		final int nbWorks = filesCount(resource.getProject().members(), 0);

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		try {
			dialog.run(true, true, new IRunnableWithProgress() {

				public void run(final IProgressMonitor monitor) throws InvocationTargetException,
				InterruptedException {
					monitor.beginTask(
							"Reversing code contained in project " + resource.getProject().getName() + ".", nbWorks); //$NON-NLS-1$

					try {
						cProject.accept(new ICElementVisitor() {
							public boolean visit(ICElement element) throws CoreException {
								if (monitor.isCanceled()) {
									throw new CoreException(new Status(IStatus.INFO, Activator.getId(),
											"Reverse process interrupted by user"));
								}

								// element received must be instance of
								// ITranslationUnit and must belong to the
								// workspace.
								if (element instanceof ITranslationUnit
										&& wsRoot.findMember(element.getPath()) != null) {
									switch (element.getElementType()) {
										case ICElement.C_UNIT:
											monitor.subTask("Reversing file " + element.getPath());
											ITranslationUnit translationUnit = (ITranslationUnit)element;
											build(translationUnit, true);
											monitor.worked(1);
											return false;
										default:
											break;
									}
								}
								return true;
							}
						});
					} catch (CoreException e) {
						Activator.log(e);
					} finally {
						monitor.done();
					}
				}
			});
		} catch (InvocationTargetException e) {
			Activator.log("Error in job", IStatus.ERROR, e);
		} catch (InterruptedException e) {
			Activator.log("Interrupted job", IStatus.ERROR, e);
		}
	}

	public void build(ITranslationUnit translationUnit) throws CoreException, CModelException {
		build(translationUnit, false);
	}

	public void build(ITranslationUnit translationUnit, boolean fullBuild) throws CoreException,
	CModelException {
		Activator.log("Reversing file : " + translationUnit.getPath(), IStatus.INFO);

		if (!fullBuild) {
			preBuild(translationUnit);
		}

		IASTTranslationUnit ast = translationUnit.getAST();
		IASTDeclaration[] declarationsArray = translationUnit.getAST().getDeclarations(includeInactiveNode);

		// needed for Binding Resolver
		declarations = filter(newArrayList(declarationsArray), new SameFileLocation(ast));
		declarations = Collections.EMPTY_LIST;

		IResource cRsc = translationUnit.getResource();
		if (cRsc != null) {
			umlModelChangeListener = getUMLModelChangeListener(cRsc);

			CModelChangedEvent event = CUnitAdded.builder().translationUnit(translationUnit).currentName(
					translationUnit.getElementName()).setModelMananager(
							umlModelChangeListener.getModelManager()).build();

			umlModelChangeListener.notifyChanges(event, false);

			addIfNDef(translationUnit, ast);

			addIncludes(translationUnit, ast);

			addEnumerations(translationUnit, ast);

			addStructures(translationUnit, ast);

			addTypeDefs(translationUnit, ast);

			addMacros(translationUnit, ast);

			addVariableDeclarations(translationUnit, ast);

			addVariables(translationUnit, ast);

			addFunctionDeclarations(translationUnit, ast);

			addFunctionDefinitions(translationUnit, ast);

			/* need resources for comment process */
			addComments(translationUnit, ast);
		}

		if (!fullBuild) {
			postBuild(translationUnit);
		}

	}

	/**
	 * Gets the UML model change listener that is instantiated only one time.
	 *
	 * @param rsc
	 *            The IResouce currently processed
	 * @return The instantiated UML model change listener
	 */
	private UMLModelChangeListener getUMLModelChangeListener(IResource rsc) {
		if (umlModelChangeListener == null) {
			umlModelChangeListener = new UMLModelChangeListener(rsc);
		}
		return umlModelChangeListener;
	}

	/**
	 * Disposes the structural builder after a build : the UML model change listener is disposed, itself it
	 * disposes the model manager.
	 */
	public void dispose() {
		// The UML model change listener can be null when no source code is set
		// into the project.
		if (umlModelChangeListener != null) {
			umlModelChangeListener.dispose();
			umlModelChangeListener = null;
		}
	}

	private void postBuild(ITranslationUnit translationUnit) throws CoreException {
		ProjectUtil.addToBuildSpec(translationUnit.getCProject().getProject(),
				BundleConstants.UML2C_BUILDER_ID);
	}

	private void preBuild(ITranslationUnit translationUnit) throws CoreException {
		// Temporally removing UML2C builder to avoid workspace building after
		// each reverse during the structural build
		ProjectUtil.removeFromBuildSpec(translationUnit.getCProject().getProject(),
				BundleConstants.UML2C_BUILDER_ID);
	}

	private void addComments(ITranslationUnit translationUnit, IASTTranslationUnit ast) {
		try {
			IWorkingCopy workingUnit = translationUnit.getWorkingCopy();
			commentReconciler.addModelChangeListener(umlModelChangeListener);
			commentReconciler.reconcile(workingUnit, null, new IASTDeclaration[1]);
			commentReconciler.removeModelChangeListener(umlModelChangeListener);
		} catch (Exception e) {
			Activator.log("Error during the Addition of Comments : " + e.getMessage(), IStatus.ERROR);
		}
	}

	private IFileReconciler getFileReconciler(IASTTranslationUnit tu) {
		return tu.isHeaderUnit() ? hFileReconciler : cFileReconciler;
	}

	private void addIncludes(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		IASTPreprocessorIncludeStatement[] includesArray = ast.getIncludeDirectives();

		Collection<IASTPreprocessorIncludeStatement> filteredInclusions = filter(newArrayList(includesArray),
				new SameFileLocation(ast));
		for (IASTPreprocessorIncludeStatement include : filteredInclusions) {
			CModelChangedEvent event = getFileReconciler(ast).addElement(include, translationUnit);
			umlModelChangeListener.notifyChanges(event, false);
		}
	}

	private void addMacros(ITranslationUnit translationUnit, IASTTranslationUnit ast) throws CModelException {
		IASTPreprocessorMacroDefinition[] macros = ast.getMacroDefinitions();
		Collection<IASTPreprocessorMacroDefinition> filteredMacros = filter(newArrayList(macros),
				new SameFileLocation(ast));
		for (IASTPreprocessorMacroDefinition macro : filteredMacros) {
			CModelChangedEvent event = getFileReconciler(ast).addElement(macro, translationUnit);
			umlModelChangeListener.notifyChanges(event, false);
		}
	}

	private void addIfNDef(ITranslationUnit translationUnit, IASTTranslationUnit ast) {
		IASTPreprocessorStatement[] prerocStmts = ast.getAllPreprocessorStatements();
		Collection<IASTPreprocessorStatement> filteredPreprocStmts = filter(newArrayList(prerocStmts),
				new SameFileLocation(ast));

		// may be no declaration in the ast
		if (ast.getFileLocation() != null) {
			int firstNodePosition = ast.getFileLocation().getNodeLength();
			if (ast.getDeclarations().length > 0 && ast.getDeclarations()[0] instanceof IASTNode) {
				firstNodePosition = ast.getFileLocation().getNodeLength();
			}
			// avoid to treat multiple #ifndef
			boolean first = true;

			for (IASTPreprocessorStatement preprocStmt : filteredPreprocStmts) {
				if (preprocStmt instanceof IASTPreprocessorIfndefStatement && first) {
					if (((IASTPreprocessorIfndefStatement)preprocStmt).getFileLocation().getNodeOffset() < firstNodePosition) {
						CModelChangedEvent event = getFileReconciler(ast).addElement(
								(IASTPreprocessorIfndefStatement)preprocStmt, translationUnit);
						umlModelChangeListener.notifyChanges(event, false);
						first = false;
					}
				}
			}
		}
	}

	/**
	 * Adds a <b>Variable Declaration</b>
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addVariableDeclarations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_VARIABLE_DECLARATION)) {
			IVariableDeclaration varDecl = (IVariableDeclaration)element;
			IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTSimpleDeclaration(
					declarations, varDecl);
			CModelChangedEvent event = getFileReconciler(ast).addElement(simpleDeclaration, varDecl);
			umlModelChangeListener.notifyChanges(event, false);
		}
	}

	/**
	 * Adds a <b>Simple Variable</b>
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addVariables(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_VARIABLE)) {
			IVariable var = (IVariable)element;
			IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTSimpleDeclaration(
					declarations, var);
			CModelChangedEvent event = getFileReconciler(ast).addElement(simpleDeclaration, var);
			umlModelChangeListener.notifyChanges(event, false);
		}
	}

	/**
	 * Adds a <b>Function Declaration</b>
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addFunctionDeclarations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_FUNCTION_DECLARATION)) {
			IFunctionDeclaration coreElement = (IFunctionDeclaration)element;
			IASTSimpleDeclaration declaration = BindingResolver.resolveBindingIASTFunctionDeclarator(
					declarations, coreElement);
			if (declaration != null) {
				CModelChangedEvent event = getFileReconciler(ast).addElement(
						(IASTFunctionDeclarator)declaration.getDeclarators()[0], coreElement);
				umlModelChangeListener.notifyChanges(event, false);
			} else {
				Activator
						.log("Undefined declaration : "
								+ coreElement.getElementName()
								+ ". The Type is defined outside and the Declaration is not found even in includes dependencies. Module is not already defined.",
								IStatus.WARNING);
			}
		}
	}

	/**
	 * Adds a <b>Function Definition</b>
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addFunctionDefinitions(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_FUNCTION)) {
			IFunction coreElement = (IFunction)element;
			IASTFunctionDefinition functionDefinition = BindingResolver.resolveBindingIASTFunctionDefinition(
					declarations, coreElement);
			CModelChangedEvent event = getFileReconciler(ast).addElement(functionDefinition, coreElement);
			umlModelChangeListener.notifyChanges(event, false);
		}
	}

	/**
	 * Adds a structure
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addStructures(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		int anonymousRanking = 1;
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_STRUCT)) {
			IStructure coreElement = (IStructure)element;
			IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTStructure(
					declarations, coreElement, anonymousRanking);
			if (simpleDeclaration != null) {
				CModelChangedEvent event = getFileReconciler(ast).addElement(
						(IASTCompositeTypeSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
				umlModelChangeListener.notifyChanges(event, false);
				if ("".equals(coreElement.getElementName())) {
					anonymousRanking++;
				}
			} else {
				Activator
						.log("Undefined Structure : "
								+ coreElement.getElementName()
								+ ". The Type is defined outside and the Declaration is not found even in includes dependencies. Module is not already defined.",
								IStatus.WARNING);
			}
		}
	}

	/**
	 * Adds an enumeration
	 *
	 * @param translationUnit
	 * @param ast
	 * @throws CModelException
	 */
	private void addEnumerations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		int anonymousRanking = 1;
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_ENUMERATION)) {
			IEnumeration coreElement = (IEnumeration)element;
			IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTEnumeration(
					declarations, coreElement, anonymousRanking);
			if (simpleDeclaration != null) {
				CModelChangedEvent event = getFileReconciler(ast).addElement(
						(IASTEnumerationSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
				umlModelChangeListener.notifyChanges(event, false);
				if ("".equals(coreElement.getElementName())) {
					anonymousRanking++;
				}
			} else {
				Activator
						.log("Undefined Enumeration : "
								+ coreElement.getElementName()
								+ ". The Type is defined outside and the Declaration is not found even in includes dependencies. Module is not already defined.",
								IStatus.WARNING);
			}
		}
	}

	private void addTypeDefs(ITranslationUnit translationUnit, IASTTranslationUnit ast)
			throws CModelException {
		CModelChangedEvent event = null;
		for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_TYPEDEF)) {
			ITypeDef coreElement = (ITypeDef)element;
			IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTypeDefDeclaration(
					declarations, coreElement);

			if (simpleDeclaration != null && simpleDeclaration.getFileLocation() != null) {
				if (simpleDeclaration.getDeclSpecifier() instanceof IASTEnumerationSpecifier) {
					event = getFileReconciler(ast).addElement(
							(IASTEnumerationSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
				} else if (simpleDeclaration.getDeclSpecifier() instanceof IASTCompositeTypeSpecifier) {
					event = getFileReconciler(ast).addElement(
							(IASTCompositeTypeSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
				} else if (simpleDeclaration.getDeclSpecifier() instanceof IASTElaboratedTypeSpecifier) {
					event = getFileReconciler(ast).addElement(
							(IASTElaboratedTypeSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
				} else if (simpleDeclaration.getDeclSpecifier() instanceof IASTNamedTypeSpecifier) {
					event = getFileReconciler(ast).addElement(simpleDeclaration.getDeclarators()[0],
							coreElement);
				} else if (simpleDeclaration.getDeclSpecifier() instanceof IASTSimpleDeclSpecifier) {
					event = getFileReconciler(ast).addElement(simpleDeclaration.getDeclarators()[0],
							coreElement);
				} else if (simpleDeclaration.getDeclarators()[0] instanceof IASTFunctionDeclarator) {
					event = getFileReconciler(ast).addElement(simpleDeclaration.getDeclarators()[0],
							coreElement);
				}

				umlModelChangeListener.notifyChanges(event, false);
			} else {
				Activator
						.log("Undefined TypedDef : "
								+ coreElement.getElementName()
								+ ". The Type is defined outside and the Declaration is not found even in includes dependencies. Module is not already defined.",
								IStatus.ERROR);
			}
		}
	}

}
