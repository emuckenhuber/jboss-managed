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

package org.jboss.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.model.entity.info.ManagedResourceChildrenInfo;

/**
 * @author Emanuel Muckenhuber
 */
public class ManagedResourceChildren implements Serializable, Cloneable {

    private static final long serialVersionUID = -1258248193327793391L;

    private final ManagedResourceChildrenInfo info;
    private final Map<EntityId, ManagedResource> children = new HashMap<EntityId, ManagedResource>();

    public ManagedResourceChildren(final ManagedResourceChildrenInfo info) {
        if(info == null) {
            throw new IllegalArgumentException("null entity children info");
        }
        this.info = info;
    }

    ManagedResourceChildren(final ManagedResourceChildren toClone) {
        this.info = toClone.info;
        for(final Entry<EntityId, ManagedResource> entry : toClone.children.entrySet()) {
            children.put(entry.getKey(), new ManagedResource(entry.getValue()));
        }
    }

    protected ManagedResource getChild(final EntityId id) {
        return children.get(id);
    }

    protected void addChild(final EntityId id, final ManagedResource entity) {
        if(id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        if(entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        // TODO check EntityIdType ?
        final int max = info.getCardinality().getMax();
        if(max != -1) {
            final int size = children.size();
            if(size > max) {
                throw new IllegalArgumentException(String.format("max number of (%d) children reached.", max));
            }
        }
        children.put(id, entity);
    }

    protected Collection<ManagedResource> getChildren() {
        return Collections.unmodifiableCollection(children.values());
    }

    protected boolean removeChild(final EntityId id) {
        if(id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        final int min = info.getCardinality().getMin();
        if(min != 0) {
            final int size = children.size() -1;
            if(size < min) {
                throw new IllegalArgumentException(String.format("min number of (%d) children reached.", min));
            }
        }
        return children.remove(id) != null;
    }


}
