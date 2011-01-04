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
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.model.entity.EntityIdType;
import org.jboss.model.types.MetaType;
import org.jboss.model.values.MetaValue;

/**
 * TODO add class javadoc for ManagedResourceInfo.
 *
 * @author Brian Stansberry
 */
public class ManagedResourceInfo implements Serializable, Cloneable, MetaType {

    private static final long serialVersionUID = 5142058281439142818L;

    private static final ManagedResourceAttributeInfo[] NO_ATTRIBUTES = new ManagedResourceAttributeInfo[0];
    private static final ManagedResourceOperationInfo[] NO_OPERATIONS = new ManagedResourceOperationInfo[0];
    private static final ManagedResourceAdderInfo[] NO_ADDERS = new ManagedResourceAdderInfo[0];
    private static final Map<EntityIdType, ManagedResourceChildrenInfo> NO_CHILDREN = Collections.emptyMap();

    /** The human readable description of the entity. */
    private final String description;

    /** The identifier type for the entity. */
    private final EntityIdType identifierType;

    /** The entity adder descriptors. */
    private final ManagedResourceAdderInfo[] adders;

    /** The entity attribute descriptors. */
    private final ManagedResourceAttributeInfo[] attributes;

    /** The entity operation descriptors. */
    private final ManagedResourceOperationInfo[] operations;

    /** The children infos. */
    private final Map<EntityIdType, ManagedResourceChildrenInfo> childrenInfo;

    /** The fields. */
    private final Fields fields;

    public ManagedResourceInfo(EntityIdType identifierType, String description, ManagedResourceAttributeInfo[] attributes,
            ManagedResourceOperationInfo[] operations, ManagedResourceAdderInfo[] adders, Map<EntityIdType, ManagedResourceChildrenInfo> children) throws IllegalArgumentException {
        this(identifierType, description, attributes, operations, adders, children, null);
    }

    public ManagedResourceInfo(EntityIdType identifierType, String description, ManagedResourceAttributeInfo[] attributes,
            ManagedResourceOperationInfo[] operations, ManagedResourceAdderInfo[] adders, Map<EntityIdType, ManagedResourceChildrenInfo> children, Fields fields)
            throws IllegalArgumentException {

        this.identifierType = identifierType;
        this.description = description;

        if (attributes == null) {
            attributes = NO_ATTRIBUTES;
        }
        this.attributes = attributes;

        if (operations == null) {
            operations = NO_OPERATIONS;
        }
        this.operations = operations;

        if (adders == null) {
            adders = NO_ADDERS;
        }
        this.adders = adders;

        if(children == null) {
            children = NO_CHILDREN;
        }
        this.childrenInfo = children;

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

    public ManagedResourceAttributeInfo[] getAttributes() {
        return attributes.length == 0 ? attributes : attributes.clone();
    }

    public Set<String> getAttributeNames() {
        final Set<String> set = new HashSet<String>();
        for(final ManagedResourceAttributeInfo attribute : attributes) {
            set.add(attribute.getName());
        }
        return set;
    }

    public ManagedResourceAttributeInfo getAttributeInfo(final String name) {
        for(final ManagedResourceAttributeInfo attribute : attributes) {
            if(name.equals(attribute.getName())) {
                return attribute;
            }
        }
        return null;
    }

    public ManagedResourceOperationInfo[] getOperations() {
        return operations.length == 0 ? operations : operations.clone();
    }

    public ManagedResourceAdderInfo[] getAdders() {
        return adders.length == 0 ? adders : adders.clone();
    }

    public ManagedResourceChildrenInfo getChildInfo(final EntityIdType type) {
        return childrenInfo.get(type);
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTypeName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public boolean isComposite() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isValue(MetaValue value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isValue(Object obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T extends MetaType> T as(Class<T> expected) {
        // TODO Auto-generated method stub
        return null;
    }

}
