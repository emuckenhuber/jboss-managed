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

import java.util.List;

import org.jboss.model.entity.info.EntityFeatureInfo;
import org.jboss.model.entity.info.EntityParameterInfo;
import org.jboss.model.types.Named;

/**
 * @author Emanuel Muckenhuber
 */
public abstract class AbstractEntityFeatureBuilder<T extends EntityFeatureInfo> {

    final String name;
    final String description;

    AbstractEntityFeatureBuilder(final String name) {
        this(name, ModelEntityInfoBuilder.UNDOCUMENTED);
    }

    AbstractEntityFeatureBuilder(final String name, final String description) {
        if(name == null) {
            throw new IllegalArgumentException("null name");
        }
        if(description == null) {
            throw new IllegalArgumentException("null description");
        }
        this.name = name;
        this.description = description;
    }

    public abstract void checkValid();

    protected abstract T create();

    static EntityParameterInfo[] createSignature(List<EntityParameterInfoBuilder> signatureBulders) {
        final int size = signatureBulders.size();
        if(size > 0) {
            final EntityParameterInfo[] array = new EntityParameterInfo[size];
            return createArray(signatureBulders, array);
        }
        return null;
    }

    static <T extends EntityFeatureInfo> T[] createArray(final List<? extends AbstractEntityFeatureBuilder<T>> builders, T[] array) {
        final int length = array.length;
        for(int i = 0; i < length; i++) {
            array[i] = builders.get(i).create();
        }
        return array;
    }

    static String getName(final Named named) {
        if(named == null) {
            throw new IllegalArgumentException("null name");
        }
        return named.getName();
    }
}
