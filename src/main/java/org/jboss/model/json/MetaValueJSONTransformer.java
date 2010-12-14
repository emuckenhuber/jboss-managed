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

package org.jboss.model.json;

import java.lang.reflect.Array;

import org.jboss.model.types.ArrayMetaType;
import org.jboss.model.types.CollectionMetaType;
import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.MetaType;
import org.jboss.model.types.TableMetaType;
import org.jboss.model.values.ArrayValue;
import org.jboss.model.values.CollectionValue;
import org.jboss.model.values.CompositeMapValue;
import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.EnumValue;
import org.jboss.model.values.MetaValue;
import org.jboss.model.values.MetaValueTransformer;
import org.jboss.model.values.SimpleValue;
import org.jboss.model.values.TableValue;

/**
 * {@code MetaValue} to JSON transformer.
 *
 * @author Emanuel Muckenhuber
 */
public class MetaValueJSONTransformer implements MetaValueTransformer<MetaValue, Object> {

    private static final MetaValueJSONTransformer INSTANCE = new MetaValueJSONTransformer();

    public static MetaValueJSONTransformer getInstance() {
        return INSTANCE;
    }

    private MetaValueJSONTransformer() {
        //
    }

    public Object transform(final MetaValue value, final MetaType metaType) throws JSONException {
        if(metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if(value == null) {
            return null;
        }
        if(metaType.isSimple()) {
            return value.as(SimpleValue.class).getValue();
        } else if (metaType.isEnum()){
            return value.as(EnumValue.class).getValue();
        } else if (metaType.isArray()) {
            return createArray(value.as(ArrayValue.class), metaType.as(ArrayMetaType.class));
        } else if (metaType.isCollection()) {
            return createCollection(value.as(CollectionValue.class), metaType.as(CollectionMetaType.class));
        } else if (metaType.isComposite()) {
            return createComposite(value.as(CompositeValue.class), metaType.as(CompositeMetaType.class));
        } else if (metaType.isTable()) {
            return createTable(value.as(TableValue.class), metaType.as(TableMetaType.class));
        } else {
            throw new IllegalArgumentException("unknown meta type " + metaType);
        }
    }

    JSONArray createArray(final ArrayValue value, final ArrayMetaType metaType) throws JSONException {
        final JSONArray array = new JSONArray();
        final MetaType elementType = metaType.getElementType();
        final int length = value.getLength();
        final int dimension = metaType.getDimension();
        if(dimension > 1) {
            for(int i = 0; i < length; i++) {
                final Object subElement = value.getValue(i);
                if(subElement != null) {
                    final JSONArray subArray = createArray(subElement, elementType);
                    array.put(subArray);
                }
            }
        } else {
            for(int i = 0; i < length; i++) {
                final MetaValue itemValue = MetaValue.class.cast((value.getValue(i)));
                array.put(transform(itemValue, elementType));
            }
        }
        return array;
    }

    JSONArray createArray(Object value, final MetaType elementType) throws JSONException{
        final JSONArray array = new JSONArray();
        int subSize = Array.getLength(value);
        for(int i = 0; i < subSize; i++) {
           Object subElement = Array.get(value, i);
           if (subElement instanceof MetaValue) {
              subElement = transform((MetaValue)subElement, elementType);
           } else if (subElement != null && subElement.getClass().isArray()) {
              subElement = createArray(subElement, elementType);
           }
           array.put(subElement);
        }
        return array;
    }

    JSONArray createCollection(final CollectionValue value, final CollectionMetaType metaType) throws JSONException {
        final JSONArray array = new JSONArray();
        final MetaType elementType = metaType.getElementType();
        for(final MetaValue itemValue : value) {
            array.put(transform(itemValue, elementType));
        }
        return array;
    }

    JSONObject createComposite(final CompositeValue value, final CompositeMetaType metaType) throws JSONException {
        final JSONObject json = new JSONObject();
        for(final String itemName : metaType.itemSet()) {
            final MetaType itemType = metaType.getType(itemName);
            final MetaValue itemValue = value.get(itemName);
            json.put(itemName, transform(itemValue, itemType));
        }
        return json;
    }

    JSONArray createCompositeMap(final CompositeMapValue composite, final CompositeMapMetaType metaType) throws JSONException {
        final JSONArray array = new JSONArray();
        final CompositeMetaType entryType = metaType.getEntryType();
        for(final CompositeValue entry : composite) {
            array.put(createComposite(entry, entryType));
        }
        return array;
    }

    JSONArray createTable(final TableValue table, final TableMetaType metaType) throws JSONException {
        final JSONArray array = new JSONArray();
        final CompositeMetaType rowType = metaType.getRowType();
        for(final CompositeValue row : table) {
            array.put(createComposite(row, rowType));
        }
        return array;
    }

}
