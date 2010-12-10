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

package org.jboss.model.detyped.info;

import org.jboss.metatype.api.types.MetaType;

/**
 * TODO add class javadoc for EntityParameterInfo.
 *
 * @author Brian Stansberry
 */
public class EntityParameterInfo extends EntityFeatureInfo {

    private static final long serialVersionUID = -1934506813806183055L;

    /** The parameter type. */
    private final MetaType type;

    /**
     * @param name the name of the data
     * @param type the type of the data
     * @param description a human readable description of the data. Can be <code>null</code>.
     */
    public EntityParameterInfo(String name, MetaType type, String description) {
        this(name, type, description, null);
    }

    /**
     * @param name the name of the data
     * @param type the type of the data
     * @param description a human readable description of the data. Can be <code>null</code>.
     * @param fields the fields for the operation.  Can be <code>null</code>
     * which is equivalent to an empty descriptor.
     */
    public EntityParameterInfo(String name, MetaType type, String description, Fields fields) {
        super(name, description, fields);
        this.type = type;
    }

    /**
     * Returns the type of the data.
     *
     * @return the type
     */
    public MetaType getType() {
        return type;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + "description=" + getDescription() + ", " + "name=" + getName() + ", "
                + "type=" + getType() + ", " + "fields=" + getFields() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof EntityParameterInfo) {
            EntityParameterInfo p = (EntityParameterInfo) o;
            return (p.getName().equals(getName()) && p.getType().equals(getType())
                    && p.getDescription().equals(getDescription()) && p.getFields().equals(getFields()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() ^ getType().hashCode();
    }

}
