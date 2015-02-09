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

/**
 * Services providing requests and computation facilities for Autojava generation.
 */
public class RTSJServices {

    /** 2 bytes. */
    private static final int NB_BYTES_2 = 2;

    /** 4 bytes. */
    private static final int NB_BYTES_4 = 4;

    /** 8 bytes. */
    private static final int NB_BYTES_8 = 8;

    /** Stereotype LossyBuffer. */
    private static final String RTSJ_LOSSY_BUFFER = "RTSJ::LossyBuffer";

    /** Stereotype SporadicPort. */
    private static final String RTSJ_SPORADIC_PORT = "RTSJ::SporadicPort";

    /**
     * Tagged value property from RTSJ_SPORADIC_PORT, RTSJ_PERIODIC_PORT, RTSJ_BURST_PORT, RTSJ_SPORADIC,
     * RTSJ_PERIODIC.
     */
    private static final String PERIOD = "period";

    /** Stereotype PeriodicPort. */
    private static final String RTSJ_PERIODIC_PORT = "RTSJ::PeriodicPort";

    /** Stereotype BurstPort. */
    private static final String RTSJ_BURST_PORT = "RTSJ::BurstPort";

    /** Tagged value property from RTSJ_BURST_PORT. */
    private static final String NB_MSG_PER_PERIOD = "nbMsgPerPeriod";

    /** Stereotype Sporadic. */
    private static final String RTSJ_SPORADIC = "RTSJ::Sporadic";

    /** Stereotype Periodic. */
    private static final String RTSJ_PERIODIC = "RTSJ::Periodic";

    /**
     * List of allowed operators to parse arithmetic strings.
     */
    private static final char[] VALID_OPERATORS = {'/', '*', '+', '-' };

    /**
     * Structure storing the size of a "MBuffer" (number of messages being able to stack) and the reception
     * frequency.
     */
    private class ComputedData {
        /** The size field. */
        private int size;

        /** The frequency field. */
        private double freq;

        /**
         * Constructor of the structure.
         *
         * @param size
         *            The size field.
         * @param freq
         *            The frequency field.
         */
        public ComputedData(int size, double freq) {
            this.size = size;
            this.freq = freq;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double getFreq() {
            return freq;
        }

        public void setFreq(double freq) {
            this.freq = freq;
        }
    }

    /**
     * This computes the size of the "MBuffer" (the max number of received messages par activity period of the
     * related component).<br>
     *
     * @param prt
     *            The port on which the size of the buffer has to be retrieved.
     * @return The size of the "MBuffer".
     */
    public int computeNbMsgsSize(Port prt) {
        ComputedData data = computeMBuffer(prt);
        return data.size;
    }

    /**
     * This computes and returns the size of the largest "ParameterSet" used within the given interface (along
     * its receptions).
     *
     * @param i
     *            The target interface.
     * @return The size.
     */
    public int computePortSize(Interface i) {
        return computePortSize(0, i);
    }

    /**
     * This computes and returns the size of the largest "ParameterSet" used within the given port (along the
     * receptions of its interface).<br>
     * If the port is "lossy", the value of "bufSize" is returned.
     *
     * @param p
     *            The target port.
     * @return The size.
     */
    public int getPortSize(Port p) {
        if (p.getAppliedStereotype(RTSJ_LOSSY_BUFFER) != null) {
            return (Integer)p.getValue(p.getAppliedStereotype(RTSJ_LOSSY_BUFFER), "bufSize");
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

    /**
     * This computes and returns the size of the largest "ParameterSet" used within the given interface (along
     * its receptions).
     *
     * @param size
     *            The level from which the computed size is considered as the largest.
     * @param i
     *            The target interface.
     * @return The size.
     */
    public int computePortSize(int size, Interface i) {
        int result = size;
        Iterator<Reception> receptions = i.getOwnedReceptions().iterator();
        while (receptions.hasNext()) {
            Reception reception = receptions.next();
            Signal signal = reception.getSignal();
            int s = computeSignalSize(signal);
            if (s > result) {
                result = s;
            }
        }
        return result;
    }

    /**
     * This returns the size of the "MBuffer" which consists in stacking the name of the service calls, on the
     * given port.<br>
     * If the port is "lossy", the fixed value "mBufSize" is returned.<br>
     * Else, this value is computed according to the number of sent messages per activity period of the
     * related component instance.
     *
     * @param aPort
     *            The port concerned by the buffer.
     * @return The size of the buffer.
     */
    public int getMBuffer(Port aPort) {
        if (aPort.getAppliedStereotype(RTSJ_LOSSY_BUFFER) != null) {
            return (Integer)aPort.getValue(aPort.getAppliedStereotype(RTSJ_LOSSY_BUFFER), "mbufSize");
        } else {
            return computeNbMsgsSize(aPort);
        }
    }

    /**
     * This computes and returns the size of the "PortBuffer".<br>
     * It is the sum of the size of the "Mbuffers" of every ports of the given component.
     *
     * @param aClass
     *            The target component.
     * @return The size.
     */
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

    /**
     * This returns the ports of the given component and the ones from its ancestors.
     *
     * @param aClass
     *            The target component.
     * @return The list of the found ports.
     */
    private List<Port> getAllInheritedPorts(org.eclipse.uml2.uml.Class aClass) {
        List<Port> ports = new ArrayList<Port>();
        ports.addAll(aClass.getOwnedPorts());
        EObject parent = aClass.eContainer();
        if (parent instanceof org.eclipse.uml2.uml.Class) {
            ports.addAll(getAllInheritedPorts((org.eclipse.uml2.uml.Class)parent));
        }
        return ports;
    }

    /**
     * This computes and returns the size of the given signal<br>
     * . It is the sum of the size of its parameters (properties).
     *
     * @param signal
     *            The target signal.
     * @return the signal size.
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
     * This computes and returns the size of the given property.
     *
     * @param property
     *            The target property.
     * @return the property size
     */
    private int computeTypeSize(Property property) {
        int result = 0;
        if (property != null && property.getAppliedStereotype("RTSJ::NotSerialized") == null) {
            int multiplicity = property.getUpper();
            if (multiplicity == -1) {
                try {
                    multiplicity = Integer.parseInt(property.getDefault());
                } catch (NumberFormatException e) {
                    multiplicity = parseDefaultValue(property);
                }
            }
            if (is4bytes(property)) {
                result = NB_BYTES_4 * multiplicity;
            } else if (is8bytes(property)) {
                result = NB_BYTES_8 * multiplicity;
            } else if (is2bytes(property)) {
                result = NB_BYTES_2 * multiplicity;
            } else if (is1byte(property)) {
                result = multiplicity;
            } else if (property.getType() instanceof org.eclipse.uml2.uml.Class) {
                result = computeClassSize((Class)property.getType()) * multiplicity;
            }
        }
        return result;
    }

    /**
     * Check if the type of the property is 8 bytes long.
     *
     * @param property
     *            The target property.
     * @return True if it is 8 bytes long.
     */
    private boolean is1byte(Property property) {
        // CHECKSTYLE:OFF
        return "EByte".equals(property.getType().getName()) || "Boolean".equals(property.getType().getName())
                || "EBoolean".equals(property.getType().getName())
                || "byte".equals(property.getType().getName())
                || "boolean".equals(property.getType().getName());
        // CHECKSTYLE:ON
    }

    /**
     * Check if the type of the property is 2 bytes long.
     *
     * @param property
     *            The target property.
     * @return True if it is 2 bytes long.
     */
    private boolean is2bytes(Property property) {
        // CHECKSTYLE:OFF
        return "EShort".equals(property.getType().getName()) || "EChar".equals(property.getType().getName())
                || "short".equals(property.getType().getName());
        // CHECKSTYLE:ON
    }

    /**
     * Check if the type of the property is 4 bytes long.
     *
     * @param property
     *            The target property.
     * @return True if it is 4 bytes long.
     */
    private boolean is4bytes(Property property) {
        // CHECKSTYLE:OFF
        return "EFloat".equals(property.getType().getName())
                || "UnlimitedNatural".equals(property.getType().getName())
                || "Integer".equals(property.getType().getName())
                || "EInt".equals(property.getType().getName()) || property.getType() instanceof Enumeration
                || "int".equals(property.getType().getName()) || "float".equals(property.getType().getName());
        // CHECKSTYLE:ON
    }

    /**
     * Check if the type of the property is 8 bytes long.
     *
     * @param property
     *            The target property.
     * @return True if it is 8 bytes long.
     */
    private boolean is8bytes(Property property) {
        // CHECKSTYLE:OFF
        return "EDouble".equals(property.getType().getName()) || "ELong".equals(property.getType().getName())
                || "double".equals(property.getType().getName())
                || "long".equals(property.getType().getName());
        // CHECKSTYLE:ON
    }

    /**
     * This parses the default value of the given property.
     *
     * @param property
     *            The property to parse.
     * @return The default value.
     */
    private int parseDefaultValue(Property property) {
        return evaluate(property.getDefault().replaceAll(" ", ""), '+', "0", property.getModel());
    }

    /**
     * This computes and returns the size of the given UML Class type.
     *
     * @param cl
     *            The target UML Class.
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

    /**
     * This evaluates an arithmetic expression and returns the result.<br>
     *
     * @param leftSide
     *            The left operand.
     * @param oper
     *            The operator.
     * @param rightSide
     *            The right operand.
     * @param model
     *            The UML model.
     * @return The result.
     * @throws IllegalArgumentException
     *             Exception.
     */
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
            } catch (NumberFormatException e) {
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
            } catch (NumberFormatException e) {
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

    /**
     * This returns the index of the operator within the given string.<br>
     *
     * @param string
     *            The target string.
     * @return The index.
     */
    private static int findOperatorLocation(String string) {
        int index = -1;
        for (int i = VALID_OPERATORS.length - 1; i >= 0; i--) {
            index = string.indexOf(VALID_OPERATORS[i]);
            if (index >= 0) {
                return index;
            }
        }
        return index;
    }

    /**
     * This tries to get the value stored by the model object identified by the given name.
     *
     * @param elementname
     *            The name of the object to find in the given UML model.
     * @param model
     *            The UML model.
     * @return The stored value.
     */
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
                // CHECKSTYLE:OFF
                i = 0;
                // CHECKSTYLE:ON
                if (cptsearch == tosearch.length) {
                    // We found it !
                    if (foundlist[0] instanceof Property) {
                        try {
                            return Integer.parseInt(((Property)foundlist[0]).getDefault());
                        } catch (NumberFormatException ne) {
                            // do nothing.
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
     * This returns the number of potential object candidates for the given name.
     *
     * @param name
     *            The name of the objects to look up.
     * @param into
     *            The object from which the search has to begin.
     * @param listreturn
     *            The list of the matching objects.
     * @param cpt
     *            The current number of the found objects.
     * @param recursive
     *            True if the search has to be recursive.
     * @return the number of candidates.
     */
    private int searchNamedElements(String name, NamedElement into, NamedElement[] listreturn, int cpt,
            boolean recursive) {
        int cpt2 = cpt;
        EList<Element> elements = into.getOwnedElements();
        for (int i = 0; i < elements.size(); i++) {
            try {
                NamedElement obj = (NamedElement)elements.get(i);
                if (obj != null && obj.getName() != null) {
                    if (obj.getName().compareTo(name) == 0) {
                        listreturn[cpt2++] = obj;
                        if (cpt2 == listreturn.length) {
                            break;
                        }
                    }
                    if (recursive) {
                        cpt2 = searchNamedElements(name, obj, listreturn, cpt2, recursive);
                    }
                }
            } catch (ClassCastException e) {
                // do nothing
            }
        }
        return cpt2;
    }

    /**
     * This computes and returns different data related to the "MBuffer":<br>
     * - Its size (max number of received messages par activity period of the related component).<br>
     * - The frequency of sent/received messages.
     *
     * @param prt
     *            The port which receives the messages
     * @return The computed data.
     */
    private ComputedData computeMBuffer(Port prt) {
        ComputedData result = new ComputedData(0, 0.0);
        Integer classPeriod = 0;
        EObject container = prt.eContainer();
        if (container instanceof Class) {
            Class myClass = (Class)container;
            if (myClass.getAppliedStereotype(RTSJ_PERIODIC) != null) {
                classPeriod = (Integer)myClass.getValue(myClass.getAppliedStereotype(RTSJ_PERIODIC), PERIOD);
            } else if (myClass.getAppliedStereotype(RTSJ_SPORADIC) != null) {
                classPeriod = (Integer)myClass.getValue(myClass.getAppliedStereotype(RTSJ_SPORADIC), PERIOD);
            }
            if (isPortAsynchronous(prt)) {
                int k = 1;
                int p = 1;
                boolean isPeriodSet = false;
                if (prt.getAppliedStereotype(RTSJ_BURST_PORT) != null) {
                    k = (Integer)prt.getValue(prt.getAppliedStereotype(RTSJ_BURST_PORT), NB_MSG_PER_PERIOD);
                    p = (Integer)prt.getValue(prt.getAppliedStereotype(RTSJ_BURST_PORT), PERIOD);
                    isPeriodSet = true;
                } else if (prt.getAppliedStereotype(RTSJ_PERIODIC_PORT) != null) {
                    p = (Integer)prt.getValue(prt.getAppliedStereotype(RTSJ_PERIODIC_PORT), PERIOD);
                    isPeriodSet = true;
                } else if (prt.getAppliedStereotype(RTSJ_SPORADIC_PORT) != null) {
                    p = (Integer)prt.getValue(prt.getAppliedStereotype(RTSJ_SPORADIC_PORT), PERIOD);
                    isPeriodSet = true;
                }
                if (isPeriodSet) {
                    result = new ComputedData(k * (1 + classPeriod / p), (double)k / (double)p);
                }
            }
        }
        return result;
    }

    /**
     * This determines if the given port is asynchronous nor not.<br>
     * If its interface contains a UML Reception at least, then the port is considered as asynchronous.
     *
     * @param port
     *            The port to check.
     * @return True if asynchronous.
     */
    public boolean isPortAsynchronous(Port port) {
        boolean result = false;
        Iterator<Interface> interfaces = port.getProvideds().iterator();
        while (!result && interfaces.hasNext()) {
            Interface i = interfaces.next();
            if (i instanceof Signal) {
                result = true;
                break;
            } else {
                for (Classifier c : i.getNestedClassifiers()) {
                    if (c instanceof Signal) {
                        result = true;
                        break;
                    }
                }
                if (!result) {
                    for (NamedElement n : i.getMembers()) {
                        if (n instanceof Reception) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

}
