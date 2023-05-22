package io.fotoapparat.sample.searcher

import android.graphics.Bitmap
import java.lang.Thread.sleep

class StubPlateDetector: PlateDetector {
    override fun detect(bitmap: Bitmap): String? {
        sleep(3000L)
        return "М018НЕ77"
    }
}