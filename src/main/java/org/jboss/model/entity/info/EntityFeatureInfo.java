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

import org.jboss.model.types.Named;

/**
 * TODO add class javadoc for EntityFeatureInfo.
 *
 * @author Brian Stansberry
 * @author Emanuel Muckenhuber
 */
public class EntityFeatureInfo implements Named, Serializable, Cloneable {

    private static final long serialVersionUID = -6601713831278724297L;

    /**
     * The name of the feature. It is recommended that subclasses call
     * {@link #getName} rather than reading this field, and that they not change
     * it.
     *
     * @serial The name of the feature.
     */
    private final String name;

    /**
     * The human-readable description of the feature. It is recommended that
     * subclasses call {@link #getDescription} rather than reading this field,
     * and that they not change it.
     *
     * @serial The human-readable description of the feature.
     */
    private final String description;

    /** The fields. */
    private final Fields fields;

    /**
     * Constructs an <CODE>EntityFeatureInfo</CODE> object. This constructor is
     * equivalent to {@code MBeanFeatureInfo(name, description, (Descriptor) null}.
     *
     * @param name The name of the feature.
     * @param description A human readable description of the feature.
     */
    public EntityFeatureInfo(String name, String description) {
        this(name, description, null);
    }

    /**
     * Constructs an <CODE>EntityFeatureInfo</CODE> object.
     *
     * @param name The name of the feature.
     * @param description A human readable description of the feature.
     * @param descriptor The descriptor for the feature. This may be null which is
     *            equivalent to an empty descriptor.
     */
    public EntityFeatureInfo(String name, String description, Fields fields) {
        this.name = name;
        this.description = description;
        this.fields = fields;
    }

    /**
     * Returns the name of the feature.
     *
     * @return the name of the feature.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the human-readable description of the feature.
     *
     * @return the human-readable description of the feature.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the fields
     */
    public Fields getFields() {
        return fields;
    }

    /**
     * Compare this MBeanFeatureInfo to another.
     *
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an EntityFeatureInfo such
     *         that its {@link #getName()}, {@link #getDescription()}, and
     *         {@link #getFields()} values are equal (not necessarily
     *         identical) to those of this MBeanFeatureInfo.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EntityFeatureInfo)) {
            return false;
        }
        EntityFeatureInfo p = (EntityFeatureInfo) o;
        return (p.getName().equals(getName()) && p.getDescription().equals(getDescription()) && p.getFields().equals(getFields()));
    }

    @Override
    public int hashCode() {
        return getName().hashCode() ^ getDescription().hashCode() ^ getFields().hashCode();
    }

}
