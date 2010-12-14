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

import java.util.ArrayList;
import java.util.List;

import org.jboss.model.types.ArrayMetaType;
import org.jboss.model.types.CollectionMetaType;
import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.EnumMetaType;
import org.jboss.model.types.MetaType;
import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.types.TableMetaType;
import org.jboss.model.values.ArrayValue;
import org.jboss.model.values.ArrayValueSupport;
import org.jboss.model.values.CollectionValue;
import org.jboss.model.values.CollectionValueSupport;
import org.jboss.model.values.CompositeMapValue;
import org.jboss.model.values.CompositeMapValueSupport;
import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.CompositeValueSupport;
import org.jboss.model.values.EnumValueSupport;
import org.jboss.model.values.MetaValue;
import org.jboss.model.values.MetaValueTransformer;
import org.jboss.model.values.SimpleValue;
import org.jboss.model.values.TableValue;
import org.jboss.model.values.TableValueSupport;

/**
 * Transformer creating a JSON representation of {@code MetaValue}s.
 *
 * @author Emanuel Muckenhuber
 */
public class JSONMetaValueTransformer implements MetaValueTransformer<Object, MetaValue> {

    private static final JSONMetaValueTransformer INSTANCE = new JSONMetaValueTransformer();

    public static JSONMetaValueTransformer getInstance() {
        return INSTANCE;
    }

    private JSONMetaValueTransformer() {
        //
    }

    public MetaValue transform(final Object value, final MetaType metaType) throws JSONException {
        if(metaType == null) {
            throw new IllegalArgumentException("null metaType");
        }
        if(metaType.isSimple()) {
            return SimpleValue.Factory.create(value, metaType.as(SimpleMetaType.class));
        } else if (metaType.isEnum()) {
            return new EnumValueSupport(metaType.as(EnumMetaType.class), (String) value);
        } else if (metaType.isArray()) {
            return createArray((JSONArray) value, metaType.as(ArrayMetaType.class));
        } else if (metaType.isCollection()) {
            return createCollection((JSONArray) value, metaType.as(CollectionMetaType.class));
        } else if (metaType.isComposite()) {
            return createComposite((JSONObject) value, metaType.as(CompositeMetaType.class));
        } else if (metaType.isTable()) {
            return createTable((JSONArray) value, metaType.as(TableMetaType.class));
        } else {
            throw new IllegalStateException("unknow meta type " + metaType);
        }
    }

    ArrayValue createArray(final JSONArray jsonArray, ArrayMetaType metaType) throws JSONException {
        final MetaType elementType = metaType.getElementType();
        final int dimension = metaType.getDimension();
        if(dimension > 1) {
            return new ArrayValueSupport(metaType, createArray(jsonArray, elementType, dimension));
        } else {
            final List<MetaValue> list = createList(jsonArray, elementType);
            return new ArrayValueSupport(metaType, list.toArray());
        }
    }

    Object createArray(final JSONArray jsonArray, final MetaType elementType, final int dimension) throws JSONException {
        if(dimension > 1) {
            final int length = jsonArray.length();
            final List<Object> list = new ArrayList<Object>(length);
            for(int i =0; i < length; i++) {
                list.add(createArray(jsonArray, elementType, dimension -1));
            }
            return list.toArray();
        } else {
            final List<MetaValue> list = createList(jsonArray, elementType);
            return list.toArray();
        }
    }

    CollectionValue createCollection(final JSONArray jsonArray, CollectionMetaType metaType) throws JSONException {
        final MetaType elementType = metaType.getElementType();
        final List<MetaValue> list = createList(jsonArray, elementType);
        return new CollectionValueSupport(metaType, list);
    }

    CompositeValue createComposite(final JSONObject json, final CompositeMetaType metaType) throws JSONException {
        final CompositeValue value = new CompositeValueSupport(metaType);
        for(final String itemName : metaType.itemSet()) {
            final MetaType itemType = metaType.getType(itemName);
            if(json.has(itemName)) {
                final Object o = json.get(itemName);
                final MetaValue itemValue = transform(o, itemType);
                if(itemValue != null) {
                    value.set(itemName, itemValue);
                }
            }
        }
        return value;
    }

    CompositeMapValue createCompositeMap(final JSONArray jsonArray, final CompositeMapMetaType metaType) throws JSONException {
        final int length = jsonArray.length();
        final CompositeMapValue composite = new CompositeMapValueSupport(metaType);
        for(int i = 0; i < length; i++) {
            final JSONObject element = jsonArray.getJSONObject(i);
            final CompositeValue value = createComposite(element, metaType.getEntryType());
            composite.put(value);
        }
        return composite;
    }

    TableValue createTable(final JSONArray jsonArray, final TableMetaType metaType) throws JSONException {
        final int length = jsonArray.length();
        final TableValue table = new TableValueSupport(metaType);
        for(int i = 0; i < length; i++) {
            final JSONObject element = jsonArray.getJSONObject(i);
            final CompositeValue value = createComposite(element, metaType.getRowType());
            table.put(value);
        }
        return table;
    }

    List<MetaValue> createList(final JSONArray jsonArray, final MetaType elementType) throws JSONException {
        final int length = jsonArray.length();
        final List<MetaValue> list = new ArrayList<MetaValue>();
        for(int i = 0; i < length; i++) {
            list.add(transform(jsonArray.get(i), elementType));
        }
        return list;
    }
}
