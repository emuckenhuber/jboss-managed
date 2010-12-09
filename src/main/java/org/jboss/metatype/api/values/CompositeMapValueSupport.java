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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jboss.metatype.api.types.CompositeMapMetaType;
import org.jboss.metatype.api.types.CompositeMetaType;
import org.jboss.metatype.api.types.MetaType;

/**
 * @author Emanuel Muckenhuber
 */
public class CompositeMapValueSupport extends AbstractMetaValue implements CompositeMapValue {

    private static final long serialVersionUID = 3928342349629922682L;

    private final CompositeMapMetaType metaType;
    private final Map<MetaValue, CompositeValue> delegate;

    public CompositeMapValueSupport(final CompositeMapMetaType metaType) {
        this(metaType, new HashMap<MetaValue, CompositeValue>());
    }

    public CompositeMapValueSupport(final CompositeMapMetaType metaType, Map<MetaValue, CompositeValue> delegate) {
        if(metaType == null) {
            throw new IllegalArgumentException("null meta type");
        }
        if(delegate == null) {
            throw new IllegalArgumentException("null delegate map");
        }
        this.metaType = metaType;
        this.delegate = delegate;
    }

    public CompositeMapMetaType getMetaType() {
        return metaType;
    }

    public CompositeMetaType getEntryType() {
        return metaType.getEntryType();
    }

    public CompositeValue put(CompositeValue value) {
        if(value == null) {
            throw new IllegalArgumentException("null value");
        }
        final String indexName = metaType.getIndexName();
        return put(value.get(indexName), value);
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

    public CompositeValue get(Object key) {
        return delegate.get(key);
    }

    public CompositeValue put(MetaValue key, CompositeValue value) {
        if(key == null) {
            throw new IllegalArgumentException("null key");
        }
        final MetaType keyType = metaType.getIndexType();
        if(!keyType.isValue(key)) {
            throw new IllegalArgumentException(String.format("key (%s) is not of type %s", key, keyType));
        }
        final CompositeMetaType valueType = metaType.getEntryType();
        if(value != null && ! valueType.isValue(value)) {
            throw new IllegalArgumentException(String.format("value (%s) is not of type %s", value, valueType));
        }
        return delegate.put(key, value);
    }

    public CompositeValue remove(Object key) {
        return delegate.remove(key);
    }

    public void putAll(Map<? extends MetaValue, ? extends CompositeValue> m) {
        delegate.putAll(m);
    }

    public void clear() {
        delegate.clear();
    }

    public Set<MetaValue> keySet() {
        return delegate.keySet();
    }

    public Collection<CompositeValue> values() {
        return delegate.values();
    }

    public Set<java.util.Map.Entry<MetaValue, CompositeValue>> entrySet() {
        return delegate.entrySet();
    }

}
