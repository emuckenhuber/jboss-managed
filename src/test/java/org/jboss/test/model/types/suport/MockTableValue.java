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

package org.jboss.test.model.types.suport;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.TableMetaType;
import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.MetaValue;
import org.jboss.model.values.TableValue;

/**
 * MockTableValue.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class MockTableValue extends MockMetaValue implements TableValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new MockTableValue.
     *
     * @param metaType the meta type
     */
    public MockTableValue(TableMetaType metaType) {
        super(metaType);
    }

    public TableMetaType getMetaType() {
        return (TableMetaType) super.getMetaType();
    }

    public CompositeMetaType getRowType() {
        return getMetaType().getRowType();
    }

    public MetaValue[] calculateIndex(CompositeValue value) {
        throw new IllegalStateException("calculateIndex");
    }

    public void clear() {
        throw new IllegalStateException("clear");
    }

    public boolean containsKey(MetaValue[] key) {
        throw new IllegalStateException("containsKey");
    }

    public boolean containsValue(CompositeValue value) {
        throw new IllegalStateException("containsValue");
    }

    public CompositeValue get(MetaValue[] key) {
        throw new IllegalStateException("get");
    }

    public boolean isEmpty() {
        throw new IllegalStateException("isEmpty");
    }

    public Set<List<MetaValue>> keySet() {
        throw new IllegalStateException("keySet");
    }

    public void put(CompositeValue value) {
        throw new IllegalStateException("put");
    }

    public void putAll(CompositeValue[] values) {
        throw new IllegalStateException("putAll");
    }

    public CompositeValue remove(MetaValue[] key) {
        throw new IllegalStateException("remove");
    }

    public int size() {
        throw new IllegalStateException("size");
    }

    public Collection<CompositeValue> values() {
        throw new IllegalStateException("values");
    }
}
