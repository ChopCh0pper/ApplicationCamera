package io.fotoapparat.sample.searcher

import android.graphics.Bitmap

interface PlateDetector {
    fun detect(bitmap: Bitmap): String?
}