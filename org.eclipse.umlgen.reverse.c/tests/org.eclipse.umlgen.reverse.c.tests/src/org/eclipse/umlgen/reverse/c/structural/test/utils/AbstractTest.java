/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *      Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.structural.test.utils;

import static org.eclipse.umlgen.reverse.c.structural.test.utils.TestUtils.getActual;
import static org.eclipse.umlgen.reverse.c.structural.test.utils.TestUtils.getExpected;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.IWorkingCopy;
import org.eclipse.cdt.internal.ui.text.ICReconcilingListener;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.ui.IWorkingCopyManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Model;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;
import org.junit.After;
import org.junit.Before;

public class AbstractTest {

    private ResourceSet rs;

    @Before
    public void setUp() {
        rs = new ResourceSetImpl();
    }

    @After
    public void tearDown() {
        for (Iterator<Resource> it = rs.getResources().iterator(); it.hasNext();) {
            Resource r = it.next();
            r.unload();
            it.remove();
        }
        rs = null;
    }

    protected void testModel(IProject project, String expectedUMLFilePath) throws InterruptedException {
        Model actualSourceModel = getActual(getResourceSet(), project);
        Model expectedSourceModel = getExpected(getResourceSet(), expectedUMLFilePath);
        // now way to build the expected model with the good name of actual as
        // we do not create Model in expected
        expectedSourceModel.setName(actualSourceModel.getName());

        org.eclipse.umlgen.reverse.c.activity.test.utils.TestUtils.assertEquals(expectedSourceModel,
                actualSourceModel);
    }

    protected ResourceSet getResourceSet() {
        return rs;
    }

    protected TextEditor openEditor(IFile cFile) {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "openEditor"));
        OpenEditorRunnable runnable = new OpenEditorRunnable();
        runnable.setIFile(cFile);
        Display.getDefault().syncExec(runnable);

        sleep(800);

        return runnable.getEditor();
    }

    private static class OpenEditorRunnable implements Runnable {

        private IFile iFile;

        private TextEditor editor;

        public void setIFile(IFile iFile) {
            this.iFile = iFile;
        }

        public TextEditor getEditor() {
            if (editor == null) {
                throw new NullPointerException("Editor can not be null");
            }
            return editor;
        }

        public void run() {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(
                    iFile.getName());
            try {
                editor = (TextEditor)page.openEditor(new FileEditorInput(iFile), desc.getId());
            } catch (PartInitException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Closes the given editor. The editor must belong to this workbench page.
     * <p>
     * If the editor has unsaved content and <code>save</code> is <code>true</code>, the user will be given
     * the opportunity to save it.
     * </p>
     *
     * @param editor
     *            the editor to close
     * @param save
     *            <code>true</code> to save the editor contents if required (recommended), and
     *            <code>false</code> to discard any unsaved changes
     */
    protected void closeEditor(TextEditor editor, boolean saveBeforeClose) {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "closeEditor"));
        CloseEditorRunnable runnable = new CloseEditorRunnable();
        runnable.setEditor(editor);
        runnable.saveBeforeClose(saveBeforeClose);
        Display.getDefault().syncExec(runnable);

        sleep(800);
    }

    private static class CloseEditorRunnable implements Runnable {

        private TextEditor editor;

        private boolean saveBeforeClose;

        public void setEditor(TextEditor editor) {
            this.editor = editor;
        }

        public void saveBeforeClose(boolean saveBeforeClose) {
            this.saveBeforeClose = saveBeforeClose;
        }

        public void run() {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

            if (saveBeforeClose) {
                page.saveEditor(editor, false);
            }

            if (!page.closeEditor(editor, false)) {
                throw new RuntimeException("Editor can not be closed");
            }
        }
    }

    protected void saveEditor(TextEditor editor) {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "closeEditor"));
        SaveEditorRunnable runnable = new SaveEditorRunnable();
        runnable.setEditor(editor);
        Display.getDefault().syncExec(runnable);

        sleep(800);
    }

    private static class SaveEditorRunnable implements Runnable {

        private TextEditor editor;

        public void setEditor(TextEditor editor) {
            this.editor = editor;
        }

        public void run() {
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

            page.saveEditor(editor, false);
        }
    }

    /**
     * Set the document view of the editor input to the content of the given {@link InputStream}. This stream
     * is closed after the end of the call.
     *
     * @param textEditor
     * @param is
     */
    public void setEditorContent(final TextEditor textEditor, final InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char)ch);
            }
            setEditorContent(textEditor, sb.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void reconcile(final TextEditor editor, IProgressMonitor progressMonitor) {
        sleep(1200);
        IWorkingCopyManager fManager = CUIPlugin.getDefault().getWorkingCopyManager();
        boolean computeAST = editor instanceof ICReconcilingListener;
        IASTTranslationUnit ast = null;
        IWorkingCopy workingCopy = fManager.getWorkingCopy(editor.getEditorInput());
        if (workingCopy == null) {
            return;
        }
        boolean forced = false;
        try {
            // reconcile
            synchronized(workingCopy) {
                forced = workingCopy.isConsistent();
                ast = workingCopy.reconcile(computeAST, true, progressMonitor);
            }
        } catch (OperationCanceledException oce) {
            // document was modified while parsing
        } catch (CModelException e) {
            IStatus status = new Status(IStatus.ERROR, CUIPlugin.PLUGIN_ID, IStatus.OK,
                    "Error in CDT UI during reconcile", e); //$NON-NLS-1$
            CUIPlugin.log(status);
        } finally {
            if (computeAST) {
                IIndex index = null;
                if (ast != null) {
                    index = ast.getIndex();
                }
                try {
                    final boolean canceled = progressMonitor.isCanceled();
                    if (ast == null || canceled) {
                        ((ICReconcilingListener)editor).reconciled(null, forced, progressMonitor);
                    } else {
                        synchronized(ast) {
                            ((ICReconcilingListener)editor).reconciled(ast, forced, progressMonitor);
                        }
                    }
                    if (canceled) {
                        aboutToBeReconciled(editor);
                    }
                } catch (Exception e) {
                    IStatus status = new Status(IStatus.ERROR, CUIPlugin.PLUGIN_ID, IStatus.OK,
                            "Error in CDT UI during reconcile", e); //$NON-NLS-1$
                    CUIPlugin.log(status);
                } finally {
                    if (index != null) {
                        index.releaseReadLock();
                    }
                }
            }
        }
    }

    private void aboutToBeReconciled(final TextEditor editor) {
        if (editor instanceof ICReconcilingListener) {
            ((ICReconcilingListener)editor).aboutToBeReconciled();
        }
    }

    public void setEditorContent(final TextEditor textEditor, final String is) {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "setEditorContent"));
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                FileEditorInput editorInput = (FileEditorInput)textEditor.getEditorInput();
                IDocument document = textEditor.getDocumentProvider().getDocument(editorInput);
                document.set(is);
            }
        });

        reconcile(textEditor, new NullProgressMonitor());
    }

    private void sleep(int millis) {
        if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public IProject createIProject(final String name, IProgressMonitor monitor) throws CoreException {
        IProject projectHandle = createUnsynchronizedProject(name, monitor);

        ProjectUtil.addNature(projectHandle, BundleConstants.NATURE_ID);
        TestUtils.addBuilders(projectHandle);

        IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
        if (synchronizer != null) {
            synchronizer.createModel(projectHandle);
        }

        monitor.done();
        return TestUtils.root.getProject(name);
    }

    public IProject createUnsynchronizedProject(final String name, IProgressMonitor monitor)
            throws CoreException {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "create IProject"));
        final IProject newProjectHandle = TestUtils.root.getProject(name);

        if (!newProjectHandle.exists()) {
            IProjectDescription description = TestUtils.workspace.newProjectDescription(newProjectHandle
                    .getName());
            CCorePlugin.getDefault().createCDTProject(description, newProjectHandle,
                    new SubProgressMonitor(monitor, 25));
        } else {
            Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "refreshLocal"));
            newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE, monitor);
        }

        // Open the project if we have to
        if (!newProjectHandle.isOpen()) {
            newProjectHandle.open(new SubProgressMonitor(monitor, 25));
        }

        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "CProjectNature.addCNature"));
        CProjectNature.addCNature(newProjectHandle, new SubProgressMonitor(monitor, 1));

        monitor.done();
        return TestUtils.root.getProject(name);
    }

    public IFile createIFile(IProject iProject, IPath path, IProgressMonitor monitor) throws CoreException {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "create IFile"));
        IFile cFile = iProject.getFile(path);
        cFile.create(new ByteArrayInputStream("".getBytes()), true, new SubProgressMonitor(monitor, 25));
        monitor.done();

        sleep(1200);

        return cFile;
    }

    public IFile createIFile(IProject iProject, IPath path, String contents, IProgressMonitor monitor)
            throws CoreException {
        Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID, "create IFile"));
        IFile cFile = iProject.getFile(path);
        cFile.create(new ByteArrayInputStream(contents.getBytes()), true, new SubProgressMonitor(monitor, 25));
        monitor.done();

        sleep(1200);

        return cFile;
    }

    // private void commitWorkingCopy(IFile cFile) {
    // ITranslationUnit translationUnit =
    // CoreModelUtil.findTranslationUnit(cFile);
    // IWorkingCopy copy;
    // try {
    // sleep(1000);
    // IWorkingCopy sharedWorkingCopy = translationUnit.findSharedWorkingCopy();
    // if (sharedWorkingCopy == null) {
    // Activator.log(new Status(IStatus.INFO, BundleConstants.BUNDLE_ID,
    // ">>>>>>>>>>>>>>>>> sharedWorkingCopy == null"));
    // }
    // copy = (translationUnit.findSharedWorkingCopy() == null ?
    // translationUnit.getWorkingCopy() :
    // translationUnit.findSharedWorkingCopy());
    // copy.reconcile();//commit(true, null);
    // } catch (CModelException e) {
    // Activator.log(e);
    // }
    // }

    // public CReconciler getReconciler(CSourceViewer sourceViewer, CEditor
    // editor) {
    // CCompositeReconcilingStrategy strategy=
    // new CCompositeReconcilingStrategy(sourceViewer, editor,
    // getConfiguredDocumentPartitioning(sourceViewer));
    // CReconciler reconciler= new CReconciler(editor, strategy);
    // reconciler.setIsIncrementalReconciler(false);
    // reconciler.setProgressMonitor(new NullProgressMonitor());
    // reconciler.setDelay(500);
    // return reconciler;
    // }
}
