/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Topcased contributors and others - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.rtsj.framework.async;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.ServiceNotFoundException;

public interface PortProviderAsync {

	public void store(String service, ArgsBuffer params, int priority) ;
	
	public void invokeNextOperation () throws ServiceNotFoundException ;
	
	public boolean empty() ;
	public boolean full() ;

}
