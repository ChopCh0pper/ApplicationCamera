package io.fotoapparat.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.Zoom
import io.fotoapparat.result.transformer.scaled
import io.fotoapparat.sample.constance.Constance
import io.fotoapparat.sample.searcher.CameraController
import io.fotoapparat.sample.searcher.PlateSearcher
import io.fotoapparat.sample.searcher.StubPlateDetector
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val permissionsDelegate = PermissionsDelegate(this)

    private var permissionsGranted: Boolean = false
    private var activeCamera: Camera = Camera.Back

    private lateinit var fotoapparat: Fotoapparat

    private val plateDetector = StubPlateDetector()
    private val cameraController = CameraController()
    private val searher = PlateSearcher(
        cameraController,
        plateDetector
    ) { number ->
        openResultActivity(number)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionsGranted = permissionsDelegate.hasCameraPermission()

        if (permissionsGranted) {
            cameraView.visibility = View.VISIBLE
        } else {
            permissionsDelegate.requestCameraPermission()
        }

        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                focusView = focusView,
                logger = logcat(),
                lensPosition = activeCamera.lensPosition,
                cameraConfiguration = activeCamera.configuration,
                cameraErrorCallback = { Log.e(Constance.LOGGING_TAG, "Camera error: ", it) }
        )
    }

    override fun onResume() {
        super.onResume()
        searher.startLooking()

    }

//    private fun takePicture(): () -> Unit = {
//        val photoResult = fotoapparat
//                .autoFocus()
//                .takePicture()
//
//        photoResult
//                .saveToFile(File(
//                        getExternalFilesDir("photos"),
//                        "photo.jpg"
//                ))
//
//        photoResult
//                .toBitmap(scaled(scaleFactor = 0.25f))
//    }


    override fun onStart() {
        super.onStart()
        if (permissionsGranted) {
            fotoapparat.start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (permissionsGranted) {
            fotoapparat.stop()
        }
    }

    private fun openResultActivity(number: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constance.INTENT_KEY_1, number)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            permissionsGranted = true
            fotoapparat.start()
            cameraView.visibility = View.VISIBLE
        }
    }

private sealed class Camera(
        val lensPosition: LensPositionSelector,
        val configuration: CameraConfiguration
) {

    object Back : Camera(
            lensPosition = back(),
            configuration = CameraConfiguration(
                    previewResolution = firstAvailable(
                            wideRatio(highestResolution()),
                            standardRatio(highestResolution())
                    ),
                    previewFpsRange = highestFps(),
                    flashMode = off(),
                    focusMode = firstAvailable(
                            continuousFocusPicture(),
                            autoFocus()
                    ),
                    frameProcessor = {
                        // Do something with the preview frame
                    }
            )
    )
    }
}
