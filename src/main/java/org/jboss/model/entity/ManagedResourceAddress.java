/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2000 - 2008, Red Hat Middleware LLC, and individual contributors
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A ManagedResourceAddress is a list of {@link EntityId address parts}, which
 * represent a path to a particular element in a model.
 * <p/>
 * This name can be absolute (i.e., relative from the root node - {@link #ROOT}
 * ), or relative to any node in the cache. Reading the documentation on each
 * API call that makes use of {@link ManagedResourceAddress}es will tell you whether the
 * API expects a relative or absolute ManagedResourceAddress.
 * <p/>
 *
 * @version $Revision: 8221 $
 */
public final class ManagedResourceAddress implements Comparable<ManagedResourceAddress>, Serializable {

    private static final long serialVersionUID = -6901735117605327068L;

    /**
     * Separator between ManagedResourceAddress elements.
     */
    public static final String SEPARATOR = "/";

    protected final EntityId[] elements;
    private transient volatile int hash_code = 0;

    /**
     * Immutable root ManagedResourceAddress.
     */
    public static final ManagedResourceAddress ROOT = new ManagedResourceAddress();

    /**
     * A cached string representation of this ManagedResourceAddress, used by toString
     * so it isn't calculated again every time.
     */
    protected String stringRepresentation;

    // ----------------- START: Private constructors for use by factory methods
    // only. ----------------------

    /**
     * Public to satisfy Externalization.
     */
    public ManagedResourceAddress() {
        elements = new EntityId[] {};
    }

    /**
     * If safe is false, Collections.unmodifiableList() is used to wrap the list
     * passed in. This is an optimisation so ManagedResourceAddress.fromString(),
     * probably the most frequently used factory method, doesn't end up needing
     * to use the unmodifiableList() since it creates the list internally.
     *
     * @param elements
     *            List of element ids
     * @param safe
     *            whether this list is referenced externally (safe = false) or
     *            not (safe = true).
     */
    private ManagedResourceAddress(EntityId[] elements, boolean safe) {
        if (elements != null) {
            validateIds(elements);
            // if not safe make a defensive copy
            if (safe)
                this.elements = elements;
            else {
                this.elements = new EntityId[elements.length];
                System.arraycopy(elements, 0, this.elements, 0, elements.length);
            }
        } else {
            this.elements = new EntityId[] {};
        }
    }

    private ManagedResourceAddress(ManagedResourceAddress base, EntityId... childIds) {
        validateIds(childIds);
        elements = new EntityId[base.elements.length + childIds.length];
        System.arraycopy(base.elements, 0, elements, 0, base.elements.length);
        System.arraycopy(childIds, 0, elements, base.elements.length, childIds.length);
    }

    private static void validateIds(EntityId[] names) {
        for (int i = 0; i < names.length; i++) {
            if (names[i] == null) {
                throw new IllegalArgumentException(String.format("%s %d is null", EntityId.class.getSimpleName(), i));
            }
        }
    }

    // ----------------- END: Private constructors for use by factory methods
    // only. ----------------------

    /**
     * Retrieves a ManagedResourceAddress that represents the list of elements passed
     * in.
     *
     * @param elements
     *            list of elements that comprise the ManagedResourceAddress
     * @return a ManagedResourceAddress
     */
    public static ManagedResourceAddress fromList(List<EntityId> elements) {
        return new ManagedResourceAddress(elements.toArray(new EntityId[elements.size()]), true);
    }

    /**
     * Retrieves a ManagedResourceAddress that represents the array of elements passed
     * in.
     *
     * @param elements
     *            array of elements that comprise the ManagedResourceAddress
     *
     * @return a ManagedResourceAddress
     */
    public static ManagedResourceAddress fromElements(EntityId... elements) {
        return new ManagedResourceAddress(elements, true);
    }

    /**
     * Retrieves a ManagedResourceAddress that represents the absolute ManagedResourceAddress of
     * the relative ManagedResourceAddress passed in.
     *
     * @param base
     *            base ManagedResourceAddress
     * @param relative
     *            relative ManagedResourceAddress
     * @return a ManagedResourceAddress
     */
    public static ManagedResourceAddress fromRelativeAddress(ManagedResourceAddress base, ManagedResourceAddress relative) {
        return new ManagedResourceAddress(base, relative.elements);
    }

    /**
     * Retrieves a ManagedResourceAddress that represents the List<EntityId> of
     * elements passed in, relative to the base ManagedResourceAddress.
     *
     * @param base
     *            base ManagedResourceAddress
     * @param relativeElements
     *            relative List<EntityId> of elements
     * @return a ManagedResourceAddress
     */
    public static ManagedResourceAddress fromRelativeList(ManagedResourceAddress base, List<EntityId> relativeElements) {
        return new ManagedResourceAddress(base, relativeElements.toArray(new EntityId[relativeElements.size()]));
    }

    /**
     * Retrieves a ManagedResourceAddress that represents the array of elements passed
     * in, relative to the base ManagedResourceAddress.
     *
     * @param base
     *            base ManagedResourceAddress
     * @param relativeElements
     *            relative elements
     * @return a ManagedResourceAddress
     */
    public static ManagedResourceAddress fromRelativeElements(ManagedResourceAddress base, EntityId... relativeElements) {
        return new ManagedResourceAddress(base, relativeElements);
    }

    /**
     * Returns a new ManagedResourceAddress from a string, where the elements are
     * deliminated by one or more separator ({@link #SEPARATOR}) characters.
     *
     * @param stringRepresentation
     *            String representation of the ManagedResourceAddress
     * @return an ManagedResourceAddress constructed from the string representation passed
     *         in
     */
    public static ManagedResourceAddress fromString(String stringRepresentation) {
        if (stringRepresentation == null || stringRepresentation.equals(SEPARATOR) || stringRepresentation.equals(""))
            return root();

        String toMatch = stringRepresentation.startsWith(SEPARATOR) ? stringRepresentation.substring(1)
                : stringRepresentation;
        String[] el = toMatch.split(SEPARATOR);
        EntityId[] parts = new EntityId[el.length];
        for (int i = 0; i < parts.length; i++) {
            parts[i] = EntityId.fromString(el[i]);
        }
        return new ManagedResourceAddress(parts, true);
    }

    /**
     * Obtains an ancestor of the current ManagedResourceAddress. Literally performs
     * <code>elements.subList(0, generation)</code> such that if
     * <code> generation == ManagedResourceAddress.size() </code> then the return value
     * is the ManagedResourceAddress itself (current generation), and if
     * <code> generation == ManagedResourceAddress.size() - 1 </code> then the return
     * value is the same as <code> ManagedResourceAddress.getParent() </code> i.e., just
     * one generation behind the current generation.
     * <code> generation == 0 </code> would return ManagedResourceAddress.ROOT.
     *
     * @param generation
     *            the generation of the ancestor to retrieve
     * @return an ancestor of the current ManagedResourceAddress
     */
    public ManagedResourceAddress getAncestor(int generation) {
        if (generation == 0)
            return root();
        return getSubAddress(0, generation);
    }

    /**
     * Obtains a sub-address from the given ManagedResourceAddress.
     *
     * @param startIndex
     *            starting index
     * @param endIndex
     *            end index
     * @return a sub-ManagedResourceAddress
     */
    public ManagedResourceAddress getSubAddress(int startIndex, int endIndex) {
        if (endIndex < startIndex)
            throw new IllegalArgumentException("End index cannot be less than start index!");
        int len = endIndex - startIndex;
        EntityId[] subElements = new EntityId[len];
        System.arraycopy(elements, startIndex, subElements, 0, len);
        return new ManagedResourceAddress(subElements, true);
    }

    /**
     * @return the number of elements in the ManagedResourceAddress. The root node
     *         contains zero.
     */
    public int size() {
        return elements.length;
    }

    /**
     * @param n
     *            index of the element to return
     * @return Returns the nth element in the ManagedResourceAddress.
     */
    public EntityId get(int n) {
        return elements[n];
    }

    /**
     * @return the last element in the ManagedResourceAddress.
     * @see #getLastElementAsString
     */
    public EntityId getLastElement() {
        if (isRoot())
            return null;
        return elements[elements.length - 1];
    }

    /**
     * @param element
     *            element to find
     * @return true if the ManagedResourceAddress contains this element, false otherwise.
     */
    public boolean hasElement(EntityId element) {
        return indexOf(element) != -1;
    }

    public List<EntityIdType> getEntityIdTypes() {
        List<EntityIdType> result = new ArrayList<EntityIdType>(elements.length);
        for (EntityId id : elements) {
            result.add(new EntityIdType(id.getElementName(), id.getIdAttributeName()));
        }
        return result;
    }

    /**
     * Returns true if obj is a ManagedResourceAddress with the same elements.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ManagedResourceAddress)) {
            return false;
        }
        ManagedResourceAddress other = (ManagedResourceAddress) obj;
        if (elements.length != other.elements.length)
            return false;
        // compare elements in *reverse*!
        for (int i = elements.length - 1; i >= 0; i--) {
            if (!safeEquals(elements[i], other.elements[i]))
                return false;
        }
        return true;
    }

    /**
     * Returns a hash code with ManagedResourceAddress elements.
     */
    @Override
    public int hashCode() {
        if (hash_code == 0) {
            hash_code = calculateHashCode();
        }
        return hash_code;
    }

    /**
     * Returns this ManagedResourceAddress as a string, prefixing the first element with a
     * {@link ManagedResourceAddress#SEPARATOR} and joining each subsequent element with
     * a {@link ManagedResourceAddress#SEPARATOR}. If this is the root ManagedResourceAddress, returns
     * {@link ManagedResourceAddress#SEPARATOR}.
     */
    @Override
    public String toString() {
        if (stringRepresentation == null) {
            stringRepresentation = getStringRepresentation(elements);
        }
        return stringRepresentation;
    }

    /**
     * Returns true if this ManagedResourceAddress is a child of parent.
     *
     * @param parent
     *            candidate parent to test against
     * @return true if the target is a child of parent
     */
    public boolean isChildOf(ManagedResourceAddress parent) {
        return parent.elements.length != elements.length && isChildOrEquals(parent);
    }

    /**
     * Returns true if this ManagedResourceAddress is a <i>direct</i> child of a given
     * ManagedResourceAddress.
     *
     * @param parent
     *            parent to compare with
     * @return true if this is a direct child, false otherwise.
     */
    public boolean isDirectChildOf(ManagedResourceAddress parent) {
        return elements.length == parent.elements.length + 1 && isChildOf(parent);
    }

    /**
     * Returns true if this ManagedResourceAddress is equal to or the child of parent.
     *
     * @param parent
     *            candidate parent to test against
     * @return true if this ManagedResourceAddress is equals or the child of parent.
     */
    public boolean isChildOrEquals(ManagedResourceAddress parent) {
        EntityId[] parentElems = parent.elements;
        if (parentElems.length > elements.length) {
            return false;
        }
        for (int i = parentElems.length - 1; i >= 0; i--) {
            if (!parentElems[i].equals(elements[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates a hash code by summing the hash code of all elements.
     *
     * @return a calculated hashcode
     */
    protected int calculateHashCode() {
        int hashCode = 19;
        for (Object o : elements)
            hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
        if (hashCode == 0)
            hashCode = 0xDEADBEEF; // degenerate case
        return hashCode;
    }

    private String getStringRepresentation(EntityId[] elements) {
        StringBuilder builder = new StringBuilder();
        for (EntityId e : elements) {
            // incase user element 'e' does not implement equals() properly,
            // don't rely on their implementation.
            if (!SEPARATOR.equals(e) && !"".equals(e)) {
                builder.append(SEPARATOR);
                builder.append(e);
            }
        }
        return builder.length() == 0 ? SEPARATOR : builder.toString();
    }

    /**
     * Returns the parent of this parent. The parent of the root node is
     * {@link #ROOT}.
     *
     * @return the parent parent
     */
    public ManagedResourceAddress getParent() {
        switch (elements.length) {
        case 0:
        case 1:
            return root();
        default:
            return getSubAddress(0, elements.length - 1);
        }
    }

    public static ManagedResourceAddress root() {
        return ROOT;
    }

    /**
     * Returns true if this is a root ManagedResourceAddress.
     *
     * @return true if the ManagedResourceAddress is {@link ManagedResourceAddress#ROOT}.
     */
    public boolean isRoot() {
        return elements.length == 0;
    }

    /**
     * If this is the root, returns {@link ManagedResourceAddress#SEPARATOR}.
     *
     * @return a String representation of the last element that makes up this
     *         ManagedResourceAddress.
     */
    public String getLastElementAsString() {
        if (isRoot()) {
            return SEPARATOR;
        } else {
            Object last = getLastElement();
            if (last instanceof String)
                return (String) last;
            else
                return String.valueOf(getLastElement());
        }
    }

    /**
     * Peeks into the elements that build up this parent. The list returned is
     * read-only, to maintain the immutable nature of parent.
     *
     * @return an unmodifiable list
     */
    public List<EntityId> peekElements() {
        return Arrays.asList(elements);
    }

    private int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < elements.length; i++)
                if (elements[i] == null)
                    return i;
        } else {
            for (int i = 0; i < elements.length; i++)
                if (o.equals(elements[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Compares this ManagedResourceAddress to another using {@link ManagedResourceAddressComparator}.
     */
    public int compareTo(ManagedResourceAddress address) {
        return ManagedResourceAddressComparator.INSTANCE.compare(this, address);
    }

    /**
     * Creates a new ManagedResourceAddress whose ancestor has been replaced with the
     * new ancestor passed in.
     *
     * @param oldAncestor
     *            old ancestor to replace
     * @param newAncestor
     *            nw ancestor to replace with
     * @return a new ManagedResourceAddress with ancestors replaced.
     */
    public ManagedResourceAddress replaceAncestor(ManagedResourceAddress oldAncestor, ManagedResourceAddress newAncestor) {
        if (!isChildOf(oldAncestor))
            throw new IllegalArgumentException("Old ancestor must be an ancestor of the current ManagedResourceAddress!");
        ManagedResourceAddress subAddress = this.getSubAddress(oldAncestor.size(), size());
        return ManagedResourceAddress.fromRelativeAddress(newAncestor, subAddress);
    }

    private static boolean safeEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
}
