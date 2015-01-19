/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.c.common.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.Messages;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;

/**
 * Manages the customization for reverse C to UML.<br />
 * Two categories are suggested : the first one allows to specify UML/UMLDI resources to synchronize, the
 * second one enables to declare the different UML packages on which classes will be placed.<br>
 * Creation : 04 may 2010<br/>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to facilities
public class CProjectPropertyPage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

    /** The related eclipse project. */
    private IProject project;

    // FIXME MIGRATION
    // private RadioGroupFieldEditor syncModeEditor;

    // FIXME MIGRATION reference to facilities
    // private ResourceFieldEditor diagramPath;

    /** The model path text field. */
    private StringFieldEditor modelPath;

    // FIXME MIGRATION reference to facilities
    // private EObjectFieldEditor srcPath;
    /** The source path text field. */
    private StringFieldEditor srcPath;

    // FIXME MIGRATION reference to facilities
    // private EObjectFieldEditor typePath;
    /** The type path text field. */
    private StringFieldEditor typePath;

    // FIXME MIGRATION reference to facilities
    // private EObjectFieldEditor extPath;
    /** The external package path text field. */
    private StringFieldEditor extPath;

    /** The resource set. */
    private ResourceSet rscSet;

    /** The list of the packages. */
    private Object[] packages;

    /** The default label provider. */
    private ILabelProvider defaultLabelProvider;

    /** The label provider. */
    private ILabelProvider advancedLabelProvider;

    /**
     * Constructor.
     */
    public CProjectPropertyPage() {

        rscSet = new ResourceSetImpl();

        // FIXME MIGRATION reference to modeler
        // advancedLabelProvider = new
        // QualifiedNameLabelProvider().createAdapterFactory();

        defaultLabelProvider = new AdapterFactoryLabelProvider(new UMLItemProviderAdapterFactory());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
     */
    public void setElement(IAdaptable element) {
        project = (IProject)element.getAdapter(IResource.class);
        IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
        if (synchronizer != null) {
            synchronizer.setInitialValues(project);
            synchronizer.setDefaultValues(project);
        }
    }

    public IAdaptable getElement() {
        return project;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    @Override
    protected IPreferenceStore doGetPreferenceStore() {
        return PreferenceStoreManager.getPreferenceStore(project);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        // Main Composite
        final Composite mainComposite = new Composite(parent, SWT.NONE);
        mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        final GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        mainComposite.setLayout(layout);

        // FIXME MIGRATION
        // create the top group related to define the synchronization mode when
        // a new project has been started
        // createSyncModeGroup(mainComposite);

        // create the second group related to model paths
        createModelsGroup(mainComposite);

        // create the bottom group related to project settings
        createSettingsGroup(mainComposite);

        loadPreferences();

        return mainComposite;
    }

    // FIXME MIGRATION
    // /**
    // * Creates the first group on which synchronization policy is defined (from
    // * C source or from UML model).
    // *
    // * @param parent
    // * The composite parent
    // */
    // private void createSyncModeGroup(Composite parent) {
    // String[][] data = new String[2][2];
    // data[0] = new String[] {
    //				Messages.getString("CProjectPropertyPage.4"), BundleConstants.SYNC_SOURCE_VALUE }; //$NON-NLS-1$
    // data[1] = new String[] {
    //				Messages.getString("CProjectPropertyPage.5"), BundleConstants.SYNC_MODEL_VALUE }; //$NON-NLS-1$
    // syncModeEditor = new RadioGroupFieldEditor(
    // BundleConstants.SYNC_AT_STARTING,
    //				Messages.getString("CProjectPropertyPage.6"), 2, data, parent, true); //$NON-NLS-1$
    // syncModeEditor.setPreferenceStore(getPreferenceStore());
    // }

    /**
     * Creates the second group on which access paths to models must be specified.
     *
     * @param parent
     *            The composite parent
     */
    private void createModelsGroup(Composite parent) {
        final Group mainGroup = new Group(parent, SWT.NONE);
        mainGroup.setText(Messages.getString("CProjectPropertyPage.0")); //$NON-NLS-1$
        mainGroup.setLayout(new GridLayout());
        mainGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

        // FIXME MIGRATION
        // Composite for the diagram path
        // final Composite diagramComposite = new Composite(mainGroup, SWT.NONE);
        // diagramComposite.setLayout(new GridLayout());
        // diagramComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
        // true, 0, 0));

        // FIXME MIGRATION reference to facilities
        // Access to UMLDI Model path
        //        diagramPath = new ResourceFieldEditor(BundleConstants.UMLDI_MODEL_PATH, Messages.getString("CProjectPropertyPage.1"), diagramComposite); //$NON-NLS-1$
        // diagramPath.setPreferenceStore(getPreferenceStore());
        // diagramPath.setPage(this);
        // diagramPath.setEmptyStringAllowed(false);
        // diagramPath.setPropertyChangeListener(new IPropertyChangeListener()
        // {
        // /**
        // * @see
        // org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
        // */
        // public void propertyChange(PropertyChangeEvent event)
        // {
        // if (FieldEditor.VALUE.equals(event.getProperty()))
        // {
        // // deduce and set a default value into the next field.
        // URI uri = URI.createURI(diagramPath.getStringValue(), false);
        // try
        // {
        // Resource rsc = rscSet.getResource(uri, true);
        // EObject root = rsc.getContents().get(0);
        // // FIXME MIGRATION reference to modeler
        // // if (root instanceof Diagrams)
        // // {
        // // EObject model = ((Diagrams) root).getModel();
        // // URI modelURI = EcoreUtil.getURI(model);
        // //
        // modelPath.setStringValue(URI.decode(modelURI.trimFragment().toString()));
        // // }
        // }
        // catch (Exception e)
        // {
        //                        modelPath.setStringValue(""); //$NON-NLS-1$
        // }
        // }
        // }
        // });

        // Composite for the model path
        final Composite modelComposite = new Composite(mainGroup, SWT.NONE);
        modelComposite.setLayout(new GridLayout());
        modelComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 0, 0));

        // Access to UML Model path
        // CHECKSTYLE:OFF
        modelPath = new StringButtonFieldEditor() {
            // CHECKSTYLE:ON
            {
                init(BundleConstants.UML_MODEL_PATH, Messages.getString("CProjectPropertyPage.3")); //$NON-NLS-1$
                createControl(modelComposite);
            }

            ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
                    new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());

            @Override
            protected String changePressed() {
                IFile d = null;
                dialog.setInput(getElement());
                if (dialog.open() == Window.OK) {
                    Object[] result = dialog.getResult();
                    // sanity check
                    if (result.length == 1) {
                        if (result[0] instanceof IFile) {
                            d = (IFile)result[0];
                        }
                    }
                }

                if (d == null) {
                    return null;
                }

                URI uri = URI.createPlatformResourceURI(d.getFullPath().toString(), false);
                return URI.decode(uri.toString());
            }
        };

        modelPath.setPreferenceName(BundleConstants.UML_MODEL_PATH);
        modelPath.setLabelText(Messages.getString("CProjectPropertyPage.3")); //$NON-NLS-1$
        modelPath.setEnabled(true, modelComposite);

        // FIXME MIGRATION
        // modelPath = new ResourceFieldEditor(BundleConstants.UML_MODEL_PATH,
        // Messages.getString("CProjectPropertyPage.3"), modelComposite); //$NON-NLS-1$
        // modelPath.setEnabled(false, modelComposite);
        // modelPath.getLabelControl(modelComposite).setEnabled(true);
        modelPath.setPreferenceStore(getPreferenceStore());
        modelPath.setPage(this);
        modelPath.setEmptyStringAllowed(false);
        modelPath.setPropertyChangeListener(new IPropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (FieldEditor.VALUE.equals(event.getProperty())) {
                    resetPathFields();
                    extractPackagesFromModel();
                }
            }
        });
    }

    /**
     * Resets the three bottom fields when the value of the UML model field changes.
     */
    private void resetPathFields() {
        srcPath.setStringValue(""); //$NON-NLS-1$
        typePath.setStringValue(""); //$NON-NLS-1$
        extPath.setStringValue(""); //$NON-NLS-1$
    }

    /**
     * Extracts all UML Packages contained into the semantic model, then this collection is transformed into
     * an array before being set to the different eobject field editor.
     */
    private void extractPackagesFromModel() {
        Collection<EObject> collection = new ArrayList<EObject>();
        URI uri = URI.createURI(modelPath.getStringValue(), false);
        try {
            Resource model = rscSet.getResource(uri, true);
            if (model != null) {
                for (TreeIterator<EObject> iterator = EcoreUtil.<EObject> getAllContents(model, false); iterator
                        .hasNext();) {
                    collection.add(iterator.next());
                }
                packages = EcoreUtil.getObjectsByType(collection, UMLPackage.Literals.PACKAGE).toArray();
                // FIXME MIGRATION
                // srcPath.setCandidates(packages);
                // typePath.setCandidates(packages);
                // extPath.setCandidates(packages);
            }
            // CHECKSTYLE:OFF
        } catch (Exception e) {
            // CHECKSTYLE:ON
            // nothing to do
        }
    }

    /**
     * Creates the settings group (bottom part).
     *
     * @param parent
     *            The composite parent
     */
    private void createSettingsGroup(Composite parent) {
        // Settings Group
        final Group settingsGroup = new Group(parent, SWT.NONE);
        settingsGroup.setLayout(new GridLayout());
        settingsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        settingsGroup.setText(Messages.getString("CProjectPropertyPage.7")); //$NON-NLS-1$

        // Intermediate composite to permit to get inner borders
        final Composite intermediateComposite = new Composite(settingsGroup, SWT.NONE);
        intermediateComposite.setLayout(new GridLayout());
        intermediateComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        srcPath = createStringFieldEditor(intermediateComposite, BundleConstants.SRC_PCK_NAME, Messages
                .getString("CProjectPropertyPage.2")); //$NON-NLS-1$
        typePath = createStringFieldEditor(intermediateComposite, BundleConstants.TYPE_PCK_NAME, Messages
                .getString("CProjectPropertyPage.9")); //$NON-NLS-1$
        extPath = createStringFieldEditor(intermediateComposite, BundleConstants.EXT_PCK_NAME, Messages
                .getString("CProjectPropertyPage.10")); //$NON-NLS-1$
    }

    // FIXME MIGRATION reference to facilities
    // /**
    // * Creates the string button field editor for selecting one model element
    // among a set.
    // *
    // * @param parent The parent composite hosting this field editor.
    // * @param key The key to retrieve value inside the preference store.
    // * @param label The label to display before the text field.
    // */
    // protected EObjectFieldEditor createPathFieldEditor(Composite parent,
    // String key, String label)
    // {
    // EObjectFieldEditor fieldEditor = new EObjectFieldEditor(key, label,
    // parent);
    // fieldEditor.setLabelProvider(defaultLabelProvider);
    // fieldEditor.setAdvancedLabelProvider(advancedLabelProvider);
    // fieldEditor.setPage(this);
    // fieldEditor.setPreferenceStore(getPreferenceStore());
    // return fieldEditor;
    // }

    /**
     * Creates the string button field editor for selecting one model element among a set.
     *
     * @param parent
     *            The parent composite hosting this field editor.
     * @param key
     *            The key to retrieve value inside the preference store.
     * @param label
     *            The label to display before the text field.
     * @return The string button field editor.
     */
    protected StringFieldEditor createStringFieldEditor(Composite parent, String key, String label) {
        StringFieldEditor fieldEditor = new StringFieldEditor(key, label, parent);
        fieldEditor.setPage(this);
        fieldEditor.setPreferenceStore(getPreferenceStore());
        return fieldEditor;
    }

    /**
     * Loads the preferences.
     */
    private void loadPreferences() {
        // syncModeEditor.load();
        // diagramPath.load();
        modelPath.load();
        srcPath.load();
        typePath.load();
        extPath.load();

        extractPackagesFromModel();
    }

    /**
     * Stores the preferences.
     */
    private void storePreferences() {
        // syncModeEditor.store();
        // diagramPath.store();
        modelPath.store();
        srcPath.store();
        typePath.store();
        extPath.store();
    }

    /**
     * Loads the default preferences.
     */
    private void loadDefaultPreferences() {
        // syncModeEditor.loadDefault();
        // diagramPath.loadDefault();
        modelPath.loadDefault();
        srcPath.loadDefault();
        typePath.loadDefault();
        extPath.loadDefault();

        extractPackagesFromModel();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        storePreferences();
        return super.performOk();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     */
    @Override
    protected void performDefaults() {
        loadDefaultPreferences();
        super.performDefaults();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // nothing to do while initializing
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     */
    @Override
    public void dispose() {
        for (Resource rsc : rscSet.getResources()) {
            rsc.unload();
        }
        rscSet = null;
    }

}
