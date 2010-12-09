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

package org.jboss.model.detyped.info;

import java.io.Serializable;

/**
 * TODO add class javadoc for Cardinality.
 *
 * @author Brian Stansberry
 */
public class Cardinality implements Serializable {

    private static final long serialVersionUID = 7476244718496525796L;

    public static final int CARDINALITY_INFINITY = -1;

    public static final Cardinality ZERO_ONE = new Cardinality(0, 1);
    public static final Cardinality ZERO_INFINITY = new Cardinality(0, -1);
    public static final Cardinality ONE = new Cardinality(1, 1);
    public static final Cardinality ONE_INFINITY = new Cardinality(1, -1);

    private final int min;
    private final int max;

    public Cardinality(int min, int max) {
        if (min < 0)
            throw new IllegalArgumentException("Invalid min " + min + " -- cannot be less than 0");
        if (max < -1)
            throw new IllegalArgumentException("Invalid max " + max + " -- cannot be less than -1");
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isInfiniteMax() {
        return max == CARDINALITY_INFINITY;
    }
}
