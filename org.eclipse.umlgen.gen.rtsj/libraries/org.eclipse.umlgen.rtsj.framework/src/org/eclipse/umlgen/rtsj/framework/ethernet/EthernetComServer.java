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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.CommunicationLayer;

public class EthernetComServer extends Thread{

	private Socket socket ;
	private BufferedInputStream inputBuffer ;
	private CommunicationLayer communicationLayer;
	//la taille correspond � la taille max des buffer header
	ArgsBuffer header = new ArgsBuffer (100);
	//la taille de ce buffer est d�fini par la taille max le l'ensemble de param�tres pouvant �tre re�u
	ArgsBuffer params = new ArgsBuffer (800);
	
	//la taille des stringbuffers correspondent aux tailles max des noms de services ou noms de port
	StringBuffer identSb = new StringBuffer(50);
	StringBuffer serviceSb = new StringBuffer(50);
	
	public EthernetComServer(Socket socket, CommunicationLayer communicationLayer) {
		this.socket = socket;
		this.communicationLayer = communicationLayer;
		try {
			inputBuffer = new BufferedInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		start() ;
	}
	
	public void run() {
		int paramSize ;
		while (true){
			try {
				int headerSize = inputBuffer.read();
				//System.out.println("Server headerSize" + headerSize);
				for (int i = 0 ; i < headerSize ; i++){
					header.writeByte((byte) inputBuffer.read());
				}
				header.readString(identSb) ;
				header.readString(serviceSb) ;
				int priority = header.readInteger();
				paramSize = header.readInteger() ;
				for (int i = 0 ; i < paramSize ; i++){
					params.writeByte((byte) inputBuffer.read());
				}
				if (communicationLayer.isSynchronousCall(identSb.toString())){
					communicationLayer.callAsynchronous(serviceSb.toString(), identSb.toString(), "", params, priority);
				} 
				if (communicationLayer.isSendEventData(identSb.toString())){
					communicationLayer.sendEventData(serviceSb.toString(), identSb.toString(), "", params);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
}
