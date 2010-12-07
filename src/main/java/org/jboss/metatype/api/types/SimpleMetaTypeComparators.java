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

package org.jboss.metatype.api.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Date;

/**
 * Comparator helper for simple meta types
 *
 * @author Emanuel Muckenhuber
 */
class SimpleMetaTypeComparators {

    final static Comparator<BigDecimal> BIG_DECIMAL = new Comparator<BigDecimal>() {
        public int compare(BigDecimal o1, BigDecimal o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<BigInteger> BIG_INTEGER = new Comparator<BigInteger>() {
        public int compare(BigInteger o1, BigInteger o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Boolean> BOOLEAN = new Comparator<Boolean>() {
        public int compare(Boolean o1, Boolean o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Byte> BYTE = new Comparator<Byte>() {
        public int compare(Byte o1, Byte o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Character> CHARACTER = new Comparator<Character>() {
        public int compare(Character o1, Character o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Date> DATE = new Comparator<Date>() {
        public int compare(Date o1, Date o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Double> DOUBLE = new Comparator<Double>() {
        public int compare(Double o1, Double o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Float> FLOAT = new Comparator<Float>() {
        public int compare(Float o1, Float o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Integer> INTEGER = new Comparator<Integer>() {
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Long> LONG = new Comparator<Long>() {
        public int compare(Long o1, Long o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<Short> SHORT = new Comparator<Short>() {
        public int compare(Short o1, Short o2) {
            return o1.compareTo(o2);
        }
    };
    final static Comparator<String> STRING = new Comparator<String>() {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };
}
