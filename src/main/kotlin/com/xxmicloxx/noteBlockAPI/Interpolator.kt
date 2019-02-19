package com.xxmicloxx.noteBlockAPI

import java.util.*

/**
 *
 * Static methods for doing useful math<hr></hr>
 *
 * @author : $Author: brian $
 * @version : $Revision: 1.1 $
 *
 *
 * <hr></hr>
 *
 *<font size="-1" color="#336699">[
 * The Monterey Bay Aquarium Research Institute (MBARI)](http://www.mbari.org) provides this
 * documentation and code &quot;as is&quot;, with no warranty, express or
 * implied, of its quality or consistency. It is provided without support and
 * without obligation on the part of MBARI to assist in its use, correction,
 * modification, or enhancement. This information should not be published or
 * distributed to third parties without specific written permission from
 * MBARI.</font><br></br>
 *
 *
 * <font size="-1" color="#336699">Copyright 2002 MBARI.<br></br>
 * MBARI Proprietary Information. All rights reserved.</font><br></br><hr></hr><br></br>
 */
object Interpolator {
    @Throws(IllegalArgumentException::class)
    fun interpLinear(x: DoubleArray, y: DoubleArray, xi: DoubleArray): DoubleArray {

        if (x.size != y.size) {
            throw IllegalArgumentException("X and Y must be the same length")
        }
        if (x.size == 1) {
            throw IllegalArgumentException("X must contain more than one value")
        }
        val dx = DoubleArray(x.size - 1)
        val dy = DoubleArray(x.size - 1)
        val slope = DoubleArray(x.size - 1)
        val intercept = DoubleArray(x.size - 1)

        // Calculate the line equation (i.e. slope and intercept) between each point
        for (i in 0 until x.size - 1) {
            dx[i] = x[i + 1] - x[i]
            if (dx[i] == 0.0) {
                throw IllegalArgumentException("X must be montotonic. A duplicate " + "x-value was found")
            }
            if (dx[i] < 0) {
                throw IllegalArgumentException("X must be sorted")
            }
            dy[i] = y[i + 1] - y[i]
            slope[i] = dy[i] / dx[i]
            intercept[i] = y[i] - x[i] * slope[i]
        }

        // Perform the interpolation here
        val yi = DoubleArray(xi.size)
        for (i in xi.indices) {
            if (xi[i] > x[x.size - 1] || xi[i] < x[0]) {
                yi[i] = java.lang.Double.NaN
            } else {
                var loc = Arrays.binarySearch(x, xi[i])
                if (loc < -1) {
                    loc = -loc - 2
                    yi[i] = slope[loc] * xi[i] + intercept[loc]
                } else {
                    yi[i] = y[loc]
                }
            }
        }

        return yi
    }

    @Throws(IllegalArgumentException::class)
    fun interpLinear(x: LongArray, y: DoubleArray, xi: LongArray): DoubleArray {

        val xd = DoubleArray(x.size)
        for (i in x.indices) {
            xd[i] = x[i].toDouble()
        }

        val xid = DoubleArray(xi.size)
        for (i in xi.indices) {
            xid[i] = xi[i].toDouble()
        }

        return interpLinear(xd, y, xid)
    }

    fun interpLinear(xy: DoubleArray, xx: Double): Double {
        if (xy.size % 2 != 0) {
            throw IllegalArgumentException("XY must be divisible by two.")
        }
        val x = DoubleArray(xy.size / 2)
        val y = DoubleArray(x.size)
        for (i in xy.indices) {
            if (i % 2 == 0) {
                x[i / 2] = xy[i]
            } else {
                y[i / 2] = xy[i]
            }
        }
        return interpLinear(x, y, doubleArrayOf(xx))[0]
    }
}