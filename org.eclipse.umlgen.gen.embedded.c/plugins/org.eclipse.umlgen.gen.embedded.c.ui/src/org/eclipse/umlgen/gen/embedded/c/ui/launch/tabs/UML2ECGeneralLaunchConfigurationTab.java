/*******************************************************************************
 * Copyright (c) 2011, 2014, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephane Begaudeau (Obeo) - initial API and implementation
 *     Johan Hardy (Spacebel) - adapted for Embedded C generator
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.launch.tabs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.umlgen.gen.embedded.c.ui.UML2ECUIActivator;
import org.eclipse.umlgen.gen.embedded.c.ui.common.ConfigurationServices;
import org.eclipse.umlgen.gen.embedded.c.ui.utils.UML2ECMessages;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * The general tab of the launch configuration.
 */
public class UML2ECGeneralLaunchConfigurationTab extends AbstractUML2ECLaunchConfigurationTab {

    /**
     * The workspace relative path of the input model.
     */
    private Text modelPathText;

    /**
     * The path of the output folder of a project.
     */
    private Text outputFolderPathText;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Font font = parent.getFont();
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        composite.setFont(font);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 1;
        composite.setLayoutData(gd);

        this.createGenerationGroup(composite, font);
        this.createEmbeddedCGroup(composite, font);

        this.setControl(composite);
        this.update();
    }

    /**
     * Creates the group containing the general options of the generation.
     *
     * @param composite
     *            The composite containing the group
     * @param font
     *            the font used by the parent of the group
     */
    private void createGenerationGroup(Composite composite, Font font) {
        GridData gd;
        Group generationGroup = createGroup(composite, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.GenerationGroupName"), 3, 1,
                GridData.FILL_HORIZONTAL);
        Composite comp = new Composite(generationGroup, SWT.NONE);
        GridLayout layout = new GridLayout(4, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        comp.setLayout(layout);
        comp.setFont(font);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        // Model path
        Label modelPathLabel = new Label(comp, SWT.NONE);
        modelPathLabel.setText(UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.UMLModelPathLabel"));

        this.modelPathText = new Text(comp, SWT.SINGLE | SWT.BORDER);
        this.modelPathText.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.modelPathText.setLayoutData(gd);
        this.modelPathText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                update();
            }
        });

        final Button browseModelButton = createPushButton(comp, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.UMLModelBrowseButtonName"), null);
        browseModelButton.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(),
                        false, ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
                dialog.setTitle(UML2ECMessages
                        .getString("UML2ECGeneralLaunchConfigurationTab.UMLModelDialogTitle"));
                dialog.setInitialPattern("*.uml");
                dialog.open();
                if (dialog.getResult() != null && dialog.getResult().length > 0) {
                    Object[] results = dialog.getResult();
                    for (Object result : results) {
                        if (result instanceof IFile) {
                            modelPathText.setText(((IFile)result).getFullPath().toString());
                            break;
                        }
                    }
                }
                update();
                updateLaunchConfigurationDialog();
            }
        });
        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.UMLModelPathHelp"));
    }

    /**
     * Creates the group containing the options of the generation.
     *
     * @param composite
     *            the composite containing the group
     * @param font
     *            The font used by the parent of the group
     */
    private void createEmbeddedCGroup(Composite composite, Font font) {
        GridData gd;
        Group embeddedCGroup = createGroup(composite, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.ECGroupName"), 3, 1, GridData.FILL_HORIZONTAL);
        Composite comp = new Composite(embeddedCGroup, SWT.NONE);
        GridLayout layout = new GridLayout(4, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        comp.setLayout(layout);
        comp.setFont(font);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        // Output folder path
        Label outputFolderPathLabel = new Label(comp, SWT.NONE);
        outputFolderPathLabel.setText(UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.OutputFolderPathLabel"));

        this.outputFolderPathText = new Text(comp, SWT.SINGLE | SWT.BORDER);
        this.outputFolderPathText.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.outputFolderPathText.setLayoutData(gd);
        this.outputFolderPathText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                update();
            }
        });

        final Button browseModelButton = createPushButton(comp, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.OutputFolderBrowseButtonName"), null);
        browseModelButton.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(),
                        false, ResourcesPlugin.getWorkspace().getRoot(), IResource.FOLDER);
                dialog.setTitle(UML2ECMessages
                        .getString("UML2ECGeneralLaunchConfigurationTab.OutputFolderDialogTitle"));
                dialog.setInitialPattern("**");
                dialog.open();
                if (dialog.getResult() != null && dialog.getResult().length > 0) {
                    Object[] results = dialog.getResult();
                    for (Object result : results) {
                        if (result instanceof IFolder) {
                            outputFolderPathText.setText(((IFolder)result).getFullPath().toString());
                            break;
                        }
                    }
                }
                update();
                updateLaunchConfigurationDialog();
            }
        });
        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECGeneralLaunchConfigurationTab.OutputFolderPathHelp"));
    }

    /**
     * Checks potential errors.
     */
    private void update() {
        this.setErrorMessage(null);

        this.getLaunchConfigurationDialog().updateButtons();
        this.getLaunchConfigurationDialog().updateMessage();

        // Check the path of the model
        if (this.modelPathText != null) {
            String text = this.modelPathText.getText();
            if (text != null && text.length() > 0) {
                IFile model = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(text));
                if (model != null && !model.exists()) {
                    this.setErrorMessage(UML2ECMessages
                            .getString("UML2ECGeneralLaunchConfigurationTab.MissingInputModel"));
                }
            }
        }

        // Check the path of the output folder
        if (this.outputFolderPathText != null) {
            String text = this.outputFolderPathText.getText();
            if (text != null && text.length() > 0) {
                IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(text));
                if (folder != null && !folder.exists()) {
                    this.setErrorMessage(UML2ECMessages
                            .getString("UML2ECGeneralLaunchConfigurationTab.MissingOutputFolderPath"));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        // model path
        configuration.setAttribute(IUML2ECConstants.UML_MODEL_PATH, "");
        if (this.modelPathText != null) {
            this.modelPathText.setText("");
        }

        // output folder path
        configuration.setAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, "");
        if (this.outputFolderPathText != null) {
            this.outputFolderPathText.setText("");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
     */
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            // Model path
            String attribute = configuration.getAttribute(IUML2ECConstants.UML_MODEL_PATH, "");
            this.modelPathText.setText(attribute);

            // Output folder path
            attribute = configuration.getAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, "");
            this.outputFolderPathText.setText(attribute);
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        // Model path
        String umlModelPath = this.modelPathText.getText();
        configuration.setAttribute(IUML2ECConstants.UML_MODEL_PATH, umlModelPath);
        // Save configuration for the model file
        if (umlModelPath != null && umlModelPath.length() != 0) {
            IFile modelFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(umlModelPath));
            ConfigurationServices.saveConfigurationProperty((IResource)modelFile, configuration.getName());
        }

        // Output folder path
        String outputFolderPath = this.outputFolderPathText.getText();
        configuration.setAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, outputFolderPath);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
     */
    @Override
    public boolean isValid(ILaunchConfiguration configuration) {
        boolean isValid = true;
        try {
            // Model path
            String attribute = configuration.getAttribute(IUML2ECConstants.UML_MODEL_PATH, "");
            isValid = isValid && attribute != null && attribute.trim().length() > 0;

            // Output folder path
            attribute = configuration.getAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, "");
            isValid = isValid && attribute != null && attribute.trim().length() > 0;
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }
        return isValid;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
     */
    public String getName() {
        return UML2ECMessages.getString("UML2ECGeneralLaunchConfigurationTab.Name");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
     */
    @Override
    public Image getImage() {
        return UML2ECUIActivator.getDefault().getImage("icons/model_obj.gif");
    }

}
