/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.common.interactions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.registry.SynchronizerDescriptor;
import org.eclipse.umlgen.c.common.interactions.extension.registry.SynchronizerRegistry;

/**
 * This allows to retrieve a potential model synchronizer to interact with semantic or graphical models.
 */
public class SynchronizersManager {

	private static Comparator<SynchronizerDescriptor> synchronizerComparator = new Comparator<SynchronizerDescriptor>() {

		public int compare(SynchronizerDescriptor o1, SynchronizerDescriptor o2) {
			int o1ranking = Integer.parseInt(o1.getRanking());
			int o2ranking = Integer.parseInt(o2.getRanking());
			return o2ranking - o1ranking;
		}

	};

	/**
	 * Get the most priority registered model synchronizer
	 *
	 * @return The model synchronizer
	 */
	public static IModelSynchronizer getSynchronizer() {
		List<SynchronizerDescriptor> descriptors = SynchronizerRegistry.getRegisteredExtensions();
		Collections.sort(descriptors, synchronizerComparator);
		for (SynchronizerDescriptor desc : descriptors) {
			return desc.getSynchronizerExtension();
		}
		return null;
	}

}
