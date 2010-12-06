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
package org.jboss.metatype.api.values;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jboss.metatype.api.types.Name;
import org.jboss.metatype.api.types.SimpleMetaType;

/**
 * SimpleValue.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class SimpleValueSupport extends AbstractMetaValue implements SimpleValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = 8473043036261557127L;

    /** The simple meta type */
    private SimpleMetaType metaType;

    /** The value */
    private Serializable value;

    /**
     * Wrap a simple object in simple value
     *
     * @param object
     *            the simple object
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
        int compare = -1;
        if (getMetaType() == SimpleMetaType.BIGINTEGER && sv.getMetaType() == SimpleMetaType.BIGINTEGER) {
            BigInteger v1 = BigInteger.class.cast(value);
            BigInteger v2 = BigInteger.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.BIGDECIMAL && sv.getMetaType() == SimpleMetaType.BIGDECIMAL) {
            BigDecimal v1 = BigDecimal.class.cast(value);
            BigDecimal v2 = BigDecimal.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.BOOLEAN && sv.getMetaType() == SimpleMetaType.BOOLEAN) {
            Boolean v1 = Boolean.class.cast(value);
            Boolean v2 = Boolean.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.BOOLEAN_PRIMITIVE && sv.getMetaType() == SimpleMetaType.BOOLEAN_PRIMITIVE) {
            Boolean v1 = Boolean.class.cast(value);
            Boolean v2 = Boolean.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.BYTE && sv.getMetaType() == SimpleMetaType.BYTE) {
            Byte v1 = Byte.class.cast(value);
            Byte v2 = Byte.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.CHARACTER && sv.getMetaType() == SimpleMetaType.CHARACTER) {
            Character v1 = Character.class.cast(value);
            Character v2 = Character.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.CHARACTER_PRIMITIVE && sv.getMetaType() == SimpleMetaType.CHARACTER_PRIMITIVE) {
            Character v1 = Character.class.cast(value);
            Character v2 = Character.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.DATE && sv.getMetaType() == SimpleMetaType.DATE) {
            Date v1 = Date.class.cast(value);
            Date v2 = Date.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.DOUBLE && sv.getMetaType() == SimpleMetaType.DOUBLE) {
            Double v1 = Double.class.cast(value);
            Double v2 = Double.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.FLOAT && sv.getMetaType() == SimpleMetaType.FLOAT) {
            Float v1 = Float.class.cast(value);
            Float v2 = Float.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.INTEGER && sv.getMetaType() == SimpleMetaType.INTEGER) {
            Integer v1 = Integer.class.cast(value);
            Integer v2 = Integer.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.LONG && sv.getMetaType() == SimpleMetaType.LONG) {
            Long v1 = Long.class.cast(value);
            Long v2 = Long.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.SHORT && sv.getMetaType() == SimpleMetaType.SHORT) {
            Short v1 = Short.class.cast(value);
            Short v2 = Short.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.STRING && sv.getMetaType() == SimpleMetaType.STRING) {
            String v1 = String.class.cast(value);
            String v2 = String.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.NAMEDOBJECT && sv.getMetaType() == SimpleMetaType.NAMEDOBJECT) {
            Name v1 = Name.class.cast(value);
            Name v2 = Name.class.cast(sv.getValue());
            compare = v1.compareTo(v2);
        } else if (getMetaType() == SimpleMetaType.VOID && sv.getMetaType() == SimpleMetaType.VOID) {
            compare = 0;
        } else if (value instanceof Number && sv.getValue() instanceof Number) {
            // Compare two Numbers using the most precise type
            Number n0 = (Number) value;
            Number n1 = (Number) sv.getValue();
            if (n0 instanceof BigDecimal) {
                BigDecimal db0 = (BigDecimal) n0;
                BigDecimal db1 = new BigDecimal(n1.doubleValue());
                compare = db0.compareTo(db1);
            } else if (n1 instanceof BigDecimal) {
                BigDecimal db1 = (BigDecimal) n1;
                BigDecimal db0 = new BigDecimal(n0.doubleValue());
                compare = db0.compareTo(db1);
            } else if (n0 instanceof Float || n0 instanceof Double
                    || n1 instanceof Float || n1 instanceof Double) {
                double d0 = n0.doubleValue();
                double d1 = n1.doubleValue();
                compare = Double.compare(d0, d1);
            } else {
                long l0 = n0.longValue();
                long l1 = n1.longValue();
                if (l0 < l1) {
                    compare = -1;
                } else if (l0 > l1) {
                    compare = 1;
                } else {
                    compare = 0;
                }
            }
        }

        return compare;
    }

}
