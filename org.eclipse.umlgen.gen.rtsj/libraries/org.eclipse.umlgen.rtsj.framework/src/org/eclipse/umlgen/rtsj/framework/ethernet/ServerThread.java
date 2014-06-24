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
package org.eclipse.umlgen.rtsj.framework.ethernet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.umlgen.rtsj.framework.CommunicationLayer;

public class ServerThread extends Thread {

		private ServerSocket server;
		private Socket socketComp1server;
		private int port ;
		private CommunicationLayer communicationLayer;
		
		
		
		public ServerThread(int port, CommunicationLayer communicationLayer) {
			this.port = port;
			this.communicationLayer = communicationLayer;
			start() ;
		}



		public void run() {
			//partie serveur
			try {
				server = new ServerSocket(port);
				while(true){
					socketComp1server = server.accept();
					new EthernetComServer(socketComp1server, communicationLayer) ;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	
}
