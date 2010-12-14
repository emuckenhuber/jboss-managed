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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.types.SimpleTypes;

/**
 * SimpleValue.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @author Emanuel Muckenhuber
 */
public interface SimpleValue extends MetaValue, SimpleTypes, Comparable<SimpleValue> {

    /** {@inheritDoc} */
    SimpleMetaType getMetaType();

    /**
     * Get the underlying value
     *
     * @return the underlying value
     */
    Serializable getValue();

    /**
     * A value factory for simple types.
     */
    public static class Factory {

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
         * Create a simple value.
         *
         * @param value the BigDecimal value
         * @return the created simple value
         */
        public static SimpleValue create(BigDecimal value) {
            return new SimpleValueSupport(SimpleMetaType.BIGDECIMAL, value);
        }

        /**
         * Create a simple value.
         *
         * @param value the BigInteger value
         * @return the created simple value
         */
        public static SimpleValue create(BigInteger value) {
            return new SimpleValueSupport(SimpleMetaType.BIGINTEGER, value);
        }

        /**
         * Create a simple meta type.
         *
         * @param o the object
         * @param metaType the expected type
         * @return the simple meta type
         * @throws IllegalArgumentException if the object is not a simple value, or the
         *         cannot be converted to a simple value
         */
        public static SimpleValue create(Object o, SimpleMetaType metaType) {
            return SimpleValueSupport.create(o, metaType);
        }

    }

}
