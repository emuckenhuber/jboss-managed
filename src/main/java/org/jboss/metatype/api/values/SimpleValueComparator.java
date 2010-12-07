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

package org.jboss.metatype.api.values;

import java.util.Comparator;

import org.jboss.metatype.api.types.SimpleMetaType;

/**
 * A Comparator for SimpleValues.
 *
 * @author Scott.Stark@jboss.org
 * @author Ales.Justin@jboss.org
 */
public class SimpleValueComparator implements Comparator<SimpleValue> {

    public int compare(SimpleValue o1, SimpleValue o2) {
        SimpleMetaType smt1 = o1.getMetaType();
        SimpleMetaType smt2 = o2.getMetaType();
        return (smt1 == smt2) ? smt1.compare(o1.getValue(), o2.getValue()) : -1;
    }
}
