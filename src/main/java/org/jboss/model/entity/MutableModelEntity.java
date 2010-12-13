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

import org.jboss.model.entity.info.ModelEntityInfo;
import org.jboss.model.values.MetaValue;

/**
 * TODO add class javadoc for MutableModelEntity.
 *
 * @author Brian Stansberry
 */
public class MutableModelEntity extends ModelEntity {

    private static final long serialVersionUID = 1518224492609827243L;

    public MutableModelEntity(final ModelEntityInfo info) {
        super(info);
    }

    public MutableModelEntity(final EntityId id, final ModelEntityInfo info) {
        super(id, info);
    }

    protected void setAttribute(String attributeName, MetaValue value) {
        super.setAttribute(attributeName, value);
    }

    public void addChildEntity(MutableModelEntity child) {
        super.addChildEntity(child);
    }

    public boolean removeChildEntity(EntityId child) {
        return super.removeChildEntity(child);
    }

}
