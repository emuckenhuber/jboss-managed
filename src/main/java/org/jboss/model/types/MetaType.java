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

package org.jboss.model.types;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jboss.model.values.ArrayValue;
import org.jboss.model.values.CompositeMapValue;
import org.jboss.model.values.CompositeValue;
import org.jboss.model.values.EnumValue;
import org.jboss.model.values.MetaValue;
import org.jboss.model.values.SimpleValue;
import org.jboss.model.values.TableValue;

/**
 * MetaType.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Emanuel Muckenhuber
 */
public interface MetaType extends Serializable {

    /**
     * The allowed classnames.
     * <p>
     *
     * One of<br>
     * java.lang.Void<br>
     * java.lang.Boolean<br>
     * java.lang.Character<br>
     * java.lang.Byte<br>
     * java.lang.Short<br>
     * java.lang.Integer<br>
     * java.lang.Long<br>
     * java.lang.Float<br>
     * java.lang.Double<br>
     * java.lang.String<br>
     * java.lang.Date<br>
     * java.math.BigDecimal<br>
     * java.math.BigInteger<br>
     * java.util.Properties<br>
     * {@link SimpleValue}<br>
     * {@link EnumValue}<br>
     * {@link ArrayValue}<br>
     * {@link CompositeValue}<br>
     * {@link TableValue}
     */
    List<String> ALLOWED_CLASSNAMES = Collections
            .unmodifiableList(Arrays.asList(
                    Void.class.getName(),
                    Boolean.class.getName(),
                    Character.class.getName(),
                    Byte.class.getName(),
                    Short.class.getName(),
                    Integer.class.getName(),
                    Long.class.getName(),
                    Float.class.getName(),
                    Double.class.getName(),
                    boolean.class.getName(),
                    char.class.getName(),
                    byte.class.getName(),
                    short.class.getName(),
                    int.class.getName(),
                    long.class.getName(),
                    float.class.getName(),
                    double.class.getName(),
                    String.class.getName(),
                    Date.class.getName(),
                    BigDecimal.class.getName(),
                    BigInteger.class.getName(),
                    SimpleValue.class.getName(),
                    EnumValue.class.getName(),
                    ArrayValue.class.getName(),
                    CompositeValue.class.getName(),
                    CompositeMapValue.class.getName(),
                    TableValue.class.getName()));

    /**
     * Retrieve the class name of the values of this meta type. It is one of
     * those listed in ALLOWED_CLASSNAMES or a (multi-dimensional) array of one
     * of those classes.
     *
     * @return the class name
     */
    String getClassName();

    /**
     * Retrieve the name of the meta type
     *
     * @return the type name
     */
    String getTypeName();

    /**
     * Retrieve the description of the type
     *
     * @return the description
     */
    String getDescription();

    /**
     * Retrieve whether the class name of the type is an array
     *
     * @return true when it is an array or false otherwise
     */
    boolean isArray();

    /**
     * Retrieve whether the class name of the type is a collection
     *
     * @return true when it is a collection or false otherwise
     */
    boolean isCollection();

    /**
     * Retrieve whether the class name of the type is an enum
     *
     * @return true when it is an enum or false otherwise
     */
    boolean isEnum();

    /**
     * Retrieve whether the class name of the type is simple
     *
     * @return true when it is simple or false otherwise
     */
    boolean isSimple();

    /**
     * Retrieve whether the class name of the type is composite
     *
     * @return true when it is composite or false otherwise
     */
    boolean isComposite();

    /**
     * Retrieve whether the class name of the type is a table
     *
     * @return true when it is a table or false otherwise
     */
    boolean isTable();

    /**
     * Whether the passed value is one of those described by this meta type.
     *
     * @param value the meta value to test
     * @return true when it is value for this meta type, false otherwise
     */
    boolean isValue(final MetaValue value);

    /**
     * Whether the passed value is one of those described by this meta type.
     *
     * @param obj the object to test
     * @return true when it is value for this meta type, false otherwise
     */
    boolean isValue(Object obj);

}
