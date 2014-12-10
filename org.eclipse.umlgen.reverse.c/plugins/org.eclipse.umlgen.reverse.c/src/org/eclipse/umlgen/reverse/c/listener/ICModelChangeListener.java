/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Christophe Le Camus (CS) - initial API and implementation
 *      Sebastien Gabel (CS) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.listener;

import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;

/** A model change listener. */
public interface ICModelChangeListener {

    /**
     * This notifies changes from the given event.
     * 
     * @param event
     *            the event.
     * @param needSave
     *            True if it is required to save.
     */
    void notifyChanges(AbstractCModelChangedEvent event, boolean needSave);
}
