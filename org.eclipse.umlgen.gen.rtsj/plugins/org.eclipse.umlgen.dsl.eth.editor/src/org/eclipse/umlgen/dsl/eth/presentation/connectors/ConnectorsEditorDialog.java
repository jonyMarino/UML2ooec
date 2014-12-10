/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.dsl.eth.presentation.connectors;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.edit.tree.provider.TreeItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.uml2.uml.Connector;

/**
 * Specific dialog to select the connectors to reference from the related ethernet configuration.
 *
 * @author cnotot
 */
public class ConnectorsEditorDialog extends FeatureEditorDialog {

    /** The property descriptor. */
    private IItemPropertyDescriptor propertyDescriptor;

    // CHECKSTYLE:OFF

    public ConnectorsEditorDialog(Shell parent, ILabelProvider labelProvider, Object object,
            EClassifier eClassifier, List<?> currentValues, String displayName, List<?> choiceOfValues,
            boolean multiLine, boolean sortChoices, boolean unique, IItemPropertyDescriptor propertyDescriptor) {
        super(parent, labelProvider, object, eClassifier, currentValues, displayName, choiceOfValues,
                multiLine, sortChoices, unique);
        this.propertyDescriptor = propertyDescriptor;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite contents = new Composite(parent, SWT.NONE);
        {
            GridLayout layout = new GridLayout();
            layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
            layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
            layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
            layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
            contents.setLayout(layout);
            contents.setLayoutData(new GridData(GridData.FILL_BOTH));
            applyDialogFont(contents);
        }

        GridLayout contentsGridLayout = (GridLayout)contents.getLayout();
        contentsGridLayout.numColumns = 3;

        GridData contentsGridData = (GridData)contents.getLayoutData();
        contentsGridData.horizontalAlignment = SWT.FILL;
        contentsGridData.verticalAlignment = SWT.FILL;

        Text patternText = null;

        if (choiceOfValues != null) {
            Group filterGroupComposite = new Group(contents, SWT.NONE);
            filterGroupComposite.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_pattern_group"));
            filterGroupComposite.setLayout(new GridLayout(2, false));
            filterGroupComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

            Label label = new Label(filterGroupComposite, SWT.NONE);
            label.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_pattern_label"));

            patternText = new Text(filterGroupComposite, SWT.BORDER);
            patternText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }

        Composite choiceComposite = new Composite(contents, SWT.NONE);
        {
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.horizontalAlignment = SWT.END;
            choiceComposite.setLayoutData(data);

            GridLayout layout = new GridLayout();
            data.horizontalAlignment = SWT.FILL;
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.numColumns = 1;
            choiceComposite.setLayout(layout);

        }

        Label choiceLabel = new Label(choiceComposite, SWT.NONE);
        choiceLabel.setText(choiceOfValues == null ? EMFEditUIPlugin.INSTANCE.getString("_UI_Value_label")
                : EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_label"));
        GridData choiceLabelGridData = new GridData();
        choiceLabelGridData.verticalAlignment = SWT.FILL;
        choiceLabelGridData.horizontalAlignment = SWT.FILL;
        choiceLabel.setLayoutData(choiceLabelGridData);

        final Tree choiceTable = choiceOfValues == null ? null : new Tree(choiceComposite, SWT.MULTI
                | SWT.BORDER);
        if (choiceTable != null) {
            GridData choiceTableGridData = new GridData();
            choiceTableGridData.widthHint = Display.getCurrent().getBounds().width / 5;
            choiceTableGridData.heightHint = Display.getCurrent().getBounds().height / 3;
            choiceTableGridData.verticalAlignment = SWT.FILL;
            choiceTableGridData.horizontalAlignment = SWT.FILL;
            choiceTableGridData.grabExcessHorizontalSpace = true;
            choiceTableGridData.grabExcessVerticalSpace = true;
            choiceTable.setLayoutData(choiceTableGridData);
        }

        final TreeViewer choiceTreeViewer = choiceOfValues == null ? null : new TreeViewer(choiceTable);
        if (choiceOfValues != null) {
            choiceTreeViewer.setContentProvider(new ConnectorsChoiceAdapterFactoryContentProvider(
                    new TreeItemProviderAdapterFactory(), values.getChildren()));
            choiceTreeViewer.setLabelProvider(new ConnectorsChoiceLabelProvider(propertyDescriptor
                    .getLabelProvider(object)));
            final PatternFilter filter = new PatternFilter() {
                @Override
                protected boolean isParentMatch(Viewer viewer, Object element) {
                    return viewer instanceof AbstractTreeViewer && super.isParentMatch(viewer, element);
                }
            };
            choiceTreeViewer.addFilter(filter);
            choiceTreeViewer.setComparator(new ViewerComparator());
            assert patternText != null;
            patternText.addModifyListener(new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    filter.setPattern(((Text)e.widget).getText());
                    choiceTreeViewer.refresh();
                }
            });
            choiceTreeViewer.setInput(new ItemProvider(choiceOfValues));

        }

        // We use multi even for a single line because we want to respond to the
        // enter key.
        //
        int style = multiLine ? SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER : SWT.MULTI | SWT.BORDER;
        final Text choiceText = choiceOfValues == null ? new Text(choiceComposite, style) : null;
        if (choiceText != null) {
            GridData choiceTextGridData = new GridData();
            choiceTextGridData.widthHint = Display.getCurrent().getBounds().width / 5;
            choiceTextGridData.verticalAlignment = SWT.BEGINNING;
            choiceTextGridData.horizontalAlignment = SWT.FILL;
            choiceTextGridData.grabExcessHorizontalSpace = true;
            if (multiLine) {
                choiceTextGridData.verticalAlignment = SWT.FILL;
                choiceTextGridData.grabExcessVerticalSpace = true;
            }
            choiceText.setLayoutData(choiceTextGridData);
        }

        Composite controlButtons = new Composite(contents, SWT.NONE);
        GridData controlButtonsGridData = new GridData();
        controlButtonsGridData.verticalAlignment = SWT.FILL;
        controlButtonsGridData.horizontalAlignment = SWT.FILL;
        controlButtons.setLayoutData(controlButtonsGridData);

        GridLayout controlsButtonGridLayout = new GridLayout();
        controlButtons.setLayout(controlsButtonGridLayout);

        new Label(controlButtons, SWT.NONE);

        final Button addButton = new Button(controlButtons, SWT.PUSH);
        addButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Add_label"));
        GridData addButtonGridData = new GridData();
        addButtonGridData.verticalAlignment = SWT.FILL;
        addButtonGridData.horizontalAlignment = SWT.FILL;
        addButton.setLayoutData(addButtonGridData);

        final Button removeButton = new Button(controlButtons, SWT.PUSH);
        removeButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Remove_label"));
        GridData removeButtonGridData = new GridData();
        removeButtonGridData.verticalAlignment = SWT.FILL;
        removeButtonGridData.horizontalAlignment = SWT.FILL;
        removeButton.setLayoutData(removeButtonGridData);

        Label spaceLabel = new Label(controlButtons, SWT.NONE);
        GridData spaceLabelGridData = new GridData();
        spaceLabelGridData.verticalSpan = 2;
        spaceLabel.setLayoutData(spaceLabelGridData);

        Composite featureComposite = new Composite(contents, SWT.NONE);
        {
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.horizontalAlignment = SWT.END;
            featureComposite.setLayoutData(data);

            GridLayout layout = new GridLayout();
            data.horizontalAlignment = SWT.FILL;
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.numColumns = 1;
            featureComposite.setLayout(layout);
        }

        Label featureLabel = new Label(featureComposite, SWT.NONE);
        featureLabel.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Feature_label"));
        GridData featureLabelGridData = new GridData();
        featureLabelGridData.horizontalSpan = 2;
        featureLabelGridData.horizontalAlignment = SWT.FILL;
        featureLabelGridData.verticalAlignment = SWT.FILL;
        featureLabel.setLayoutData(featureLabelGridData);

        final Tree featureTable = new Tree(featureComposite, SWT.MULTI | SWT.BORDER);
        GridData featureTableGridData = new GridData();
        featureTableGridData.widthHint = Display.getCurrent().getBounds().width / 5;
        featureTableGridData.heightHint = Display.getCurrent().getBounds().height / 3;
        featureTableGridData.verticalAlignment = SWT.FILL;
        featureTableGridData.horizontalAlignment = SWT.FILL;
        featureTableGridData.grabExcessHorizontalSpace = true;
        featureTableGridData.grabExcessVerticalSpace = true;
        featureTable.setLayoutData(featureTableGridData);

        final TreeViewer featureTableViewer = new TreeViewer(featureTable);
        featureTableViewer.setContentProvider(contentProvider);
        featureTableViewer.setLabelProvider(labelProvider);
        featureTableViewer.setComparator(new ViewerComparator());
        featureTableViewer.setInput(values);
        if (!values.getChildren().isEmpty()) {
            featureTableViewer.setSelection(new StructuredSelection(values.getChildren().get(0)));
        }

        if (choiceTreeViewer != null) {
            choiceTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
                public void doubleClick(DoubleClickEvent event) {
                    if (addButton.isEnabled()) {
                        addButton.notifyListeners(SWT.Selection, null);
                    }
                }
            });

            featureTableViewer.addDoubleClickListener(new IDoubleClickListener() {
                public void doubleClick(DoubleClickEvent event) {
                    if (removeButton.isEnabled()) {
                        removeButton.notifyListeners(SWT.Selection, null);
                    }
                }
            });
        }

        if (choiceText != null) {
            choiceText.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent event) {
                    if (!multiLine && (event.character == '\r' || event.character == '\n')) {
                        try {
                            Object value = EcoreUtil.createFromString((EDataType)eClassifier, choiceText
                                    .getText());
                            values.getChildren().add(value);
                            choiceText.setText("");
                            featureTableViewer.setSelection(new StructuredSelection(value));
                            event.doit = false;
                        } catch (RuntimeException exception) {
                            // Ignore
                        }
                    } else if (event.character == '\33') {
                        choiceText.setText("");
                        event.doit = false;
                    }
                }
            });
        }

        addButton.addSelectionListener(new SelectionAdapter() {
            // event is null when choiceTableViewer is double clicked
            @Override
            public void widgetSelected(SelectionEvent event) {
                if (choiceTreeViewer != null) {
                    IStructuredSelection selection = (IStructuredSelection)choiceTreeViewer.getSelection();
                    for (Iterator<?> i = selection.iterator(); i.hasNext();) {
                        Object value = i.next();
                        if (isCandidateValue(value)) {
                            values.getChildren().add(value);
                        }
                    }
                    featureTableViewer.setSelection(selection);
                } else if (choiceText != null) {
                    try {
                        Object value = EcoreUtil.createFromString((EDataType)eClassifier, choiceText
                                .getText());
                        if (isCandidateValue(value)) {
                            values.getChildren().add(value);
                            choiceText.setText("");
                        }
                        featureTableViewer.setSelection(new StructuredSelection(value));
                    } catch (RuntimeException exception) {
                        // Ignore
                    }
                }
                choiceTreeViewer.refresh();
            }
        });

        removeButton.addSelectionListener(new SelectionAdapter() {
            // event is null when featureTableViewer is double clicked
            @Override
            public void widgetSelected(SelectionEvent event) {
                IStructuredSelection selection = (IStructuredSelection)featureTableViewer.getSelection();
                Object firstValue = null;
                for (Iterator<?> i = selection.iterator(); i.hasNext();) {
                    Object value = i.next();
                    if (firstValue == null) {
                        firstValue = value;
                    }
                    values.getChildren().remove(value);
                }

                if (!values.getChildren().isEmpty()) {
                    featureTableViewer.setSelection(new StructuredSelection(values.getChildren().get(0)));
                }

                if (choiceTreeViewer != null) {
                    choiceTreeViewer.setSelection(selection);
                } else if (choiceText != null) {
                    if (firstValue != null) {
                        String value = EcoreUtil.convertToString((EDataType)eClassifier, firstValue);
                        choiceText.setText(value);
                    }
                }
                choiceTreeViewer.refresh();
            }
        });

        return contents;
    }

    // CHECKSTYLE:ON

    /**
     * This checks if the given object is a good candidate to be displayed.
     *
     * @param value
     *            The object.
     * @return Trus if yes.
     */
    private boolean isCandidateValue(Object value) {
        return (!unique || !values.getChildren().contains(value)) && value instanceof Connector;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog#okPressed()
     */
    @Override
    protected void okPressed() {
        super.okPressed();

    }

}
