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
package com.neuronrobotics.bowlercad.vitamin

import com.neuronrobotics.bowlerkernel.vitamins.vitamin.CenterOfMass
import com.neuronrobotics.bowlerkernel.vitamins.vitamin.DefaultTimingBelt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.octogonapus.ktguava.collections.emptyImmutableMap
import org.octogonapus.ktunits.quantities.gram
import org.octogonapus.ktunits.quantities.inch
import org.octogonapus.ktunits.quantities.millimeter

internal class TimingBeltGeneratorTest {

    private val generator = TimingBeltGenerator()

    @Test
    fun `test timing belt`() {
        val belt = DefaultTimingBelt(
            height = 11.2.millimeter,
            width = 6.2.millimeter,
            toothHeight = 1.2.millimeter,
            pitchWidth = 5.millimeter,
            mass = 10.gram,
            centerOfMass = CenterOfMass(
                0.inch,
                0.inch,
                0.inch
            ),
            specs = emptyImmutableMap()
        )

        val length = 40.millimeter
        val cad = generator.generateCAD(belt, length)

        assertAll(
            { assertEquals(length.millimeter / 2, cad.bounds.center.x) },
            { assertEquals(0.0, cad.bounds.center.y) },
            {
                assertEquals(
                    belt.height.millimeter / 2,
                    cad.bounds.center.z
                )
            },
            { assertEquals(length.millimeter, cad.bounds.bounds.x) },
            { assertEquals(belt.width.millimeter, cad.bounds.bounds.y) },
            { assertEquals(belt.height.millimeter, cad.bounds.bounds.z) }
        )
    }
}
