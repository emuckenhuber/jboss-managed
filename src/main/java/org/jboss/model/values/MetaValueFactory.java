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

import org.jboss.model.entity.ManagedResource;
import org.jboss.model.entity.info.ManagedResourceAttributeInfo;
import org.jboss.model.entity.info.ManagedResourceInfo;
import org.jboss.model.types.ArrayMetaType;
import org.jboss.model.types.CollectionMetaType;
import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.MapMetaType;
import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.types.TableMetaType;
import org.jboss.model.types.builders.MetaTypeFactory;

/**
 * @author Emanuel Muckenhuber
 */
public class MetaValueFactory extends SimpleValue.Factory {

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
     * Create a simple array value.
     *
     * @param array the array object
     * @param metaType the array meta type
     * @return the array value
     */
    public static ArrayValue createSimpleArray(final Object[] array, final SimpleMetaType metaType) {
        final int length = array.length;
        final MetaValue[] value = new MetaValue[length];
        for(int i = 0; i < length; i++) {
            value[i] = create(array[i], metaType);
        }
        return createArray(value, MetaTypeFactory.createArrayMetaType(metaType));
    }

    /**
     * Create an array value.
     *
     * @param value the {@code MetaValue] array value
     * @param metaType the array meta type
     * @return the array value
     */
    public static ArrayValue createArray(final Object value, final ArrayMetaType metaType) {
        return new ArrayValueSupport(metaType, value);
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
     * Create a map value.
     *
     * @param metaType the map meta type
     * @return the map value
     */
    public static MapValue create(final MapMetaType metaType) {
        return new MapValueSupport(metaType);
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

    /**
     * Create a composite value based on a {@code ManagedResource}.
     *
     * @param entity the model entity.
     * @return the composite value
     */
    public static CompositeValue createCompositeValue(final ManagedResource entity) {
        if(entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        final ManagedResourceInfo entityInfo = entity.getEntityInfo();
        final CompositeMetaType metaType = MetaTypeFactory.createCompositeType(entityInfo);
        final CompositeValue value = create(metaType);
        for(final ManagedResourceAttributeInfo attribute : entityInfo.getAttributes()) {
            final String attributeName = attribute.getName();
            final MetaValue attributeValue = entity.getAttribute(attributeName);
            if(attributeValue != null) {
                value.set(attributeName, attributeValue);
            }
        }
        return value;
    }

}
