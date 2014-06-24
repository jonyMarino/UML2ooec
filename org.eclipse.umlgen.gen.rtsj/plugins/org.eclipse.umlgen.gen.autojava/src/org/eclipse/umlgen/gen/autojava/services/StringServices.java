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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class StringServices {

	public String insertBefore(String receiver, String string) {
		return string + receiver;
	}

	public int getMaxLength(List<String> receiver) {
		int reference = 0;
		final Iterator<String> values = receiver.iterator();
		while (values.hasNext()) {
			String string = values.next();
			if (string.length() > reference) {
				reference = string.length();
			}
		}
		return reference;
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
	 * Gets the date in a short format : 06/08/07 <br>
	 * <br>
	 * it is inspired from
	 * /org.eclipse.umlgen.gen.java/src/org/eclipse/umlgen/gen/java/services/CommonServices.java#reqDate()
	 *
	 * @return String representing the short format date
	 */
	public static String getShortDate() {
		Date date = new Date(); // to get the date
		Locale locale = Locale.getDefault();// to get the language of the system
		DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return dateFormatShort.format(date);
	}

	/**
	 * Surrounds the value with double quote if is not done (abcd to "abcd")<br/>
	 * Replace double quote with \" (ab"cd to "ab\"cd")<br/>
	 * Remove simple quote starting and ending in value ('abcd' to "abcd")<br/>
	 * Usefull for default string value. <br>
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String addQuotes(String value) {
		if (value.endsWith("'") && value.startsWith("'")) {
			value = value.substring(1, value.length() - 1);
		}
		if (value.endsWith("\"") && value.startsWith("\"")) {
			value = value.substring(1, value.length() - 1);
		}
		return "\"" + value.trim().replaceAll("\"", "\\\\\\\"") + "\"";
	}

}
