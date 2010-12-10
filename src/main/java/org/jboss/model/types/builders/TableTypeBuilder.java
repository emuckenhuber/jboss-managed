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

package org.jboss.model.types.builders;

import org.jboss.model.types.MetaType;
import org.jboss.model.types.TableMetaType;

/**
 * @author Emanuel Muckenhuber
 */
public interface TableTypeBuilder {

    /**
     * Add a column to the table type.
     *
     * @param name the column name
     * @param type the column type
     * @return the table builder
     */
    TableTypeBuilder addColumn(final String name, final MetaType type);

    /**
     * Add a column to the table type.
     *
     * @param name the column name
     * @param type the column type
     * @param description the column description
     * @return the table builder
     */
    TableTypeBuilder addColumn(final String name, final String description, final MetaType type);

    /**
     * Add an index column to the table type.
     *
     * @param name the column name
     * @param type the column type
     * @return the table builder
     */
    TableTypeBuilder addIndexColumn(final String name, final MetaType type);

    /**
     * Add an index column to the table type.
     *
     * @param name the column name
     * @param type the column type
     * @param description the column description
     * @return the table builder
     */
    TableTypeBuilder addIndexColumn(final String name, final String description, final MetaType type);

    /**
     * Create the table meta type.
     *
     * @return the table meta type.
     */
    TableMetaType create();

}
