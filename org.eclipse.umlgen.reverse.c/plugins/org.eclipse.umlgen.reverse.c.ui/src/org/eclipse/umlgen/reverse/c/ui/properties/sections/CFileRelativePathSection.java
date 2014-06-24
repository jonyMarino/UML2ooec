/*******************************************************************************
 * Copyright (c) 2011, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.properties.sections;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section allowing to specify the relative path of the C file to generate.<br>
 * This section is available from a {@link org.eclipse.uml2.uml.Class}. Creation : 15 february 2011<br>
 * Updated : 13 april 2011<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class CFileRelativePathSection extends AbstractModuleSection {

	@Override
	protected String getLabelText() {
		return Messages.getString("CFileRelativePathSection.label"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.ui.properties.sections.AbstractModuleSection#getKeyDetailsEntry()
	 */
	@Override
	protected String getKeyDetailsEntry() {
		return AnnotationConstants.C_FILENAME;
	}

	/**
	 * ! SANITY CHECK ! When the focus is lost, we can check that the text value ends with the right
	 * extension.<br>
	 * This change is performed otherwise the generator will not produce anything.
	 */
	@Override
	protected void focusOut() {
		String currentText = getText().getText();
		if (currentText != null && !"".equals(currentText)) //$NON-NLS-1$
		{
			IPath p = new Path(currentText);
			if (p.getFileExtension() == null) {
				getText().setText(p.addFileExtension(BundleConstants.C_EXTENSION).toString());
			} else if (!BundleConstants.C_EXTENSION.equals(p.getFileExtension())) {
				p = p.removeFileExtension();
				getText().setText(p.addFileExtension(BundleConstants.C_EXTENSION).toString());
			}
		}
	}
}
