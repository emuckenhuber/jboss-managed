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

package org.jboss.test.model.types.test;

import java.util.List;

import junit.framework.TestCase;

import org.jboss.model.types.EnumMetaType;
import org.jboss.model.values.EnumValue;
import org.jboss.model.values.EnumValueSupport;

/**
 * @author Emanuel Muckenhuber
 */
public class EnumTypeUnitTestCase extends TestCase {

    static enum TestEnum {
        ONE, TWO, THREE
    }

    public void testCreateType() {
        final EnumMetaType metaType = EnumMetaType.create(TestEnum.class);
        assertNotNull(metaType);
        final List<String> validValues = metaType.getValidValues();
        assertEquals("ONE", validValues.get(0));
        assertEquals("TWO", validValues.get(1));
        assertEquals("THREE", validValues.get(2));
    }

    public void testValue() {
        final EnumMetaType metaType = EnumMetaType.create(TestEnum.class);
        assertNotNull(metaType);
        EnumValue one = new EnumValueSupport(metaType, "ONE");
        assertEquals("ONE", one.getValue());
        assertEquals(TestEnum.ONE, one.getValue(TestEnum.class));

    }

}
