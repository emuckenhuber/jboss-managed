/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.test.metatype.values.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jboss.metatype.api.types.SimpleMetaType;
import org.jboss.metatype.api.values.SimpleValue;
import org.jboss.metatype.api.values.SimpleValueComparator;
import org.jboss.metatype.api.values.SimpleValueSupport;
import org.jboss.test.metatype.AbstractMetaTypeTest;

/**
 * SimpleValueSupportUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class SimpleValueSupportUnitTestCase extends AbstractMetaTypeTest {

   /**
    * Create a new SimpleValueSupportUnitTestCase.
    *
    * @param name the test name
    */
   public SimpleValueSupportUnitTestCase(String name) {
      super(name);
   }

   /**
    * Test the simple value
    *
    * @throws Exception for any problem
    */
   public void testSimpleValueSupport() throws Exception {
      initStringValue1();
   }

   /**
    * Test the meta type for a simple value
    *
    * @throws Exception for any problem
    */
   public void testGetSimpleMetaType() throws Exception {
      SimpleValue value = initStringValue1();
      assertEquals(SimpleMetaType.STRING, value.getMetaType());
   }

   /**
    * Test the getValue for a simple value
    *
    * @throws Exception for any problem
    */
   public void testGetValue()throws Exception {
      SimpleValue value = initStringValue1();
      assertEquals("value1", value.getValue());
   }

   /**
    * Test the setValue for a simple value
    *
    * @throws Exception for any problem
    */
   public void testSetValue()throws Exception {
      SimpleValueSupport value = (SimpleValueSupport) initStringValue1();
      value.setValue("value2");
      assertEquals("value2", value.getValue());
   }

   /**
    * Test the equals for a simple value
    *
    * @throws Exception for any problem
    */
   public void testEquals() throws Exception {
      SimpleValue v = initStringValue1();

      assertEquals("data should equal itself", v, v);
      assertNotSame("data should not equal null", v, null);
      assertNotSame("data should not equal null value", v, initStringNull());
      assertNotSame("data should not equal empty value", v, initStringEmpty());
      assertNotSame("data should not equal non SimpleValue", v, new Object());

      SimpleValue v2 = initStringValue1();

      assertEquals("data should equal with data2 with different instance of the same simple type", v, v2);
      assertEquals("data should equal with data2 with different instance of the same simple type", v2, v);

      v2 = initInteger2();

      assertNotSame("data should not be equal with data2 with different simple type", v, v2);
      assertNotSame("data2 should not be equal with data with different simple type", v2, v);

      v2 = initStringName1();

      assertNotSame("data should not be equal with data2 with different values", v, v2);
      assertNotSame("data2 should not be equal with data with different value", v2, v);
   }

   /**
    * Test the hashCode for a simple value
    *
    * @throws Exception for any problem
    */
   public void testHashCode() throws Exception {
      SimpleValue v = initStringValue1();

      int myHashCode = "value1".hashCode();
      assertEquals("Wrong hash code generated", myHashCode, v.hashCode());
   }

   public void testStringComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue s1 = SimpleValueSupport.wrap("value1");
      SimpleValue s2 = SimpleValueSupport.wrap("value2");
      assertTrue("value1 < value2", comparator.compare(s1, s2) < 0);
      assertTrue("value2 < value1", comparator.compare(s2, s1) > 0);
      assertTrue("value1 < value2", comparator.compare(s1, s1) == 0);

      assertTrue("value1 < value2", s1.compareTo(s2) < 0);
      assertTrue("value2 < value1", s2.compareTo(s1) > 0);
      assertTrue("value1 < value2", s1.compareTo(s1) == 0);
   }

   public void testBigDecimalComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue v1 = SimpleValueSupport.wrap(new BigDecimal(123456));
      SimpleValue v2 = SimpleValueSupport.wrap(new BigDecimal(654321));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testBigIntegerComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue v1 = SimpleValueSupport.wrap(new BigInteger("123456"));
      SimpleValue v2 = SimpleValueSupport.wrap(new BigInteger("654321"));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testBooleanComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue v1 = SimpleValueSupport.wrap(Boolean.FALSE);
      SimpleValue v2 = SimpleValueSupport.wrap(Boolean.TRUE);
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testByteComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue v1 = SimpleValueSupport.wrap(new Byte("1"));
      SimpleValue v2 = SimpleValueSupport.wrap(new Byte("2"));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testCharacterComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();

      SimpleValue v1 = SimpleValueSupport.wrap(new Character('1'));
      SimpleValue v2 = SimpleValueSupport.wrap(new Character('2'));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testDateComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      long d1 = System.currentTimeMillis();
      long d2 = d1 + 100000;
      SimpleValue v1 = SimpleValueSupport.wrap(new Date(d1));
      SimpleValue v2 = SimpleValueSupport.wrap(new Date(d2));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testDoubleComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      SimpleValue v1 = SimpleValueSupport.wrap(new Double(12345));
      SimpleValue v2 = SimpleValueSupport.wrap(new Double(67890));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testFloatComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      SimpleValue v1 = SimpleValueSupport.wrap(new Float(12345));
      SimpleValue v2 = SimpleValueSupport.wrap(new Float(67890));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testIntegerComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      SimpleValue v1 = SimpleValueSupport.wrap(new Integer(12345));
      SimpleValue v2 = SimpleValueSupport.wrap(new Integer(67890));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testLongComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      SimpleValue v1 = SimpleValueSupport.wrap(new Long(12345));
      SimpleValue v2 = SimpleValueSupport.wrap(new Long(67890));
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   public void testShortComparable() {
      SimpleValueComparator comparator = new SimpleValueComparator();
      SimpleValue v1 = SimpleValueSupport.wrap(Short.MIN_VALUE);
      SimpleValue v2 = SimpleValueSupport.wrap(Short.MAX_VALUE);
      assertTrue("value1 < value2", comparator.compare(v1, v2) < 0);
      assertTrue("value2 < value1", comparator.compare(v2, v1) > 0);
      assertTrue("value1 < value2", comparator.compare(v1, v1) == 0);

      assertTrue("value1 < value2", v1.compareTo(v2) < 0);
      assertTrue("value2 < value1", v2.compareTo(v1) > 0);
      assertTrue("value1 < value2", v1.compareTo(v1) == 0);
   }

   /**
    * Test the toString for a simple value
    *
    * @throws Exception for any problem
    */
   public void testToString() throws Exception {
      SimpleValue v = initStringValue1();

      String toString = v.toString();

      assertTrue("toString() should contain the simple type", toString.indexOf(SimpleMetaType.STRING.toString()) != -1);
      assertTrue("toString() should contain value1", toString.indexOf("value1") != -1);
   }

   /**
    * Test the serialization for a simple value
    *
    * @throws Exception for any problem
    */
   public void testSerialization() throws Exception {
      SimpleValue v = initStringValue1();
      byte[] bytes = serialize(v);
      Object result = deserialize(bytes);
      assertEquals(v, result);
   }

   /**
    * Test the errors for a simple value
    *
    * @throws Exception for any problem
    */
   public void testErrors() throws Exception {
      try {
         new SimpleValueSupport(null, "value1");
         fail("Excepted IllegalArgumentException for null simple type");
      } catch (Throwable t) {
         checkThrowable(IllegalArgumentException.class, t);
      }

      new SimpleValueSupport(SimpleMetaType.STRING, null);
   }
}
