/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.model.values;

import java.util.Collection;

import org.jboss.model.types.CompositeMetaType;

/**
 * A {@link MetaValue} representing a <code>Map&lt;String,MetaValue&gt;</code>,
 * where the set of allowed keys and their corresponding values' MetaTypes are
 * specified by the associated {@link CompositeMetaType}.
 *
 * @see CompositeValueSupport
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Emanuel Muckenhuber
 */
public interface CompositeValue extends MetaValue {

    /** {@inheritDoc} */
    CompositeMetaType getMetaType();

    /**
     * Retrieve the value for the item with the passed key
     *
     * @param key the key to the item
     * @return the value
     * @throws IllegalArgumentException when the key is null or the empty string or when the key does not exist
     */
    MetaValue get(String key);

    /**
     * Set an item value.
     *
     * @param key the key to the item
     * @param metaValue the value
     */
    void set(String key, MetaValue metaValue);

    /**
     * Retrieve the array of values for the item with the passed keys
     *
     * @param keys an array of key values
     * @return the array of values
     * @throws IllegalArgumentException when a key is null or the empty string or the array is null
     *         or when a key does not exist
     */
    MetaValue[] getAll(String[] keys);

    /**
     * Tests whether a key is part of this composite value
     *
     * @param key the key to test
     * @return true when the key exists, false otherwise
     */
    boolean containsKey(String key);

    /**
     * Tests whether a item exists with the passed value
     *
     * @param value the value to test
     * @return true when the value exists, false otherwise
     */
    boolean containsValue(MetaValue value);

    /**
     * The values of this composite value
     * <p>
     * An iterator over the returned collection returns result in ascending
     * lexicographic order
     *
     * @return an unmodifiable Collection of the values of this CompositeMetaType.
     */
    Collection<MetaValue> values();

    /**
     * Tests whether two composite value objects are equal
     * <p>
     *
     * The object is non-null<br>
     * The object implements this interface<br>
     * The composite meta types are equal<br>
     * The values are equal
     *
     * @param obj the object to test
     * @return true when the above conditions are satisfied, false otherwise.
     */
    boolean equals(Object obj);

    /**
     * Generates a hashcode for the implementation.
     * <p>
     *
     * The sum of the hashCodes for the elements mentioned in the equals method
     *
     * @return the calculated hashcode
     */
    int hashCode();

    /**
     * A string representation of the compsite value.
     * <p>
     *
     * It is made up of implementation class and the values mentioned in the
     * equals method
     *
     * @return the string
     */
    String toString();
}
