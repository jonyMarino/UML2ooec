/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Obeo 					- initial API and implementation
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Represents the hightest event level. All instantiated events inherit this abstract class.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public abstract class AbstractCModelChangedEvent {

    /** A translation unit. */
    private ITranslationUnit translationUnit;

    public ITranslationUnit getTranslationUnit() {
        return translationUnit;
    }

    public void setTranslationUnit(ITranslationUnit tu) {
        translationUnit = tu;
    }

    public String getUnitName() {
        return getTranslationUnit().getPath().removeFileExtension().lastSegment();
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractCModelChangedEvent> {

        /**
         * Get the event.
         *
         * @return The event.
         */
        protected abstract T getEvent();

        /**
         * This sets the translation unit of the event.
         *
         * @param tu
         *            The translation unit.
         * @return self
         */
        public AbstractBuilder<T> translationUnit(ITranslationUnit tu) {
            getEvent().setTranslationUnit(tu);
            return this;
        }

        /**
         * This returns the event.
         *
         * @return The event.
         */
        public T build() {
            return getEvent();
        }
    }

    /**
     * Notify changes accordingly to the kind of event received.
     *
     * @param manager
     *            The model manager
     */
    public abstract void notifyChanges(ModelManager manager);

    /**
     * This cleans the given text deleting invalid XML chars.
     * 
     * @param text
     *            The text to clean.
     * @return The cleaned text.
     */
    protected static String cleanInvalidXmlChars(String text) {
        // From xml spec valid chars:
        // #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] |
        // [#x10000-#x10FFFF]
        // any Unicode character, excluding the surrogate blocks, FFFE, and
        // FFFF.
        String patternStr = "[^\\x09\\x0A\\x0D\\x20-\\xD7FF\\xE000-\\xFFFD\\x10000-x10FFFF]";
        return text.replaceAll(patternStr, "");
    }
}
