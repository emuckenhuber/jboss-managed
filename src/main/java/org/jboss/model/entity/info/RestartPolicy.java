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
 * Whether a given operation requires a server restart to take effect
 * in the runtime.
 *
 * @author Brian Stansberry
 */
public enum RestartPolicy {

    /**
     * Requires a full restart of the server VM process for the change to
     * take effect.
     */
    COLD_START_REQUIRED,
    /**
     * Does not require a restart of the server VM process, but requires a
     * restart of the core service on which all other services depend. Effect
     * is a restart of all services.
     */
    WARM_START_REQUIRED,
    /**
     * No restart is required; the operation will impact the runtime immediately.
     */
    NOT_REQUIRED,
    /** Indicates that whether a restart is required is unknown. */
    UNKNOWN
}
