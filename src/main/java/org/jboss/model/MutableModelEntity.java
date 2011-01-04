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

package org.jboss.model;

import org.jboss.model.entity.EntityId;
import org.jboss.model.entity.ManagedResource;
import org.jboss.model.entity.ManagedResourceAddress;
import org.jboss.model.entity.info.ManagedResourceInfo;
import org.jboss.model.types.Named;
import org.jboss.model.values.MetaValue;

/**
 * ManagedResource subclass that exposes the mutator methods.
 *
 * @author Brian Stansberry
 */
public class MutableModelEntity extends ManagedResource {
    private static final long serialVersionUID = 1518224492609827243L;

    public MutableModelEntity(final ManagedResourceAddress address, final ManagedResourceInfo info) {
        super(address, info);
    }

    @Override
    public void setAttribute(String attributeName, MetaValue value) {
        super.setAttribute(attributeName, value);
    }

    public void setAttribute(Named named, MetaValue value) {
        super.setAttribute(named.getName(), value);
    }

    public void addChildEntity(MutableModelEntity child) {
        super.addChildEntity(child);
    }

    @Override
    public boolean removeChildEntity(EntityId child) {
        return super.removeChildEntity(child);
    }

}
