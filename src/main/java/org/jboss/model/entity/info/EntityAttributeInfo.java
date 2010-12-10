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

import org.jboss.model.types.MetaType;

/**
 * TODO add class javadoc for EntityAttributeInfo.
 *
 * @author Brian Stansberry
 */
public class EntityAttributeInfo extends EntityFeatureInfo {

    private static final long serialVersionUID = 5982298740361044908L;

    /**
     * The actual attribute type.
     */
    private final MetaType attributeType;

    /**
     * Constructs an <CODE>EntityAttributeInfo</CODE> object.
     *
     * @param name
     *            The name of the attribute.
     * @param type
     *            The type of the attribute.
     * @param description
     *            A human readable description of the attribute.
     */
    public EntityAttributeInfo(String name, MetaType type, String description) {
        this(name, type, description, null);
    }

    /**
     * Constructs an <CODE>EntityAttributeInfo</CODE> object.
     *
     * @param name
     *            The name of the attribute.
     * @param type
     *            The type or class name of the attribute.
     * @param description
     *            A human readable description of the attribute.
     * @param descriptor
     *            The descriptor for the attribute. This may be null which is
     *            equivalent to an empty descriptor.
     *
     */
    public EntityAttributeInfo(String name, MetaType type, String description, Fields fields) {
        super(name, description, fields);

        this.attributeType = type;
    }

    /**
     * <p>
     * Returns a shallow clone of this instance. The clone is obtained by simply
     * calling <tt>super.clone()</tt>, thus calling the default native shallow
     * cloning mechanism implemented by <tt>Object.clone()</tt>. No deeper
     * cloning of any internal field is made.
     * </p>
     *
     * <p>
     * Since this class is immutable, cloning is chiefly of interest to
     * subclasses.
     * </p>
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // should not happen as this class is cloneable
            return null;
        }
    }

    /**
     * Returns the type of the attribute.
     *
     * @return the class name.
     */
    public MetaType getType() {
        return attributeType;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + "description=" + getDescription() + ", " + "name=" + getName() + ", "
                + "type=" + getType() + ", " + "fields=" + getFields() + "]";
    }

    /**
     * Compare this MBeanAttributeInfo to another.
     *
     * @param o
     *            the object to compare to.
     *
     * @return true if and only if <code>o</code> is an MBeanAttributeInfo such
     *         that its {@link #getName()}, {@link #getType()},
     *         {@link #getDescription()}, {@link #isReadable()},
     *         {@link #isWritable()}, and {@link #isIs()} values are equal (not
     *         necessarily identical) to those of this MBeanAttributeInfo.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EntityAttributeInfo))
            return false;
        EntityAttributeInfo p = (EntityAttributeInfo) o;
        return (p.getName().equals(getName()) && p.getType().equals(getType())
                && p.getDescription().equals(getDescription()) && p.getFields().equals(getFields()));
    }

    /*
     * We do not include everything in the hashcode. We assume that if two
     * operations are different they'll probably have different names or types.
     * The penalty we pay when this assumption is wrong should be less than the
     * penalty we would pay if it were right and we needlessly hashed in the
     * description and parameter array.
     */
    @Override
    public int hashCode() {
        return getName().hashCode() ^ getType().hashCode();
    }
}
