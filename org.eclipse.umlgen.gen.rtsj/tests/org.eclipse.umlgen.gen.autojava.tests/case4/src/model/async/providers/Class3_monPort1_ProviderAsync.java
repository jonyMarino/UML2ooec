/**
 * model/async/providers/Class3_monPort1_ProviderAsync.java
 *
 * File generated from the ::model::Class3::monPort1 uml Port
 */
package model.async.providers;

import model.InterfaceAsynchronous;
import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import org.eclipse.umlgen.rtsj.framework.async.PortProviderAsync;
import model.params.*;

public class Class3_monPort1_ProviderAsync implements PortProviderAsync {

	InterfaceAsynchronous provider;
	String ident;
	Object thread;
	PortBuffer pbuff;
	MBuffer mbuffer;
	ArgsBuffer argsBuffer;
	int max_size_parameter_set = 21;
	InterfaceAsynchronous_monService1_Params monService1Params = new InterfaceAsynchronous_monService1_Params(true);
	InterfaceAsynchronous_monService2_Params monService2Params = new InterfaceAsynchronous_monService2_Params(true);
	CommunicationLayer communicationLayer;

	public Class3_monPort1_ProviderAsync(InterfaceAsynchronous provider, String ident, int size, Object thread, PortBuffer pbuff, CommunicationLayer communicationLayer) {
		this.provider = provider;
		this.ident = ident;
		communicationLayer.registerAsynchronousProviderPort(ident, this);
		mbuffer = new MBuffer(size);
		argsBuffer = new ArgsBuffer(max_size_parameter_set * size);
		this.thread = thread;
		this.pbuff = pbuff;
		this.communicationLayer = communicationLayer;
	}

	public boolean empty() { 
		return mbuffer.empty(); 
	}
	
	public boolean full() { 
		return mbuffer.full(); 
	}

	public void invokeNextOperation() throws ServiceNotFoundException {
		if (mbuffer.empty()) return;
		String op = mbuffer.get();
		if (op.equals("monService1int_int")) {
			monService1Params.readObject(argsBuffer);
			provider.monService1(monService1Params.param1, monService1Params.param2);
		} else if (op.equals("monService2SharedData")) {
			monService2Params.readObject(argsBuffer);
			provider.monService2(monService2Params.param1);
		} else {
			throw new ServiceNotFoundException();
		}
	}

	public void store(String service, ArgsBuffer params, int priority) {
		synchronized (thread) {
			if (mbuffer.full() || !argsBuffer.check(params.getUsed())) {
				System.out.println("!! Configuration Error for port Class3_monPort1_ProviderAsync : mBuffer full=" + full() + "; argsBuffer size=" + argsBuffer.getSize() + "; message size=" + params.getUsed() + " !!");
			}
			if (pbuff.empty()) {
				thread.notify();
			}
			pbuff.put(this, priority);
			int offset = mbuffer.put(service, priority, params.getUsed());
			argsBuffer.copy(params, offset);
		}
	}
}