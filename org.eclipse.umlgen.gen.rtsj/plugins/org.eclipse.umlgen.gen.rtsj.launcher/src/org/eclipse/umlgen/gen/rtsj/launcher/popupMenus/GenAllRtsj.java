/*******************************************************************************
 * Copyright (c) 2014, 2015 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     JF Rolland (ATOS) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.rtsj.launcher.popupMenus;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.umlgen.gen.autojava.launcher.popupMenus.AbstractGenAutoJavaComponents;
import org.eclipse.umlgen.gen.autojava.main.Uml2autojava;
import org.eclipse.umlgen.gen.rtsj.main.Uml2rtsj;

public class GenAllRtsj extends AbstractGenAutoJavaComponents {

	@Override
	protected Uml2autojava getGenerator(URI modelURI, String sDecorators, File target) throws IOException {
		return new Uml2rtsj(modelURI, target, Collections.EMPTY_LIST, sDecorators);
	}

	@Override
	protected String getModuleQualifiedName() {
		// TODO Auto-generated method stub
		return "org.eclipse.umlgen.gen.rtsj.main.Uml2rtsj";
	}

}
