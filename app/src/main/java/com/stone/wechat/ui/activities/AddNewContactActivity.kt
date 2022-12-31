package com.stone.wechat.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.stone.wechat.R
import com.stone.wechat.utils.getString
import kotlinx.android.synthetic.main.activity_add_new_contact.*
import java.nio.ByteBuffer
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.ln
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class AddNewContactActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, AddNewContactActivity::class.java)
        }
    }
    override val layoutId: Int = R.layout.activity_add_new_contact


    //    private lateinit var cameraExecutor: ExecutorService
    private lateinit var barcodeScanner: BarcodeScanner
    val DESIRED_WIDTH_CROP_PERCENT = 8
    val DESIRED_HEIGHT_CROP_PERCENT = 74
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)
        makeStatusBarTransparent()


//        Log.i("xy",overLay.y.toString())

        viewFinder.post {
            startCamera()
        }
//        overLay.apply {
//            setZOrderOnTop(true)
//            holder.setFormat(PixelFormat.TRANSPARENT)
//            holder.addCallback(object : SurfaceHolder.Callback {
//                override fun surfaceChanged(
//                    holder: SurfaceHolder,
//                    format: Int,
//                    width: Int,
//                    height: Int
//                ) {
//                }
//
//                override fun surfaceDestroyed(holder: SurfaceHolder) {}
//
//                override fun surfaceCreated(holder: SurfaceHolder) {
//                    holder?.let {
//                        drawOverlay(
//                            it,
//                            DESIRED_HEIGHT_CROP_PERCENT,
//                            DESIRED_WIDTH_CROP_PERCENT
//                        )
//                    }
//                }
//            })
//
//
//        }

        imgBack.setOnClickListener { onBackPressed() }


    }


    private fun startCamera() {
        var cameraController = LifecycleCameraController(baseContext)
        val previewView: PreviewView = viewFinder

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            MlKitAnalyzer(
                listOf(barcodeScanner),
                COORDINATE_SYSTEM_VIEW_REFERENCED,
                ContextCompat.getMainExecutor(this)
            ) { result: MlKitAnalyzer.Result? ->
                val barcodeResults = result?.getValue(barcodeScanner)
                if ((barcodeResults == null) ||
                    (barcodeResults.size == 0) ||
                    (barcodeResults.first() == null)
                ) {
                    previewView.overlay.clear()
                    previewView.setOnTouchListener { _, _ -> false } //no-op
                    return@MlKitAnalyzer
                }


                val qrCodeViewModel = QrCodeViewModel(barcodeResults[0])
//                var clip = overLay.clipBounds
                Log.i("cliptop",qrCodeViewModel.boundingRect.top.toString())
                Log.i("clipbottom",qrCodeViewModel.boundingRect.bottom.toString())
                if(800 < qrCodeViewModel.boundingRect.top && 1470 > qrCodeViewModel.boundingRect.bottom
                    && 200 < qrCodeViewModel.boundingRect.left && 850 > qrCodeViewModel.boundingRect.right
                     ) {


                    val qrCodeDrawable = QrCodeDrawable(qrCodeViewModel)
                    previewView.overlay.clear()
                    previewView.overlay.add(qrCodeDrawable)
                }
//
//                previewView.setOnTouchListener(qrCodeViewModel.qrCodeTouchCallback)
//                Toast.makeText(this, barcodeResults[0].url!!.url!!, Toast.LENGTH_SHORT).show()

            }
        )
        cameraController.bindToLifecycle(this)
        previewView.controller = cameraController
    }
}
//    /** Blocking camera and inference operations are performed using this executor. */
//    private lateinit var cameraExecutor: ExecutorService
//
//    /** UI callbacks are run on this executor. */
////    private lateinit var scopedExecutor: ScopedExecutor
//
//    private var displayId: Int = -1
//    val DESIRED_WIDTH_CROP_PERCENT = 8
//    val DESIRED_HEIGHT_CROP_PERCENT = 74
//    private  val RATIO_4_3_VALUE = 4.0 / 3.0
//    private  val RATIO_16_9_VALUE = 16.0 / 9.0
//    private var camera: Camera? = null
//    private var imageAnalyzer: ImageAnalysis? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_new_contact)
//        cameraExecutor = Executors.newCachedThreadPool()
//        viewFinder.post {
//            // Keep track of the display in which this view is attached
//            displayId = viewFinder.display.displayId
//
//            // Set up the camera and its use cases
//            startCamera()
//        }
//        overLay.apply {
//            setZOrderOnTop(true)
//            holder.setFormat(PixelFormat.TRANSPARENT)
//            holder.addCallback(object : SurfaceHolder.Callback {
//                override fun surfaceChanged(
//                    holder: SurfaceHolder,
//                    format: Int,
//                    width: Int,
//                    height: Int
//                ) {
//                }
//
//                override fun surfaceDestroyed(holder: SurfaceHolder) {}
//
//                override fun surfaceCreated(holder: SurfaceHolder) {
//                    holder?.let {
//                        drawOverlay(
//                            it,
//                            DESIRED_HEIGHT_CROP_PERCENT,
//                            DESIRED_WIDTH_CROP_PERCENT
//                        )
//                    }
//                }
//            })
//        }
//
//    }
//
//    fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider = try {
//                cameraProviderFuture.get()
//            } catch (e: ExecutionException) {
//                throw IllegalStateException("Camera initialization failed.", e.cause!!)
//            }
//            // Build and bind the camera use cases
//
//            val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
//            Log.d("TAG", "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")
//
//            val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
//            Log.d("TAG", "Preview aspect ratio: $screenAspectRatio")
//
//            val rotation = viewFinder.display.rotation
//
//            val preview = Preview.Builder()
//                .setTargetAspectRatio(screenAspectRatio)
//                .setTargetRotation(rotation)
//                .build()
//
//            // Build the image analysis use case and instantiate our analyzer
//            val options = BarcodeScannerOptions.Builder()
//                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
//                .build()
//            barcodeScanner = BarcodeScanning.getClient(options)
//            imageAnalyzer = ImageAnalysis.Builder()
//                // We request aspect ratio but no resolution
//                .setTargetAspectRatio(screenAspectRatio)
//                .setTargetRotation(rotation)
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//                .also {
//                    it.setAnalyzer(
//                        ContextCompat.getMainExecutor(this),
//                        MlKitAnalyzer(
//                            listOf(barcodeScanner),
//                            COORDINATE_SYSTEM_VIEW_REFERENCED,
//                            ContextCompat.getMainExecutor(this)
//                        ) { result: MlKitAnalyzer.Result? ->
//                            val barcodeResults = result?.getValue(barcodeScanner)
//                            if ((barcodeResults == null) ||
//                                (barcodeResults.size == 0) ||
//                                (barcodeResults.first() == null)
//                            ) {
//                                viewFinder.overlay.clear()
//                                viewFinder.setOnTouchListener { _, _ -> false } //no-op
//                                return@MlKitAnalyzer
//                            }
//
//                            val qrCodeViewModel = QrCodeViewModel(barcodeResults[0])
//                            val qrCodeDrawable = QrCodeDrawable(qrCodeViewModel)
////
////                previewView.setOnTouchListener(qrCodeViewModel.qrCodeTouchCallback)
////                Toast.makeText(this, barcodeResults[0].url!!.url!!, Toast.LENGTH_SHORT).show()
//                            viewFinder.overlay.clear()
//                            viewFinder.overlay.add(qrCodeDrawable)
//                        }
//                    )
//                }
//            val cameraSelector =
//                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
//
//            try {
//                // Unbind use cases before rebinding
//                cameraProvider.unbindAll()
//
//                // Bind use cases to camera
//                camera = cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageAnalyzer
//                )
//                preview.setSurfaceProvider(viewFinder.surfaceProvider)
//            } catch (exc: IllegalStateException) {
//                Log.e("TAG", "Use case binding failed. This must be running on main thread.", exc)
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
//    private fun aspectRatio(width: Int, height: Int): Int {
//        val previewRatio = ln(max(width, height).toDouble() / min(width, height))
//        if (abs(previewRatio - ln(RATIO_4_3_VALUE))
//            <= abs(previewRatio - ln(RATIO_16_9_VALUE))
//        ) {
//            return AspectRatio.RATIO_4_3
//        }
//        return AspectRatio.RATIO_16_9
//    }
    private fun drawOverlay(
        holder: SurfaceHolder,
        heightCropPercent: Int,
        widthCropPercent: Int
    ) {
        val canvas = holder.lockCanvas()
        val bgPaint = Paint().apply {
            alpha = 140
        }
        canvas.drawPaint(bgPaint)
        val rectPaint = Paint()
        rectPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        rectPaint.style = Paint.Style.FILL
        rectPaint.color = Color.WHITE
        val outlinePaint = Paint()
        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.color = Color.WHITE
        outlinePaint.strokeWidth = 4f
        val surfaceWidth = holder.surfaceFrame.width()
        val surfaceHeight = holder.surfaceFrame.height()

        val cornerRadius = 25f
        // Set rect centered in frame
        val rectTop = surfaceHeight * heightCropPercent / 2 / 100f
        val rectLeft = surfaceWidth * widthCropPercent / 2 / 100f
        val rectRight = surfaceWidth * (1 - widthCropPercent / 2 / 100f)
        val rectBottom = surfaceHeight * (1 - heightCropPercent / 2 / 100f)
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRoundRect(
            rect, cornerRadius, cornerRadius, rectPaint
        )
        canvas.drawRoundRect(
            rect, cornerRadius, cornerRadius, outlinePaint
        )
        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 50F

        val overlayText = getString(R.string.app_name)
        val textBounds = Rect()
        textPaint.getTextBounds(overlayText, 0, overlayText.length, textBounds)
        val textX = (surfaceWidth - textBounds.width()) / 2f
        val textY = rectBottom + textBounds.height() + 15f // put text below rect and 15f padding
        canvas.drawText(getString(R.string.app_name), textX, textY, textPaint)
        holder.unlockCanvasAndPost(canvas)
    }
//}
//
class QrCodeDrawable(qrCodeViewModel: QrCodeViewModel) : Drawable() {
    private val boundingRectPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.YELLOW
        strokeWidth = 5F
        alpha = 200
    }

    private val contentRectPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        alpha = 255
    }

    private val contentTextPaint = Paint().apply {
        color = Color.DKGRAY
        alpha = 255
        textSize = 36F
    }

    private val qrCodeViewModel = qrCodeViewModel
    private val contentPadding = 25
    private var textWidth = contentTextPaint.measureText(qrCodeViewModel.qrContent).toInt()

    override fun draw(canvas: Canvas) {


        canvas.drawRect(qrCodeViewModel.boundingRect, boundingRectPaint)
        canvas.drawRect(
            Rect(
                qrCodeViewModel.boundingRect.left,
                qrCodeViewModel.boundingRect.bottom + contentPadding / 2,
                qrCodeViewModel.boundingRect.left + textWidth + contentPadding * 2,
                qrCodeViewModel.boundingRect.bottom + contentTextPaint.textSize.toInt() + contentPadding
            ),
            contentRectPaint
        )
        canvas.drawText(
            qrCodeViewModel.boundingRect.left.toString().plus("-----").plus(qrCodeViewModel.boundingRect.right),
            (qrCodeViewModel.boundingRect.left + contentPadding).toFloat(),
            (qrCodeViewModel.boundingRect.bottom + contentPadding * 2).toFloat(),
            contentTextPaint
        )
//        canvas.drawText(
//            qrCodeViewModel.qrContent,
//            (qrCodeViewModel.boundingRect.left + contentPadding).toFloat(),
//            (qrCodeViewModel.boundingRect.bottom + contentPadding * 2).toFloat(),
//            contentTextPaint
//        )
    }

    override fun setAlpha(alpha: Int) {
        boundingRectPaint.alpha = alpha
        contentRectPaint.alpha = alpha
        contentTextPaint.alpha = alpha
    }

    override fun setColorFilter(colorFiter: ColorFilter?) {
        boundingRectPaint.colorFilter = colorFilter
        contentRectPaint.colorFilter = colorFilter
        contentTextPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}

class QrCodeViewModel(barcode: Barcode) {
    var boundingRect: Rect = barcode.boundingBox!!
    var qrContent: String = ""
    var qrCodeTouchCallback = { v: View, e: MotionEvent -> false } //no-op

    init {
        when (barcode.valueType) {
            Barcode.TYPE_URL -> {
                qrContent = barcode.url!!.url!!
                qrCodeTouchCallback = { v: View, e: MotionEvent ->
                    if (e.action == MotionEvent.ACTION_DOWN && boundingRect.contains(
                            e.getX().toInt(), e.getY().toInt()
                        )
                    ) {
                        val openBrowserIntent = Intent(Intent.ACTION_VIEW)
                        openBrowserIntent.data = Uri.parse(qrContent)
                        v.context.startActivity(openBrowserIntent)
                    }
                    true // return true from the callback to signify the event was handled
                }
            }
            // Add other QR Code types here to handle other types of data,
            // like Wifi credentials.
            else -> {
                qrContent = "Unsupported data type: ${barcode.rawValue.toString()}"
            }
        }
    }
}
fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}