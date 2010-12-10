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

import org.jboss.model.types.EnumMetaType;

/**
 * EnumValue.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Emanuel Muckenhuber
 */
public class EnumValueSupport extends AbstractMetaValue implements EnumValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = -6472212391813711803L;

    /** The enum meta type */
    private EnumMetaType metaType;

    /** The value */
    private String value;

    /**
     * Create a new EnumValueSupport.
     *
     * @param metaType the enum meta type
     * @param value the value
     * @throws IllegalArgumentException for a null enum MetaType
     */
    public EnumValueSupport(EnumMetaType metaType, String value) {
        if (metaType == null) {
            throw new IllegalArgumentException("Null enum meta type");
        }
        this.metaType = metaType;
        this.value = value;
    }

    /**
     * Create a new EnumValueSupport from an Enum value.
     *
     * @param metaType the enum meta type
     * @param value the enum instance
     * @throws IllegalArgumentException for a null enum MetaType
     */
    public EnumValueSupport(EnumMetaType metaType, Enum<?> value) {
        this(metaType, value.name());
    }

    public EnumMetaType getMetaType() {
        return metaType;
    }

    /**
     * Get the value.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the value as <code>enum</code>.
     *
     * @param <E> the <code>enum</code> type
     * @param enumType
     * @return
     */
    public <E extends Enum<E>> E getValue(Class<E> enumType) {
        return Enum.valueOf(enumType, value);
    }

    /**
     * Set the value.
     *
     * @param value the value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj instanceof EnumValue == false) {
            return false;
        }
        EnumValue other = (EnumValue) obj;
        if (metaType.equals(other.getMetaType()) == false) {
            return false;
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
}
