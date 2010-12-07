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

package org.jboss.metatype.api.values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamField;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jboss.metatype.api.types.CompositeMetaType;
import org.jboss.metatype.api.types.ImmutableCompositeMetaType;
import org.jboss.metatype.api.types.MetaType;

/**
 * A {@link CompositeValue} representing a
 * <code>Map&lt;String,MetaValue&gt;</code>, where the set of allowed keys and
 * their corresponding values' MetaTypes are specified by the associated
 * {@link ImmutableCompositeMetaType} or {@link MutableCompositeMetaType}.
 *
 * To represent a <code>Map&lt;String,MetaValue&gt;</code> where values must all
 * have the same MetaType, use {@link MapCompositeValueSupport} instead.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class CompositeValueSupport extends AbstractMetaValue implements CompositeValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = 6262188760975631870L;

    /** The serialized form */
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] {
            new ObjectStreamField("contents", SortedMap.class),
            new ObjectStreamField("metaType", CompositeMetaType.class)
            };

    /** The contents */
    private SortedMap<String, MetaValue> contents;

    /** The composite type */
    private CompositeMetaType metaType;

    /** cached hashCode */
    private transient int cachedHashCode = Integer.MIN_VALUE;

    /**
     * Construct Composite Value
     *
     * @param metaType the composite meta type of the data
     * @param itemNames the names of the values
     * @param itemValues the values
     * @throws IllegalArgumentException for a null metaType
     */
    public CompositeValueSupport(CompositeMetaType metaType, String[] itemNames, MetaValue[] itemValues) {
        if (metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if (itemNames == null) {
            itemNames = new String[0];
        }
        if (itemValues == null) {
            itemValues = new MetaValue[0];
        }
        if (itemNames.length != itemValues.length) {
            throw new IllegalArgumentException("for " + this.metaType
                    + ", itemNames has size " + itemNames.length
                    + ", but itemValues has size " + itemValues.length);
        }
        Set<String> compositeNames = metaType.itemSet();
        int compositeNameSize = compositeNames.size();
        if (itemNames.length > compositeNameSize) {
            throw new IllegalArgumentException("for " + this.metaType
                    + ", itemNames has size " + itemNames.length
                    + ", but composite type names has size "
                    + compositeNameSize);
        }
        this.metaType = metaType;
        contents = new TreeMap<String, MetaValue>();

        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i] == null || itemNames[i].length() == 0) {
                throw new IllegalArgumentException("for " + this.metaType
                        + ", item name " + i + " is null or empty");
            }
            if (contents.get(itemNames[i]) != null) {
                throw new IllegalArgumentException("for " + this.metaType
                        + ", duplicate item name: " + itemNames[i]);
            }
            MetaType itemType = metaType.getType(itemNames[i]);
            if (itemType == null) {
                throw new IllegalArgumentException("for " + this.metaType
                        + ", item name not in composite type: " + itemNames[i]);
            }
            if (itemValues[i] != null && itemType.isValue(itemValues[i]) == false) {
                throw new IllegalArgumentException("for " + this.metaType
                        + ", item value " + itemValues[i] + " for item name "
                        + itemNames[i] + " is not a " + itemType);
            }
            contents.put(itemNames[i], itemValues[i]);
        }

        if (itemNames.length < compositeNameSize) {
            List<String> itemList = Arrays.asList(itemNames);
            for (String name : compositeNames) {
                if (itemList.contains(name) == false) {
                    contents.put(name, null);
                }
            }
        }
    }

    /**
     * Construct Composite Value
     *
     * @param metaType the composite meta type of the data
     * @throws IllegalArgumentException for a null metaType
     */
    public CompositeValueSupport(CompositeMetaType metaType) {
        this(metaType, null, null);
    }

    /**
     * Construct Composite Value
     *
     * @param compositeMetaType the composite type of the data
     * @param items map of strings to values
     * @throws IllegalArgumentException for a null metaType
     */
    public CompositeValueSupport(CompositeMetaType compositeMetaType, Map<String, MetaValue> items) {
        init(compositeMetaType, items);
    }

    /**
     * Get the value's type - either a {@link ImmutableCompositeMetaType} or a
     * {@link MutableCompositeMetaType}.
     *
     * @return the value's type - either a {@link ImmutableCompositeMetaType} or
     *         a {@link MutableCompositeMetaType}
     */
    public CompositeMetaType getMetaType() {
        return metaType;
    }

    public MetaValue get(String key) {
        validateKey(key);
        return contents.get(key);
    }

    /**
     * Set an item value
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void set(String key, MetaValue value) {
        MetaType itemType = validateKey(key);
        if (value != null && itemType.isValue(value) == false) {
            throw new IllegalArgumentException("item value " + value
                    + " for item name " + key + " is not a " + itemType);
        }
        contents.put(key, value);
    }

    public MetaValue[] getAll(String[] keys) {
        if (keys == null) {
            throw new IllegalArgumentException("Null keys");
        }
        MetaValue[] result = new MetaValue[keys.length];
        for (int i = 0; i < keys.length; i++) {
            validateKey(keys[i]);
            result[i] = contents.get(keys[i]);
        }
        return result;
    }

    public boolean containsKey(String key) {
        if (key == null || key.length() == 0) {
            return false;
        }
        return contents.containsKey(key);
    }

    public boolean containsValue(MetaValue value) {
        return contents.containsValue(value);
    }

    public Collection<MetaValue> values() {
        return Collections.unmodifiableCollection(contents.values());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj instanceof CompositeValue == false) {
            return false;
        }
        CompositeValue other = (CompositeValue) obj;
        if (getMetaType().equals(other.getMetaType()) == false) {
            return false;
        }
        for (String key : getMetaType().keySet()) {
            Object thisValue = this.get(key);
            Object otherValue = other.get(key);

            if ((thisValue == null && otherValue == null || thisValue != null && thisValue.equals(otherValue)) == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (cachedHashCode != Integer.MIN_VALUE) {
            return cachedHashCode;
        }
        cachedHashCode = getMetaType().hashCode();
        for (String key : getMetaType().keySet()) {
            Object value = contents.get(key);
            if (value != null) {
                cachedHashCode += value.hashCode();
            }
        }

        return cachedHashCode;
    }

    @Override
    public String toString() {
        CompositeMetaType metaType = getMetaType();
        StringBuilder buffer = new StringBuilder(getClass().getSimpleName());
        buffer.append(": metaType=[");
        buffer.append(metaType);
        buffer.append("] items=[");
        Iterator<String> keys = metaType.keySet().iterator();
        while (keys.hasNext()) {
            Object key = keys.next();
            buffer.append(key).append("=");
            Object value = contents.get(key);
            buffer.append(value);
            if (keys.hasNext()) {
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    /**
     * Validates the key against the composite type
     *
     * @param key the key to check
     * @return the meta type
     * @throws IllegalArgumentException for a null or empty key or when the key not a valid item name
     *         for the composite type
     */
    private MetaType validateKey(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("null or empty key");
        }
        CompositeMetaType metaType = getMetaType();
        MetaType result = metaType.getType(key);
        if (result == null) {
            throw new IllegalArgumentException("no such item name " + key
                    + " for composite type " + metaType);
        }
        return result;
    }

    /**
     * Construct Composite Value
     *
     * @param metaType the composite type of the data
     * @param items map of strings to values
     * @throws IllegalArgumentException for a metaType
     */
    private void init(CompositeMetaType metaType, Map<String, MetaValue> items) {
        if (metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if (items == null) {
            items = Collections.emptyMap();
        }
        Set<String> compositeNames = metaType.itemSet();
        int compositeNameSize = compositeNames.size();
        if (items.size() > compositeNameSize) {
            throw new IllegalArgumentException("items has size " + items.size()
                    + " but composite type has size " + compositeNameSize);
        }
        this.metaType = metaType;
        contents = new TreeMap<String, MetaValue>();

        for (Entry<String, MetaValue> entry : items.entrySet()) {
            String key = entry.getKey();
            if (key == null || key.length() == 0) {
                throw new IllegalArgumentException("Key is null or empty");
            }
            MetaType itemType = metaType.getType(key);
            if (itemType == null) {
                throw new IllegalArgumentException("item name not in composite type " + key);
            }
            MetaValue value = items.get(key);
            if (value != null && itemType.isValue(value) == false) {
                throw new IllegalArgumentException("item value " + value
                        + " for item name " + key + " is not a " + itemType);
            }
            contents.put(key, value);
        }

        if (items.size() < compositeNameSize) {
            for (String name : compositeNames) {
                if (items.containsKey(name) == false) {
                    contents.put(name, null);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField getField = in.readFields();
        SortedMap contents = (SortedMap) getField.get("contents", null);
        CompositeMetaType compositeType = (CompositeMetaType) getField.get("metaType", null);
        try {
            init(compositeType, contents);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing composite value", e);
        }
    }
}
