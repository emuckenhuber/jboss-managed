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

import org.jboss.model.types.CollectionMetaType;
import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.types.TableMetaType;

/**
 * @author Emanuel Muckenhuber
 */
public class MetaValueFactory {

    /**
     * Create a simple value.
     *
     * @param value the String value
     * @return the created simple value
     */
    public static SimpleValue create(String value) {
        return new SimpleValueSupport(SimpleMetaType.STRING, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the boolean value
     * @return the created simple value
     */
    public static SimpleValue create(boolean value) {
        return new SimpleValueSupport(SimpleMetaType.BOOLEAN_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Boolean value
     * @return the created simple value
     */
    public static SimpleValue create(Boolean value) {
        return new SimpleValueSupport(SimpleMetaType.BOOLEAN, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the byte value
     * @return the created simple value
     */
    public static SimpleValue create(byte value) {
        return new SimpleValueSupport(SimpleMetaType.BYTE_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Byte value
     * @return the created simple value
     */
    public static SimpleValue create(Byte value) {
        return new SimpleValueSupport(SimpleMetaType.BYTE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the char value
     * @return the created simple value
     */
    public static SimpleValue create(char value) {
        return new SimpleValueSupport(SimpleMetaType.CHARACTER_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Character value
     * @return the created simple value
     */
    public static SimpleValue create(Character value) {
        return new SimpleValueSupport(SimpleMetaType.CHARACTER, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the short value
     * @return the created simple value
     */
    public static SimpleValue create(short value) {
        return new SimpleValueSupport(SimpleMetaType.SHORT_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Short value
     * @return the created simple value
     */
    public static SimpleValue create(Short value) {
        return new SimpleValueSupport(SimpleMetaType.SHORT, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the int value
     * @return the created simple value
     */
    public static SimpleValue create(int value) {
        return new SimpleValueSupport(SimpleMetaType.INTEGER_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Integer value
     * @return the created simple value
     */
    public static SimpleValue create(Integer value) {
        return new SimpleValueSupport(SimpleMetaType.INTEGER, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the long value
     * @return the created simple value
     */
    public static SimpleValue create(long value) {
        return new SimpleValueSupport(SimpleMetaType.LONG_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Long value
     * @return the created simple value
     */
    public static SimpleValue create(Long value) {
        return new SimpleValueSupport(SimpleMetaType.LONG, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the float value
     * @return the created simple value
     */
    public static SimpleValue create(float value) {
        return new SimpleValueSupport(SimpleMetaType.FLOAT_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Float value
     * @return the created simple value
     */
    public static SimpleValue create(Float value) {
        return new SimpleValueSupport(SimpleMetaType.FLOAT, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the double value
     * @return the created simple value
     */
    public static SimpleValue create(double value) {
        return new SimpleValueSupport(SimpleMetaType.DOUBLE_PRIMITIVE, value);
    }

    /**
     * Create a simple value.
     *
     * @param value the Double value
     * @return the created simple value
     */
    public static SimpleValue create(Double value) {
        return new SimpleValueSupport(SimpleMetaType.DOUBLE, value);
    }

    /**
     * Create a composite value.
     *
     * @param metaType the composite meta type
     * @return the composite value
     */
    public static CompositeValue create(final CompositeMetaType metaType) {
        return new CompositeValueSupport(metaType);
    }

    /**
     * Create a composite map value.
     *
     * @param metaType the composite map type
     * @return the composite map value
     */
    public static CompositeMapValue create(final CompositeMapMetaType metaType) {
        return new CompositeMapValueSupport(metaType);
    }

    /**
     * Create a composite map value.
     *
     * @param entryType the composite entry meta type
     * @param index the composite item key which should be used as index
     * @return the composite map value
     */
    public static CompositeMapValue createCompositeMapValue(final CompositeMetaType entryType, final String index) {
        final CompositeMapMetaType metaType = new CompositeMapMetaType(entryType, index, index);
        return new CompositeMapValueSupport(metaType);
    }

    /**
     * Create a table value.
     *
     * @param metaType the table value type
     * @return the table value
     */
    public static TableValue create(final TableMetaType metaType) {
        return new TableValueSupport(metaType);
    }

    /**
     * Create a collection value.
     *
     * @param metaType the collection meta type
     * @return the collection value
     */
    public static CollectionValue create(final CollectionMetaType metaType) {
        return new CollectionValueSupport(metaType);
    }

    /**
     * Create a collection value.
     *
     * @param metaType the collection meta type
     * @param initialCapacity the initial capacity
     * @return the collection value
     */
    public static CollectionValue create(CollectionMetaType metaType, int initialCapacity) {
        return new CollectionValueSupport(metaType, initialCapacity);
    }

    /**
     * Create a collection value.
     *
     * @param metaType the collection meta type
     * @param collection a collection used for this value
     * @return the collection value
     */
    public static CollectionValue create(final CollectionMetaType metaType, Collection<MetaValue> collection) {
        return new CollectionValueSupport(metaType, collection);
    }

}
