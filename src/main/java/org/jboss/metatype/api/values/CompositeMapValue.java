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

package org.jboss.metatype.api.values;

import java.util.Map;

import org.jboss.metatype.api.types.CompositeMapMetaType;
import org.jboss.metatype.api.types.CompositeMetaType;

/**
 * A {@link MetaValue} with a Map like APi - where the {@link CompositeValue} is the map entry and contains
 * its own index. In a nutshell a variation of the {@link TableValue} only with a single index key.
 *
 * @author Emanuel Muckenhuber
 */
public interface CompositeMapValue extends MetaValue, Map<MetaValue, CompositeValue> {

    /** {@inheritDoc} */
    CompositeMapMetaType getMetaType();

    /**
     * Gets the composite entry meta type.
     *
     * @return the entry type
     */
    CompositeMetaType getEntryType();

    /**
     * Add a composite value.
     *
     * @param value the composite value
     * @return the previous value associated with the index of the composite index key
     */
    CompositeValue put(final CompositeValue value);

}
