package io.fotoapparat.preview

/**
 * Performs processing on preview frames.
 *
 * Frame processors are called from worker thread (aka non-UI thread). After
 * [.processFrame] completes the frame is returned back to the pool where it is reused
 * afterwards. This means that implementations should take special care to not do any operations on
 * frame after method completes.
 */
interface FrameProcessor {

    /**
     * Performs processing on preview frames. Read class description for more details.
     *
     * @param frame frame of the preview. Do not cache it as it will eventually be reused by the
     * camera.
     */
    fun process(frame: Frame)
}