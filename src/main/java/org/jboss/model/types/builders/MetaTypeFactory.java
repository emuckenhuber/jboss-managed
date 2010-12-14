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

import java.util.ArrayList;
import java.util.List;

import org.jboss.model.types.CollectionMetaType;
import org.jboss.model.types.CompositeMapMetaType;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.EnumMetaType;
import org.jboss.model.types.ImmutableTableMetaType;
import org.jboss.model.types.MetaType;
import org.jboss.model.types.MutableCompositeMetaType;
import org.jboss.model.types.SimpleMetaType;
import org.jboss.model.types.SimpleTypes;
import org.jboss.model.types.TableMetaType;

/**
 * The MetaTypeFactory.
 *
 * @author Emanuel Muckenhuber
 */
public class MetaTypeFactory implements SimpleTypes {

    /**
     * Create a simple meta type.
     *
     * @param class the class
     * @return the simple type
     * @throws IllegalArgumentException for a null class or if it is not a simple type
     */
    public static SimpleMetaType createSimpleType(final Class<?> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("null class");
        }
        return createSimpleType(clazz.getName());
    }

    /**
     * Create a simple meta type.
     *
     * @param className the classname.
     * @return the simple type
     * @throws IllegalArgumentException for a null className or if it is not a simple type
     */
    public static SimpleMetaType createSimpleType(final String className) {
        return SimpleMetaType.resolve(className);
    }

    /**
     * Create an {@link EnumMetaType}.
     *
     * @param <E> The {@code Enum} type
     * @param enumClass the {@code Enum} class
     * @return the enum meta type
     */
    public static <E extends Enum<E>> EnumMetaType createEnumType(Class<E> enumClass) {
        return EnumMetaType.create(enumClass);
    }

    /**
     * Create a collection meta type.
     *
     * @param typeName the type name
     * @param elementType the element type
     * @return the collection meta type
     */
    public static CollectionMetaType createCollectionType(final String typeName, final MetaType elementType) {
        return new CollectionMetaType(typeName, elementType);
    }

    /**
     * Create a composite type builder.
     *
     * @param className the class name
     * @param description the type description
     * @return the composite type builder
     */
    public static CompositeTypeBuilder compositeTypeBuilder(String className, String description) {
        return new CompositeTypeBuilderImpl(className, description);
    }

    /**
     * Create a table type builder.
     *
     * @param typeName the type name.
     * @param description the description
     * @return the type builder
     */
    public static TableTypeBuilder tableTypeBuilder(String typeName, String description) {
        return new TableTypeBuilderImpl(typeName, description);
    }

    static class CompositeTypeBuilderImpl implements CompositeTypeBuilder{

        private final MutableCompositeMetaType composite;

        CompositeTypeBuilderImpl(final String typeName, final String description) {
            this.composite = new MutableCompositeMetaType(typeName, description);
        }

        public CompositeTypeBuilderImpl addItem(final String itemName, final MetaType itemType) {
            addItem(itemName, itemName, itemType);
            return this;
        }

        public CompositeTypeBuilderImpl addItem(final String itemName, final String description, final MetaType itemType) {
            composite.addItem(itemName, description, itemType);
            return this;
        }

        public CompositeMetaType create() {
            composite.freeze();
            return composite;
        }

        public CompositeMapMetaType createMapMetaType(final String index) {
            final CompositeMetaType composite = create();
            return new CompositeMapMetaType(composite, index, composite.getDescription());
        }

        public CompositeMapMetaType createMapMetaType(String index, String description) {
            final CompositeMetaType composite = create();
            return new CompositeMapMetaType(composite, index, description);
        }
    }

    static class TableTypeBuilderImpl implements TableTypeBuilder {

        private final CompositeTypeBuilder compositeTypeBuilder;
        private final String typeName;
        private final String description;
        private List<String> indexes = new ArrayList<String>();

        TableTypeBuilderImpl(final String typeName, final String description) {
            this.typeName = typeName;
            this.description = description;
            this.compositeTypeBuilder = new CompositeTypeBuilderImpl(typeName, description);
        }

        public TableTypeBuilder addColumn(final String name, final MetaType type) {
            compositeTypeBuilder.addItem(name, type);
            return this;
        }

        public TableTypeBuilder addColumn(final String name, final String description, final MetaType type) {
            compositeTypeBuilder.addItem(name, description, type);
            return this;
        }

        public TableTypeBuilder addIndexColumn(final String name, final MetaType type) {
            addIndex(name);
            addColumn(name, type);
            return this;
        }

        public TableTypeBuilder addIndexColumn(final String name, final String description, final MetaType type) {
            addIndex(name);
            addColumn(name, description, type);
            return this;
        }

        public TableTypeBuilder addIndex(String indexName) {
            if(indexes.contains(indexName)) {
                throw new IllegalStateException("duplicate index " + indexName);
            }
            indexes.add(indexName);
            return null;
        }

        public TableMetaType create() {
            final CompositeMetaType rowType = compositeTypeBuilder.create();
            final String[] indexNames = indexes.toArray(new String[indexes.size()]);
            return new ImmutableTableMetaType(typeName, description, rowType, indexNames);
        }

    }

}
