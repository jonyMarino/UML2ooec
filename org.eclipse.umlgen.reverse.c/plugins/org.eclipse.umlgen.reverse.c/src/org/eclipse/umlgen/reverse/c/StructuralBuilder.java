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
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
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

/** A structural builder. */
public class StructuralBuilder {

    /** Constant for the message of an undefined declaration. */
    private static final String MSG_UNDEFINED_DECLARATION = ". The Type is defined outside and the Declaration is not found even in includes dependencies. Module is not already defined.";

    /** A model change listener. */
    private UMLModelChangeListener umlModelChangeListener;

    /** H file reconciler. */
    private final HFileReconciler hFileReconciler;

    /** C file reconciler. */
    private final CFileReconciler cFileReconciler;

    /** Comment reconciler. */
    private final ASTCommentReconciler commentReconciler;

    /** Flag to check if include inactive node. */
    private final boolean includeInactiveNode;

    /** The resource. */
    private final IResource resource;

    /** The declarations. */
    private Collection<IASTDeclaration> declarations;

    /**
     * Constructor.
     *
     * @param modelFile
     *            The model file.
     */
    public StructuralBuilder(IResource modelFile) {
        includeInactiveNode = true;
        cFileReconciler = new CFileReconciler();
        hFileReconciler = new HFileReconciler();
        commentReconciler = new ASTCommentReconciler();
        resource = modelFile;
    }

    /**
     * This returns the count of files.
     *
     * @param members
     *            The resources
     * @param count
     *            The current count
     * @return The new count
     */
    private int filesCount(IResource[] members, int count) {
        int result = count;
        for (IResource rsc : members) {
            if (rsc instanceof IFile
                    && (BundleConstants.C_EXTENSION.equalsIgnoreCase(((IFile)rsc).getFileExtension()) || BundleConstants.H_EXTENSION
                            .equalsIgnoreCase(((IFile)rsc).getFileExtension()))) {
                result = result + 1;
            } else {
                if (rsc instanceof IFolder) {
                    try {
                        result = filesCount(((IFolder)rsc).members(), count);
                    } catch (CoreException e) {
                        Activator.log(e);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Build.
     *
     * @throws CoreException
     *             exception
     */
    public void build() throws CoreException {
        final ICProject cProject = CoreModel.getDefault().getCModel().getCProject(
                resource.getProject().getName());
        final IWorkspaceRoot wsRoot = cProject.getResource().getWorkspace().getRoot();
        final int nbWorks = filesCount(resource.getProject().members(), 0);

        ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
        try {
            // CHECKSTYLE:OFF
            dialog.run(true, true, new IRunnableWithProgress() {
                // CHECKSTYLE:ON
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

                                // element received must be instance of ITranslationUnit and must belong to
                                // the workspace.
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

    /**
     * Build.
     *
     * @param translationUnit
     *            The translation unit.
     * @throws CoreException
     *             exception.
     * @throws CModelException
     *             exception.
     */
    public void build(ITranslationUnit translationUnit) throws CoreException, CModelException {
        build(translationUnit, false);
    }

    /**
     * Build.
     *
     * @param translationUnit
     *            The translation unit.
     * @param fullBuild
     *            This enables to request a full build or not
     * @throws CoreException
     *             exception
     * @throws CModelException
     *             exception
     */
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

        IResource cRsc = translationUnit.getResource();
        if (cRsc != null) {
            umlModelChangeListener = getUMLModelChangeListener(cRsc);

            AbstractCModelChangedEvent event = CUnitAdded.builder().translationUnit(translationUnit)
                    .currentName(translationUnit.getElementName()).setModelMananager(
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

    /**
     * Post build.
     *
     * @param translationUnit
     *            The translation unit.
     * @throws CoreException
     *             exception
     */
    private void postBuild(ITranslationUnit translationUnit) throws CoreException {
        ProjectUtil.addToBuildSpec(translationUnit.getCProject().getProject(),
                BundleConstants.UML2C_BUILDER_ID);
    }

    /**
     * Pre build.
     *
     * @param translationUnit
     *            The translation unit.
     * @throws CoreException
     *             exception
     */
    private void preBuild(ITranslationUnit translationUnit) throws CoreException {
        // Temporally removing UML2C builder to avoid workspace building after
        // each reverse during the structural build
        ProjectUtil.removeFromBuildSpec(translationUnit.getCProject().getProject(),
                BundleConstants.UML2C_BUILDER_ID);
    }

    /**
     * This adds comments.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit
     */
    private void addComments(ITranslationUnit translationUnit, IASTTranslationUnit ast) {
        try {
            IWorkingCopy workingUnit = translationUnit.getWorkingCopy();
            commentReconciler.addModelChangeListener(umlModelChangeListener);
            commentReconciler.reconcile(workingUnit, null, new IASTDeclaration[1]);
            commentReconciler.removeModelChangeListener(umlModelChangeListener);
        } catch (CoreException e) {
            Activator.log("Error during the Addition of Comments : " + e.getMessage(), IStatus.ERROR);
        }
    }

    /**
     * Get the file reconciler according to the given translation unit.
     *
     * @param tu
     *            The translation unit.
     * @return The file reconciler.
     */
    private IFileReconciler getFileReconciler(IASTTranslationUnit tu) {
        // CHECKSTYLE:OFF
        return tu.isHeaderUnit() ? hFileReconciler : cFileReconciler;
        // CHECKSTYLE:ON
    }

    /**
     * This adds includes.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception.
     */
    private void addIncludes(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        IASTPreprocessorIncludeStatement[] includesArray = ast.getIncludeDirectives();

        Collection<IASTPreprocessorIncludeStatement> filteredInclusions = filter(newArrayList(includesArray),
                new SameFileLocation(ast));
        for (IASTPreprocessorIncludeStatement include : filteredInclusions) {
            AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(include, translationUnit);
            umlModelChangeListener.notifyChanges(event, false);
        }
    }

    /**
     * This adds macros.
     *
     * @param translationUnit
     *            The translation unit
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception.
     */
    private void addMacros(ITranslationUnit translationUnit, IASTTranslationUnit ast) throws CModelException {
        IASTPreprocessorMacroDefinition[] macros = ast.getMacroDefinitions();
        Collection<IASTPreprocessorMacroDefinition> filteredMacros = filter(newArrayList(macros),
                new SameFileLocation(ast));
        for (IASTPreprocessorMacroDefinition macro : filteredMacros) {
            AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(macro, translationUnit);
            umlModelChangeListener.notifyChanges(event, false);
        }
    }

    /**
     * This adds ifndef.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     */
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
                        AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(
                                (IASTPreprocessorIfndefStatement)preprocStmt, translationUnit);
                        umlModelChangeListener.notifyChanges(event, false);
                        first = false;
                    }
                }
            }
        }
    }

    /**
     * Adds a <b>Variable Declaration</b>.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception
     */
    private void addVariableDeclarations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_VARIABLE_DECLARATION)) {
            IVariableDeclaration varDecl = (IVariableDeclaration)element;
            IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTSimpleDeclaration(
                    declarations, varDecl);
            AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(simpleDeclaration, varDecl);
            umlModelChangeListener.notifyChanges(event, false);
        }
    }

    /**
     * Adds a <b>Simple Variable</b>.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception
     */
    private void addVariables(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_VARIABLE)) {
            IVariable var = (IVariable)element;
            IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTSimpleDeclaration(
                    declarations, var);
            AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(simpleDeclaration, var);
            umlModelChangeListener.notifyChanges(event, false);
        }
    }

    /**
     * Adds a <b>Function Declaration</b>.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception
     */
    private void addFunctionDeclarations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_FUNCTION_DECLARATION)) {
            IFunctionDeclaration coreElement = (IFunctionDeclaration)element;
            IASTSimpleDeclaration declaration = BindingResolver.resolveBindingIASTFunctionDeclarator(
                    declarations, coreElement);
            if (declaration != null) {
                AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(
                        (IASTFunctionDeclarator)declaration.getDeclarators()[0], coreElement);
                umlModelChangeListener.notifyChanges(event, false);
            } else {
                Activator.log("Undefined declaration : " + coreElement.getElementName()
                        + MSG_UNDEFINED_DECLARATION, IStatus.WARNING);
            }
        }
    }

    /**
     * Adds a <b>Function Definition</b>.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception.
     */
    private void addFunctionDefinitions(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_FUNCTION)) {
            IFunction coreElement = (IFunction)element;
            IASTFunctionDefinition functionDefinition = BindingResolver.resolveBindingIASTFunctionDefinition(
                    declarations, coreElement);
            AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(functionDefinition,
                    coreElement);
            umlModelChangeListener.notifyChanges(event, false);
        }
    }

    /**
     * Adds a structure.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception.
     */
    private void addStructures(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        int anonymousRanking = 1;
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_STRUCT)) {
            IStructure coreElement = (IStructure)element;
            IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTStructure(
                    declarations, coreElement, anonymousRanking);
            if (simpleDeclaration != null) {
                AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(
                        (IASTCompositeTypeSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
                umlModelChangeListener.notifyChanges(event, false);
                if ("".equals(coreElement.getElementName())) {
                    anonymousRanking++;
                }
            } else {
                Activator.log("Undefined Structure : " + coreElement.getElementName()
                        + MSG_UNDEFINED_DECLARATION, IStatus.WARNING);
            }
        }
    }

    /**
     * Adds an enumeration.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception.
     */
    private void addEnumerations(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        int anonymousRanking = 1;
        for (ICElement element : translationUnit.getChildrenOfType(ICElement.C_ENUMERATION)) {
            IEnumeration coreElement = (IEnumeration)element;
            IASTSimpleDeclaration simpleDeclaration = BindingResolver.resolveBindingIASTEnumeration(
                    declarations, coreElement, anonymousRanking);
            if (simpleDeclaration != null) {
                AbstractCModelChangedEvent event = getFileReconciler(ast).addElement(
                        (IASTEnumerationSpecifier)simpleDeclaration.getDeclSpecifier(), coreElement);
                umlModelChangeListener.notifyChanges(event, false);
                if ("".equals(coreElement.getElementName())) {
                    anonymousRanking++;
                }
            } else {
                Activator.log("Undefined Enumeration : " + coreElement.getElementName()
                        + MSG_UNDEFINED_DECLARATION, IStatus.WARNING);
            }
        }
    }

    /**
     * Adds a type definition.
     *
     * @param translationUnit
     *            The translation unit.
     * @param ast
     *            The AST translation unit.
     * @throws CModelException
     *             exception
     */
    private void addTypeDefs(ITranslationUnit translationUnit, IASTTranslationUnit ast)
            throws CModelException {
        AbstractCModelChangedEvent event = null;
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
                Activator.log("Undefined TypedDef : " + coreElement.getElementName()
                        + MSG_UNDEFINED_DECLARATION, IStatus.ERROR);
            }
        }
    }

}
