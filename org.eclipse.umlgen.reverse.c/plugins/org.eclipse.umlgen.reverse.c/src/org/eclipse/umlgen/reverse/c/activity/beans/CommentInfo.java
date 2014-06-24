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

public class CommentInfo {
	private static final int BEFORE = 0;

	private static final int INLINE = 1;

	private static final int LAST_LINE = 2;

	private static final int SAME_LINE = 3;

	private Map<Integer, String> info = Maps.newHashMap();

	private String getInfo(int infoType) {
		return info.get(infoType);
	}

	private void addToInfo(int infoType, String content, String separator) {
		String existingContent = getInfo(infoType);
		if (existingContent == null || existingContent.equals("")) {
			info.put(infoType, content);
		} else {
			info.put(infoType, existingContent + separator + content);
		}
	}

	private boolean hasInfo(int infoType) {
		String content = getInfo(infoType);
		return content != null && !content.equals("");
	}

	public String getBefore() {
		return getInfo(BEFORE);
	}

	public void addBefore(String content) {
		addToInfo(BEFORE, content, BundleConstants.LINE_SEPARATOR);
	}

	public boolean hasBefore() {
		return hasInfo(BEFORE);
	}

	public String getInline() {
		return getInfo(INLINE);
	}

	public void addInline(String content) {
		addToInfo(INLINE, content, "");
	}

	public boolean hasInline() {
		return hasInfo(INLINE);
	}

	public String getLastLine() {
		return getInfo(LAST_LINE);
	}

	public void addLastLine(String content) {
		addToInfo(LAST_LINE, content, BundleConstants.LINE_SEPARATOR);
	}

	public boolean hasLastLine() {
		return hasInfo(LAST_LINE);
	}

	public String getSameLine() {
		return getInfo(SAME_LINE);
	}

	public void addSameLine(String content) {
		addToInfo(SAME_LINE, content, "");
	}

	public boolean hasSameLine() {
		return hasInfo(SAME_LINE);
	}
}
