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

import org.jboss.model.entity.info.EntityAttributeInfo;
import org.jboss.model.types.MetaType;

/**
 * @author Emanuel Muckenhuber
 */
public final class EntityAttributeInfoBuilder extends AbstractEntityFeatureBuilder<EntityAttributeInfo> {

    private MetaType attributeType;

    EntityAttributeInfoBuilder(String name) {
        super(name);
    }

    EntityAttributeInfoBuilder(String name, final MetaType attributeType) {
        super(name);
        this.attributeType = attributeType;
    }

    EntityAttributeInfoBuilder(String name, String description) {
        super(name, description);
    }

    EntityAttributeInfoBuilder(String name, String description, final MetaType attributeType) {
        super(name, description);
        this.attributeType = attributeType;
    }

    public EntityAttributeInfoBuilder setAttributeType(final MetaType metaType) {
        if(metaType == null) {
            throw new IllegalArgumentException("null metaType");
        }
        if(this.attributeType != null) {
            throw new IllegalArgumentException(String.format("type for attribute (%s) already defined", name));
        }
        this.attributeType = metaType;
        return this;
    }

    public void checkValid() {
        if(this.attributeType == null) {
            throw new IllegalArgumentException(String.format("type for attribute (%s) not defined", name));
        }
    }

    public EntityAttributeInfo create() {
        checkValid();
        return new EntityAttributeInfo(name, attributeType, description);
    }

}
