/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

/** Utility class. */
public final class Utils {

    /** Constructor. */
    private Utils() {
    }

    /**
     * This returns only the objects which are only in the given <code>left</code> list.
     *
     * @param left
     *            The left list
     * @param right
     *            The right list
     * @param <T>
     *            Any Java object
     * @return The objects only in left side
     */
    public static <T> Collection<T> inLeftOnly(T[] left, T[] right) {
        return inLeftOnly(Lists.newArrayList(left), Lists.newArrayList(right), Functions.identity());
    }

    /**
     * This returns only the objects which are only in the given <code>left</code> list.
     *
     * @param left
     *            The left list
     * @param right
     *            The right list
     * @param function
     *            Function which applies on the 2 lists
     * @param <T>
     *            Any Java object
     * @param <X>
     *            Any java object
     * @return The objects only in left side
     */
    public static <T, X> Collection<T> inLeftOnly(T[] left, T[] right, Function<? super T, X> function) {
        return inLeftOnly(Lists.newArrayList(left), Lists.newArrayList(right), function);
    }

    /**
     * This returns only the objects which are only in the given <code>left</code> list.
     *
     * @param left
     *            The left list
     * @param right
     *            The right list
     * @param <T>
     *            Any Java object
     * @return The objects only in left side
     */
    public static <T> Collection<T> inLeftOnly(Collection<T> left, Collection<T> right) {
        return inLeftOnly(left, right, Functions.identity());
    }

    /**
     * This returns only the objects which are only in the given <code>left</code> list.
     *
     * @param left
     *            The left list
     * @param right
     *            The right list
     * @param function
     *            Function which applies on the 2 lists
     * @param <T>
     *            Any Java object
     * @param <X>
     *            Any java object
     * @return The objects only in left side
     */
    public static <X, T> Collection<T> inLeftOnly(Collection<T> left, Collection<T> right,
            final Function<? super T, X> function) {
        Set<X> leftSet = ImmutableSet.copyOf(Iterables.transform(left, function));
        Set<X> rigthSet = ImmutableSet.copyOf(Iterables.transform(right, function));

        Set<X> intersection = Sets.intersection(leftSet, rigthSet);
        final Set<X> inLeftOnlyAfterFunction = Sets.difference(leftSet, intersection);

        return Collections2.filter(left, new Predicate<T>() {
            public boolean apply(T input) {
                return inLeftOnlyAfterFunction.contains(function.apply(input));
            }
        });
    }

}
