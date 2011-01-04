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

import org.jboss.model.entity.EntityIdType;
import org.jboss.model.entity.ManagementInvocation;
import org.jboss.model.entity.info.ManagedResourceChildrenInfo;
import org.jboss.model.entity.info.ManagedResourceInfo;
import org.jboss.model.entity.info.ManagedResourceOperationInfo;
import org.jboss.model.values.MetaValue;

/**
 * Abstract handler adding a new managed resource
 *
 * @author Brian Stansberry
 * @author Emanuel Muckenhuber
 */
public abstract class AbstractManagedResourceAddHandler extends AbstractManagedResourceHandler {

    protected AbstractManagedResourceAddHandler(final ManagedResourceOperationInfo operationInfo) {
        super(operationInfo);
    }

    /** {@inheritDoc} */
    protected void applyUpdate(final AbstractModel model, final ManagementInvocation update) throws UpdateFailedException {
        // Validate the update first
        validateParameterTypes(update);
        validateUpdate(model, update);

        final String resourceType = getResourceType(update);
        final String resourceName = getResourceName(update);

        final MutableModelEntity parent = validateUpdateAddress(model, update);
        final ManagedResourceInfo info = parent.getEntityInfo();
        final ManagedResourceChildrenInfo childrenInfo = info.getChildInfo(new EntityIdType(resourceType));
        if(childrenInfo == null) {
            throw new UpdateFailedException();
        }
        final ManagedResourceInfo resourceInfo = childrenInfo.getModelEntityInfo();
        if(resourceInfo == null) {
            throw new UpdateFailedException();
        }
        final MutableModelEntity resource = new MutableModelEntity(null, resourceInfo);
        applyUpdate(resource, update);
        parent.addChildEntity(resource);
    }

    /**
     * Get the resource type this operation creates.
     *
     * @param update the management invocation
     * @return the resource type
     */
    protected abstract String getResourceType(final ManagementInvocation update);

    /**
     * Get the resource name this operation creates.
     * @param update the management invocation
     * @return the resource name
     */
    protected abstract String getResourceName(final ManagementInvocation update);

    /**
     * Validate the update.
     *
     * @param model the model.
     * @param update the management invocation
     * @throws UpdateFailedException
     */
    protected void validateUpdate(final AbstractModel model, final ManagementInvocation update) throws UpdateFailedException {
        //
    }

    /**
     * Apply the update to the create {@code ManagedResource}. By default this will set all parameters as
     * attributes on the managed resource.
     *
     * @param resource the managed resource
     * @param update the management invocation
     * @throws UpdateFailedException
     */
    protected void applyUpdate(final MutableModelEntity resource, final ManagementInvocation update) throws UpdateFailedException {
        for(final String parameterName : update.getParameterNames()) {
            final MetaValue value = update.getParam(parameterName);
            resource.setAttribute(parameterName, value);
        }
    }

}
