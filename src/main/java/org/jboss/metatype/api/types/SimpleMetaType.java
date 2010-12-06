/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Date;

import org.jboss.metatype.api.values.SimpleValue;

/**
 * SimpleMetaType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @author Emanuel Muckenhuber
 */
public class SimpleMetaType extends AbstractMetaType {

    /** The serialVersionUID */
    private static final long serialVersionUID = 6786422588217893696L;

    /** Cached hash code */
    private transient int cachedHashCode;

    /** Cached string representation */
    private transient String cachedToString;

    /** The simple type for java.math.BigDecimal */
    public static final SimpleMetaType BIGDECIMAL;

    /** The simple type for java.math.BigInteger */
    public static final SimpleMetaType BIGINTEGER;

    /** The simple type for java.lang.Boolean */
    public static final SimpleMetaType BOOLEAN;
    public static final SimpleMetaType BOOLEAN_PRIMITIVE;

    /** The simple type for java.lang.Byte */
    public static final SimpleMetaType BYTE;
    public static final SimpleMetaType BYTE_PRIMITIVE;

    /** The simple type for java.lang.Character */
    public static final SimpleMetaType CHARACTER;
    public static final SimpleMetaType CHARACTER_PRIMITIVE;

    /** The simple type for java.lang.Date */
    public static final SimpleMetaType DATE;

    /** The simple type for java.lang.Double */
    public static final SimpleMetaType DOUBLE;
    public static final SimpleMetaType DOUBLE_PRIMITIVE;

    /** The simple type for java.lang.Float */
    public static final SimpleMetaType FLOAT;
    public static final SimpleMetaType FLOAT_PRIMITIVE;

    /** The simple type for java.lang.Integer */
    public static final SimpleMetaType INTEGER;
    public static final SimpleMetaType INTEGER_PRIMITIVE;

    /** The simple type for java.lang.Long */
    public static final SimpleMetaType LONG;
    public static final SimpleMetaType LONG_PRIMITIVE;

    /** The simple type for java.lang.Short */
    public static final SimpleMetaType SHORT;
    public static final SimpleMetaType SHORT_PRIMITIVE;

    /** The simple type for java.lang.String */
    public static final SimpleMetaType STRING;

    /** The simple type for an object name */
    public static final SimpleMetaType NAMEDOBJECT;

    /** The simple type for java.lang.Void */
    public static final SimpleMetaType VOID;

    /** The comparator */
    @SuppressWarnings("rawtypes")
    private transient Comparator comparator;

    /** */
    private transient char primitiveType;

    static {
        BIGDECIMAL = new SimpleMetaType(BigDecimal.class, SimpleMetaTypeComparators.BIG_DECIMAL);
        BIGINTEGER = new SimpleMetaType(BigInteger.class, SimpleMetaTypeComparators.BIG_INTEGER);
        BOOLEAN = new SimpleMetaType(Boolean.class, SimpleMetaTypeComparators.BOOLEAN, 'Z');
        BOOLEAN_PRIMITIVE = new SimpleMetaType(boolean.class, SimpleMetaTypeComparators.BOOLEAN, 'Z');
        BYTE = new SimpleMetaType(Byte.class, SimpleMetaTypeComparators.BYTE, 'B');
        BYTE_PRIMITIVE = new SimpleMetaType(byte.class, SimpleMetaTypeComparators.BYTE, 'B');
        CHARACTER = new SimpleMetaType(Character.class, SimpleMetaTypeComparators.CHARACTER, 'C');
        CHARACTER_PRIMITIVE = new SimpleMetaType(char.class, SimpleMetaTypeComparators.CHARACTER, 'C');
        DATE = new SimpleMetaType(Date.class, SimpleMetaTypeComparators.DATE);
        DOUBLE = new SimpleMetaType(Double.class, SimpleMetaTypeComparators.DOUBLE, 'D');
        DOUBLE_PRIMITIVE = new SimpleMetaType(double.class, SimpleMetaTypeComparators.DOUBLE, 'D');
        FLOAT = new SimpleMetaType(Float.class, SimpleMetaTypeComparators.FLOAT, 'F');
        FLOAT_PRIMITIVE = new SimpleMetaType(float.class, SimpleMetaTypeComparators.FLOAT, 'F');
        INTEGER = new SimpleMetaType(Integer.class, SimpleMetaTypeComparators.INTEGER, 'I');
        INTEGER_PRIMITIVE = new SimpleMetaType(int.class, SimpleMetaTypeComparators.INTEGER, 'I');
        LONG = new SimpleMetaType(Long.class, SimpleMetaTypeComparators.LONG, 'J');
        LONG_PRIMITIVE = new SimpleMetaType(long.class, SimpleMetaTypeComparators.LONG, 'J');
        SHORT = new SimpleMetaType(Short.class, SimpleMetaTypeComparators.SHORT, 'S');
        SHORT_PRIMITIVE = new SimpleMetaType(short.class, SimpleMetaTypeComparators.SHORT, 'S');
        STRING = new SimpleMetaType(String.class, SimpleMetaTypeComparators.STRING);
        NAMEDOBJECT = new SimpleMetaType(Name.class, SimpleMetaTypeComparators.NAME);
        VOID = new SimpleMetaType(Void.class, null);
    }

    /**
     * Resolve a simple type
     *
     * @param className the class name of the simple type
     * @return the simple type
     * @throws IllegalArgumentException for a null className or if it is not a simple type
     */
    public static SimpleMetaType resolve(String className) {
        SimpleMetaType result = isSimpleType(className);
        if (result != null) {
            return result;
        }
        throw new IllegalArgumentException("Class is not a simple type: " + className);
    }

    /**
     * Return the simple type if the class name is a simple type otherwise null.
     *
     * @param className the class name of the simple type
     * @return the simple type
     * @throws IllegalArgumentException for a null className
     */
    public static SimpleMetaType isSimpleType(String className) {
        if (className == null) {
            throw new IllegalArgumentException("Null class name");
        }
        if (className.equals(STRING.getClassName())) {
            return STRING;
        }
        if (className.equals(INTEGER.getClassName())) {
            return INTEGER;
        }
        if (className.equals(Integer.TYPE.getName())) {
            return INTEGER_PRIMITIVE;
        }
        if (className.equals(BOOLEAN.getClassName())) {
            return BOOLEAN;
        }
        if (className.equals(Boolean.TYPE.getName())) {
            return BOOLEAN_PRIMITIVE;
        }
        if (className.equals(LONG.getClassName())) {
            return LONG;
        }
        if (className.equals(Long.TYPE.getName())) {
            return LONG_PRIMITIVE;
        }
        if (className.equals(BYTE.getClassName())) {
            return BYTE;
        }
        if (className.equals(Byte.TYPE.getName())) {
            return BYTE_PRIMITIVE;
        }
        if (className.equals(CHARACTER.getClassName())) {
            return CHARACTER;
        }
        if (className.equals(Character.TYPE.getName())) {
            return CHARACTER_PRIMITIVE;
        }
        if (className.equals(DOUBLE.getClassName())) {
            return DOUBLE;
        }
        if (className.equals(Double.TYPE.getName())) {
            return DOUBLE_PRIMITIVE;
        }
        if (className.equals(FLOAT.getClassName())) {
            return FLOAT;
        }
        if (className.equals(Float.TYPE.getName())) {
            return FLOAT_PRIMITIVE;
        }
        if (className.equals(SHORT.getClassName())) {
            return SHORT;
        }
        if (className.equals(Short.TYPE.getName())) {
            return SHORT_PRIMITIVE;
        }
        if (className.equals(BIGDECIMAL.getClassName())) {
            return BIGDECIMAL;
        }
        if (className.equals(BIGINTEGER.getClassName())) {
            return BIGINTEGER;
        }
        if (className.equals(VOID.getClassName()) || className.equals(Void.TYPE.getName())) {
            return VOID;
        }
        if (className.equals(DATE.getClassName())) {
            return DATE;
        }
        if (className.equals(NAMEDOBJECT.getClassName())) {
            return NAMEDOBJECT;
        }
        return null;
    }

    /**
     * Construct an SimpleMetaType.
     * <p>
     * This constructor is used to construct the static simple meta types.
     *
     * @param className the name of the class implementing the type
     */
    private SimpleMetaType(String className) {
        super(className);
        cachedHashCode = getClassName().hashCode();
        StringBuilder buffer = new StringBuilder(SimpleMetaType.class.getSimpleName());
        buffer.append(":");
        buffer.append(getClassName());
        cachedToString = buffer.toString();
    }

    /**
     * Construct a simple meta type.
     *
     * @param clazz the class
     * @param comparator class's comparator
     */
    private <T> SimpleMetaType(Class<T> clazz, Comparator<T> comparator) {
        this(clazz, comparator, '\0');
    }

    private <T> SimpleMetaType(Class<T> clazz, Comparator<T> comparator, char primitiveType) {
        this(clazz.getName());
        this.comparator = comparator;
        this.primitiveType = primitiveType;
    }

    /**
     * Compare objects.
     *
     * @param first
     *            the first object
     * @param second
     *            the second object
     * @return compare result
     */
    @SuppressWarnings("unchecked")
    public int compare(Object first, Object second) {
        if (comparator == null) {
            return 0;
        }
        if (first == null || second == null) {
            throw new IllegalArgumentException("Null objects to compare.");
        }
        return comparator.compare(first, second);
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public boolean isPrimitive() {
        return primitiveType != '\0' && this.getTypeName().startsWith("java.lang") == false;
    }

    @Override
    public boolean isValue(Object obj) {
        if (obj == null || obj instanceof SimpleValue == false) {
            return false;
        }
        SimpleValue value = (SimpleValue) obj;
        return equals(value.getMetaType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj instanceof SimpleMetaType == false) {
            return false;
        }
        SimpleMetaType other = (SimpleMetaType) obj;
        return getClassName().equals(other.getClassName());
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    @Override
    public String toString() {
        return cachedToString;
    }

    /**
     *
     * @return
     */
    public boolean equalsIgnorePrimitive(Object obj) {
        if (obj == null || obj instanceof SimpleMetaType == false) {
            return false;
        }
        SimpleMetaType other = (SimpleMetaType) obj;
        return this.primitiveType == other.primitiveType;
    }

    /**
     * Resolve to the singletons
     *
     * @return the singletons
     * @throws ObjectStreamException for a corrupted stream
     */
    private Object readResolve() throws ObjectStreamException {
        return resolve(getClassName());
    }
}
