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

import org.jboss.model.entity.info.Cardinality;
import org.jboss.model.entity.info.ManagedResourceChildrenInfo;
import org.jboss.model.entity.info.ManagedResourceInfo;

/**
 * @author Emanuel Muckenhuber
 */
public class EntityChildrenInfoBuilder {

    private final ManagedResourceInfo info;
    private Cardinality cardinality = Cardinality.ZERO_INFINITY;

    EntityChildrenInfoBuilder(final ManagedResourceInfo info) {
        if(info == null) {
            throw new IllegalArgumentException("null child entity info");
        }
        this.info = info;
    }

    public EntityChildrenInfoBuilder setCardinality(Cardinality cardinality) {
        if(cardinality == null) {
            throw new IllegalArgumentException("null cardinality");
        }
        this.cardinality = cardinality;
        return this;
    }

    ManagedResourceChildrenInfo create() {
        return new ManagedResourceChildrenInfo(info, cardinality);
    }

}
