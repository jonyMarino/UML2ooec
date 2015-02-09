/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.services;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Services providing facilities about strings.
 */
public class StringServices {

    /** Double quote. */
    private static final String DOUBLE_QUOTE = "\"";

    /**
     * This insert the given string before the receiver string.
     *
     * @param receiver
     *            The receiver string on which the service is applied.
     * @param string
     *            The string to insert.
     * @return The result.
     */
    public String insertBefore(String receiver, String string) {
        return string + receiver;
    }

    /**
     * This returns the size of the largest string within the given list of strings.
     *
     * @param receiver
     *            The receiver list of strings.
     * @return The size of the largest string.
     */
    public int getMaxLength(List<String> receiver) {
        int result = 0;
        for (String string : receiver) {
            if (string.length() > result) {
                result = string.length();
            }
        }
        return result;
    }

    /**
     * Returns the current time.<br>
     * <br>
     * it comes from
     * /org.eclipse.umlgen.gen.java/src/org/eclipse/umlgen/gen/java/services/CommonServices.java#reqTime()
     *
     * @return The current time.
     */
    public static String getTime() {
        Date date = new Date();
        return DateFormat.getTimeInstance(DateFormat.LONG).format(date);
    }

    /**
     * Gets the date in a short format : 06/08/07.<br>
     * <br>
     * it is inspired from
     * /org.eclipse.umlgen.gen.java/src/org/eclipse/umlgen/gen/java/services/CommonServices.java#reqDate()
     *
     * @return String representing the short format date
     */
    public static String getShortDate() {
        Date date = new Date(); // to get the date
        Locale locale = Locale.getDefault(); // to get the language of the system
        DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dateFormatShort.format(date);
    }

    /**
     * Surrounds the value with double quote if is not done (abcd to "abcd")<br/>
     * Replace double quote with \" (ab"cd to "ab\"cd")<br/>
     * Remove simple quote starting and ending in value ('abcd' to "abcd")<br/>
     * Useful for default string value. <br>
     *
     * @param value
     *            the value
     * @return the string
     */
    public static String addQuotes(String value) {
        String value2 = value;
        if (value2.endsWith("'") && value2.startsWith("'")) {
            value2 = value2.substring(1, value2.length() - 1);
        }
        if (value2.endsWith(DOUBLE_QUOTE) && value2.startsWith(DOUBLE_QUOTE)) {
            value2 = value2.substring(1, value2.length() - 1);
        }
        return DOUBLE_QUOTE + value2.trim().replaceAll(DOUBLE_QUOTE, "\\\\\\\"") + DOUBLE_QUOTE;
    }

}
