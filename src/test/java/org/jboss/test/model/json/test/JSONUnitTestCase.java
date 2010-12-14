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

package org.jboss.test.model.json.test;

import junit.framework.TestCase;

import org.jboss.model.json.JSONMetaValueTransformer;
import org.jboss.model.json.JSONObject;
import org.jboss.model.json.MetaValueJSONTransformer;
import org.jboss.model.types.CompositeMetaType;
import org.jboss.model.types.builders.MetaTypeFactory;
import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.MetaValue;
import org.jboss.model.values.MetaValueFactory;

/**
 * @author Emanuel Muckenhuber
 */
public class JSONUnitTestCase extends TestCase {

    public void test() throws Exception {

        final CompositeMetaType type = MetaTypeFactory.compositeTypeBuilder("socket-binding", "test")
            .addItem("name", MetaTypeFactory.STRING)
            .addItem("port", MetaTypeFactory.INTEGER)
            .addItem("fixed-port", MetaTypeFactory.BOOLEAN)
            .addItem("interface-name", MetaTypeFactory.STRING)
            .create();

        final CompositeValue value = MetaValueFactory.create(type);
        value.set("name", MetaValueFactory.create("http"));
        value.set("port", MetaValueFactory.create(8080));
        value.set("fixed-port", MetaValueFactory.create(false));
        value.set("interface-name", MetaValueFactory.create("public"));

        // Transform to JSON format and back
        final JSONObject jsonObject = (JSONObject) MetaValueJSONTransformer.getInstance().transform(value, type);
        final String jsonText = jsonObject.toString();
        System.out.println(jsonText);
        final MetaValue recreated = JSONMetaValueTransformer.getInstance().transform(new JSONObject(jsonText), type);

        assertNotNull(recreated);
        assertTrue(recreated.getMetaType().isComposite());
        final CompositeValue rValues = recreated.as(CompositeValue.class);
        assertEquals(MetaValueFactory.create("http"), rValues.get("name"));
        assertEquals(MetaValueFactory.create(8080), rValues.get("port"));
        assertEquals(MetaValueFactory.create(false), rValues.get("fixed-port"));
        assertEquals(MetaValueFactory.create("public"), rValues.get("interface-name"));
    }



}
