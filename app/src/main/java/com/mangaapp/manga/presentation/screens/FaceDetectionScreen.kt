package com.mangaapp.manga.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.core.OutputHandler
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A composable function that displays a screen for face detection using the device's front camera.
 */
@Composable
fun FaceDetectionScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val faceDetected = remember { mutableStateOf(false) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val boxSizeDp = 220.dp
    val density = LocalDensity.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasPermission) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    val previewView = PreviewView(context).apply {
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }

                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                            .build()

                        val options = getFaceDetectorOptions(boxSizeDp, density) {
                            faceDetected.value = it
                        }
                        val faceDetector = FaceDetector.createFromOptions(context, options)

                        val analysis = ImageAnalysis.Builder()
                            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                            .setTargetRotation(previewView.display.rotation)
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .setOutputImageFormat(OUTPUT_IMAGE_FORMAT_RGBA_8888)
                            .build()

                        analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                            try {
                                val bitmapBuffer =
                                    Bitmap.createBitmap(
                                        imageProxy.width,
                                        imageProxy.height,
                                        Bitmap.Config.ARGB_8888
                                    )
                                imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }

                                val matrix =
                                    Matrix().apply {
                                        postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                                        postScale(
                                            -1f,
                                            1f,
                                            imageProxy.width.toFloat(),
                                            imageProxy.height.toFloat()
                                        )
                                    }

                                val rotatedBitmap =
                                    Bitmap.createBitmap(
                                        bitmapBuffer,
                                        0,
                                        0,
                                        bitmapBuffer.width,
                                        bitmapBuffer.height,
                                        matrix,
                                        true
                                    )
                                Log.d("FaceDetection", "Bitmap size: ${rotatedBitmap.width}x${rotatedBitmap.height}")
                                val mpImage = BitmapImageBuilder(rotatedBitmap).build()
                                val frameTime = SystemClock.uptimeMillis()
                                faceDetector.detectAsync(mpImage, frameTime)
                            } catch (e: Exception) {
                                Log.e("FaceDetection", "Detection failed", e)
                                faceDetected.value = false
                            } finally {
                                imageProxy.close()
                            }
                        }

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                analysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }, ContextCompat.getMainExecutor(context))
                    previewView
                }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(boxSizeDp)
                    .border(
                        width = 4.dp,
                        color = if (faceDetected.value) Color.Green else Color.Red,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Camera permission is required for Face Detection.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                cameraExecutor.shutdown()
                cameraExecutor.awaitTermination(
                    Long.MAX_VALUE,
                    TimeUnit.NANOSECONDS
                )
            }
        }
    }
}

/**
 * Configures and returns the options for the MediaPipe Face Detector.
 * @return A configured `FaceDetector.FaceDetectorOptions` instance ready to be used by a `FaceDetector`.
 */
fun getFaceDetectorOptions(
    boxSizeDp: Dp,
    density: Density,
    onFaceDetected: (Boolean) -> Unit
): FaceDetector.FaceDetectorOptions {
    val modelName = "face_detection_short_range.tflite"

    val baseOptions = BaseOptions.builder()
        .setDelegate(Delegate.CPU)
        .setModelAssetPath(modelName)
        .build()

    val option = FaceDetector.FaceDetectorOptions.builder()
        .setBaseOptions(baseOptions)
        .setMinDetectionConfidence(0.5f)
        .setRunningMode(RunningMode.LIVE_STREAM)
        .setResultListener(object : OutputHandler.ResultListener<FaceDetectorResult, MPImage> {
            override fun run(result: FaceDetectorResult, mpImage: MPImage) {
                if (result.detections().isNotEmpty()) {
                    val detection = result.detections().first()
                    val boundingBox = detection.boundingBox()

                    val imageWidth = mpImage.width.toFloat()
                    val imageHeight = mpImage.height.toFloat()
                    val boxSizePx = with(density) { boxSizeDp.toPx() }

                    val boxLeft = (imageWidth - boxSizePx) / 2
                    val boxTop = (imageHeight - boxSizePx) / 2
                    val boxRight = boxLeft + boxSizePx
                    val boxBottom = boxTop + boxSizePx

                    val faceInside = boundingBox.left >= boxLeft &&
                            boundingBox.top >= boxTop &&
                            boundingBox.right <= boxRight &&
                            boundingBox.bottom <= boxBottom

                    onFaceDetected(faceInside)
                } else {
                    onFaceDetected(false)
                }
            }
        })
        .setErrorListener { error ->
            Log.e("FaceDetection", "Detection failed", error)
            onFaceDetected(false)
        }
        .build()

    return option
}
