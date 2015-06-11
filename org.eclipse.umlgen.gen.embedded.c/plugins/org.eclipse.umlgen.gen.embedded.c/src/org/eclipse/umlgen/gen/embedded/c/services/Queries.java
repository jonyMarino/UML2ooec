/*******************************************************************************
 * Copyright (c) 2015 Spacebel SA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Johan Hardy (Spacebel) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.services;

import org.eclipse.uml2.uml.Comment;

/**
 * This class implements Acceleo Query services.
 */
public class Queries {

    /**
     * Convert a string into a Boolean type.
     * 
     * @param s
     *            The string to be converted.
     * @return The value of the String in Boolean.
     */
    public Boolean stringToBooleanQuery(String s) {
        Boolean result = Boolean.parseBoolean(s);
        return result;
    }

    /**
     * Format a comment according parameters.
     * 
     * @param comment
     *            The comment to be formatted.
     * @param tagLine
     *            The doxygen tag that prefixes the comment.
     * @param startLine
     *            The string that prefixes a new line in the comment.
     * @param lineSize
     *            The maximum number of semi-colomns allowed.
     * @param lastInterline
     *            A Boolean indicating whether a new line is required at the end of the comment.
     * @return A String equal to the formatted comment.
     */
    public String formatCommentQuery(Comment comment, String tagLine, Boolean lastInterline,
            String startLine, int lineSize) {
        String result = tagLine;
        String[] yy = comment.getBody().replaceAll("\\r", "").split("\n");
        for (int i = 0; i < yy.length; i++) {
            String line = yy[i];
            if (i != 0) {
                result = result + "\n" + startLine;
            }
            if ((line.indexOf("@image") >= 0) || (line.length() <= lineSize)) {
                result = result + line;
            } else {
                int size = 0;
                String[] tokens = line.split(" ");
                for (int x = 0; x < tokens.length; x++) {
                    String self = tokens[x];
                    if ((self.length() + size) < lineSize) {
                        size = size + 1 + self.length();
                        result = result + self;
                    } else {
                        size = self.length() + 1;
                        result = result + "\n" + startLine + self;
                    }
                    if ((self.length() == 0) || (!self.endsWith("\n"))) {
                        result = result + " ";
                    }
                }
            }
        }
        if (lastInterline) {
            result = result + "\n" + startLine;
        }
        return result;
    }

}
