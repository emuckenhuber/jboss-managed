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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.jboss.model.values.MetaValue;

/**
 * TODO add class javadoc for ManagementInvocation.
 *
 * @author Brian Stansberry
 */
public class ManagementInvocation {

    private static final Map<String,MetaValue> EMPTY = Collections.emptyMap();

    private final ManagedResourceAddress entityAddress;
    private final String operationId;
    private final Map<String, MetaValue> params;

    public ManagementInvocation(final ManagedResourceAddress entityAddress, final String operationId, final Map<String, MetaValue> params) {
        if(entityAddress == null) {
            throw new IllegalArgumentException("null resource address");
        }
        if(operationId == null) {
            throw new IllegalArgumentException("null operation ID");
        }
        this.entityAddress = entityAddress;
        this.operationId = operationId;
        this.params = params == null ? EMPTY : params;
    }

    public ManagedResourceAddress getEntityAddress() {
        return entityAddress;
    }

    public String getOperationId() {
        return operationId;
    }

    public MetaValue getParam(final String parameterName) {
        return params.get(parameterName);
    }

    public Set<String> getParameterNames() {
        return params.keySet();
    }

    public Map<String, MetaValue> getParams() {
        return params;
    }

}
