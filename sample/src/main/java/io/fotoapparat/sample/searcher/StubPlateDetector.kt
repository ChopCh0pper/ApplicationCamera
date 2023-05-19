package io.fotoapparat.sample.searcher

import android.graphics.Bitmap
import java.lang.Thread.sleep

class StubPlateDetector: PlateDetector {
    override fun detect(bitmap: Bitmap): String? {
        sleep(500L)
        return "с555ср86"
    }
}