/*******************************************************************************
 * Copyright (c) 2007, 2014 Topcased and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Topcased contributors and others - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.launcher.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Class that is used to add a property page to configurate the code generation.
 */
public class Uml2AutoJavaComponentProperties extends PropertyPage {

    /** Browse button for output location. */
    private Button outputBrowseButton;

    /** Text field for output location. */
    private Text outputText;

    /** Label for output location. */
    private Label outputLabel;

    /** Button for the validation. */
    private Button validationButton;

    /** Browse button for decorators location. */
    private Button comParamBrowseButton;

    /** Text field for decorators location. */
    private Text comParamText;

    /** Label for decorators location. */
    private Label comParamLabel;

    /**
     * Constructor for SamplePropertyPage.
     */
    public Uml2AutoJavaComponentProperties() {
        super();
    }

    /**
     * Create and initialize widget on the property page.
     *
     * @param parent
     *            is the composite
     */
    // CHECKSTYLE:OFF
    private void addFirstSection(Composite parent) {
        Composite composite = createDefaultComposite(parent);

        // Ouput label + text +button

        outputLabel = new Label(composite, SWT.NONE);
        outputLabel.setText("Output Folder");
        FormData data15 = new FormData();
        data15.left = new FormAttachment(0, 0);
        data15.width = 68;
        outputLabel.setLayoutData(data15);

        outputText = new Text(composite, SWT.BORDER);
        FormData data14 = new FormData();
        data14.left = new FormAttachment(outputLabel, 15);
        data14.width = 266;
        outputText.setLayoutData(data14);

        outputBrowseButton = new Button(composite, SWT.PUSH);
        outputBrowseButton.setText("Browse...");
        outputBrowseButton.addMouseListener(new BrowseOutputFolderButtonListener());
        FormData data16 = new FormData();
        data16.left = new FormAttachment(outputText, 15);
        data16.width = 70;
        outputBrowseButton.setLayoutData(data16);

        comParamLabel = new Label(composite, SWT.NONE);
        comParamLabel.setText("Communication Parametering Model");
        FormData data17 = new FormData();
        data17.left = new FormAttachment(0, 0);
        data17.width = 170;
        data17.top = new FormAttachment(outputLabel, 30);
        comParamLabel.setLayoutData(data17);

        comParamText = new Text(composite, SWT.BORDER);
        FormData data18 = new FormData();
        data18.left = new FormAttachment(comParamLabel, 15);
        data18.width = 266;
        data18.top = new FormAttachment(outputLabel, 30);
        comParamText.setLayoutData(data18);

        comParamBrowseButton = new Button(composite, SWT.PUSH);
        comParamBrowseButton.setText("Browse...");
        comParamBrowseButton.addMouseListener(new BrowseParamModelButtonListener());
        FormData data19 = new FormData();
        data19.left = new FormAttachment(comParamText, 15);
        data19.width = 70;
        data19.top = new FormAttachment(outputLabel, 30);
        comParamBrowseButton.setLayoutData(data19);

        validationButton = new Button(composite, SWT.CHECK);
        validationButton.setText("Validate the model");
        FormData data1 = new FormData();
        data1.left = new FormAttachment(0, 0);
        data1.right = new FormAttachment(100, -5);
        data1.top = new FormAttachment(comParamBrowseButton, 15);
        validationButton.setLayoutData(data1);

        // Populate owner text field
        try {
            String s;

            s = ((IResource)getElement()).getPersistentProperty(new QualifiedName("",
                    "OUTPUT_PATH_AUTOJAVA_COMP"));
            if (s != null && s.compareTo("") != 0) {
                outputText.setText(s);
            } else {
                IPath modelPath = ((IResource)getElement()).getLocation();
                modelPath = modelPath.removeLastSegments(1);
                IPath workspacePath = ((IResource)getElement()).getWorkspace().getRoot().getLocation();
                if (modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice() != null) {
                    s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString().substring(
                            modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice()
                            .toString().length());
                } else {
                    s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString();
                }
                outputText.setText('/' + s);
            }

            s = ((IResource)getElement()).getPersistentProperty(new QualifiedName("",
                    "VALIDATE_MODEL_AUTOJAVA_COMP"));
            if (s != null) {
                if (s.compareTo("true") == 0) {
                    validationButton.setSelection(true);
                } else {
                    validationButton.setSelection(false);
                }
            } else {
                validationButton.setSelection(true);
            }

            s = ((IResource)getElement()).getPersistentProperty(new QualifiedName("",
                    "COMMUNICATION_PARAMETERING"));
            if (s != null && s.compareTo("") != 0) {
                comParamText.setText(s);
            } else {
                comParamText.setText("");
            }

        } catch (CoreException e) {
            validationButton.setSelection(true);
            IPath modelPath = ((IResource)getElement()).getLocation();
            modelPath = modelPath.removeLastSegments(1);
            IPath workspacePath = ((IResource)getElement()).getWorkspace().getRoot().getLocation();
            String s = null;
            if (modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice() != null) {
                s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString().substring(
                        modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice().toString()
                        .length());
            } else {
                s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString();
            }
            outputText.setText('/' + s);
        }
    }

    // CHECKSTYLE:ON

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        FormLayout layout = new FormLayout();
        composite.setLayout(layout);
        FormData data = new FormData();
        composite.setLayoutData(data);

        addFirstSection(composite);

        return composite;
    }

    /**
     * Create default composite.
     *
     * @param parent
     *            is the parent composite
     * @return the create composite
     */
    private Composite createDefaultComposite(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        FormLayout layout = new FormLayout();
        composite.setLayout(layout);
        FormData data = new FormData();
        composite.setLayoutData(data);

        return composite;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        validationButton.setSelection(true);
        IPath modelPath = ((IResource)getElement()).getLocation();
        modelPath = modelPath.removeLastSegments(1);
        IPath workspacePath = ((IResource)getElement()).getWorkspace().getRoot().getLocation();
        String s = "";
        if (modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice() != null) {
            s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString().substring(
                    modelPath.removeFirstSegments(workspacePath.segmentCount()).getDevice().toString()
                    .length());
        } else {
            s = modelPath.removeFirstSegments(workspacePath.segmentCount()).toString();
        }
        outputText.setText('/' + s);
        comParamText.setText("");
    }

    /**
     * Convert a boolean to a string.
     *
     * @param b
     *            is the boolean
     * @return a string
     */
    public String boolToString(boolean b) {
        if (b) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        // store the value in the owner text field
        try {
            ((IResource)getElement()).setPersistentProperty(new QualifiedName("",
                    "VALIDATE_MODEL_AUTOJAVA_COMP"), boolToString(validationButton.getSelection()));
            ((IResource)getElement()).setPersistentProperty(
                    new QualifiedName("", "OUTPUT_PATH_AUTOJAVA_COMP"), outputText.getText());
            ((IResource)getElement()).setPersistentProperty(new QualifiedName("",
                    "COMMUNICATION_PARAMETERING"), comParamText.getText());
        } catch (CoreException e) {
            return false;
        }

        return true;
    }

    /**
     * This returns the eclipse resources corresponding to the given list of workspace relative paths
     * (separated by ";").
     *
     * @param property
     *            The list of paths.
     * @return The list of the related resources.
     */
    private List<IResource> getResourcesFromProperties(String property) {
        List<IResource> selectedResources = new ArrayList<IResource>();
        String[] paths = property.split(";");
        for (String path : paths) {
            IResource resource = ((IResource)getElement()).getWorkspace().getRoot().findMember(path);
            selectedResources.add(resource);
        }
        return selectedResources;
    }

    /**
     * Mouse listener for the "browse" button. It only open an Eclipse dialog for selecting the output folder.
     */
    private class BrowseOutputFolderButtonListener implements MouseListener {

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent arg0) {
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
            ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), null, true,
                    "Select output :");
            dialog.open();
            Object[] objects = dialog.getResult();
            if (objects != null && objects.length == 1) {
                if (objects[0] instanceof IPath) {
                    IPath p = (IPath)objects[0];
                    if (p.segmentCount() > 0) {
                        String s = p.toPortableString();
                        outputText.setText(s);
                    }
                }
            }
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseUp(org.eclipse.swt.events.MouseEvent arg0) {
        }

    }

    /**
     * Listener to launch action on mouse event on the decorators button.
     */
    private class BrowseParamModelButtonListener implements MouseListener {

        /**
         * A specific label provider to display the list of the decorator resources (give the name of the
         * container).
         */
        private class PathLabelProvider extends WorkbenchLabelProvider {

            /**
             * {@inheritDoc}
             *
             * @see org.eclipse.ui.model.WorkbenchLabelProvider#decorateText(java.lang.String,
             *      java.lang.Object)
             */
            @Override
            protected String decorateText(String input, Object element) {
                if (element instanceof IResource) {
                    return super.decorateText(input, element) + " - "
                            + ((IResource)element).getParent().getFullPath().makeRelative().toString();
                } else {
                    return super.decorateText(input, element);
                }
            }

        }

        /**
         * A specific content provider to display the list of the decorator resources.
         */
        private class DecoratorsContentProvider extends WorkbenchContentProvider {

            /**
             * {@inheritDoc}
             *
             * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
             */
            @Override
            public Object[] getChildren(Object o) {
                Object[] result = new Object[0];
                if (o instanceof IContainer) {
                    try {
                        Object[] elements = getChildren((IContainer)o);
                        result = getResult(elements).toArray();
                    } catch (CoreException e) {
                        // do nothing.
                    }
                }
                return result;
            }

            /**
             * This returns every looked-up decorator files from the given list of eclipse resources.
             *
             * @param elements
             *            The eclipse resources.
             * @return The list of decorator files.
             * @throws CoreException
             *             exception.
             */
            private List<Object> getResult(Object[] elements) throws CoreException {
                List<Object> result = new ArrayList<Object>();
                for (int i = 0; i < elements.length; i++) {
                    if (elements[i] instanceof IFile
                            && ("asl".equals(((IFile)elements[i]).getFileExtension()) || "eth"
                                    .equals(((IFile)elements[i]).getFileExtension()))) {
                        result.add(elements[i]);
                    } else if (elements[i] instanceof IContainer && ((IContainer)elements[i]).isAccessible()
                            && !((IContainer)elements[i]).isHidden()) {
                        result.addAll(getResult(getChildren((IContainer)elements[i])));
                    }
                }
                return result;
            }

            /**
             * This returns the children of the given container.
             *
             * @param container
             *            The container.
             * @return The children.
             * @throws CoreException
             *             exception.
             */
            private Object[] getChildren(IContainer container) throws CoreException {
                return container.members();
            }
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent arg0) {
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
            ListSelectionDialog dialog = new ListSelectionDialog(getShell(), ((IResource)getElement())
                    .getWorkspace().getRoot(), getFileProvider(), new PathLabelProvider(),
                    "Select parametering model :");

            // Initialize the research dialog for decorators with the values from the decorators text field.
            List<IResource> selectedResources = getResourcesFromProperties(comParamText.getText());
            dialog.setInitialElementSelections(selectedResources);

            dialog.open();
            Object[] files = dialog.getResult();
            if (files != null && files.length > 0) {
                // reset the decorators text field
                comParamText.setText("");
                for (Object file : files) {
                    if (file instanceof IResource) {
                        IPath path = ((IResource)file).getFullPath();
                        if (path.segmentCount() > 0) {
                            String s = path.toPortableString();
                            fillDecoratorsTextField(s);
                        }
                    }
                }
            }
        }

        /**
         * This fills the decorators text field with the given decorator path.<br>
         * If the field already contains a path, this adds the new path to the field, after ";".
         *
         * @param s
         *            The new decorator path.
         */
        private void fillDecoratorsTextField(String s) {
            if (comParamText.getText() != null && comParamText.getText().length() > 0) {
                comParamText.setText(comParamText.getText() + ";" + s);
            } else {
                comParamText.setText(s);
            }
        }

        /**
         * Returns a content provider for <code>FileSystemElement</code>s that returns only files as children.
         *
         * @return a content provider to select the decorator models.
         */
        private ITreeContentProvider getFileProvider() {
            return new DecoratorsContentProvider();
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
         */
        public void mouseUp(org.eclipse.swt.events.MouseEvent arg0) {
        }

    }
}
