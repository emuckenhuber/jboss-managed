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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jboss.model.types.MapMetaType;

/**
 * The MapValueSupport.
 *
 * @author Emanuel Muckenhuber
 */
public class MapValueSupport extends AbstractMetaValue implements MapValue {

    private static final long serialVersionUID = 2299701216433434296L;

    private final MapMetaType metaType;
    private final Map<MetaValue, MetaValue> delegate;

    public MapValueSupport(MapMetaType metaType) {
        this(metaType, new HashMap<MetaValue, MetaValue>());
    }

    public MapValueSupport(MapMetaType metaType, int initialCapacity) {
        this(metaType, new HashMap<MetaValue, MetaValue>(initialCapacity));
    }

    public MapValueSupport(MapMetaType metaType, int initialCapacity, float loadFactor) {
        this(metaType, new HashMap<MetaValue, MetaValue>(initialCapacity, loadFactor));
    }

    public MapValueSupport(final MapMetaType metaType, Map<MetaValue, MetaValue> delegate) {
        if(metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if(delegate == null) {
            throw new IllegalArgumentException("null delegate map");
        }
        this.metaType = metaType;
        this.delegate = delegate;
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    public MetaValue get(Object key) {
        return delegate.get(key);
    }

    public MetaValue put(MetaValue key, MetaValue value) {
        if(key == null) {
            throw new IllegalArgumentException("null key");
        }
        if(! metaType.getKeyType().isValue(key)) {
            throw new IllegalArgumentException("key is not of type " + metaType.getKeyType());
        }
        if(value != null && ! metaType.getValueType().isValue(value)) {
            throw new IllegalArgumentException("value is not of type " + metaType.getValueType());
        }
        return delegate.put(key, value);
    }

    public MetaValue remove(Object key) {
        return delegate.remove(key);
    }

    public void putAll(Map<? extends MetaValue, ? extends MetaValue> m) {
        delegate.putAll(m);
    }

    public void clear() {
        delegate.clear();
    }

    public Set<MetaValue> keySet() {
        return delegate.keySet();
    }

    public Collection<MetaValue> values() {
        return delegate.values();
    }

    public Set<java.util.Map.Entry<MetaValue, MetaValue>> entrySet() {
        return delegate.entrySet();
    }

    public MapMetaType getMetaType() {
        return metaType;
    }

}
