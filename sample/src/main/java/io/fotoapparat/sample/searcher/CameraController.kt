package io.fotoapparat.sample.searcher

import android.graphics.Bitmap

class CameraController {
    fun takePicture(): Bitmap {
        return Bitmap.createBitmap(1000,1000,Bitmap.Config.ARGB_8888)
    }
}