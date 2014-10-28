/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
/**
 * model/Class1.java
 *
 * File generated from the  uml Class
 */
package A.model.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import org.eclipse.umlgen.rtsj.framework.CommunicationLayer;
import org.eclipse.umlgen.rtsj.framework.ethernet.ComProtocol;
import org.eclipse.umlgen.rtsj.framework.ethernet.ServerThread;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;

public class EthernetComClass1 implements ComProtocol {


	static final int serverPort = 27;
	ArgsBuffer header = new ArgsBuffer(19);
	
	public EthernetComClass1(CommunicationLayer communicationLayer) {
		new ServerThread(serverPort, communicationLayer) ;
	}
	
	public void sendFrame (String dest, String ident, String service, ArgsBuffer params, int priority){
		header.writeString(ident);
		header.writeString(service);
		header.writeInteger(priority);
		if (params != null) {		
			header.writeInteger(params.getUsed());
		} else {
			header.writeInteger(0);
		}
		int bufferSize;
	}
}