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

package org.jboss.model.detyped;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.metatype.api.types.MetaType;
import org.jboss.metatype.api.values.CompositeValue;
import org.jboss.metatype.api.values.MetaValue;
import org.jboss.model.detyped.info.EntityAttributeInfo;
import org.jboss.model.detyped.info.ModelEntityInfo;

/**
 * Provides a detyped view of an {@link AbstractAddressablesModelElement}.
 * <p/>
 * A ModelEntity can come in two flavors, depending on the value of its
 * {@link #isIdOnly()} property. If <code>true</code> the object should be
 * regarded as simply a placeholder in a larger data structure. If <code>false</code>
 * the object contains information on the underlying model element's properties.
 * </p/>
 * Typical usages of a ModelEntity whose {@link #isIdOnly()} property
 * is <code>true</code> are:
 * <ul>
 * <li>For a remote client reading the model, as a placeholder entry for model
 * data in which the client is not interested, allowing less data to be
 * shipped to the client while still maintaining the basic model structure.</li>
 * <li>For management client that passes back a ModelEntity to the server
 * that is meant to represent an updated view of the model, as a placeholder
 * entry for a portion of the model that is not modified by the update. Including
 * the placeholder allows to server to clearly distinguish elements that are
 * added, updated and removed.</li>
 * </ul>
 *
 * @author Brian Stansberry
 */
public class ModelEntity {

    private final EntityId id;
    private final boolean idOnly;
    private final Map<EntityId, ModelEntity> childDetypedElements =
        new LinkedHashMap<EntityId, ModelEntity>();
    private final CompositeValue contentValues;

    /** The entity info. */
    private ModelEntityInfo entityInfo;
    private Map<String, MetaValue> attributeValues;

    /**
     * Creates a new ModelEntity whose {@link #isIdOnly()} method
     * returns <code>true</code>.
     *
     * @param id the EntityId. Cannot be <code>null</code>
     */
    public ModelEntity(final EntityId id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        this.id = id;
        this.idOnly = true;
        this.contentValues = null;
    }

    /**
     * Creates a new ModelEntity that includes the full content of
     * the underlying element.
     *
     * @param id the EntityId. Cannot be <code>null</code>
     * @param contentValues {@link CompositeValue} that encapsulates all the
     *                      properties of the underlying model element that
     *                      are not themselves represented as <code>children</code>
     * @param children list of DetypedModelElements that represent properties of
     *                 the underlying model element that are themselves
     *                 {@link AbstractAddressableModelElement}s.
     */
    public ModelEntity(final EntityId id,
            final CompositeValue contentValues,
            final List<ModelEntity> children) {

        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        this.id = id;
        this.idOnly = false;
        this.contentValues = contentValues;
        if (children != null) {
            for (ModelEntity child : children) {
                childDetypedElements.put(child.getElementId(), child);
            }
        }
    }

    /**
     * Gets the id of the model element.
     *
     * @return the id. Will not be <code>null</code>
     */
    public EntityId getElementId() {
        return this.id;
    }

    /**
     * Gets the entity type info.
     *
     * @return the entityInfo
     */
    public ModelEntityInfo getEntityInfo() {
        return entityInfo;
    }

    public void setAttribute(final String attributeName, final MetaValue value) {
        if(attributeName == null) {
            throw new IllegalArgumentException("null attribute name");
        }
        final EntityAttributeInfo attribute = getAttributeInfo(attributeName);
        if(attribute == null) {
            throw new IllegalArgumentException(String.format("attribute (%s) not declared.", attributeName));
        }
        final MetaType attributeType = attribute.getType();
        if(! attributeType.isValue(value)) {
            throw new IllegalArgumentException(String.format("invalid attribute value (%s). Required type: %s.", attributeName, attributeType));
        }
        attributeValues.put(attributeName, value);
    }


    public MetaValue getAttribute(final String attributeName) {
        return attributeValues.get(attributeName);
    }

    public <T extends MetaValue> T getAttribute(final String attributeName, Class<T> expected) {
        final MetaValue value = getAttribute(attributeName);
        if (value == null) {
            return null;
        }
        return expected.cast(value);
    }


    /**
     *
     * @return
     */
    public boolean isIdOnly() {
        return this.idOnly;
    }

    public ModelEntity getChildEntity(final EntityId entityId) {
        return childDetypedElements.get(entityId);
    }

    public Set<EntityId> getChildEntityIds() {
        checkIdOnly();
        return new LinkedHashSet<EntityId>(childDetypedElements.keySet());
    }

    public Map<EntityId, ModelEntity> getChildEntities() {
        checkIdOnly();
        return new LinkedHashMap<EntityId, ModelEntity>(childDetypedElements);
    }

    public ModelEntity getChildEntity(final EntityAddress relativeAddress) {
        checkIdOnly();
        ModelEntity element = this;
        for (int i = 0; i < relativeAddress.size(); i++) {
            element = element.getChildEntity(relativeAddress.get(i));
            if (element == null) {
                throw new IllegalArgumentException(String.format("No child element exists at path %s", relativeAddress.getSubAddress(0, i+1)));
            }
        }
        return element;
    }

    public CompositeValue getContentValues() {
        checkIdOnly();
        return contentValues;
    }

    private void checkIdOnly() {
        if (idOnly) {
            throw new IllegalStateException("Element is id-only; content cannot be accessed");
        }
    }

    private EntityAttributeInfo getAttributeInfo(final String name) {
        for(final EntityAttributeInfo attribute : entityInfo.getAttributes()) {
            if(name.equals(attribute.getName())) {
                return attribute;
            }
        }
        return null;
    }
}
