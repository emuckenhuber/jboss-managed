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

import org.jboss.model.types.MetaType;

/**
 * TODO add class javadoc for EntityOperationInfo.
 *
 * @author Brian Stansberry
 */
public final class EntityOperationInfo extends EntityFeatureInfo {

    private static final long serialVersionUID = 5230879009939410051L;

    private final EntityParameterInfo[] NO_PARAMS = new EntityParameterInfo[0];

    public enum Usage {
        CONFIGURATION, METRIC, MANAGEMENT, UNKOWN
    }

    /** The method's return type. */
    private final MetaType type;
    private final Usage usage;
    private final Impact impact;
    private final RestartPolicy restartPolicy;

    /** The signature of the method, that is, the class names of the arguments. */
    private final EntityParameterInfo[] signature;

    /**
     * Constructs an <CODE>EntityOperationInfo</CODE> object.
     *
     * @param name The name of the method.
     * @param description A human readable description of the operation.
     * @param signature
     *            <CODE>EntityParameterInfo</CODE> objects describing the
     *            parameters(arguments) of the method. This may be null with the
     *            same effect as a zero-length array.
     * @param type The type of the method's return value.
     * @param impact The impact of the method
     */
    public EntityOperationInfo(String name, String description, EntityParameterInfo[] signature, MetaType type,
            Usage usage, RestartPolicy restartPolicy, Impact impact) {
        this(name, description, signature, type, usage, restartPolicy, impact, null);
    }

    /**
     * Constructs an <CODE>EntityOperationInfo</CODE> object.
     *
     * @param name The name of the method.
     * @param description A human readable description of the operation.
     * @param signature
     *            <CODE>EntityParameterInfo</CODE> objects describing the
     *            parameters(arguments) of the method. This may be null with the
     *            same effect as a zero-length array.
     * @param type The type of the method's return value.
     * @param impact The impact of the method
     * @param descriptor
     *            The descriptor for the operation. This may be null which is
     *            equivalent to an empty descriptor.
     */
    public EntityOperationInfo(String name, String description, EntityParameterInfo[] signature, MetaType type,
            Usage usage, RestartPolicy restartPolicy, Impact impact, Fields fields) {

        super(name, description, fields);
        if (signature == null || signature.length == 0) {
            signature = NO_PARAMS;
        } else {
            signature = signature.clone();
        }
        this.signature = signature;
        this.type = type;
        this.usage = usage;
        this.restartPolicy = restartPolicy;
        this.impact = impact;
    }

    /**
     * Returns the type of the method's return value.
     *
     * @return the return type.
     */
    public MetaType getReturnType() {
        return type;
    }

    /**
     * <p>
     * Returns the list of parameters for this operation. Each parameter is
     * described by an <CODE>EntityParameterInfo</CODE> object.
     * </p>
     *
     * <p>
     * The returned array is a shallow copy of the internal array, which means
     * that it is a copy of the internal array of references to the
     * <CODE>EntityParameterInfo</CODE> objects but that each referenced
     * <CODE>EntityParameterInfo</CODE> object is not copied.
     * </p>
     *
     * @return An array of <CODE>EntityParameterInfo</CODE> objects.
     */
    public EntityParameterInfo[] getSignature() {
        if (signature.length == 0)
            return signature;
        else
            return signature.clone();
    }

    public Usage getUsage() {
        return usage;
    }

    public RestartPolicy getRestartPolicy() {
        return restartPolicy;
    }

    /**
     * Returns the impact of the method
     *
     * @return the impact code.
     */
    public Impact getImpact() {
        return impact;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + "description=" + getDescription() + ", " + "name=" + getName() + ", "
                + "returnType=" + getReturnType() + ", " + "signature=" + Arrays.asList(signature) + ", " + "impact=("
                + getImpact() + "), " + "fields=" + getFields() + "]";
    }

    /**
     * Compare this EntityOperationInfo to another.
     *
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an EntityOperationInfo such
     *         that its {@link #getName()}, {@link #getReturnType()},
     *         {@link #getDescription()}, {@link #getImpact()},
     *         {@link #getFields()} and {@link #getSignature()} values are
     *         equal (not necessarily identical) to those of this
     *         EntityOperationInfo. Two signature arrays are equal if their
     *         elements are pairwise equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EntityOperationInfo))
            return false;
        EntityOperationInfo p = (EntityOperationInfo) o;
        return (p.getName().equals(getName()) && p.getReturnType().equals(getReturnType())
                && p.getDescription().equals(getDescription()) && p.getImpact() == getImpact()
                && Arrays.equals(p.signature, signature) && p.getFields().equals(getFields()));
    }

    /*
     * We do not include everything in the hashcode. We assume that if two
     * operations are different they'll probably have different names or types.
     * The penalty we pay when this assumption is wrong should be less than the
     * penalty we would pay if it were right and we needlessly hashed in the
     * description and the parameter array.
     */
    @Override
    public int hashCode() {
        return getName().hashCode() ^ getReturnType().hashCode();
    }

}
