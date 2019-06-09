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
import eu.mihosoft.vrl.v3d.STL
import java.io.File
import java.nio.file.Paths
import java.text.NumberFormat
import kotlin.random.Random

class BlenderDeduplicator(
    private val blenderExec: String = "blender",
    private val dedupScript: File = File(BlenderDeduplicator::class.java.getResource("dedup.py").file)
) : Deduplicator {

    private val folder = dedupScript.parent

    override fun deduplicate(csg: CSG, threshold: Double): CSG {
        val filenamePrefix = Random.nextBytes(12).joinToString("")

        File("$folder/$filenamePrefix.stl").writeText(csg.toStlString())

        // Double.toString() would use scientific notation
        val thresholdString = NumberFormat.getInstance().apply {
            isGroupingUsed = false
            maximumIntegerDigits = 999
            maximumFractionDigits = 999
        }.format(threshold)

        val command = "$blenderExec --background --python $dedupScript -- " +
            "$folder/$filenamePrefix.stl $folder/$filenamePrefix-deduped.stl $thresholdString"
        Runtime.getRuntime().exec(command).waitFor()

        val csgBack = STL.file(Paths.get("$folder/$filenamePrefix-deduped.stl"))

        File("$folder/$filenamePrefix.stl").delete()
        File("$folder/$filenamePrefix-deduped.stl").delete()

        return csgBack
    }
}
