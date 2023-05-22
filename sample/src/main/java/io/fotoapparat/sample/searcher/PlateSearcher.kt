package io.fotoapparat.sample.searcher

import android.os.Handler
import android.os.HandlerThread

class PlateSearcher(
    val cameraController: CameraController,
    val plateDetector: PlateDetector,
    val onPlateFoundListener: (String) -> Unit
) {
    private var thread = HandlerThread("plateSearcher").apply { start() }
    private val handler = Handler(thread.looper)

    private fun iterate(): Runnable = Runnable {
        if (thread.isInterrupted) {
            return@Runnable
        }

        val picture = cameraController.takePicture()
        val number = plateDetector.detect(picture)

        if (number == null) {
            oneMoreTime()
        } else {
            thread.interrupt()
            onPlateFoundListener.invoke(number)
        }
    }

    fun startLooking() {
        thread = HandlerThread("plateSearcher").apply { start() }
        oneMoreTime()
    }

    private fun oneMoreTime() {
        handler.post(iterate())
    }
}