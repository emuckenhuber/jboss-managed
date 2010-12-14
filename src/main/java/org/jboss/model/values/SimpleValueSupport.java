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
 * @author Emanuel Muckenhuber
 */
public final class SimpleValueSupport extends AbstractMetaValue implements SimpleValue, SimpleTypes {

    /** The serialVersionUID */
    private static final long serialVersionUID = 8473043036261557127L;

    /** The simple meta type */
    private SimpleMetaType metaType;

    /** The value */
    private Serializable value;

    /**
     * Wrap a simple object in simple value
     *
     * @param object the simple object
     * @return the simple value
     */
    public static SimpleValue wrap(Serializable object) {
        if (object == null) {
            return null;
        }
        String className = object.getClass().getName();
        return wrap(object, className);
    }

    private static SimpleValue wrap(Serializable object, String className) {
        SimpleMetaType metaType = SimpleMetaType.resolve(className);
        return new SimpleValueSupport(metaType, object);
    }

    public static SimpleValue wrap(boolean value) {
        return wrap(value, boolean.class.getName());
    }

    public static SimpleValue wrap(byte value) {
        return wrap(value, byte.class.getName());
    }

    public static SimpleValue wrap(char value) {
        return wrap(value, char.class.getName());
    }

    public static SimpleValue wrap(short value) {
        return wrap(value, short.class.getName());
    }

    public static SimpleValue wrap(int value) {
        return wrap(value, int.class.getName());
    }

    public static SimpleValue wrap(long value) {
        return wrap(value, long.class.getName());
    }

    public static SimpleValue wrap(float value) {
        return wrap(value, float.class.getName());
    }

    public static SimpleValue wrap(double value) {
        return wrap(value, double.class.getName());
    }

    public static SimpleValue create(final Object o, final SimpleMetaType metaType) {
        if(metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if(o == null) {
            return new SimpleValueSupport(metaType, null);
        }
        // First check if it's a simple type
        SimpleMetaType.resolve(o.getClass().getName());
        if(metaType == STRING) {
            return new SimpleValueSupport(metaType, asString(o));
        } else if (metaType == SHORT || metaType == SHORT_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).shortValue());
        } else if (metaType == INTEGER || metaType == INTEGER_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).intValue());
        } else if (metaType == LONG || metaType == LONG_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).longValue());
        } else if (metaType == DOUBLE || metaType == DOUBLE_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).doubleValue());
        } else if (metaType == FLOAT || metaType == FLOAT_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).floatValue());
        } else if (metaType == BYTE || metaType == BYTE_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType).byteValue());
        } else if (metaType == BOOLEAN || metaType == BOOLEAN_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asBoolean(o));
        } else if (metaType == CHARACTER || metaType == CHARACTER_PRIMITIVE) {
            return new SimpleValueSupport(metaType, asCharacter(o));
        } else if (metaType == BIGINTEGER) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType));
        } else if (metaType == BIGDECIMAL) {
            return new SimpleValueSupport(metaType, asNumber(o, metaType));
        } else {
            throw new IllegalArgumentException("object not a simple type " + o);
        }
    }

    /**
     * Create a new SimpleValueSupport.
     *
     * @param metaType the simple meta type
     * @param value the value
     * @throws IllegalArgumentException for a null simpleMetaType
     */
    public SimpleValueSupport(SimpleMetaType metaType, Serializable value) {
        if (metaType == null) {
            throw new IllegalArgumentException("Null simple meta type");
        }
        this.metaType = metaType;
        setValue(value);
    }

    public SimpleMetaType getMetaType() {
        return metaType;
    }

    /**
     * Get the value.
     *
     * @return the value.
     */
    public Serializable getValue() {
        return value;
    }

    /**
     * Set the value.
     *
     * @param value the value.
     */
    public void setValue(Serializable value) {
        this.value = value;
    }

    public String asString() {
        return asString(value);
    }

    public Integer asInteger() {
        if(value == null) {
            return null;
        }
       return asNumber().intValue();
    }

    public Float asFloat() {
        if(value == null) {
            return null;
        }
        return asNumber().floatValue();
    }

    public Short asShort() {
        if(value == null) {
            return null;
        }
        return asNumber().shortValue();
    }

    public Boolean asBoolean() {
        return asBoolean(value);
    }

    public Byte asByte() {
        if(value == null) {
            return null;
        }
        return asNumber().byteValue();
    }

    public Number asNumber() {
        return asNumber(value, metaType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj instanceof SimpleValue == false) {
            return false;
        }
        SimpleValue other = (SimpleValue) obj;
        if (metaType.equals(other.getMetaType()) == false) {
            // Check equivalent types
            if (metaType.equalsIgnorePrimitive(other.getMetaType()) == false) {
                return false;
            }
        }
        Object otherValue = other.getValue();
        if (value == null && otherValue == null) {
            return true;
        }
        if (value == null && otherValue != null) {
            return false;
        }
        return value.equals(otherValue);
    }

    @Override
    public int hashCode() {
        if (value == null) {
            return 0;
        }
        return value.hashCode();
    }

    @Override
    public String toString() {
        return metaType + ":" + value;
    }

    public int compareTo(SimpleValue sv) {
        if(metaType.isValue(sv)) {
            return metaType.compare(getValue(), sv.getValue());
        }
        return -1;
    }

    static String asString(final Object obj) {
        if(obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    static Number asNumber(final Object o, final SimpleMetaType metaType) {
        if(o == null) {
            return null;
        } else if(o instanceof Number) {
            return (Number) o;
        } else {
            return asNumber(asString(o), metaType);
        }
    }

    static Number asNumber(final String str, final SimpleMetaType metaType) {
        if(str == null) {
            return null;
        }
        if (metaType == SHORT || metaType == SHORT_PRIMITIVE) {
            return Short.parseShort(str);
        } else if (metaType == INTEGER || metaType == INTEGER_PRIMITIVE) {
            return Integer.parseInt(str);
        } else if (metaType == LONG || metaType == LONG_PRIMITIVE) {
            return Long.parseLong(str);
        } else if (metaType == DOUBLE || metaType == DOUBLE_PRIMITIVE) {
            return Double.parseDouble(str);
        } else if (metaType == BYTE || metaType == BYTE_PRIMITIVE) {
            return Byte.parseByte(str);
        } else if (metaType == FLOAT || metaType == FLOAT_PRIMITIVE) {
            return Float.parseFloat(str);
        } else if (metaType == BIGINTEGER) {
            return BigInteger.valueOf(asNumber(str, LONG).longValue());
        } else if (metaType == BIGDECIMAL) {
            return new BigDecimal(str);
        } else {
            throw new IllegalArgumentException("cannot convert (" + str + ") to " + metaType);
        }
    }

    static Character asCharacter(final Object o) {
        if(o == null) {
            return null;
        } else if (o instanceof Character) {
            return (Character) o;
        } else {
            return asCharacter(asString(o));
        }
    }

    static Character asCharacter(final String str) {
        if(str == null) {
            return null;
        }
        if(str.length() > 1) {
            throw new IllegalArgumentException("illegal character" + str);
        }
        return new Character(str.charAt(0));
    }

    static Boolean asBoolean(final Object o) {
        if(o == null) {
            return null;
        } else if (o instanceof Boolean) {
            return (Boolean) o;
        } else {
            return asBoolean(asString(o));
        }
    }

    static Boolean asBoolean(final String str) {
        if(str == null) {
            return null;
        }
        return Boolean.valueOf(str);
    }

}
