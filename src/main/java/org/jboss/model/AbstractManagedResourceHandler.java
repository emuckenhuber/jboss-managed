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

import java.util.Arrays;

import org.jboss.model.entity.ManagedResourceAddress;
import org.jboss.model.entity.ManagementInvocation;
import org.jboss.model.entity.info.ManagedResourceInfoUtils;
import org.jboss.model.entity.info.ManagedResourceOperationInfo;

/**
 * @author Emanuel Muckenhuber
 */
public abstract class AbstractManagedResourceHandler {

    private final ManagedResourceOperationInfo operationInfo;

    protected AbstractManagedResourceHandler(final ManagedResourceOperationInfo operationInfo) {
        if(operationInfo == null) {
            throw new IllegalArgumentException("null operation info");
        }
        this.operationInfo = operationInfo;
    }

    /**
     * Get the operation name.
     *
     * @return the operation name
     */
    public String getOperationName() {
        return operationInfo.getName();
    }

    /**
     * Get operation info this handler handles.
     *
     * @return the operationInfo the operation info
     */
    public ManagedResourceOperationInfo getOperationInfo() {
        return operationInfo;
    }

    /**
     * Apply this update to the given model entity. This method should either
     * successfully change the model entity, or leave the entity unchanged
     * and throw an UpdateFailedException.
     *
     * @param model the model to which the update should be applied
     * @throws UpdateFailedException if the update failed. If this exception is
     *            thrown, no change should have been made to <code>entity</code>
     */
    protected abstract void applyUpdate(final AbstractModel model, final ManagementInvocation update) throws UpdateFailedException;

    /**
     * Get an update which would revert (compensate for) the given update.
     * This method may only be called before the update
     * is applied to the target entity. May return {@code null} if the state of
     * {@code original} is such that no compensating update is possible (e.g.
     * if this update is intended to remove something from {@code original}
     * that does not exist).
     *
     * @param original the original model
     * @return the compensating update, or {@code null} if no compensating update
     *           is possible
     */
    public abstract ManagementInvocation getCompensatingUpdate(final AbstractModel original, final ManagementInvocation update);

    /**
     * Returns a mutable view of the portion of the data structure behind the given model
     * that represents the given address.
     *
     * @param model the model. Cannot be <code>null</code>
     * @param address the address. Cannot be <code>null</code>
     *
     * @return the mutable view of the model data
     *
     * @throws IllegalArgumentException if the data structure does not include
     *                                  an entity at the given address
     */
    protected final MutableModelEntity getMutableModelEntity(AbstractModel model, ManagedResourceAddress address) {
        return model.getMutableModelEntity(address);
    }

    protected final MutableModelEntity validateUpdateAddress(AbstractModel model, ManagementInvocation update) throws UpdateFailedException {
        MutableModelEntity entity = getMutableModelEntity(model, update.getEntityAddress());
        if (entity == null) {
            throw new UpdateFailedException("No model entity found at address " + update.getEntityAddress());
        }
        return entity;
    }

    /**
     * Validate the invocation parameters.
     *
     * @param update the management invocation
     * @throws UpdateFailedException if the parameters don't match the operation signature
     */
    public void validateParameterTypes(final ManagementInvocation update) throws UpdateFailedException {
        if(! ManagedResourceInfoUtils.matches(operationInfo, update.getParams())) {
            throw new UpdateFailedException(String.format("update params (%s) don't match the operation signature (%s)",
                    update.getParameterNames(), Arrays.asList(operationInfo.getSignature())));
        }
    }

}
