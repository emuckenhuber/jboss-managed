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
 * One element in a {@link ManagedResourceAddress}; the portion of the address that
 * uniquely identifies a particular model element within the portion of the
 * model identified by by preceding elements in the ManagedResourceAddress.
 *
 * @author Brian Stansberry
 */
public class EntityId implements Comparable<EntityId>, Serializable {

    private static final long serialVersionUID = 2737753027936998087L;

    private final String elementName;
    private final String idAttributeName;

    private final String idAttributeValue;

    /**
     * Parses a string in the format returned by {@link #toString()} back into
     * an EntityId.
     *
     * @param stringForm
     *            the string form. Cannot be <code>null</code>
     *
     * @return the EntityId
     *
     * @throws IllegalArgumentException
     *             if <code>stringForm</code> is <code>null</code> or isn't
     *             properly formatted
     */
    public static EntityId fromString(final String stringForm) {
        if (stringForm == null)
            throw new IllegalArgumentException("stringForm is null");

        int idx = stringForm.indexOf("[@");
        if (idx == -1) {
            return new EntityId(stringForm, null, null);
        } else if (idx == stringForm.length() - 2) {
            throw new IllegalArgumentException(stringForm + " contains an id "
                    + "attribute delimiter ('@') but does not contain an id attribute");
        } else if (!stringForm.endsWith("']")) {
            throw new IllegalArgumentException(stringForm + " contains an id "
                    + "attribute delimiter (\"[@'\") but does not property terminate "
                    + "the id attribute section with \"']");
        } else {
            String elementName = stringForm.substring(0, idx);
            // Trim foo[@bar='baz'] down to bar='baz
            String idAttrStr = stringForm.substring(idx + 2, stringForm.length() - 2);
            idx = idAttrStr.indexOf("='");
            if (idx == -1 || idx == idAttrStr.length() - 2) {
                throw new IllegalArgumentException(stringForm + " contains an id "
                        + "attribute section (\"[@'...']\") but does not contain an id attribute value");
            }
            String idAttr = idAttrStr.substring(0, idx);
            String idAttrVal = idAttrStr.substring(idx + 2);
            return new EntityId(elementName, idAttr, idAttrVal);
        }
    }

    /**
     * Creates a new EntityId with the given element name and a
     * <code>null</code> {@link #getIdAttributeName() id attribute}.
     *
     * @param elementName
     *            the name of the element. Cannot be <code>null</code>
     */
    public EntityId(final String elementName) {
        this(elementName, null, null);
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
     * @param idAttributeValue
     *            the value of the attribute. Cannot be <code>null</code> if
     *            <code>idAttributeName</code> is not <code>null</code>
     */
    public EntityId(final String elementName, final String idAttributeName, final String idAttributeValue) {
        if (elementName == null) {
            throw new IllegalArgumentException("elementName is null");
        }
        if (idAttributeName != null && idAttributeValue == null) {
            throw new IllegalArgumentException("idAttributeValue is null");
        }
        this.elementName = elementName;
        this.idAttributeName = idAttributeName;
        this.idAttributeValue = idAttributeName == null ? null : idAttributeValue;
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

    /**
     * Gets the value of the attribute that makes this element unique compared
     * to all other elements with the same element name, or <code>null</code> if
     * the {@link #getElementName() element name} alone makes this id unique
     *
     * @return the value of the attribute
     */
    public String getIdAttributeValue() {
        return idAttributeValue;
    }

    @Override
    public int hashCode() {
        int hash = 19;
        hash += 31 * elementName.hashCode();
        if (idAttributeName != null) {
            hash += 31 * idAttributeName.hashCode();
            hash += 31 * idAttributeValue.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = this == obj;
        if (!equals && obj instanceof EntityId) {
            EntityId other = (EntityId) obj;
            equals = (elementName.equals(other.elementName) && safeEquals(idAttributeName, other.idAttributeName) && safeEquals(
                    idAttributeValue, other.idAttributeValue));
        }
        return equals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elementName);
        if (idAttributeName != null) {
            sb.append("[@");
            sb.append(idAttributeName);
            sb.append("='");
            sb.append(idAttributeValue);
            sb.append("']");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(EntityId o) {
        int result = elementName.compareTo(o.elementName);
        if (result == 0) {
            if (idAttributeName == null) {
                result = o.idAttributeName == null ? 0 : -1;
            } else {
                result = idAttributeName.compareTo(o.idAttributeName);
                if (result == 0) {
                    result = idAttributeValue.compareTo(o.idAttributeValue);
                }
            }
        }
        return result;
    }

    private static boolean safeEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
