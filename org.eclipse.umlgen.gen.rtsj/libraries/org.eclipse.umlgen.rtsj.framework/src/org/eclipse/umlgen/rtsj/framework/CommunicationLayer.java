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
package org.eclipse.umlgen.rtsj.framework;

import java.util.HashMap;

import org.eclipse.umlgen.rtsj.framework.async.PortProviderAsync;
import org.eclipse.umlgen.rtsj.framework.ethernet.ComProtocol;
import org.eclipse.umlgen.rtsj.framework.sync.PortProviderSync;

public class CommunicationLayer {

	private HashMap SynchronousConnections = new HashMap();  // map appelant -> appel�
	private HashMap SynchronousProviderPortMap = new HashMap(); // map nom de port -> instance
	private HashMap AsynchronousConnections = new HashMap();  // map appelant ->  appel�
	private HashMap AsynchronousProviderPortMap = new HashMap(); // map nom de port -> instance
	private HashMap EventDataConnections= new HashMap();
	private HashMap EventDataPortMap= new HashMap();
	private HashMap ComponentMap = new HashMap();
	private HashMap<String, ComProtocol> comProtocols = new HashMap<String, ComProtocol>();
	
	public CommunicationLayer() {
	}

	public Object callSynchronous(String service, String ident,
			ParameterSet params) throws ServiceNotFoundException {
		PortProviderSync p = (PortProviderSync) SynchronousProviderPortMap.get(SynchronousConnections.get(ident));		
		return p.invoke(service, params);
	}

	public void callAsynchronous(String service, String ident, String sender,
			ArgsBuffer params, int priority) {
		if (ComponentMap.containsKey(ident) && comProtocols.containsKey(sender)){
			ComProtocol comProtocol = comProtocols.get(sender);
			comProtocol.sendFrame(((String[]) ComponentMap.get(ident))[0], ident, service, params, priority);
			params.dequeue(params.getUsed());
		}
		else if (AsynchronousConnections.containsKey(ident)){
			PortProviderAsync p = (PortProviderAsync) AsynchronousProviderPortMap.get(AsynchronousConnections.get(ident));
			p.store(service, params, priority);
			params.dequeue(params.getUsed());
		} 
	}

	public void sendEventData(String service, String ident, String sender,
			ArgsBuffer params) {
		if (EventDataConnections.containsKey(ident)){
			String[] receivers = (String[]) EventDataConnections.get(ident);
			for (int i = 0 ; i < receivers.length;i++){
				EventDataPort p = (EventDataPort) EventDataPortMap.get(receivers[i]) ;
				p.store(service, params);
			}
			params.dequeue(params.getUsed());
		}
		if (ComponentMap.containsKey(ident) && comProtocols.containsKey(sender)){
			ComProtocol comProtocol = comProtocols.get(sender);
			String[] receivers = (String[]) ComponentMap.get(ident);
			for (int i = 0 ; i < receivers.length;i++){
				comProtocol.sendFrame(receivers[i], ident, service, params, 1);
			}
			params.dequeue(params.getUsed());
		}
	}
	
	public void registerSynchronousProviderPort(String ident,
			PortProviderSync ifProvider) {
		SynchronousProviderPortMap.put(ident, ifProvider);
	}
	
	public void registerAsynchronousProviderPort(String ident,
			PortProviderAsync ifProviderAsync) {
		AsynchronousProviderPortMap.put(ident, ifProviderAsync);
		
	}
	
	public void registerEventDataReceiverPort(String ident,
			EventDataPort ifProviderAsync) {
		EventDataPortMap.put(ident, ifProviderAsync);
		
	}
	
	public void setSynchronousConnection (String provider,String user){
		SynchronousConnections.put(user, provider);
	}
	
	public void setAsynchronousConnection (String provider,String user){
		AsynchronousConnections.put(user, provider);
	}
	
	public void setEventDataConnection (String sender,String[] receiver){
		EventDataConnections.put(sender, receiver);
	}
	
	public void setComponentMap (String sender,String[] component){
		ComponentMap.put(sender, component);
	}
	
	public boolean isSynchronousCall (String portIdent){
		return AsynchronousConnections.containsKey(portIdent) ;
	}
	
	public boolean isSendEventData (String portIdent){
		return EventDataConnections.containsKey(portIdent) ;
	}
	
	public void registerComProtocol(String comp, ComProtocol comProtocol) {
		comProtocols.put(comp, comProtocol);
	}
	
}
