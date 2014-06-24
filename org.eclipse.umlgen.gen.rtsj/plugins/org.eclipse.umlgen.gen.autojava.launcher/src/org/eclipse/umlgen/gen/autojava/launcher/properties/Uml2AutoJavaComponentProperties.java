/*******************************************************************************
 * Copyright (c) 2008, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
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
import org.eclipse.jface.preference.PreferencePage;
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
 * Class that is used to add a property page for configurating the code generation
 *
 * @author Cédric Notot (Obeo)
 */
public class Uml2AutoJavaComponentProperties extends PropertyPage {

	private Button outputBrowseButton;

	private Text outputText;

	private Label outputLabel;

	private Button validationButton;

	private Button comParamBrowseButton;

	private Text comParamText;

	private Label comParamLabel;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public Uml2AutoJavaComponentProperties() {
		super();
	}

	/**
	 * Create and initialize widget on the property page
	 * 
	 * @param parent
	 *            is the composite
	 */
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

	/**
	 * @see PreferencePage#createContents(Composite)
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
	 * Create default composite
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
	 * Initalize every widget to a default value
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
	 * Convert a boolean to a string
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
	 * Event executed when validating the property page. All properties are saved by Eclipse.
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
	 * 
	 * @author aRamphul Modified by Nathalie Lepine (Obeo) to adapt for UML to RTSJ
	 */
	private class BrowseOutputFolderButtonListener implements MouseListener {

		public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent arg0) {
		}

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

		public void mouseUp(org.eclipse.swt.events.MouseEvent arg0) {
		}

	}

	private class BrowseParamModelButtonListener implements MouseListener {

		private class PathLabelProvider extends WorkbenchLabelProvider {

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

		public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent arg0) {
		}

		public void mouseDown(org.eclipse.swt.events.MouseEvent arg0) {
			ListSelectionDialog dialog = new ListSelectionDialog(getShell(), ((IResource)getElement())
					.getWorkspace().getRoot(), getFileProvider(), new PathLabelProvider(),
					"Select parametering model :");

			List<IResource> selectedResources = getResourcesFromProperties(comParamText.getText());
			dialog.setInitialElementSelections(selectedResources);

			dialog.open();
			Object[] files = dialog.getResult();
			if (files != null && files.length > 0) {
				comParamText.setText("");
				for (Object file : files) {
					if (file instanceof IResource) {
						IPath path = ((IResource)file).getFullPath();
						if (path.segmentCount() > 0) {
							String s = path.toPortableString();
							if (comParamText.getText() != null && comParamText.getText().length() > 0) {
								comParamText.setText(comParamText.getText() + ";" + s);
							} else {
								comParamText.setText(s);
							}
						}
					}
				}
			}
		}

		/**
		 * Returns a content provider for <code>FileSystemElement</code>s that returns only files as children.
		 */
		private ITreeContentProvider getFileProvider() {
			return new WorkbenchContentProvider() {
				@Override
				public Object[] getChildren(Object o) {
					if (o instanceof IContainer) {
						try {
							Object[] elements = getChildren((IContainer)o);
							return getResult(elements).toArray();
						} catch (CoreException e) {
							return new Object[0];
						}
					}
					return new Object[0];
				}

				private List<Object> getResult(Object[] elements) throws CoreException {
					List<Object> result = new ArrayList<Object>();
					for (int i = 0; i < elements.length; i++) {
						if (elements[i] instanceof IFile
								&& (((IFile)elements[i]).getFileExtension().equals("asl") || ((IFile)elements[i])
										.getFileExtension().equals("eth"))) {
							result.add(elements[i]);
						} else if (elements[i] instanceof IContainer
								&& ((IContainer)elements[i]).isAccessible()
								&& !((IContainer)elements[i]).isHidden()) {
							result.addAll(getResult(getChildren((IContainer)elements[i])));
						}
					}
					return result;
				}

				private Object[] getChildren(IContainer container) throws CoreException {
					return container.members();
				}
			};
		}

		public void mouseUp(org.eclipse.swt.events.MouseEvent arg0) {
		}

	}
}
