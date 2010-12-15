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

package org.jboss.model.types;

import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.MapValue;

/**
 * A basic Map<MetaValue, MetaValue> type. Also see {@link CompositeMapMetaType} in case the map
 * should contain a {@link CompositeValue} as value.
 *
 * @author Emanuel Muckenhuber
 */
public class MapMetaType extends AbstractMetaType {

    private static final long serialVersionUID = 4937011667344237556L;

    private final String[] itemNames = new String[] { "KEY", "VALUE" };
    private final String[] itemDescriptions = new String[] { "The Map key", "The Map value" };

    private final CompositeMetaType entryType;

    public MapMetaType(final MetaType keyType, final MetaType valueType) {
        super(MapValue.class.getName(), "");
        this.entryType = new ImmutableCompositeMetaType("", "", itemNames, itemDescriptions,
                new MetaType[] { keyType, valueType });
    }

    /**
     * @return the entryType
     */
    public CompositeMetaType getEntryType() {
        return entryType;
    }

    /**
     * @return the keyType
     */
    public MetaType getKeyType() {
        return entryType.getType("KEY");
    }

    /**
     * @return the valueType
     */
    public MetaType getValueType() {
        return entryType.getType("VALUE");
    }

    public boolean isValue(Object obj) {
        if(obj == null || (obj instanceof MapValue) == false) {
            return false;
        }
        final MapMetaType type = MapValue.class.cast(obj).getMetaType();
        return equals(type);
    }

    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || (obj instanceof MapMetaType) == false) {
            return false;
        }
        final MapMetaType other = MapMetaType.class.cast(obj);
        if (getTypeName().equals(other.getTypeName()) == false) {
            return false;
        }
        if(!getKeyType().equals(other.getKeyType())) {
            return false;
        }
        if(!getValueType().equals(other.getValueType())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 17;
        result += 31 * getKeyType().hashCode();
        result += 31 * getValueType().hashCode();
        return result;
    }

}
