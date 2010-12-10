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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Emanuel Muckenhuber
 */
public class Fields implements Serializable, Cloneable {

    private static final long serialVersionUID = -7634822320747727964L;

    /** The fields. */
    private final Map<String, Object> fields = new HashMap<String, Object>();

    public Object getField(final String name) {
        return fields.get(name);
    }

    public <T> T getField(final String name, Class<T> expected) {
        final Object value = getField(name);
        if(value == null) {
            return null;
        }
        return expected.cast(value);
    }

    public void setField(final String name, final Object value) {
        fields.put(name, value);
    }

    public Object removeField(final String name) {
        return fields.remove(name);
    }

    public int hashCode() {
        return fields.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj == null || (obj instanceof Fields) == false) {
            return false;
        }
        final Fields other = Fields.class.cast(obj);
        return fields.equals(other.fields);
    }

    public Fields clone() {
        try {
            return (Fields) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Unexpected error in clone: ", e);
        }
    }

}
