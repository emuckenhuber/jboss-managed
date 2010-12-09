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

package org.jboss.metatype.api.types;

import org.jboss.metatype.api.values.CompositeMapValue;

/**
 * The CompositeMapMetaType defines a map like type with a
 * {@link CompositeMetaType} as entry and a single index key.
 *
 * @author Emanuel Muckenhuber
 */
public class CompositeMapMetaType extends AbstractMetaType implements MetaType {

    private static final long serialVersionUID = 7167931601277402689L;
    private final String indexName;
    private final MetaType indexType;
    private final CompositeMetaType entryType;

    public CompositeMapMetaType(final CompositeMetaType entryType, final String indexName, final String description) {
        super(CompositeMapValue.class.getName(), CompositeMapValue.class.getName(), description);
        if(entryType == null) {
            throw new IllegalArgumentException("null entry type");
        }
        if(indexName == null) {
            throw new IllegalArgumentException("null index name");
        }
        this.indexType = entryType.getType(indexName);
        if(this.indexType == null) {
            throw new IllegalArgumentException("could not resolve type of index " + indexName);
        }
        this.entryType = entryType;
        this.indexName = indexName;
    }

    /**
     * Get the composite item name defining the index.
     *
     * @return the index name
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * Get the meta type of the index.
     *
     * @return the indexType
     */
    public MetaType getIndexType() {
        return indexType;
    }

    /**
     * Get the composite meta type.
     *
     * @return the composite entry type
     */
    public CompositeMetaType getEntryType() {
        return entryType;
    }

    public boolean isValue(Object obj) {
        if(obj == null || (obj instanceof CompositeMapValue) == false) {
            return false;
        }
        CompositeMapMetaType other = CompositeMapValue.class.cast(obj).getMetaType();
        return equals(other);
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || (obj instanceof CompositeMapMetaType) == false) {
            return false;
        }
        CompositeMapMetaType other = CompositeMapMetaType.class.cast(obj);
        if(!indexName.equals(other.indexName)) {
            return false;
        }
        return entryType.equals(other.entryType);
    }

    public int hashCode() {
        int result = 7;
        result += 31 * indexName.hashCode();
        result += 31 * entryType.hashCode();
        return result;
    }

}
