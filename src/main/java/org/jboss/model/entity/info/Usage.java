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

/**
 * The intended use of an operation.
 *
 * @author Brian Stansberry
 */
public enum Usage {

    /**
     * Impacts the persistent configuration of the domain, a host controller, or
     * one or servers.
     */
    CONFIGURATION,
    /**
     * A read-only operation that gathers runtime metric information from
     * one or more servers.
     */
    METRIC,
    /**
     * An write-only or read-write operation that affects the runtime state
     * of a server but does not affect its persistent configuration.
     */
    MANAGEMENT,
    /**
     * An operation whose usage is unknown.
     */
    UNKOWN
}
