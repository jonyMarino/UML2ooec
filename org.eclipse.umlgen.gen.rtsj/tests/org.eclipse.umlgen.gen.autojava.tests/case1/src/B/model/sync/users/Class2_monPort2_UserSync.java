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
 * B/model/sync/users/Class2_monPort2_UserSync.java
 *
 * File generated from the ::model::Class2::monPort2 uml Port
 */
package B.model.sync.users;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.params.*;
import model.SharedData;
import model.InterfaceSynchronous;

public class Class2_monPort2_UserSync implements InterfaceSynchronous {

	String ident;
	InterfaceSynchronous_monService1_Int_Params monService1IntParams = new InterfaceSynchronous_monService1_Int_Params(false);
	InterfaceSynchronous_monService2_Params monService2Params = new InterfaceSynchronous_monService2_Params(false);
	InterfaceSynchronous_monService1_BooleanInt_Params monService1BooleanIntParams = new InterfaceSynchronous_monService1_BooleanInt_Params(false);
	
	CommunicationLayer communicationLayer;

	public Class2_monPort2_UserSync(String ident, CommunicationLayer communicationLayer) {
		this.ident = ident;
		this.communicationLayer = communicationLayer;
	}

	public void monService1(int param1) throws ServiceNotFoundException {
		monService1IntParams.param1 = param1;
		communicationLayer.callSynchronous("monService1int", ident, monService1IntParams);
		
	}
		
	public void monService2(SharedData param) throws ServiceNotFoundException {
		monService2Params.param = param;
		communicationLayer.callSynchronous("monService2SharedData", ident, monService2Params);
		
	}
		
	public void monService1(int param1, boolean param2) throws ServiceNotFoundException {
		monService1BooleanIntParams.param1 = param1;
		monService1BooleanIntParams.param2 = param2;
		communicationLayer.callSynchronous("monService1int_boolean", ident, monService1BooleanIntParams);
		
	}
		
}