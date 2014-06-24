/**
 * B/model/eventdata/emitter/Class2_Port4_EmitterEventData.java
 *
 * File generated from the ::model::Class2::Port4 uml Port
 */
package B.model.eventdata.emitter;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.Interface2;
import model.SharedData;

public class Class2_Port4_EmitterEventData {

	String ident;
	String sender;
	ArgsBuffer argsBuffer = new ArgsBuffer(21);
	CommunicationLayer communicationLayer;

	public Class2_Port4_EmitterEventData(String ident, String sender, CommunicationLayer communicationLayer) {
		this.ident = ident;
		this.sender = sender;
		this.communicationLayer = communicationLayer;
	}

	public void Signal3(int params) throws ServiceNotFoundException {
		argsBuffer.dequeue(argsBuffer.getUsed());
		argsBuffer.writeInteger(params);
		communicationLayer.sendEventData("Signal3", ident, sender, argsBuffer);
	}
		
	public void Signal2(SharedData params) throws ServiceNotFoundException {
		argsBuffer.dequeue(argsBuffer.getUsed());
		params.writeObject(argsBuffer);
		communicationLayer.sendEventData("Signal2", ident, sender, argsBuffer);
	}
		
}