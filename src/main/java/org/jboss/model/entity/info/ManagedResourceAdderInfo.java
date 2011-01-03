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

package org.jboss.model.entity.info;

import java.util.Arrays;

/**
 * TODO add class javadoc for ManagedResourceOperationInfo.
 *
 * @author Brian Stansberry
 */
public final class ManagedResourceAdderInfo extends ManagedResourceFeatureInfo {

    private static final long serialVersionUID = 5230879009939410051L;

    private final ManagedResourceParameterInfo[] NO_PARAMS = new ManagedResourceParameterInfo[0];

    /** The signature of the method, that is, the class names of the arguments. */
    private final ManagedResourceParameterInfo[] signature;
    private final RestartPolicy restartPolicy;

    /**
     * Constructs an <CODE>ManagedResourceAdderInfo</CODE> object.
     *
     * @param name The name of the method.
     * @param description A human readable description of the operation.
     * @param signature
     *            <CODE>ManagedResourceParameterInfo</CODE> objects describing the
     *            parameters(arguments) of the method. This may be null with the
     *            same effect as a zero-length array.
     * @param restartPolicy The restart policy.
     */
    public ManagedResourceAdderInfo(String name, String description, ManagedResourceParameterInfo[] signature, RestartPolicy restartPolicy) {
        this(name, description, signature, restartPolicy, null);
    }

    /**
     * Constructs an <CODE>ManagedResourceAdderInfo</CODE> object.
     *
     * @param name
     *            The name of the method.
     * @param description
     *            A human readable description of the operation.
     * @param signature
     *            <CODE>ManagedResourceParameterInfo</CODE> objects describing the
     *            parameters(arguments) of the method. This may be null with the
     *            same effect as a zero-length array.
     * @param restartPolicy the restart policy
     * @param fields The fields.
     */
    public ManagedResourceAdderInfo(String name, String description, ManagedResourceParameterInfo[] signature, RestartPolicy restartPolicy, Fields fields) {
        super(name, description, fields);

        if (signature == null || signature.length == 0) {
            signature = NO_PARAMS;
        } else {
            signature = signature.clone();
        }
        this.signature = signature;
        this.restartPolicy = restartPolicy;
    }

    /**
     * <p>
     * Returns the list of parameters for this operation. Each parameter is
     * described by an <CODE>ManagedResourceParameterInfo</CODE> object.
     * </p>
     *
     * <p>
     * The returned array is a shallow copy of the internal array, which means
     * that it is a copy of the internal array of references to the
     * <CODE>ManagedResourceParameterInfo</CODE> objects but that each referenced
     * <CODE>ManagedResourceParameterInfo</CODE> object is not copied.
     * </p>
     *
     * @return An array of <CODE>ManagedResourceParameterInfo</CODE> objects.
     */
    public ManagedResourceParameterInfo[] getSignature() {
        if (signature.length == 0) {
            return signature;
        } else {
            return signature.clone();
        }
    }

    public RestartPolicy getRestartPolicy() {
        return restartPolicy;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + "description=" + getDescription() + ", " + "name=" + getName() + ", "
                + "signature=" + Arrays.asList(signature) + ", " + "fields=" + getFields() + "]";
    }

    /**
     * Compare this ManagedResourceAdderInfo to another.
     *
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an ManagedResourceAdderInfo such
     *         that its {@link #getName()}, {@link #getReturnType()},
     *         {@link #getDescription()}, {@link #getRestartPolicy()},
     *         {@link #getFields()} and {@link #getSignature()} values are
     *         equal (not necessarily identical) to those of this
     *         ManagedResourceAdderInfo. Two signature arrays are equal if their
     *         elements are pairwise equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ManagedResourceAdderInfo))
            return false;
        ManagedResourceAdderInfo p = (ManagedResourceAdderInfo) o;
        return (p.getName().equals(getName())
                && p.getDescription().equals(getDescription())
                && restartPolicy == p.getRestartPolicy()
                && Arrays.equals(p.signature, signature) && p.getFields().equals(getFields()));
    }

    @Override
    public int hashCode() {
        return getName().hashCode() ^ Arrays.asList(signature).hashCode();
    }

}
