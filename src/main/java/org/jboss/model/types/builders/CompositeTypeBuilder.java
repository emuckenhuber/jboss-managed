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

import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.MetaType;
import org.jboss.model.types.Named;

/**
 * @author Emanuel Muckenhuber
 */
public interface CompositeTypeBuilder {

    /**
     * Add an item to the composite type.
     *
     * @param itemName the item name
     * @param itemType the item type
     * @return the composite builder
     */
    CompositeTypeBuilder addItem(final String itemName, final MetaType itemType);

    /**
     * Add an item to the composite type.
     *
     * @param itemName the item name
     * @param description the item description
     * @param itemType the item type
     * @return the composite builder
     */
    CompositeTypeBuilder addItem(final String itemName, final String description, final MetaType itemType);

    /**
     * Add an item to the composite type.
     *
     * @param itemName the item name
     * @param itemType the item type
     * @return the composite builder
     */
    CompositeTypeBuilder addItem(final Named itemName, final MetaType itemType);

    /**
     * Add an item to the composite type.
     *
     * @param itemName the item name
     * @param description the item description
     * @param itemType the item type
     * @return the composite builder
     */
    CompositeTypeBuilder addItem(final Named itemName, final String description, final MetaType itemType);

    /**
     * Create the composite type.
     *
     * @return the composite type
     */
    CompositeMetaType create();

    /**
     * Create a composite map meta type.
     *
     * @param index the item name which should be used as index
     * @return the composite map meta type
     */
    CompositeMapMetaType createMapMetaType(final String index);

    /**
     * Create a composite map meta type.
     *
     * @param index the item name which should be used as index
     * @return the composite map meta type
     */
    CompositeMapMetaType createMapMetaType(final Named index);

    /**
     * Create a composite map meta type.
     *
     * @param index the item name which should be used as index
     * @param description the type description
     * @return the composite map meta type
     */
    CompositeMapMetaType createMapMetaType(final String index, final String description);

    /**
     * Create a composite map meta type.
     *
     * @param index the item name which should be used as index
     * @param description the type description
     * @return the composite map meta type
     */
    CompositeMapMetaType createMapMetaType(final Named index, final String description);

}
