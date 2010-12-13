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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jboss.model.entity.EntityIdType;

/**
 * TODO add class javadoc for ModelEntityInfo.
 *
 * @author Brian Stansberry
 */
public class ModelEntityInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 5142058281439142818L;

    private static final EntityAttributeInfo[] NO_ATTRIBUTES = new EntityAttributeInfo[0];
    private static final EntityOperationInfo[] NO_OPERATIONS = new EntityOperationInfo[0];
    private static final EntityAdderInfo[] NO_ADDERS = new EntityAdderInfo[0];

    /** The human readable description of the entity. */
    private final String description;

    /** The identifier type for the entity. */
    private final EntityIdType identifierType;

    /** The entity adder descriptors. */
    private final EntityAdderInfo[] adders;

    /** The entity attribute descriptors. */
    private final EntityAttributeInfo[] attributes;

    /** The entity operation descriptors. */
    private final EntityOperationInfo[] operations;

    /** The children infos. */
    private final Map<EntityIdType, EntityChildrenInfo> childrenInfo = new HashMap<EntityIdType, EntityChildrenInfo>();

    /** The fields. */
    private final Fields fields;

    public ModelEntityInfo(EntityIdType identifierType, String description, EntityAttributeInfo[] attributes,
            EntityOperationInfo[] operations, EntityAdderInfo[] adders, Map<ModelEntityInfo, Cardinality> children) throws IllegalArgumentException {
        this(identifierType, description, attributes, operations, adders, children, null);
    }

    public ModelEntityInfo(EntityIdType identifierType, String description, EntityAttributeInfo[] attributes,
            EntityOperationInfo[] operations, EntityAdderInfo[] adders, Map<ModelEntityInfo, Cardinality> children, Fields fields)
            throws IllegalArgumentException {

        this.identifierType = identifierType;

        this.description = description;

        if (attributes == null)
            attributes = NO_ATTRIBUTES;
        this.attributes = attributes;

        if (operations == null)
            operations = NO_OPERATIONS;
        this.operations = operations;

        if (adders == null)
            adders = NO_ADDERS;
        this.adders = adders;

        if (fields == null) {
            //
        }
        this.fields = fields;
    }

    public Fields getFields() {
        return fields;
    }

    public String getDescription() {
        return description;
    }

    public EntityIdType getIdentifierType() {
        return identifierType;
    }

    public EntityAttributeInfo[] getAttributes() {
        return attributes.length == 0 ? attributes : attributes.clone();
    }

    public EntityAttributeInfo getAttributeInfo(final String name) {
        for(final EntityAttributeInfo attribute : attributes) {
            if(name.equals(attribute.getName())) {
                return attribute;
            }
        }
        return null;
    }

    public EntityOperationInfo[] getOperations() {
        return operations.length == 0 ? operations : operations.clone();
    }

    public EntityAdderInfo[] getAdders() {
        return adders.length == 0 ? adders : adders.clone();
    }

    public EntityChildrenInfo getChildInfo(final EntityIdType type) {
        return childrenInfo.get(type);
    }

}
