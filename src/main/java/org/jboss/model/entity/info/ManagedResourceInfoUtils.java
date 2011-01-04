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

package org.jboss.model.entity.info;

import java.util.Map;

import org.jboss.model.values.MetaValue;

/**
 * ManagedResource utils.
 *
 * @author Emanuel Muckenhuber
 */
public class ManagedResourceInfoUtils {

    private ManagedResourceInfoUtils() {
        //
    }

    /**
     * Try to resolve the operation info for given invocation parameters.
     *
     * @param resourceInfo the managed resource info
     * @param params the invocation parameters
     * @return the operation info, <code>null</code> if there is not matching operation signature
     */
    public static ManagedResourceOperationInfo resolveOperationInfo(final ManagedResourceInfo resourceInfo, final Map<String, MetaValue> params) {
        if(resourceInfo == null) {
            throw new IllegalArgumentException("null managed resource info");
        }
        for(final ManagedResourceOperationInfo operationInfo : resourceInfo.getOperations()) {
            if(matches(operationInfo, params)) {
                return operationInfo;
            }
        }
        return null;
    }

    /**
     * Try to resolve the adder info for given invocation parameters.
     *
     * @param resourceInfo the managed resource info
     * @param params the invocation parameters
     * @return the adder info, <code>null</code> if there is not matching adder signature
     */
    public static ManagedResourceAdderInfo resolveAdderInfo(final ManagedResourceInfo resourceInfo, final Map<String, MetaValue> params) {
        if(resourceInfo == null) {
            throw new IllegalArgumentException("null managed resource info");
        }
        for(final ManagedResourceAdderInfo adderInfo : resourceInfo.getAdders()) {
            if(matches(adderInfo, params)) {
                return adderInfo;
            }
        }
        return null;
    }

    /**
     * Check whether given invocation parameters patch an operation info.
     *
     * @param adderInfo the managed resource operation info
     * @param params the invocation parameters
     * @return true if the parameters matches the signature, false otherwise
     */
    public static boolean matches(final ManagedResourceOperationInfo operationInfo, final Map<String, MetaValue> params) {
        if(operationInfo == null) {
            throw new IllegalArgumentException("null operation info");
        }
        final ManagedResourceParameterInfo signature[] = operationInfo.getSignature();
        return matches(signature, params);
    }

    /**
     * Check whether given invocation parameters patch an adder info.
     *
     * @param adderInfo the managed resource adder info
     * @param params the invocation parameters
     * @return true if the parameters matches the signature, false otherwise
     */
    public static boolean matches(final ManagedResourceAdderInfo adderInfo, final Map<String, MetaValue> params) {
        if(adderInfo == null) {
            throw new IllegalArgumentException("null operation info");
        }
        final ManagedResourceParameterInfo signature[] = adderInfo.getSignature();
        return matches(signature, params);
    }

    /**
     * Check whether given invocation parameters patch a operation signature.
     *
     * @param signature the signature
     * @param params the invocation parameters
     * @return true if the parameters matches the signature, false otherwise
     */
    public static boolean matches(final ManagedResourceParameterInfo signature[], final Map<String, MetaValue> params) {
        if(signature == null) {
            throw new IllegalArgumentException("null operation signature");
        }
        if(params == null) {
            throw new IllegalArgumentException("null invocation parameters");
        }
        if(signature.length != params.size()) {
            return false;
        }
        for(final ManagedResourceParameterInfo parameter : signature) {
            final String parameterName = parameter.getName();
            final MetaValue value = params.get(parameterName);
            if(! parameter.isValue(value)) {
                return false;
            }
        }
        return true;
    }

}
