/**
 * model/InterfaceAsynchronous.java
 *
 * File generated from the  uml Interface
 */
package model;

import org.eclipse.umlgen.rtsj.annotations.*;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;

/**
 * Description of the interface InterfaceAsynchronous.
 *
 */

@Asynchronous
public interface InterfaceAsynchronous  {




	/**
	 *  Description of the signal monService1.
	 *
	 *
	 * @param param1
	 * @param param2
	 */
	@reception
	void monService1(int param1, int param2);

	/**
	 *  Description of the signal monService2.
	 *
	 *
	 * @param param1
	 */
	@reception
	void monService2(SharedData param1);



}