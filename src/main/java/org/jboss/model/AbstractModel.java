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

import org.jboss.model.entity.ManagedResource;
import org.jboss.model.entity.ManagedResourceAddress;
import org.jboss.model.entity.ManagementInvocation;
import org.jboss.model.entity.info.ManagedResourceInfo;

/**
 * @author Brian Stansberry
 * @author Emanuel Muckenhuber
 */
public abstract class AbstractModel {

    private final MutableModelEntity root;

    protected AbstractModel(final ManagedResourceInfo modelInfo) {
        if(modelInfo == null) {
            throw new IllegalArgumentException("null model info");
        }
        this.root = new MutableModelEntity(ManagedResourceAddress.ROOT, modelInfo);
    }

    /**
     * Gets the object that describes the detyped API exposed by this model.
     *
     * @return the API description. Will not be <code>null</code>
     */
    public ManagedResourceInfo getModelEntityInfo() {
        return root.getEntityInfo();
    }

    /**
     * Gets a copy of detyped data that backs the model.
     *
     * @return {@link ManagedResource} that represents the root entity in the model. Will not be <code>null</code>
     */
    public ManagedResource getModelData() {
        return new ManagedResource(root);
    }

    /**
     * Applies the given update to the model.
     *
     * @param update the update. Cannot be <code>null</code>
     * @throws UpdateFailedException
     */
    public abstract void applyUpdateToModel(final ManagementInvocation update) throws UpdateFailedException;

    protected MutableModelEntity getMutableModelEntity(ManagedResourceAddress address) {
        return (address.isRoot()) ? root : (MutableModelEntity) root.getChildEntity(address);
    }
}
