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

package org.jboss.test.metatype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.jboss.metatype.api.types.ArrayMetaType;
import org.jboss.metatype.api.types.CollectionMetaType;
import org.jboss.metatype.api.types.CompositeMetaType;
import org.jboss.metatype.api.types.ImmutableCompositeMetaType;
import org.jboss.metatype.api.types.MetaType;
import org.jboss.metatype.api.types.SimpleMetaType;
import org.jboss.metatype.api.types.TableMetaType;
import org.jboss.metatype.api.values.MetaValue;
import org.jboss.metatype.api.values.SimpleValue;
import org.jboss.metatype.api.values.SimpleValueSupport;

/**
 * AbstractMetaTypeTest.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public abstract class AbstractMetaTypeTest extends TestCase {

    /**
     * Create a new AbstractMetaTypeTest.
     *
     * @param name the test name
     */
    public AbstractMetaTypeTest(String name) {
        super(name);
    }

    /**
     * Initialize string value 1
     *
     * @return the value
     */
    protected SimpleValue initStringValue1() {
        return SimpleValueSupport.wrap("value1");
    }

    /**
     * Initialize string value 2
     *
     * @return the value
     */
    protected SimpleValue initStringValue2() {
        return SimpleValueSupport.wrap("value2");
    }

    /**
     * Initialize string name 1
     *
     * @return the name
     */
    protected SimpleValue initStringName1() {
        return SimpleValueSupport.wrap("name1");
    }

    /**
     * Initialize the empty string
     *
     * @return the empty string
     */
    protected SimpleValue initStringEmpty() {
        return SimpleValueSupport.wrap("");
    }

    /**
     * Initialize the null string
     *
     * @return the null string
     */
    protected SimpleValue initStringNull() {
        return new SimpleValueSupport(SimpleMetaType.STRING, null);
    }

    /**
     * Initialize the wrong string
     *
     * @return the wrong string
     */
    protected SimpleValue initStringWrong() {
        return SimpleValueSupport.wrap("wrong");
    }

    /**
     * Initialize the integer 2
     *
     * @return the value
     */
    protected SimpleValue initInteger2() {
        return SimpleValueSupport.wrap(new Integer(2));
    }

    /**
     * Initialize the integer 3
     *
     * @return the value
     */
    protected SimpleValue initInteger3() {
        return SimpleValueSupport.wrap(new Integer(3));
    }

    /**
     * Initialize the integer 4
     *
     * @return the value
     */
    protected SimpleValue initInteger4() {
        return SimpleValueSupport.wrap(new Integer(4));
    }

    /**
     * Initialize the null integer
     *
     * @return the value
     */
    protected SimpleValue initIntegerNull() {
        return new SimpleValueSupport(SimpleMetaType.INTEGER, null);
    }

    /**
     * Initialize map values
     *
     * @return the value
     */
    protected Map<String, MetaValue> initMapValues() {
        Map<String, MetaValue> map = new HashMap<String, MetaValue>();
        map.put("name1", initStringValue1());
        map.put("name2", initInteger2());
        return map;
    }

    /**
     * Initialize map values 2
     *
     * @return the value
     */
    protected Map<String, MetaValue> initMapValues2() {
        Map<String, MetaValue> map = new HashMap<String, MetaValue>();
        map.put("name1", initStringValue1());
        map.put("name2", initInteger3());
        return map;
    }

    /**
     * Initialize map values 3
     *
     * @return the value
     */
    protected Map<String, MetaValue> initMapValues3() {
        Map<String, MetaValue> map = new HashMap<String, MetaValue>();
        map.put("name1", initStringValue2());
        map.put("name2", initInteger3());
        return map;
    }

    /**
     * Initialize map values 4
     *
     * @return the value
     */
    protected Map<String, MetaValue> initMapValues4() {
        Map<String, MetaValue> map = new HashMap<String, MetaValue>();
        map.put("name1", initStringValue1());
        map.put("name2", initInteger4());
        return map;
    }

    /**
     * Initialize map keys
     *
     * @return the keys
     */
    protected String[] initKeys() {
        return new String[] { "name1", "name2" };
    }

    /**
     * Initialize map values
     *
     * @return the values
     */
    protected MetaValue[] initValues() {
        return new MetaValue[] { initStringValue1(), initInteger2() };
    }

    /**
     * Initialize map values 2
     *
     * @return the values
     */
    protected MetaValue[] initValues2() {
        return new MetaValue[] { initStringValue1(), initInteger3() };
    }

    /**
     * Initialize map values 4
     *
     * @return the values
     */
    protected MetaValue[] initValues4() {
        return new MetaValue[] { initStringValue1(), initInteger4() };
    }

    /**
     * Initialize a composite meta type
     *
     * @return the type
     */
    protected CompositeMetaType initCompositeMetaType() {
        String[] itemNames = new String[] { "name1", "name2" };
        String[] itemDescriptions = new String[] { "desc1", "desc2" };
        MetaType[] itemTypes = new MetaType[] { SimpleMetaType.STRING, SimpleMetaType.INTEGER };
        CompositeMetaType compositeMetaType = new ImmutableCompositeMetaType( "typeName", "description", itemNames, itemDescriptions, itemTypes);
        return compositeMetaType;
    }

    /**
     * Initialize a composite meta type 2
     *
     * @return the type
     */
    protected CompositeMetaType initCompositeMetaType2() {
        String[] itemNames = new String[] { "name1", "name2" };
        String[] itemDescriptions = new String[] { "desc1", "desc2" };
        MetaType[] itemTypes = new MetaType[] { SimpleMetaType.STRING, SimpleMetaType.INTEGER };
        CompositeMetaType compositeMetaType = new ImmutableCompositeMetaType(
                "typeName2", "description", itemNames, itemDescriptions,
                itemTypes);
        return compositeMetaType;
    }

    /**
     * Print a composite type
     *
     * @param context the context
     * @param type the type
     */
    protected void printComposite(String context, CompositeMetaType type) {
        System.out.println(context + " className=" + type.getClassName()
                + " typeName=" + type.getTypeName() + " description="
                + type.getDescription() + " keys=" + type.keySet() + " items="
                + type.itemSet());
    }

    /**
     * Test an array type
     *
     * @param expected the expected
     * @param actual the actual
     * @throws Exception for any problem
     */
    protected void testArray(ArrayMetaType expected, ArrayMetaType actual) throws Exception {
        System.out.println("Array MetaType: className=" + actual.getClassName()
                + " typeName=" + actual.getTypeName() + " description="
                + actual.getDescription() + " dim=" + actual.getDimension());
        assertEquals(expected, actual);
    }

    /**
     * Test an array type
     *
     * @param expected the expected
     * @param actual the actual
     * @throws Exception for any problem
     */
    protected void testCollection(CollectionMetaType expected, CollectionMetaType actual) throws Exception {
        System.out.println("Collection MetaType: className="
                + actual.getClassName() + " typeName=" + actual.getTypeName()
                + " description=" + actual.getDescription());
        assertEquals(expected, actual);
    }

    /**
     * Test a table type
     *
     * @param expected the expected
     * @param actual the actual
     * @throws Exception for any problem
     */
    protected void testTable(TableMetaType expected, TableMetaType actual) throws Exception {
        System.out.println("Table MetaType: className=" + actual.getClassName()
                + " typeName=" + actual.getTypeName() + " description="
                + actual.getDescription() + " index=" + actual.getIndexNames()
                + " row=" + actual.getRowType());
        assertEquals(expected, actual);
    }

    /**
     * Test a composite type
     *
     * @param expected the expected
     * @param actual the actual
     * @throws Exception for any problem
     */
    protected void testComposite(CompositeMetaType expected, CompositeMetaType actual) throws Exception {
        printComposite("Composite MetaType", actual);
        assertEquals(expected, actual);
    }

    /**
     * Serialize an object
     *
     * @param object the object
     * @return the bytes
     * @throws Exception for any error
     */
    protected byte[] serialize(Serializable object) throws Exception {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(baos);
       oos.writeObject(object);
       oos.close();
       return baos.toByteArray();
    }

    /**
     * Serialize an object
     *
     * @param bytes - the raw serialzied object data
     * @return the bytes
     * @throws Exception for any error
     */
    protected Object deserialize(byte[] bytes) throws Exception {
       ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
       ObjectInputStream ois = new ObjectInputStream(bais);
       return ois.readObject();
    }

    /**
     * Serialize/deserialize
     *
     * @param <T> the expected type
     * @param value the value
     * @param expected the expected type
     * @return the result
     * @throws Exception for any problem
     */
    protected <T> T serializeDeserialize(Serializable value, Class<T> expected) throws Exception {
       byte[] bytes = serialize(value);
       Object result = deserialize(bytes);
       return assertInstanceOf(result, expected);
    }

    /**
     * Check we have the expected type
     *
     * @param <T> the expected type
     * @param o the object
     * @param expectedType the excepted class of the exception
     * @return the expected type
     */
    protected <T> T assertInstanceOf(Object o, Class<T> expectedType) {
       return assertInstanceOf(o, expectedType, true);
    }

    /**
     * Check we have the expected type
     *
     * @param <T> the expected type
     * @param o the object
     * @param expectedType the excepted class of the exception
     * @param allowNull whether the object can be null
     * @return the expected type
     */
    protected <T> T assertInstanceOf(Object o, Class<T> expectedType, boolean allowNull) {
       if (expectedType == null) {
          fail("Null expectedType");
       }
       if (o == null) {
          if (allowNull == false) {
             fail("Null object not allowed.");
          } else {
             return null;
          }
       }
       try {
          return expectedType.cast(o);
       } catch (ClassCastException e) {
          fail("Object " + o + " of class " + o.getClass().getName() + " is not an instanceof " + expectedType.getName());
          // should not reach this
          return null;
       }
    }

    /**
     * Check we have the expected exception
     *
     * @param expected the excepted class of the exception
     * @param throwable the real exception
     */
    public static void checkThrowable(Class<? extends Throwable> expected, Throwable throwable)
    {
       if (expected == null) {
          fail("Must provide an expected class");
       }
       if (throwable == null) {
          fail("Must provide a throwable for comparison");
       }
       if (throwable instanceof AssertionFailedError || throwable instanceof AssertionError) {
          throw (Error) throwable;
       }
       if (expected.equals(throwable.getClass()) == false)
       {
          throwable.printStackTrace();
          fail("Unexpected throwable: " + throwable);
       }
       else
       {
          System.out.println("Got expected " + expected.getName() + "(" + throwable + ")");
       }
    }
}
