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

package org.jboss.test.metatype.types.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.jboss.metatype.api.types.Name;
import org.jboss.metatype.api.types.SimpleMetaType;
import org.jboss.metatype.api.values.SimpleValue;
import org.jboss.metatype.api.values.SimpleValueSupport;
import org.jboss.test.metatype.AbstractMetaTypeTest;

/**
 * SimpleMetaTypeUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 */
public class SimpleMetaTypeUnitTestCase extends AbstractMetaTypeTest
{

   /**
    * Create a new SimpleMetaTypeUnitTestCase.
    *
    * @param name the test name
    */
   public SimpleMetaTypeUnitTestCase(String name) {
      super(name);
   }

   SimpleMetaType[] types = new SimpleMetaType[] {
      SimpleMetaType.BIGDECIMAL,
      SimpleMetaType.BIGINTEGER,
      SimpleMetaType.BOOLEAN,
      SimpleMetaType.BYTE,
      SimpleMetaType.CHARACTER,
      SimpleMetaType.DATE,
      SimpleMetaType.DOUBLE,
      SimpleMetaType.FLOAT,
      SimpleMetaType.INTEGER,
      SimpleMetaType.LONG,
      SimpleMetaType.SHORT,
      SimpleMetaType.STRING,
      SimpleMetaType.VOID,
   };

   Class<?>[] classes = new Class[] {
      BigDecimal.class,
      BigInteger.class,
      Boolean.class,
      Byte.class,
      Character.class,
      Date.class,
      Double.class,
      Float.class,
      Integer.class,
      Long.class,
      Short.class,
      String.class,
      Void.class,
      Name.class
   };

   SimpleValue[] values = new SimpleValue[] {
      new SimpleValueSupport(SimpleMetaType.BIGDECIMAL, new BigDecimal(1)),
      new SimpleValueSupport(SimpleMetaType.BIGINTEGER, BigInteger.ONE),
      new SimpleValueSupport(SimpleMetaType.BOOLEAN, new Boolean(false)),
      new SimpleValueSupport(SimpleMetaType.BYTE, new Byte(Byte.MAX_VALUE)),
      new SimpleValueSupport(SimpleMetaType.CHARACTER, new Character('a')),
      new SimpleValueSupport(SimpleMetaType.DATE, new Date(System.currentTimeMillis())),
      new SimpleValueSupport(SimpleMetaType.DOUBLE, new Double(1)),
      new SimpleValueSupport(SimpleMetaType.FLOAT, new Float(1)),
      new SimpleValueSupport(SimpleMetaType.INTEGER, new Integer(1)),
      new SimpleValueSupport(SimpleMetaType.LONG, new Long(1)),
      new SimpleValueSupport(SimpleMetaType.SHORT, new Short(Short.MAX_VALUE)),
      new SimpleValueSupport(SimpleMetaType.STRING, new String("hello")),
      new SimpleValueSupport(SimpleMetaType.VOID, null),
   };

   SimpleValue[] nullValues = new SimpleValue[] {
      new SimpleValueSupport(SimpleMetaType.BIGDECIMAL, null),
      new SimpleValueSupport(SimpleMetaType.BIGINTEGER, null),
      new SimpleValueSupport(SimpleMetaType.BOOLEAN, null),
      new SimpleValueSupport(SimpleMetaType.BYTE, null),
      new SimpleValueSupport(SimpleMetaType.CHARACTER, null),
      new SimpleValueSupport(SimpleMetaType.DATE, null),
      new SimpleValueSupport(SimpleMetaType.DOUBLE, null),
      new SimpleValueSupport(SimpleMetaType.FLOAT, null),
      new SimpleValueSupport(SimpleMetaType.INTEGER, null),
      new SimpleValueSupport(SimpleMetaType.LONG, null),
      new SimpleValueSupport(SimpleMetaType.SHORT, null),
      new SimpleValueSupport(SimpleMetaType.STRING, null),
      new SimpleValueSupport(SimpleMetaType.VOID, null),
      new SimpleValueSupport(SimpleMetaType.NAMEDOBJECT, null)
   };

   /**
    * Test the simple types
    *
    * @throws Exception for any problem
    */
   public void testSimpleTypes() throws Exception {
      for (int i = 0; i < types.length; i++) {
         String className = classes[i].getName();
         System.out.println("SimpleMetaType: " + className + " className=" + types[i].getClassName() + " typeName=" + types[i].getTypeName() + " description=" + types[i].getDescription());
         assertEquals(className, types[i].getClassName());
         assertEquals(className, types[i].getTypeName());
         assertEquals(className, types[i].getDescription());
      }
   }

   /**
    * Test the equals
    *
    * @throws Exception for any problem
    */
   public void testEquals() throws Exception {
      for (int i = 0; i < types.length; i++) {
        for (int j = 0; j < types.length; j++) {
           boolean resultEquals = types[i].equals(types[j]);
           boolean resultReference = types[i] != types[j];
           System.out.println("equals  : " + types[i].getClassName() + " " + types[j] + " result=" + resultEquals);
           System.out.println("equality: " + types[i].getClassName() + " " + types[j] + " result=" + resultReference);
           if (i == j) {
              assertEquals("SimpleMetaTypes should be equal to itself " + classes[i], types[i], types[j]);
           } else {
              assertNotSame("SimpleMetaTypes should be different under equality " + classes[i], types[i], types[j]);
              assertTrue("SimpleMetaTypes should be different under reference " + classes[i], types[i] != types[j]);
           }
        }
      }
   }

   /**
    * Test the isValue
    *
    * @throws Exception for any problem
    */
   public void testIsValue() throws Exception {
      for (int i = 0; i < types.length; ++i) {
         for (int j = 0; j < types.length; ++j) {

            // isValue makes no sense for Void
            if (values[i].getValue() == null) {
               continue;
            }
            boolean result = types[j].isValue(values[i]);
            System.out.println("isValue: " + types[j].getClassName() + " value=" + values[i] + " result=" + result);

            if (i == j) {
               assertTrue(classes[i] + " should be a simple value of " + types[j], result);
               result = types[i].isValue(nullValues[j]);
               System.out.println("isValue: " + types[i].getClassName() + " value=null value result=" + result);
               assertTrue(nullValues[j] + " should be a simple value of " + types[i], result);
            } else {
               assertFalse(classes[i] + " should NOT be a simple value of " + types[j], result);
               result = types[i].isValue(nullValues[j]);
               System.out.println("isValue: " + types[i].getClassName() + " value=null value result=" + result);
               assertFalse(nullValues[j] + " should NOT be a simple value of " + types[i], result);
            }
         }
      }
   }

   /**
    * Test the hashCode
    *
    * @throws Exception for any problem
    */
   public void testHashCode() throws Exception {
      for (int i = 0; i < types.length; i++) {
         int classHashCode = classes[i].getName().hashCode();
         int typeHashCode = types[i].hashCode();
         System.out.println("hashCode: " + types[i].getClassName() + " expected=" + classHashCode + " actual=" + typeHashCode);
         assertEquals(classHashCode, typeHashCode);
      }
   }

   /**
    * Test the toString
    *
    * @throws Exception for any problem
    */
   public void testToString() throws Exception {
      String smt = SimpleMetaType.class.getSimpleName();
      for (int i = 0; i < types.length; i++) {
         String className = classes[i].getName();
         String toString = types[i].toString();
         System.out.println("toString: " + types[i].getClassName() + " value=" + toString);
         assertTrue("SimpleMetaType " + className + " should contain " + smt, toString.indexOf(smt) != -1);
         assertTrue("SimpleMetaType " + className + " should contain " + className, toString.indexOf(className) != -1);
      }
   }

   /**
    * Test the serialization
    *
    * @throws Exception for any problem
    */
   public void testSerialization() throws Exception {
      for (int i = 0; i < types.length; i++) {
         System.out.println("serialization: " + types[i].getClassName() + " original=" + types[i]);
         byte[] bytes = serialize(types[i]);
         SimpleMetaType result = (SimpleMetaType) deserialize(bytes);
         System.out.println("serialization: " + types[i].getClassName() + " result  =" + types[i]);

         assertTrue("Should resolve to same object after serialization " + types[i], types[i] == result);
      }
   }

   public void testBytePrimitives() throws Exception {
      assertEquals(SimpleMetaType.BYTE_PRIMITIVE, SimpleMetaType.isSimpleType(byte.class.getName()));
      SimpleMetaType byteType = SimpleMetaType.resolve("byte");
      assertNotNull(byteType);
      assertEquals(SimpleMetaType.BYTE_PRIMITIVE, byteType);
      assertTrue(byteType.isPrimitive());
      Serializable one = (byte) 1;
      System.out.println("one.class: "+one.getClass());
      SimpleValue byte1p = SimpleValueSupport.wrap((byte)1);
      SimpleValue byte1p2 = SimpleValueSupport.wrap((byte)1);
      assertEquals(SimpleMetaType.BYTE_PRIMITIVE, byte1p.getMetaType());
      SimpleValue byte1 = SimpleValueSupport.wrap(one);
      assertEquals(SimpleMetaType.BYTE, byte1.getMetaType());
      assertEquals(byte1, byte1p);
      assertEquals(byte1p, byte1p2);

      SimpleValue byte2p = SimpleValueSupport.wrap((byte)2);
      assertFalse(byte1p.equals(byte2p));
   }

   public void testShortPrimitives() throws Exception {
      assertEquals(SimpleMetaType.SHORT_PRIMITIVE, SimpleMetaType.isSimpleType(short.class.getName()));
      SimpleMetaType shortType = SimpleMetaType.resolve("short");
      assertNotNull(shortType);
      assertEquals(SimpleMetaType.SHORT_PRIMITIVE, shortType);
      assertTrue(shortType.isPrimitive());
      Serializable one = (short) 1;
      System.out.println("one.class: "+one.getClass());
      SimpleValue short1p = SimpleValueSupport.wrap((short)1);
      SimpleValue short1p2 = SimpleValueSupport.wrap((short)1);
      assertEquals(SimpleMetaType.SHORT_PRIMITIVE, short1p.getMetaType());
      SimpleValue short1 = SimpleValueSupport.wrap(one);
      assertEquals(SimpleMetaType.SHORT, short1.getMetaType());
      assertEquals(short1, short1p);
      assertEquals(short1p, short1p2);

      SimpleValue short2p = SimpleValueSupport.wrap((short)2);
      assertFalse(short1p.equals(short2p));
   }

   public void testIntPrimitives() throws Exception {
      assertEquals(SimpleMetaType.INTEGER_PRIMITIVE, SimpleMetaType.isSimpleType(int.class.getName()));
      SimpleMetaType intType = SimpleMetaType.resolve("int");
      assertNotNull(intType);
      assertEquals(SimpleMetaType.INTEGER_PRIMITIVE, intType);
      assertTrue(intType.isPrimitive());
      Serializable one = (int) 1;
      System.out.println("one.class: "+one.getClass());
      SimpleValue int1p = SimpleValueSupport.wrap((int)1);
      SimpleValue int1p2 = SimpleValueSupport.wrap((int)1);
      assertEquals(SimpleMetaType.INTEGER_PRIMITIVE, int1p.getMetaType());
      SimpleValue int1 = SimpleValueSupport.wrap(one);
      assertEquals(SimpleMetaType.INTEGER, int1.getMetaType());
      assertEquals(int1, int1p);
      assertEquals(int1p, int1p2);

      SimpleValue int2p = SimpleValueSupport.wrap((int)2);
      assertFalse(int1p.equals(int2p));
   }

   public void testLongPrimitives() throws Exception {
      assertEquals(SimpleMetaType.LONG_PRIMITIVE, SimpleMetaType.isSimpleType(long.class.getName()));
      SimpleMetaType longType = SimpleMetaType.resolve("long");
      assertNotNull(longType);
      assertEquals(SimpleMetaType.LONG_PRIMITIVE, longType);
      assertTrue(longType.isPrimitive());
      Serializable one = (long) 1;
      System.out.println("one.class: "+one.getClass());
      SimpleValue long1p = SimpleValueSupport.wrap((long)1);
      SimpleValue long1p2 = SimpleValueSupport.wrap((long)1);
      assertEquals(SimpleMetaType.LONG_PRIMITIVE, long1p.getMetaType());
      SimpleValue long1 = SimpleValueSupport.wrap(one);
      assertEquals(SimpleMetaType.LONG, long1.getMetaType());
      assertEquals(long1, long1p);
      assertEquals(long1p, long1p2);

      SimpleValue long2p = SimpleValueSupport.wrap((long)2);
      assertFalse(long1p.equals(long2p));
   }

   public void testFloatPrimitives() throws Exception {
      assertEquals(SimpleMetaType.FLOAT_PRIMITIVE, SimpleMetaType.isSimpleType(float.class.getName()));
      SimpleMetaType floatType = SimpleMetaType.resolve("float");
      assertNotNull(floatType);
      assertTrue(SimpleMetaType.FLOAT_PRIMITIVE == floatType);
      assertEquals(SimpleMetaType.FLOAT_PRIMITIVE, floatType);
      assertTrue(floatType.isPrimitive());
      Serializable pi = 3.14f;
      System.out.println("pi.class: "+pi.getClass());
      SimpleValue float1p = SimpleValueSupport.wrap(3.14f);
      SimpleValue float1p2 = SimpleValueSupport.wrap(3.14f);
      assertEquals(SimpleMetaType.FLOAT_PRIMITIVE, float1p.getMetaType());
      SimpleValue float1 = SimpleValueSupport.wrap(pi);
      assertEquals(SimpleMetaType.FLOAT, float1.getMetaType());
      assertEquals(float1, float1p);
      assertEquals(float1p, float1p2);

      SimpleValue float2p = SimpleValueSupport.wrap(3.24f);
      assertFalse(float1p.equals(float2p));
   }

   public void testDoublePrimitives() throws Exception {
      assertEquals(SimpleMetaType.DOUBLE_PRIMITIVE, SimpleMetaType.isSimpleType(double.class.getName()));
      SimpleMetaType doubleType = SimpleMetaType.resolve("double");
      assertNotNull(doubleType);
      assertEquals(SimpleMetaType.DOUBLE_PRIMITIVE, doubleType);
      assertTrue(doubleType.isPrimitive());
      Serializable pi = 3.14;
      System.out.println("pi.class: "+pi.getClass());
      SimpleValue double1p = SimpleValueSupport.wrap(3.14);
      SimpleValue double1p2 = SimpleValueSupport.wrap(3.14);
      assertEquals(SimpleMetaType.DOUBLE_PRIMITIVE, double1p.getMetaType());
      SimpleValue double1 = SimpleValueSupport.wrap(pi);
      assertEquals(SimpleMetaType.DOUBLE, double1.getMetaType());
      assertEquals(double1, double1p);
      assertEquals(double1p, double1p2);

      SimpleValue double2p = SimpleValueSupport.wrap(3.24);
      assertFalse(double1p.equals(double2p));
   }

   public void testBooleanPrimitives() throws Exception {
      assertEquals(SimpleMetaType.BOOLEAN_PRIMITIVE, SimpleMetaType.isSimpleType(boolean.class.getName()));
      SimpleMetaType booleanType = SimpleMetaType.resolve("boolean");
      assertNotNull(booleanType);
      assertEquals(SimpleMetaType.BOOLEAN_PRIMITIVE, booleanType);
      assertTrue(booleanType.isPrimitive());
      Serializable b = true;;
      System.out.println("b.class: "+b.getClass());
      SimpleValue boolean1p = SimpleValueSupport.wrap(true);
      SimpleValue boolean1p2 = SimpleValueSupport.wrap(true);
      assertEquals(SimpleMetaType.BOOLEAN_PRIMITIVE, boolean1p.getMetaType());
      SimpleValue boolean1 = SimpleValueSupport.wrap(b);
      assertEquals(SimpleMetaType.BOOLEAN, boolean1.getMetaType());
      assertEquals(boolean1, boolean1p);
      assertEquals(boolean1p, boolean1p2);

      SimpleValue boolean2p = SimpleValueSupport.wrap(false);
      assertFalse(boolean1p.equals(boolean2p));
   }
}
