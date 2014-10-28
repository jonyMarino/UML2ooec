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
 * B/model/async/users/Class2_monPort2_UserAsync.java
 *
 * File generated from the ::model::Class2::monPort2 uml Port
 */
package B.model.async.users;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.InterfaceAsynchronous;
import model.params.*;
import model.SharedData;

public class Class2_monPort2_UserAsync implements InterfaceAsynchronous{

	String ident;
	String sender;
	InterfaceAsynchronous_monService1_Params monService1Params = new InterfaceAsynchronous_monService1_Params(false);
	InterfaceAsynchronous_monService2_Params monService2Params = new InterfaceAsynchronous_monService2_Params(false);
	ArgsBuffer argsBuffer = new ArgsBuffer(21);
	CommunicationLayer communicationLayer;
	int signalPriority = 1;

	public Class2_monPort2_UserAsync(String ident, String sender, CommunicationLayer communicationLayer) {
		this.ident = ident;
		this.sender = sender;
		this.communicationLayer = communicationLayer;
	}

	public InterfaceAsynchronous setSignalPriority(int priority) {
		this.signalPriority = priority;
		return this;
	}

	public void monService1(int param1, int param2) throws ServiceNotFoundException {
		monService1Params.param1 = param1;
		monService1Params.param2 = param2;
		argsBuffer.dequeue(argsBuffer.getUsed());
		monService1Params.writeObject(argsBuffer);
		communicationLayer.callAsynchronous("monService1int_int", ident, sender, argsBuffer, this.signalPriority);
		this.setSignalPriority(1);
	}
		
	public void monService2(SharedData param1) throws ServiceNotFoundException {
		monService2Params.param1 = param1;
		argsBuffer.dequeue(argsBuffer.getUsed());
		monService2Params.writeObject(argsBuffer);
		communicationLayer.callAsynchronous("monService2SharedData", ident, sender, argsBuffer, this.signalPriority);
		this.setSignalPriority(1);
	}
		
}