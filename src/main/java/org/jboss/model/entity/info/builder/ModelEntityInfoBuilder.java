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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.model.entity.EntityIdType;
import org.jboss.model.entity.info.ManagedResourceAdderInfo;
import org.jboss.model.entity.info.ManagedResourceAttributeInfo;
import org.jboss.model.entity.info.ManagedResourceChildrenInfo;
import org.jboss.model.entity.info.ManagedResourceFeatureInfo;
import org.jboss.model.entity.info.ManagedResourceOperationInfo;
import org.jboss.model.entity.info.ManagedResourceInfo;
import org.jboss.model.types.MetaType;
import org.jboss.model.types.Named;

/**
 * The {@code ModelEntityInfoBuilder}.
 *
 * @author Emanuel Muckenhuber
 */
public class ModelEntityInfoBuilder {

    final static String UNDOCUMENTED = "##UNDOCUMENTED##";

    public static ModelEntityInfoBuilder create(final EntityIdType identifierType) {
        return new ModelEntityInfoBuilder(identifierType);
    }

    public static ModelEntityInfoBuilder create(final EntityIdType identifierType, final String description) {
        return new ModelEntityInfoBuilder(identifierType, description);
    }

    private final EntityIdType identifierType;
    private final String description;
    private List<EntityAdderInfoBuilder> adderBuilders = new ArrayList<EntityAdderInfoBuilder>();
    private List<EntityAttributeInfoBuilder> attributeBuilders = new ArrayList<EntityAttributeInfoBuilder>();
    private List<EntityOperationBuilder> operationBuilders = new ArrayList<EntityOperationBuilder>();
    private List<EntityChildrenInfoBuilder> childrenBuilders = new ArrayList<EntityChildrenInfoBuilder>();

    ModelEntityInfoBuilder(final EntityIdType identifierType) {
        this(identifierType, UNDOCUMENTED);
    }

    ModelEntityInfoBuilder(final EntityIdType identifierType, final String description) {
        if(identifierType == null) {
            throw new IllegalArgumentException("null identifier type");
        }
        if(description == null) {
            throw new IllegalArgumentException("null description");
        }
        this.identifierType = identifierType;
        this.description = description;
    }

    public EntityAttributeInfoBuilder addAttribute(final String name) {
        final EntityAttributeInfoBuilder builder = new EntityAttributeInfoBuilder(name);
        attributeBuilders.add(builder);
        return builder;
    }

    public EntityAttributeInfoBuilder addAttribute(final String name, final MetaType attributeType) {
        final EntityAttributeInfoBuilder builder = new EntityAttributeInfoBuilder(name, attributeType);
        attributeBuilders.add(builder);
        return builder;
    }

    public EntityAttributeInfoBuilder addAttribute(final String name, final String description) {
        final EntityAttributeInfoBuilder builder = new EntityAttributeInfoBuilder(name, description);
        attributeBuilders.add(builder);
        return builder;
    }

    public EntityAttributeInfoBuilder addAttribute(final String name, final String description, final MetaType attributeType) {
        final EntityAttributeInfoBuilder builder = new EntityAttributeInfoBuilder(name, description, attributeType);
        attributeBuilders.add(builder);
        return builder;
    }

    public EntityAttributeInfoBuilder addAttribute(final Named name) {
        return addAttribute(getName(name));
    }

    public EntityAttributeInfoBuilder addAttribute(final Named name, final MetaType attributeType) {
        return addAttribute(getName(name), attributeType);
    }

    public EntityAttributeInfoBuilder addAttribute(final Named name, final String description) {
        return addAttribute(getName(name), description);
    }

    public EntityAttributeInfoBuilder addAttribute(final Named name, final String description, final MetaType attributeType) {
        return addAttribute(getName(name), description, attributeType);
    }

    public EntityAdderInfoBuilder addAdderInfo(final String name) {
        final EntityAdderInfoBuilder builder = new EntityAdderInfoBuilder(name);
        adderBuilders.add(builder);
        return builder;
    }

    public EntityAdderInfoBuilder addAdderInfo(final String name, final String description) {
        final EntityAdderInfoBuilder builder = new EntityAdderInfoBuilder(name, description);
        adderBuilders.add(builder);
        return builder;
    }

    public EntityAdderInfoBuilder addAdderInfo(final Named name) {
        return addAdderInfo(getName(name));
    }

    public EntityAdderInfoBuilder addAdderInfo(final Named name, final String description) {
        return addAdderInfo(getName(name), description);
    }

    public EntityOperationBuilder addOperation(final String name) {
        final EntityOperationBuilder builder = new EntityOperationBuilder(name);
        operationBuilders.add(builder);
        return builder;
    }

    public EntityOperationBuilder addOperation(final String name, final String description) {
        final EntityOperationBuilder builder = new EntityOperationBuilder(name, description);
        operationBuilders.add(builder);
        return builder;
    }

    public EntityOperationBuilder addOperation(final Named name) {
        return addOperation(getName(name));
    }

    public EntityOperationBuilder addOperation(final Named name, final String description) {
        return addOperation(getName(name), description);
    }

    public EntityChildrenInfoBuilder addChildInfo(final ManagedResourceInfo info) {
        final EntityChildrenInfoBuilder builder = new EntityChildrenInfoBuilder(info);
        childrenBuilders.add(builder);
        return builder;
    }

    public ManagedResourceInfo create() {
        return new ManagedResourceInfo(identifierType, description,
                createAttributes(attributeBuilders),
                creatOperations(operationBuilders),
                createAdders(adderBuilders),
                createChildrenMap(childrenBuilders));
    }

    static ManagedResourceAttributeInfo[] createAttributes(List<EntityAttributeInfoBuilder> builders) {
        final int size = builders.size();
        if(size > 0) {
            ManagedResourceAttributeInfo[] array = new ManagedResourceAttributeInfo[size];
            return createArray(builders, array);
        }
        return null;
    }

    static ManagedResourceOperationInfo[] creatOperations(List<EntityOperationBuilder> builders) {
        final int size = builders.size();
        if(size > 0) {
            ManagedResourceOperationInfo[] array = new ManagedResourceOperationInfo[size];
            return createArray(builders, array);
        }
        return null;
    }

    static ManagedResourceAdderInfo[] createAdders(List<EntityAdderInfoBuilder> builders) {
        final int size = builders.size();
        if(size > 0) {
            ManagedResourceAdderInfo[] array = new ManagedResourceAdderInfo[size];
            return createArray(builders, array);
        }
        return null;
    }

    static Map<EntityIdType, ManagedResourceChildrenInfo> createChildrenMap(List<EntityChildrenInfoBuilder> builders) {
        final int size = builders.size();
        if(size > 0) {
            final Map<EntityIdType, ManagedResourceChildrenInfo> adders = new HashMap<EntityIdType, ManagedResourceChildrenInfo>(size);
            for(final EntityChildrenInfoBuilder builder : builders) {
                final ManagedResourceChildrenInfo child = builder.create();
                adders.put(child.getIdentifierType(), child);
            }
        }
        return null;
    }

    static <T extends ManagedResourceFeatureInfo> T[] createArray(final List<? extends AbstractEntityFeatureBuilder<T>> builders, T[] array) {
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
