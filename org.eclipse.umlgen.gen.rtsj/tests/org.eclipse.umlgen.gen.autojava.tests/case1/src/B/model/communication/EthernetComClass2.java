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
 * model/Class2.java
 *
 * File generated from the  uml Class
 */
package B.model.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import org.eclipse.umlgen.rtsj.framework.CommunicationLayer;
import org.eclipse.umlgen.rtsj.framework.ethernet.ComProtocol;
import org.eclipse.umlgen.rtsj.framework.ethernet.ServerThread;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;

public class EthernetComClass2 implements ComProtocol {

	private InetAddress addrClass3;
	private Socket socketClass3client ;
	static final int class3Port = 29;
	private BufferedOutputStream outClass3 ;

	static final int serverPort = 28;
	ArgsBuffer header = new ArgsBuffer(27);
	
	public EthernetComClass2(CommunicationLayer communicationLayer) {
		new ServerThread(serverPort, communicationLayer) ;
		try {
			addrClass3 = InetAddress.getByName("127.0.0.1");

			for (int i = 0 ; i < 10 ; i++){
				try {
					if (socketClass3client == null) {
						socketClass3client = new Socket(addrClass3, class3Port);
					}
					break ;
				} catch (IOException e) {
					if (i == 9){
						e.printStackTrace();
						throw (new RuntimeException());
					} else {
						try {
							Thread.sleep((i+1)*5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			outClass3 = new BufferedOutputStream(socketClass3client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		if (dest.equals("class3")){
			try {
				outClass3.write(header.getUsed());
				bufferSize = header.getUsed();
				while (header.getUsed() > 0){
					outClass3.write(header.readByte());
				}
				bufferSize = params.getUsed();
				for (int i = 0; i < bufferSize; i++){
					outClass3.write(params.readByteAt(i));
				}
				outClass3.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}