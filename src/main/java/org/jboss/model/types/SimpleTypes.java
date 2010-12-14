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

/**
 * @author Emanuel Muckenhuber
 */
public interface SimpleTypes {

    /** The simple type for java.math.BigDecimal */
    public static final SimpleMetaType BIGDECIMAL = SimpleMetaType.BIGDECIMAL;

    /** The simple type for java.math.BigInteger */
    public static final SimpleMetaType BIGINTEGER = SimpleMetaType.BIGINTEGER;

    /** The simple type for java.lang.Boolean */
    public static final SimpleMetaType BOOLEAN = SimpleMetaType.BOOLEAN;
    public static final SimpleMetaType BOOLEAN_PRIMITIVE = SimpleMetaType.BOOLEAN_PRIMITIVE;

    /** The simple type for java.lang.Byte */
    public static final SimpleMetaType BYTE = SimpleMetaType.BYTE;
    public static final SimpleMetaType BYTE_PRIMITIVE = SimpleMetaType.BYTE_PRIMITIVE;

    /** The simple type for java.lang.Character */
    public static final SimpleMetaType CHARACTER = SimpleMetaType.CHARACTER;
    public static final SimpleMetaType CHARACTER_PRIMITIVE = SimpleMetaType.CHARACTER_PRIMITIVE;

    /** The simple type for java.lang.Date */
    public static final SimpleMetaType DATE = SimpleMetaType.DATE;

    /** The simple type for java.lang.Double */
    public static final SimpleMetaType DOUBLE = SimpleMetaType.DOUBLE;
    public static final SimpleMetaType DOUBLE_PRIMITIVE = SimpleMetaType.DOUBLE_PRIMITIVE;

    /** The simple type for java.lang.Float */
    public static final SimpleMetaType FLOAT = SimpleMetaType.FLOAT;
    public static final SimpleMetaType FLOAT_PRIMITIVE = SimpleMetaType.FLOAT_PRIMITIVE;

    /** The simple type for java.lang.Integer */
    public static final SimpleMetaType INTEGER = SimpleMetaType.INTEGER;
    public static final SimpleMetaType INTEGER_PRIMITIVE = SimpleMetaType.INTEGER_PRIMITIVE;

    /** The simple type for java.lang.Long */
    public static final SimpleMetaType LONG = SimpleMetaType.LONG;
    public static final SimpleMetaType LONG_PRIMITIVE = SimpleMetaType.LONG_PRIMITIVE;

    /** The simple type for java.lang.Short */
    public static final SimpleMetaType SHORT = SimpleMetaType.SHORT;
    public static final SimpleMetaType SHORT_PRIMITIVE = SimpleMetaType.SHORT_PRIMITIVE;

    /** The simple type for java.lang.String */
    public static final SimpleMetaType STRING = SimpleMetaType.STRING;

    /** The simple type for java.lang.Void */
    public static final SimpleMetaType VOID = SimpleMetaType.VOID;

}
