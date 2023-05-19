package io.fotoapparat.hardware.orientation

typealias DeviceOrientation = Orientation
typealias ScreenOrientation = Orientation

/**
 * The device orientation.
 */
sealed class Orientation(
        val degrees: Int
) {

    /**
     * A vertical device orientation.
     */
    sealed class Vertical(degrees: Int) : Orientation(degrees) {

        /**
         * A vertical, normal orientation.
         */
        object Portrait : Vertical(0) {
            override fun toString(): String = "Orientation.Vertical.Portrait"
        }

        /**
         * A reversed (flipped phone) orientation.
         */
        object ReversePortrait : Vertical(180) {
            override fun toString(): String = "Orientation.Vertical.ReversePortrait"
        }

    }

    /**
     * A horizontal device orientation.
     */
    sealed class Horizontal(degrees: Int) : Orientation(degrees) {

        /**
         * A 90 degrees clockwise from "normal", orientation.
         */
        object Landscape : Horizontal(90) {
            override fun toString(): String = "Orientation.Horizontal.Landscape"
        }

        /**
         * A 90 degrees counter-clockwise from "normal", orientation.
         */
        object ReverseLandscape : Horizontal(270) {
            override fun toString(): String = "Orientation.Horizontal.ReverseLandscape"
        }

    }
}

internal fun Int.toOrientation(): Orientation {
    return when (this) {
        0, 360 -> Orientation.Vertical.Portrait
        90 -> Orientation.Horizontal.Landscape
        180 -> Orientation.Vertical.ReversePortrait
        270 -> Orientation.Horizontal.ReverseLandscape
        else -> throw IllegalArgumentException("Cannot convert $this to absolute Orientation.")
    }
}
