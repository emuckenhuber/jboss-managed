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

package org.jboss.test.metatype.types.builders.test;

import junit.framework.TestCase;

import org.jboss.metatype.api.types.CompositeMapMetaType;
import org.jboss.metatype.api.types.CompositeMetaType;
import org.jboss.metatype.api.types.SimpleMetaType;
import org.jboss.metatype.api.types.builders.MetaTypeFactory;
import org.jboss.metatype.api.values.CompositeMapValue;
import org.jboss.metatype.api.values.CompositeValue;
import org.jboss.metatype.api.values.MetaValueFactory;
import org.jboss.metatype.api.values.TableValue;

/**
 * @author Emanuel Muckenhuber
 */
public class BuilderUnitTestCase extends TestCase {


    static enum Protocol {
        HTTP, AJP
    }

    public void test() {

        TableValue value = MetaValueFactory.create(
            MetaTypeFactory.tableTypeBuilder("test", "test")
                 .addIndexColumn("id", MetaTypeFactory.INTEGER_PRIMITIVE)
                 .addColumn("name", MetaTypeFactory.STRING)
                 .create());

        CompositeValue row = MetaValueFactory.create(value.getRowType());
        row.set("id", MetaValueFactory.create(1));
        row.set("name", MetaValueFactory.create("tada"));

        value.put(row);

        CompositeMetaType type = MetaTypeFactory.compositeTypeBuilder("typeName?", "awesome composite type")
            .addItem("name", SimpleMetaType.STRING)
            .addItem("port", SimpleMetaType.INTEGER_PRIMITIVE)
            .addItem("protocol", MetaTypeFactory.createEnumType(Protocol.class))
            .addItem("scheme", SimpleMetaType.STRING)
            .create();

        CompositeValue composite = MetaValueFactory.create(type);
        composite.set("name", MetaValueFactory.create("test"));

        CompositeMapMetaType compositeMapType = MetaTypeFactory.compositeTypeBuilder("typeName?", "awesome composite type")
            .addItem("id", MetaTypeFactory.STRING)
            .addItem("test", MetaTypeFactory.STRING)
            .createMapMetaType("id");

        CompositeMapValue compositeMap = MetaValueFactory.create(compositeMapType);

        CompositeValue entry = MetaValueFactory.create(compositeMap.getEntryType());
        entry.set("id", MetaValueFactory.create("id1"));
        entry.set("test", MetaValueFactory.create("test1"));

        compositeMap.put(entry);

        assertNotNull("test1", compositeMap.get(MetaValueFactory.create("id1")).get("test"));

    }

}
