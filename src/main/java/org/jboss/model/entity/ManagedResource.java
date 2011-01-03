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

package org.jboss.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jboss.model.entity.info.ManagedResourceAttributeInfo;
import org.jboss.model.entity.info.ManagedResourceChildrenInfo;
import org.jboss.model.entity.info.ManagedResourceInfo;
import org.jboss.model.types.MetaType;
import org.jboss.model.values.MetaValue;

/**
 * Provides a detyped view of a managed resource.
 * <p/>
 * A ManagedResource can come in two flavors, depending on the value of its
 * {@link #isIdOnly()} property. If <code>true</code> the object should be
 * regarded as simply a placeholder in a larger data structure. If <code>false</code>
 * the object contains information on the underlying model element's properties.
 * </p/>
 * Typical usages of a ManagedResource whose {@link #isIdOnly()} property
 * is <code>true</code> are:
 * <ul>
 * <li>For a remote client reading the model, as a placeholder entry for model
 * data in which the client is not interested, allowing less data to be
 * shipped to the client while still maintaining the basic model structure.</li>
 * <li>For management client that passes back a ManagedResource to the server
 * that is meant to represent an updated view of the model, as a placeholder
 * entry for a portion of the model that is not modified by the update. Including
 * the placeholder allows to server to clearly distinguish elements that are
 * added, updated and removed.</li>
 * </ul>
 *
 * @author Brian Stansberry
 */
public class ManagedResource implements Serializable, Cloneable {

    private static final long serialVersionUID = -1796243230556461143L;

    /** The entity address. */
    private final ManagedResourceAddress address;
    private final boolean idOnly;

    /** The entity info. */
    private final ManagedResourceInfo entityInfo;

    /** The attribute values. */
    private final Map<String, MetaValue> attributeValues = new HashMap<String, MetaValue>();

    /** The children grouped by type. */
    private final Map<EntityIdType, ManagedResourceChildren> children = new HashMap<EntityIdType, ManagedResourceChildren>();

    /**
     * Create a new ManagedResource, with idOnly false.
     *
     * @param addresss the entity address
     * @param info the model entity info
     */
    public ManagedResource(final ManagedResourceAddress address, final ManagedResourceInfo info) {
        this(address, info, false);
    }

    /**
     * Create a new ManagedResource.
     *
     * @param address the entity address
     * @param info the model entity info
     * @param idOnly true if this is a idOnly entity
     */
    public ManagedResource(final ManagedResourceAddress address, final ManagedResourceInfo info, final boolean idOnly) {
        if (address == null) {
            throw new IllegalArgumentException("address is null");
        }
        if(info == null) {
            throw new IllegalArgumentException("info is null");
        }
        // Check the ID type
        final EntityIdType type = new EntityIdType(address.getLastElement().getElementName());
        if(! info.getIdentifierType().equals(type)) {
            throw new IllegalArgumentException(String.format("invalid identifier type (%s), should be (%s)",
                    info.getIdentifierType(), type));
        }
        this.address = address;
        this.entityInfo = info;
        this.idOnly = idOnly;
    }

    public ManagedResource(ManagedResource toClone) {
        this.address = toClone.address;
        this.idOnly = toClone.idOnly;
        this.entityInfo = toClone.entityInfo;
        if (!idOnly) {
            for (Map.Entry<String, MetaValue> entry : toClone.attributeValues.entrySet()) {
                MetaValue value = entry.getValue();
                if (value != null) {
                    value = value.clone();
                }
                this.attributeValues.put(entry.getKey(), value);
            }
            for (Map.Entry<EntityIdType, ManagedResourceChildren> entry : toClone.children.entrySet()) {
                children.put(entry.getKey(), new ManagedResourceChildren(entry.getValue()));
            }
        }
    }

    /**
     * Get the entity address
     *
     * @return the entity address
     */
    public ManagedResourceAddress getAddress() {
        return address;
    }


    /**
     * Gets the entity type info.
     *
     * @return the entityInfo
     */
    public ManagedResourceInfo getEntityInfo() {
        return entityInfo;
    }

    /**
     * Get the available attribute names for this {@code ManagedResource}.
     *
     * @return the attribute names
     */
    public Set<String> getAttributeNames() {
        return entityInfo.getAttributeNames();
    }

    /**
     * Set an attribute value.
     *
     * @param attributeName the attribute name
     * @param value the value to set
     * @throws IllegalArgumentException if the types don't match
     */
    protected void setAttribute(final String attributeName, final MetaValue value) {
        checkIdOnly();
        if (isRoot()) {
            throw new IllegalStateException("Cannot mutate content of a root entity");
        }
        if(attributeName == null) {
            throw new IllegalArgumentException("null attribute name");
        }
        final ManagedResourceAttributeInfo attribute = entityInfo.getAttributeInfo(attributeName);
        if(attribute == null) {
            throw new IllegalArgumentException(String.format("attribute (%s) not declared.", attributeName));
        }
        final MetaType attributeType = attribute.getType();
        if(! attributeType.isValue(value)) {
            throw new IllegalArgumentException(String.format("invalid attribute value (%s), should be (%s).", attributeName, attributeType));
        }
        attributeValues.put(attributeName, value);
    }

    /**
     * Get an attribute value.
     *
     * @param attributeName the attribute name
     * @return the attribute value, <code>null</code> if not set
     */
    public MetaValue getAttribute(final String attributeName) {
        checkIdOnly();
        if(attributeName == null) {
            throw new IllegalArgumentException("null attribute name");
        }
        return attributeValues.get(attributeName);
    }

    /**
     * Get an attribute value.
     *
     * @param <T> the expected <code>MetaValue</code> type
     * @param attributeName the attribute name
     * @param expected the expected class
     * @return the attribute value, <code>null</code> if not set
     */
    public <T extends MetaValue> T getAttribute(final String attributeName, Class<T> expected) {
        checkIdOnly();
        final MetaValue value = getAttribute(attributeName);
        if (value == null) {
            return null;
        }
        return expected.cast(value);
    }

    /**
     * Get a child entity.
     *
     * @param id the entity id
     * @return the model entity, <code>null</code> if it does not exist
     */
    public ManagedResource getChildEntity(final EntityId id) {
        if(id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        final EntityIdType type = new EntityIdType(id.getElementName()); // TODO
        ManagedResourceChildren children = this.children.get(type);
        if(children == null) {
            return null;
        }
        return children.getChild(id);
    }

    /**
     * Add a child entity
     *
     * @param entity the model entity to add
     */
    protected void addChildEntity(final ManagedResource entity) {
        if(entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        final EntityId id = entity.getAddress().getLastElement();
        addChildEntity(id, entity);
    }

    /**
     * Add a child entity.
     *
     * @param id the entity id
     * @param entity the mode entity
     */
    protected void addChildEntity(final EntityId id, final ManagedResource entity) {
        if(id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        if(entity == null) {
            throw new IllegalArgumentException("null entity");
        }
        final EntityIdType type = new EntityIdType(id.getElementName()); // TODO
        ManagedResourceChildren children = this.children.get(type);
        if(children == null) {
            final ManagedResourceChildrenInfo info = entityInfo.getChildInfo(type);
            if(info == null) {
                throw new IllegalArgumentException();
            }
            children = new ManagedResourceChildren(info);
            this.children.put(type, children);
        }
        children.addChild(id, entity);
    }

    /**
     * Remove a child from this entity.
     *
     * @param id the entity id
     * @return true if the entity was removed, false otherwise
     */
    protected boolean removeChildEntity(final EntityId id) {
        if(id == null) {
            throw new IllegalArgumentException("null entity id");
        }
        final EntityIdType type = new EntityIdType(id.getElementName()); // TODO
        final ManagedResourceChildren children = this.children.get(type);
        if(children == null) {
            return false;
        } else {
            return children.removeChild(id);
        }
    }

    /**
     * Get the children for a given type.
     *
     * @param entityType the entity type
     * @return the entities
     */
    public Collection<ManagedResource> getChildren(EntityIdType entityType) {
        if(entityType == null) {
            throw new IllegalArgumentException("null entity type");
        }
        final ManagedResourceChildren children = this.children.get(entityType);
        if(children != null) {
            return children.getChildren();
        } else {
            return Collections.emptySet();
        }
    }

    public ManagedResource getChildEntity(final ManagedResourceAddress relativeAddress) {
        ManagedResource element = this;
        for (int i = 0; i < relativeAddress.size(); i++) {
            element = element.getChildEntity(relativeAddress.get(i));
            if (element == null) {
                return null;
            }
        }
        return element;
    }

    /**
     * Gets whether this entity represents the root of a model, against which
     * all addresses are relative.
     *
     * @return <code>true</code> if this is the root entity; <code>false</code> if
     *          it is a descendant of the root
     */
    public boolean isRoot() {
        return this.address == ManagedResourceAddress.ROOT;
    }

    /**
     *
     * @return
     */
    public boolean isIdOnly() {
        return this.idOnly;
    }

    private void checkIdOnly() {
        if (idOnly) {
            throw new IllegalStateException("Element is id-only; content cannot be accessed");
        }
    }

}
