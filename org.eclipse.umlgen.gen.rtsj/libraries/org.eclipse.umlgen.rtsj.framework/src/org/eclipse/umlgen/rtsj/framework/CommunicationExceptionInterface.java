/*******************************************************************************
 * Copyright (c) 2014 CNES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sylvain Jouanneau (CNES) - bug 440109
 *******************************************************************************/
package org.eclipse.umlgen.rtsj.framework;

public interface CommunicationExceptionInterface {

	void catchCommunicationException(String service, ArgsBuffer params);

}
