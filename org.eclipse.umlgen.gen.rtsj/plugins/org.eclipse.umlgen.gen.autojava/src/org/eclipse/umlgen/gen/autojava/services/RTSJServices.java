/*******************************************************************************
 * Copyright (c) 2009, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nathalie Lepine (Obeo) - initial API and implementation
 *     Sylvain Jouanneau (CNES) - bug 440107
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Type;

public class RTSJServices {

	private static final char[] validOperators = {'/', '*', '+', '-' };

	private class ComputedData {
		public int taille;

		public double freq;

		public ComputedData(int taille, double freq) {
			this.taille = taille;
			this.freq = freq;
		}
	}

	public int computeNbMsgsSize(Port prt) {
		ComputedData data = computeMBuffer(prt);
		return data.taille;
	}

	public int computePortSize(Interface i) {
		return computePortSize(0, i);
	}

	public int getPortSize(Port p) {
		if (p.getAppliedStereotype("RTSJ::LossyBuffer") != null) {
			return (Integer)p.getValue(p.getAppliedStereotype("RTSJ::LossyBuffer"), "bufSize");
		} else {
			int size = 0;
			List<Interface> interfaces = new ArrayList<Interface>();
			interfaces.addAll(p.getProvideds()); // user port case
			Type type = p.getType();
			if (type instanceof Interface) {
				interfaces.add((Interface)type); // provider port case
			}
			for (Interface i : interfaces) {
				size = computePortSize(size, i);
			}
			return size;
		}
	}

	public int computePortSize(int size, Interface i) {
		Iterator<Reception> receptions = i.getOwnedReceptions().iterator();
		while (receptions.hasNext()) {
			Reception reception = receptions.next();
			Signal signal = reception.getSignal();
			int s = computeSignalSize(signal);
			if (s > size) {
				size = s;
			}
		}
		return size;
	}

	public int getMBuffer(Port aPort) {
		if (aPort.getAppliedStereotype("RTSJ::LossyBuffer") != null) {
			return (Integer)aPort.getValue(aPort.getAppliedStereotype("RTSJ::LossyBuffer"), "mbufSize");
		} else {
			return computeNbMsgsSize(aPort);
		}
	}

	public int getPortBuffer(org.eclipse.uml2.uml.Class aClass) {
		int result = 0;
		for (Port aPort : getAllInheritedPorts(aClass)) {
			if (aPort.getType() != null) {
				int mBuffSize = getMBuffer(aPort);
				result += mBuffSize;
			}
		}
		return result;
	}

	private List<Port> getAllInheritedPorts(org.eclipse.uml2.uml.Class aClass) {
		List<Port> ports = new ArrayList<Port>();
		ports.addAll(aClass.getOwnedPorts());
		for (org.eclipse.uml2.uml.Classifier parent : aClass.allParents()) {
			if (parent instanceof org.eclipse.uml2.uml.Class) {
				ports.addAll(getAllInheritedPorts((org.eclipse.uml2.uml.Class)parent));
			}
		}
		return ports;
	}

	/**
	 * Compute the size of the signal : attributes size sum
	 *
	 * @param signal
	 * @return the signal size
	 * @throws ENodeCastException
	 */
	public int computeSignalSize(Signal signal) {
		int taille = 0;
		if (signal != null) {
			for (Property property : signal.getAllAttributes()) {
				taille += computeTypeSize(property);
			}
		}
		return taille;
	}

	/**
	 * @param property
	 * @return the property size
	 */
	private int computeTypeSize(Property property) {
		if (property != null && property.getAppliedStereotype("RTSJ::NotSerialized") == null) {
			int multiplicity = property.getUpper();
			if (multiplicity == -1) {
				try {
					multiplicity = Integer.parseInt(property.getDefault());
				} catch (NumberFormatException e) {
					multiplicity = parseDefaultValue(property);
				}
			}
			if (property.getType().getName().equals("EFloat")
					|| property.getType().getName().equals("UnlimitedNatural")
					|| property.getType().getName().equals("Integer")
					|| property.getType().getName().equals("EInt")
					|| property.getType() instanceof Enumeration
					|| property.getType().getName().equals("int")
					|| property.getType().getName().equals("float")) {
				return 4 * multiplicity;
			} else if (property.getType().getName().equals("EDouble")
					|| property.getType().getName().equals("ELong")
					|| property.getType().getName().equals("double")
					|| property.getType().getName().equals("long")) {
				return 8 * multiplicity;
			} else if (property.getType().getName().equals("EShort")
					|| property.getType().getName().equals("EChar")
					|| property.getType().getName().equals("short")) {
				return 2 * multiplicity;
			} else if (property.getType().getName().equals("EByte")
					|| property.getType().getName().equals("Boolean")
					|| property.getType().getName().equals("EBoolean")
					|| property.getType().getName().equals("byte")
					|| property.getType().getName().equals("boolean")) {
				return multiplicity;
			} else if (property.getType() instanceof org.eclipse.uml2.uml.Class) {
				return computeClassSize((Class)property.getType()) * multiplicity;
			}
		}
		return 0;
	}

	private int parseDefaultValue(Property property) {
		return evaluate(property.getDefault().replaceAll(" ", ""), '+', "0", property.getModel());
	}

	/**
	 * @param cl
	 * @return the class size
	 */
	private int computeClassSize(org.eclipse.uml2.uml.Class cl) {
		int size = 0;
		/*
		 * try { if (UML2Service.hasStereotype(cl, "RSTJ::Message")) { return size; } } catch
		 * (ENodeCastException e) { e.printStackTrace(); }
		 */
		for (Generalization generalization : cl.getGeneralizations()) {
			if (generalization instanceof org.eclipse.uml2.uml.Class) {
				size += computeClassSize((Class)generalization);
			}
		}
		for (Property prop : cl.getAllAttributes()) {
			size += computeTypeSize(prop);
		}
		for (Association association : cl.getAssociations()) {
			for (Property prop : association.getNavigableOwnedEnds()) {
				size += computeTypeSize(prop);
			}
		}
		return size;
	}

	private int evaluate(String leftSide, char oper, String rightSide, Model model)
			throws IllegalArgumentException {
		int total = 0;
		int leftResult = 0;
		int rightResult = 0;

		int operatorLoc = findOperatorLocation(leftSide);
		if (operatorLoc > 0 && operatorLoc < leftSide.length() - 1) {
			leftResult = evaluate(leftSide.substring(0, operatorLoc), leftSide.charAt(operatorLoc), leftSide
					.substring(operatorLoc + 1, leftSide.length()), model);
		} else {
			try {
				leftResult = Integer.parseInt(leftSide);
			} catch (Exception e) {
				leftResult = parseElementValue(leftSide, model);
				if (leftResult == Integer.MAX_VALUE) {
					throw new IllegalArgumentException("Invalid value found in portion of equation: "
							+ leftSide);
				}
			}
		}

		operatorLoc = findOperatorLocation(rightSide);
		if (operatorLoc > 0 && operatorLoc < rightSide.length() - 1) {
			rightResult = evaluate(rightSide.substring(0, operatorLoc), rightSide.charAt(operatorLoc),
					rightSide.substring(operatorLoc + 1, rightSide.length()), model);
		} else {
			try {
				rightResult = Integer.parseInt(rightSide);
			} catch (Exception e) {
				rightResult = parseElementValue(rightSide, model);
				if (rightResult == Integer.MAX_VALUE) {
					throw new IllegalArgumentException("Invalid value found in portion of equation: "
							+ rightSide);
				}
			}
		}

		switch (oper) {
			case '/':
				total = leftResult / rightResult;
				break;
			case '*':
				total = leftResult * rightResult;
				break;
			case '+':
				total = leftResult + rightResult;
				break;
			case '-':
				total = leftResult - rightResult;
				break;
			default:
				throw new IllegalArgumentException("Unknown operator.");
		}
		return total;
	}

	private static int findOperatorLocation(String string) {
		int index = -1;
		for (int i = validOperators.length - 1; i >= 0; i--) {
			index = string.indexOf(validOperators[i]);
			if (index >= 0) {
				return index;
			}
		}
		return index;
	}

	private int parseElementValue(String elementname, Model model) {
		String[] tosearch = elementname.split("\\.");
		NamedElement[] foundlist = new NamedElement[100];
		int foundnb = this.searchNamedElements(tosearch[0], model, foundlist, 0, true);
		int cptsearch = 1;
		for (int i = 0; i < foundnb; i++) {
			NamedElement[] foundlist2 = new NamedElement[2];
			int foundnb2 = this.searchNamedElements(tosearch[cptsearch], foundlist[i], foundlist2, 0, false);
			if (foundnb2 == 1) {
				cptsearch++;
				foundlist = foundlist2;
				foundnb = foundnb2;
				i = 0;
				if (cptsearch == tosearch.length) {
					// We found it !
					if (foundlist[0] instanceof Property) {
						try {
							return Integer.parseInt(((Property)foundlist[0]).getDefault());
						} catch (NumberFormatException ne) {
						}
					}
					break;
				}
			} else if (cptsearch != 1) {
				break;
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * @param name
	 * @param into
	 * @return All elements found by name in "into"
	 */
	private int searchNamedElements(String name, NamedElement into, NamedElement[] listreturn, int cpt,
			boolean recursive) {
		EList<Element> elements = into.getOwnedElements();
		for (int i = 0; i < elements.size(); i++) {
			try {
				NamedElement obj = (NamedElement)elements.get(i);
				if (obj != null && obj.getName() != null) {
					if (obj.getName().compareTo(name) == 0) {
						listreturn[cpt++] = obj;
						if (cpt == listreturn.length) {
							break;
						}
					}
					if (recursive) {
						cpt = searchNamedElements(name, obj, listreturn, cpt, recursive);
					}
				}
			} catch (ClassCastException e) {
			}
		}
		return cpt;
	}

	private ComputedData computeMBuffer(Port prt) {
		Integer classPeriod = 0;
		EObject container = prt.eContainer();
		if (container instanceof Class) {
			Class myClass = (Class)container;
			if (myClass.getAppliedStereotype("RTSJ::Periodic") != null) {
				classPeriod = (Integer)myClass.getValue(myClass.getAppliedStereotype("RTSJ::Periodic"),
						"period");
			} else if (myClass.getAppliedStereotype("RTSJ::Sporadic") != null) {
				classPeriod = (Integer)myClass.getValue(myClass.getAppliedStereotype("RTSJ::Sporadic"),
						"period");
			}
			if (!isPortAsynchronous(prt)) {
				return new ComputedData(0, 0.0);
			}
			int k = 1;
			int p = 1;
			if (prt.getAppliedStereotype("RTSJ::BurstPort") != null) {
				k = (Integer)prt.getValue(prt.getAppliedStereotype("RTSJ::BurstPort"), "nbMsgPerPeriod");
				p = (Integer)prt.getValue(prt.getAppliedStereotype("RTSJ::BurstPort"), "period");
			} else if (prt.getAppliedStereotype("RTSJ::PeriodicPort") != null) {
				p = (Integer)prt.getValue(prt.getAppliedStereotype("RTSJ::PeriodicPort"), "period");
			} else if (prt.getAppliedStereotype("RTSJ::SporadicPort") != null) {
				p = (Integer)prt.getValue(prt.getAppliedStereotype("RTSJ::SporadicPort"), "period");
			} else {
				return new ComputedData(0, 0.0);
			}
			return new ComputedData(k * (1 + classPeriod / p), (double)k / (double)p);
		}
		return new ComputedData(0, 0.0);
	}

	/**
	 * @param port
	 * @return the port is stereotyped synchronous
	 */
	public boolean isPortAsynchronous(Port port) {
		for (Interface i : port.getProvideds()) {
			if (i instanceof Signal) {
				return true;
			} else {
				for (Classifier c : i.getNestedClassifiers()) {
					if (c instanceof Signal) {
						return true;
					}
				}
				for (NamedElement n : i.getMembers()) {
					if (n instanceof Reception) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
