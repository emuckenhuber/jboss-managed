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

package org.jboss.test.model.types.suport;

import java.io.Serializable;

import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.values.SimpleValue;

/**
 * MockSimpleValue.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class MockSimpleValue extends MockMetaValue implements SimpleValue {

    /** The serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new MockSimpleValue.
     *
     * @param metaType the meta type
     */
    public MockSimpleValue(SimpleMetaType metaType) {
        super(metaType);
    }

    @SuppressWarnings("unchecked")
    public SimpleMetaType getMetaType() {
        return (SimpleMetaType) super.getMetaType();
    }

    public Serializable getValue() {
        throw new IllegalStateException("getValue");
    }

    public int compareTo(SimpleValue sv) {
        int compare = -1;
        if (equals(sv))
            compare = 0;
        return compare;
    }

    /** {@inheritDocs} */
    public String asString() {
        return null;
    }

    /** {@inheritDocs} */
    public Integer asInteger() {
        return null;
    }

    /** {@inheritDocs} */
    public Float asFloat() {
        return null;
    }

    /** {@inheritDocs} */
    public Short asShort() {
        return null;
    }

    /** {@inheritDocs} */
    public Boolean asBoolean() {
        return null;
    }

    /** {@inheritDocs} */
    public Byte asByte() {
        return null;
    }

    /** {@inheritDocs} */
    public Number asNumber() {
        return null;
    }
}
