/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2000 - 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.model.entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares the order of two {@link ManagedResourceAddress}es. Sorts by
 * {@link EntityId}, then by depth, e.g.
 *
 * <pre>
 * aaa/bbb
 * xxx
 * xxx/ccc
 * </pre>
 *
 * @author Manik Surtani (<a href="mailto:manik AT jboss DOT org">manik AT jboss
 *         DOT org</a>)
 * @author Steve Woodcock (<a
 *         href="mailto:stevew@jofti.com">stevew@jofti.com</a>)
 */
class ManagedResourceAddressComparator implements Comparator<ManagedResourceAddress>, Serializable {
    private static final long serialVersionUID = -1420112846834101918L;
    static final ManagedResourceAddressComparator INSTANCE = new ManagedResourceAddressComparator();

    @Override
    public int compare(ManagedResourceAddress a, ManagedResourceAddress b) {
        int s1 = a.size();
        int s2 = b.size();

        if (s1 == 0) {
            return (s2 == 0) ? 0 : -1;
        }

        if (s2 == 0) {
            return 1;
        }

        int size = Math.min(s1, s2);

        for (int i = 0; i < size; i++) {
            EntityId e1 = a.get(i);
            EntityId e2 = b.get(i);
            if (e1 == e2) {
                continue;
            }
            if (e1 == null) {
                return 0;
            }
            if (e2 == null) {
                return 1;
            }
            if (!e1.equals(e2)) {
                int c = e1.compareTo(e2);
                if (c != 0) {
                    return c;
                }
            }
        }

        return s1 - s2;
    }

}
