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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
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
import org.eclipse.umlgen.gen.embedded.c.ui.UML2ECUIActivator;
import org.eclipse.umlgen.gen.embedded.c.ui.utils.UML2ECMessages;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * The documentation tab of the launch configuration.
 */
public class UML2ECDocumentationLaunchConfigurationTab extends AbstractUML2ECLaunchConfigurationTab {

    /**
     * Indicates if traceability SRD-DDD must be generated.
     */
    private Button generateTraceabilityButton;

    /**
     * Indicates if the svn:property $Date$ must be generated.
     */
    private Button generateSvnDateButton;

    /**
     * Indicates if the svn:property $Id$ must be generated.
     */
    private Button generateSvnIdButton;

    /**
     * The author of the code.
     */
    private Text authorText;

    /**
     * The version of the code.
     */
    private Text versionText;

    /**
     * The copyright of the code.
     */
    private Text copyrightText;

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

        this.createHeaderGroup(composite, font);
        this.createMiscellaneousGroup(composite, font);

        this.setControl(composite);
        this.update();
    }

    /**
     * Creates the group containing the miscellaneous options of the class generation.
     *
     * @param composite
     *            The composite containing the group
     * @param font
     *            The font used by the parent of the group
     */
    private void createMiscellaneousGroup(Composite composite, Font font) {
        GridData gd;
        Group miscellaneousGroup = createGroup(composite, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.MiscellaneousGroupName"), 3, 1,
                GridData.FILL_HORIZONTAL);
        Composite comp = new Composite(miscellaneousGroup, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        comp.setLayout(layout);
        comp.setFont(font);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        // Generate traceability
        this.generateTraceabilityButton = new Button(comp, SWT.CHECK);
        this.generateTraceabilityButton.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.generateTraceabilityButton.setLayoutData(gd);
        this.generateTraceabilityButton.setText(UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateTraceabilityLabel"));
        this.generateTraceabilityButton.setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_TRACEABILITY);
        this.generateTraceabilityButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                update();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateTraceabilityHelp"));
    }

    /**
     * Creates the group containing the documentation options of the class generation.
     *
     * @param composite
     *            The composite containing the group
     * @param font
     *            The font used by the parent of the group
     */
    private void createHeaderGroup(Composite composite, Font font) {
        GridData gd;
        Group headerGroup = createGroup(composite, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.HeaderGroupName"), 3, 1,
                GridData.FILL_HORIZONTAL);
        Composite comp = new Composite(headerGroup, SWT.NONE);
        GridLayout layout = new GridLayout(3, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        comp.setLayout(layout);
        comp.setFont(font);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);

        // Author
        Label authorLabel = new Label(comp, SWT.NONE);
        authorLabel
                .setText(UML2ECMessages.getString("UML2ECDocumentationLaunchConfigurationTab.AuthorLabel"));

        this.authorText = new Text(comp, SWT.SINGLE | SWT.BORDER);
        this.authorText.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.authorText.setLayoutData(gd);
        this.authorText.setText(IUML2ECConstants.Default.DEFAULT_AUTHOR);
        this.authorText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.AuthorHelp"));

        // Version
        Label versionLabel = new Label(comp, SWT.NONE);
        versionLabel.setText(UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.VersionLabel"));

        this.versionText = new Text(comp, SWT.SINGLE | SWT.BORDER);
        this.versionText.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.versionText.setLayoutData(gd);
        this.versionText.setText(IUML2ECConstants.Default.DEFAULT_VERSION);
        this.versionText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.VersionHelp"));

        // Copyright
        Label copyrightLabel = new Label(comp, SWT.NONE);
        copyrightLabel.setText(UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.CopyrightLabel"));

        this.copyrightText = new Text(comp, SWT.MULTI | SWT.BORDER);
        this.copyrightText.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.copyrightText.setLayoutData(gd);
        this.copyrightText.setText(IUML2ECConstants.Default.DEFAULT_COPYRIGHT);
        this.copyrightText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.CopyrightHelp"));

        // Generate svn:property $Date$
        Label svnDateLabel = new Label(comp, SWT.NONE);
        svnDateLabel.setText("");

        this.generateSvnDateButton = new Button(comp, SWT.CHECK);
        this.generateSvnDateButton.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.generateSvnDateButton.setLayoutData(gd);
        this.generateSvnDateButton.setText(UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateSvnDateLabel"));
        this.generateSvnDateButton.setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_DATE);
        this.generateSvnDateButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                update();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateSvnDateHelp"));

        // Generate svn:property $Id$
        Label svnIdLabel = new Label(comp, SWT.NONE);
        svnIdLabel.setText("");

        this.generateSvnIdButton = new Button(comp, SWT.CHECK);
        this.generateSvnIdButton.setFont(composite.getFont());
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 1;
        this.generateSvnIdButton.setLayoutData(gd);
        this.generateSvnIdButton.setText(UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateSvnIdLabel"));
        this.generateSvnIdButton.setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_ID);
        this.generateSvnIdButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                update();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                update();
            }
        });

        createHelpButton(comp, UML2ECMessages
                .getString("UML2ECDocumentationLaunchConfigurationTab.GenerateSvnIdHelp"));
    }

    /**
     * Update the launch configuration and check potential errors.
     */
    private void update() {
        this.getLaunchConfigurationDialog().updateButtons();
        this.getLaunchConfigurationDialog().updateMessage();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        // author
        configuration.setAttribute(IUML2ECConstants.AUTHOR, IUML2ECConstants.Default.DEFAULT_AUTHOR);
        if (this.authorText != null) {
            this.authorText.setText(IUML2ECConstants.Default.DEFAULT_AUTHOR);
        }

        // version
        configuration.setAttribute(IUML2ECConstants.VERSION, IUML2ECConstants.Default.DEFAULT_VERSION);
        if (this.versionText != null) {
            this.versionText.setText(IUML2ECConstants.Default.DEFAULT_VERSION);
        }

        // copyright
        configuration.setAttribute(IUML2ECConstants.COPYRIGHT, IUML2ECConstants.Default.DEFAULT_COPYRIGHT);
        if (this.copyrightText != null) {
            this.copyrightText.setText(IUML2ECConstants.Default.DEFAULT_COPYRIGHT);
        }

        // Generate traceability
        configuration.setAttribute(IUML2ECConstants.GENERATE_TRACEABILITY,
                IUML2ECConstants.Default.DEFAULT_GENERATE_TRACEABILITY);
        if (this.generateTraceabilityButton != null) {
            this.generateTraceabilityButton
                    .setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_TRACEABILITY);
        }

        // Generate svn:property $Date$
        configuration.setAttribute(IUML2ECConstants.GENERATE_SVN_DATE,
                IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_DATE);
        if (this.generateSvnDateButton != null) {
            this.generateSvnDateButton.setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_DATE);
        }

        // Generate svn:property $Id$
        configuration.setAttribute(IUML2ECConstants.GENERATE_SVN_ID,
                IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_ID);
        if (this.generateSvnIdButton != null) {
            this.generateSvnIdButton.setSelection(IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_ID);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
     */
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            // Generate traceability
            boolean booleanAttribute = configuration.getAttribute(IUML2ECConstants.GENERATE_TRACEABILITY,
                    IUML2ECConstants.Default.DEFAULT_GENERATE_TRACEABILITY);
            this.generateTraceabilityButton.setSelection(booleanAttribute);

            // Generate svn:property $Date$
            booleanAttribute = configuration.getAttribute(IUML2ECConstants.GENERATE_SVN_DATE,
                    IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_DATE);
            this.generateSvnDateButton.setSelection(booleanAttribute);

            // Generate svn:property $Id$
            booleanAttribute = configuration.getAttribute(IUML2ECConstants.GENERATE_SVN_ID,
                    IUML2ECConstants.Default.DEFAULT_GENERATE_SVN_ID);
            this.generateSvnIdButton.setSelection(booleanAttribute);

            // Author
            String stringAttribute = configuration.getAttribute(IUML2ECConstants.AUTHOR,
                    IUML2ECConstants.Default.DEFAULT_AUTHOR);
            this.authorText.setText(stringAttribute);

            // Version
            stringAttribute = configuration.getAttribute(IUML2ECConstants.VERSION,
                    IUML2ECConstants.Default.DEFAULT_VERSION);
            this.versionText.setText(stringAttribute);

            // Copyright
            stringAttribute = configuration.getAttribute(IUML2ECConstants.COPYRIGHT,
                    IUML2ECConstants.Default.DEFAULT_COPYRIGHT);
            this.copyrightText.setText(stringAttribute);

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
        // Traceability
        boolean value = this.generateTraceabilityButton.getSelection();
        configuration.setAttribute(IUML2ECConstants.GENERATE_TRACEABILITY, value);

        // svn:property $Date$
        value = this.generateSvnDateButton.getSelection();
        configuration.setAttribute(IUML2ECConstants.GENERATE_SVN_DATE, value);

        // svn:property $Id$
        value = this.generateSvnIdButton.getSelection();
        configuration.setAttribute(IUML2ECConstants.GENERATE_SVN_ID, value);

        // Author
        String author = this.authorText.getText();
        configuration.setAttribute(IUML2ECConstants.AUTHOR, author);

        // Version
        String version = this.versionText.getText();
        configuration.setAttribute(IUML2ECConstants.VERSION, version);

        // Copyright
        String copyright = this.copyrightText.getText();
        configuration.setAttribute(IUML2ECConstants.COPYRIGHT, copyright);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse.debug.core.ILaunchConfiguration)
     */
    @Override
    public boolean isValid(ILaunchConfiguration launchConfig) {
        return super.isValid(launchConfig);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
     */
    public String getName() {
        return UML2ECMessages.getString("UML2ECDocumentationLaunchConfigurationTab.Name");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
     */
    @Override
    public Image getImage() {
        return UML2ECUIActivator.getDefault().getImage("icons/class_obj.gif");
    }

}
