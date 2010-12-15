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

package org.jboss.model.entity.info.builder;

import org.jboss.model.entity.info.EntityParameterInfo;
import org.jboss.model.types.MetaType;

/**
 * @author Emanuel Muckenhuber
 */
public class EntityParameterInfoBuilder extends AbstractEntityFeatureBuilder<EntityParameterInfo> {

    private MetaType parameterType;
    private boolean nillable;

    EntityParameterInfoBuilder(String name) {
        super(name);
    }

    EntityParameterInfoBuilder(String name, String description) {
        super(name, description);
    }

    /**
     * @param nillable the nillable to set
     */
    public EntityParameterInfoBuilder setNillable(boolean nillable) {
        this.nillable = nillable;
        return this;
    }

    public EntityParameterInfoBuilder setParameterType(MetaType parameterType) {
        if(parameterType == null) {
            throw new IllegalArgumentException("null parameter type");
        }
        if(this.parameterType != null) {
            throw new IllegalArgumentException(String.format("type for parameter (%s) already defined", name));
        }
        this.parameterType = parameterType;
        return this;
    }

    public void checkValid() {
        if(parameterType == null) {
            throw new IllegalArgumentException(String.format("type for parameter (%s) not defined", name));
        }
    }

    protected EntityParameterInfo create() {
        checkValid();
        return new EntityParameterInfo(name, parameterType, description, nillable);
    }

}
