/*
 * This file is part of bowler-cad.
 *
 * bowler-cad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * bowler-cad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with bowler-cad.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.neuronrobotics.bowlercad.blender

import eu.mihosoft.vrl.v3d.CSG

interface Deduplicator {

    /**
     * Deduplicate the vertices as close as (or closer than) [threshold].
     *
     * @param csg The CSG.
     * @param threshold The minimum closeness to remove.
     * @return The simplified CSG>
     */
    fun deduplicate(csg: CSG, threshold: Double = 0.0001): CSG
}
