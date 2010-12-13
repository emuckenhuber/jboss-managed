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

package org.jboss.model.entity;

import java.util.List;

/**
 * TODO add class javadoc for UpdateIdentifier.
 *
 * @author Brian Stansberry
 */
public class UpdateIdentifier {

    private final List<EntityIdType> addressType;
    private final String updateId;

    public UpdateIdentifier(final List<EntityIdType> addressType, final String updateId) {
        assert addressType != null : "addressType is null";
        assert updateId != null : "updateId is null";
        this.addressType = addressType;
        this.updateId = updateId;
    }

    public List<EntityIdType> getAddressType() {
        return addressType;
    }

    public String getUpdateId() {
        return updateId;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * addressType.hashCode();
        result += 31 * updateId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof UpdateIdentifier) {
            UpdateIdentifier other = (UpdateIdentifier) obj;
            return addressType.equals(other.addressType) && updateId.equals(other.updateId);
        }
        return false;
    }




}
