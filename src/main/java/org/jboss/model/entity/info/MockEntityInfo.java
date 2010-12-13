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

import org.jboss.model.entity.EntityIdType;
import org.jboss.model.types.SimpleMetaType;

/**
 * The MockEntityInfo creates new EntityAttributeInfo and EntityChildrenInfo
 * upon request.
 *
 * TODO remove
 *
 * @author Emanuel Muckenhuber
 */
public class MockEntityInfo extends ModelEntityInfo {

    private static final long serialVersionUID = 1L;

    public static MockEntityInfo create(final String entityType) {
        return new MockEntityInfo(new EntityIdType(entityType));
    }

    /**
     * @param identifierType
     * @throws IllegalArgumentException
     */
    public MockEntityInfo(EntityIdType identifierType) throws IllegalArgumentException {
        super(identifierType, MockEntityInfo.class.getName(), null, null, null, null);
    }

    public EntityAttributeInfo getAttributeInfo(String name) {
        return new EntityAttributeInfo(name, SimpleMetaType.STRING, "");
    }

    public EntityChildrenInfo getChildInfo(EntityIdType type) {
        return new EntityChildrenInfo(this, Cardinality.ZERO_INFINITY);
    }

}
