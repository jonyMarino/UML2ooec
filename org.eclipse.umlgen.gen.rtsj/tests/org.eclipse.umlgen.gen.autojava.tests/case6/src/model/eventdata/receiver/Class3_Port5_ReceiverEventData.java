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
 * model/eventdata/receiver/Class3_Port5_ReceiverEventData.java
 *
 * File generated from the ::model::Class3::Port5 uml Port
 */
package model.eventdata.receiver;

import model.Interface2;
import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.SharedData;

public class Class3_Port5_ReceiverEventData implements EventDataPort {

	String ident;
	ArgsBuffer Signal3Buffer;
	ArgsBuffer Signal2Buffer;
	int Signal3Param;
	SharedData Signal2Param;
	CommunicationLayer communicationLayer;

	public Class3_Port5_ReceiverEventData(String ident, CommunicationLayer communicationLayer) {
		this.ident = ident;
		Signal3Buffer = new ArgsBuffer(4);
		Signal2Buffer = new ArgsBuffer(21);
		Signal2Param = new SharedData();
		this.communicationLayer = communicationLayer;
	}

	/**
	 * Delinearization of data if received.
	 * 
	 * @return data delinearized or stocked data by default
	 */
	public int getSignal3() {	
		if (this.hasReceivedSignal3()) {
			synchronized (Signal3Buffer) {	
				Signal3Param = Signal3Buffer.readInteger();
			}
		}
		return Signal3Param;
	}
	/**
	 * @return stocked data
	 */
	public int getPreviousSignal3() {	
		return Signal3Param;
	}
	/**
	 * Test if data has been received in buffer.
	 * 
	 * @return true if buffer contains data
	 */
	public boolean hasReceivedSignal3() {
		boolean result = false;
		synchronized (Signal3Buffer) {
			result = !Signal3Buffer.check(Signal3Buffer.getSize());
		}
		return result;
	}
	/**
	 * Delinearization of data if received.
	 * 
	 * @return data delinearized or stocked data by default
	 */
	public SharedData getSignal2() {	
		if (this.hasReceivedSignal2()) {
			synchronized (Signal2Buffer) {	
				Signal2Param.readObject(Signal2Buffer);
			}
		}
		return Signal2Param;
	}
	/**
	 * @return stocked data
	 */
	public SharedData getPreviousSignal2() {	
		return Signal2Param;
	}
	/**
	 * Test if data has been received in buffer.
	 * 
	 * @return true if buffer contains data
	 */
	public boolean hasReceivedSignal2() {
		boolean result = false;
		synchronized (Signal2Buffer) {
			result = !Signal2Buffer.check(Signal2Buffer.getSize());
		}
		return result;
	}

	public void store(String sig, ArgsBuffer param) throws ServiceNotFoundException {
		if (sig.equals("Signal3")) {
			synchronized (Signal3Buffer) {
				if (!Signal3Buffer.check(param.getUsed())) {
					Signal3Buffer.dequeue(param.getUsed());
				}
				Signal3Buffer.copy(param);
			}
		} else if (sig.equals("Signal2")) {
			synchronized (Signal2Buffer) {
				if (!Signal2Buffer.check(param.getUsed())) {
					Signal2Buffer.dequeue(param.getUsed());
				}
				Signal2Buffer.copy(param);
			}
		} else {
			throw new ServiceNotFoundException();
		}
	}
	
}