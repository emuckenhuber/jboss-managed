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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jboss.metatype.api.types.CollectionMetaType;
import org.jboss.metatype.api.types.MetaType;

/**
 * CollectionValueSupport.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @autor Emanuel Muckenhuber
 */
public class CollectionValueSupport extends AbstractMetaValue implements CollectionValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = 1131827130033538066L;

    /** The collection meta type */
    private CollectionMetaType metaType;

    /** The delegate collection. */
    private final Collection<MetaValue> delegate;

    /**
     * Create a new CollectionValueSupport.
     *
     * @param metaType the collection meta type
     * @throws IllegalArgumentException for a null array MetaType
     */
    public CollectionValueSupport(CollectionMetaType metaType) {
        this(metaType, new ArrayList<MetaValue>());
    }

    /**
     * Create a new CollectionValueSupport.
     *
     * @param metaType the collection meta type
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException for a null array MetaType
     */
    public CollectionValueSupport(CollectionMetaType metaType, int initialCapacity) {
        this(metaType, new ArrayList<MetaValue>(initialCapacity));
    }

    /**
     * Create a new ArrayValueSupport.
     *
     * @param metaType the collection meta type
     * @param delegate a delegate collection
     * @throws IllegalArgumentException for a null array MetaType
     */
    public CollectionValueSupport(CollectionMetaType metaType, Collection<MetaValue> delegate) {
        if (metaType == null) {
            throw new IllegalArgumentException("Null collection meta type");
        }
        if(delegate == null) {
            throw new IllegalArgumentException("Null collection");
        }
        this.metaType = metaType;
        this.delegate = delegate;
    }

    public CollectionMetaType getMetaType() {
        return metaType;
    }

    public boolean add(MetaValue e) {
        if(e == null) {
            throw new IllegalArgumentException("null value");
        }
        final MetaType elementType = metaType.getElementType();
        if(! elementType.isValue(e)) {
            throw new IllegalArgumentException("value " + e + " is not a " + elementType);
        }
        return delegate.add(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj instanceof CollectionValueSupport == false) {
            return false;
        }
        CollectionValueSupport other = (CollectionValueSupport) obj;
        if (metaType.equals(other.getMetaType()) == false) {
            return false;
        }
        return delegate.equals(other.delegate);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * metaType.hashCode();
        result += 31 * delegate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return metaType + ": " + delegate;
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    public Iterator<MetaValue> iterator() {
        return delegate.iterator();
    }

    public Object[] toArray() {
        return delegate.toArray();
    }

    public <T> T[] toArray(T[] a) {
        // TODO this might should return an array metaType?
        return delegate.toArray(a);
    }

    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    public boolean addAll(Collection<? extends MetaValue> c) {
        return delegate.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    public void clear() {
        delegate.clear();
    }

}
