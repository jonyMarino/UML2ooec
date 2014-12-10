/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.beans;

import com.google.common.collect.Maps;

import java.util.Map;

import org.eclipse.umlgen.c.common.BundleConstants;

/**
 * Comment Info.
 */
public class CommentInfo {

    /** Constant for empty strings. */
    private static final String EMPTY_STRING = "";

    /** Index to retrieve the info "before". */
    private static final int BEFORE = 0;

    /** Index to retrieve the info "inline". */
    private static final int INLINE = 1;

    /** Index to retrieve the info "last line". */
    private static final int LAST_LINE = 2;

    /** Index to retrieve the info "same line". */
    private static final int SAME_LINE = 3;

    /** Map which stores the informations. */
    private Map<Integer, String> info = Maps.newHashMap();

    /**
     * Get the info to the given index.
     *
     * @param infoType
     *            The index
     * @return The info
     */
    private String getInfo(int infoType) {
        return info.get(infoType);
    }

    /**
     * This adds a new info <code>content</code> to the given index <code>infoType</code>.<br>
     * If there already is an info to his index, this adds the new one behind the given separator.
     *
     * @param infoType
     *            The index.
     * @param content
     *            The info.
     * @param separator
     *            The separator.
     */
    private void addToInfo(int infoType, String content, String separator) {
        String existingContent = getInfo(infoType);
        if (existingContent == null || existingContent.equals(EMPTY_STRING)) {
            info.put(infoType, content);
        } else {
            info.put(infoType, existingContent + separator + content);
        }
    }

    /**
     * Check if there exists an info to the given index.
     *
     * @param infoType
     *            The index.
     * @return True if yes.
     */
    private boolean hasInfo(int infoType) {
        String content = getInfo(infoType);
        return content != null && !content.equals(EMPTY_STRING);
    }

    public String getBefore() {
        return getInfo(BEFORE);
    }

    /**
     * Add the given info as "before".
     *
     * @param content
     *            The info to add.
     */
    public void addBefore(String content) {
        addToInfo(BEFORE, content, BundleConstants.LINE_SEPARATOR);
    }

    /**
     * Check if "before" exists.
     *
     * @return True if yes.
     */
    public boolean hasBefore() {
        return hasInfo(BEFORE);
    }

    public String getInline() {
        return getInfo(INLINE);
    }

    /**
     * Add the given info as "inline".
     *
     * @param content
     *            The info to add.
     */
    public void addInline(String content) {
        addToInfo(INLINE, content, EMPTY_STRING);
    }

    /**
     * Check if "inline" exists.
     *
     * @return True if yes.
     */
    public boolean hasInline() {
        return hasInfo(INLINE);
    }

    public String getLastLine() {
        return getInfo(LAST_LINE);
    }

    /**
     * Add the given info as "last line".
     *
     * @param content
     *            The info to add.
     */
    public void addLastLine(String content) {
        addToInfo(LAST_LINE, content, BundleConstants.LINE_SEPARATOR);
    }

    /**
     * Check if "last line" exists.
     *
     * @return True if yes.
     */
    public boolean hasLastLine() {
        return hasInfo(LAST_LINE);
    }

    public String getSameLine() {
        return getInfo(SAME_LINE);
    }

    /**
     * Add the given info as "same line".
     *
     * @param content
     *            The info to add.
     */
    public void addSameLine(String content) {
        addToInfo(SAME_LINE, content, EMPTY_STRING);
    }

    /**
     * Check if "same line" exists.
     *
     * @return True if yes.
     */
    public boolean hasSameLine() {
        return hasInfo(SAME_LINE);
    }
}
