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

/**
 * One element in a {@link EntityAddress}; the portion of the address that
 * uniquely identifies a particular model element within the portion of the
 * model identified by by preceding elements in the EntityAddress.
 *
 * @author Brian Stansberry
 */
public class EntityIdType implements Serializable {

    private static final long serialVersionUID = 2737753027936998087L;

    private final String elementName;
    private final String idAttributeName;

    /**
     * Creates a new EntityId with the given element name and a
     * <code>null</code> {@link #getIdAttributeName() id attribute}.
     *
     * @param elementName
     *            the name of the element. Cannot be <code>null</code>
     */
    public EntityIdType(final String elementName) {
        this(elementName, null);
    }

    /**
     * Creates a new EntityId with the given element name and an optional
     * attribute name and value that make this id unique among all elements that
     * share the same parent.
     *
     * @param elementName
     *            the name of the element. Cannot be <code>null</code>
     * @param idAttributeName
     *            the name of the attribute. Can be <code>null</code>
     */
    public EntityIdType(final String elementName, final String idAttributeName) {
        if (elementName == null) {
            throw new IllegalArgumentException("elementName is null");
        }
        this.elementName = elementName;
        this.idAttributeName = idAttributeName;
    }

    /**
     * Gets the name of this element in the model.
     *
     * @return the name. Will not be <code>null</code>
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Gets the name of the attribute that makes this element unique compared to
     * all other elements with the same element name, or <code>null</code> if
     * the {@link #getElementName() element name} alone makes this id unique
     *
     * @return the name of the attribute
     */
    public String getIdAttributeName() {
        return idAttributeName;
    }

    @Override
    public int hashCode() {
        int hash = 19;
        hash += 31 * elementName.hashCode();
        if (idAttributeName != null) {
            hash += 31 * idAttributeName.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = this == obj;
        if (!equals && obj instanceof EntityIdType) {
            EntityIdType other = (EntityIdType) obj;
            equals = (elementName.equals(other.elementName) && safeEquals(idAttributeName, other.idAttributeName));
        }
        return equals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elementName);
        if (idAttributeName != null) {
            sb.append("[@");
            sb.append(idAttributeName);
            sb.append(']');
        }
        return sb.toString();
    }

    private static boolean safeEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
