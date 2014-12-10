/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Christophe Le Camus (CS-SI)- evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Event related to a change of C unit.
 */
public abstract class AbstractCUnitEvent extends AbstractCModelChangedEvent {

    /** The previous name. */
    private IPath previousName;

    /** The current name. */
    private IPath currentName;

    /** A model manager. */
    private ModelManager modelManager;

    public IPath getPreviousName() {
        return this.previousName;
    }

    public IPath getCurrentName() {
        return this.currentName;
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    protected void setModelMananager(ModelManager mngr) {
        this.modelManager = mngr;
    }

    protected void setCurrentName(String currentName) {
        this.currentName = new Path(currentName);
    }

    protected void setPreviousName(String previousName) {
        this.previousName = new Path(previousName);
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractCUnitEvent> extends AbstractCModelChangedEvent.AbstractBuilder<T> {

        @Override
        public AbstractBuilder<T> translationUnit(ITranslationUnit tu) {
            getEvent().setTranslationUnit(tu);
            return this;
        }

        /**
         * This sets the given function name to the event.
         *
         * @param functionName
         *            the function name.
         * @return self.
         */
        public AbstractBuilder<T> functionName(String functionName) {
            getEvent().setCurrentName(functionName);
            return this;
        }

        /**
         * This sets the given model manager to the event.
         *
         * @param mngr
         *            The model manager.
         * @return self
         */
        public AbstractBuilder<T> setModelMananager(ModelManager mngr) {
            getEvent().setModelMananager(mngr);
            return this;
        }

        /**
         * This sets the given current name to the event.
         *
         * @param currentName
         *            The current name
         * @return self.
         */
        public AbstractBuilder<T> currentName(String currentName) {
            getEvent().setCurrentName(currentName);
            return this;
        }

        /**
         * This sets the given previous name to the event.
         *
         * @param previousName
         *            The previous name.
         * @return self
         */
        public AbstractBuilder<T> previousName(String previousName) {
            getEvent().setPreviousName(previousName);
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent.AbstractBuilder#getEvent()
         */
        @Override
        protected abstract T getEvent();
    }
}
