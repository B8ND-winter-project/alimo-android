package com.b1nd.alimo.presentation.utiles

import android.app.DownloadManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowInsetsController
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun <T> Fragment.collectFlow(
    state: Flow<T>,
    action: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collectLatest {
                action(it)
            }
        }
    }
}

fun <T> Fragment.collectStateFlow(
    state: StateFlow<T>,
    action: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collectLatest {
                action(it)
            }
        }
    }
}



fun EditText.setOnPasteListener(onPaste: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        private var oldText: String = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            oldText = s?.toString() ?: ""
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newText = s?.toString() ?: ""
            if (newText.length > oldText.length && newText.length == this@setOnPasteListener.maxLines) {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val pasteData = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
                if (!pasteData.isNullOrEmpty()) {
                    onPaste(pasteData)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

internal fun ViewModel.launchMain(action: () -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {
        action()
    }
}

internal fun ViewModel.launchIO(action: suspend () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action()
    }
}

internal fun Event.onSuccessEvent(onMessage: (String) -> Unit) {
    when(this) {
        is Event.Success -> {
            onMessage(this.message)
        }
        else -> {}
    }
}

internal fun ImageView.loadImage(url: String, onLoad: (String) -> (Unit) = {}) {
    Glide.with(this)
        .load(url)
        .listener(createLogListener(onLoad))
        .centerCrop()
        .format(DecodeFormat.PREFER_RGB_565)
        .into(this)
}

internal fun ImageView.loadNotCropImage(url: String, onLoad: (String) -> (Unit) = {}) {
    Glide.with(this)
        .load(url)
        .listener(createLogListener(onLoad))
        .into(this)
}

private fun createLogListener(onLoad: (String) -> (Unit) = {}): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {

        // Image Load 실패 시 CallBack
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>,
            isFirstResource: Boolean,
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>?,
            dataSource: DataSource,
            isFirstResource: Boolean,
        ): Boolean {
            if (resource is BitmapDrawable) {
                val bitmap = resource.bitmap
                onLoad("${bitmap.width}:${bitmap.height}")
            }
            return false
        }
    }
}

fun Fragment.startAnimationWithShow(view: View, id: Int) {
    view.visibility = View.VISIBLE  //애니메이션 전에 뷰를 보이게 한다
    view.startAnimation(AnimationUtils.loadAnimation(requireContext(), id)) //애니메이션 설정&시작
}

//사라지기
fun Fragment.startAnimationWithHide(view: View, id: Int) {
    val exitAnim = AnimationUtils.loadAnimation(requireContext(), id)    //애니메이션 설정
    exitAnim.setAnimationListener(HideAnimListener(view))   //리스너를 통해 애니메이션이 끝나면 뷰를 감춘다
    view.startAnimation(exitAnim) //애니메이션 시작
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.startActivityWithFinishAll(activity: Class<*>) {
    val intent = Intent(context!!.applicationContext, activity)
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    startActivity(intent)
    this.requireActivity().finishAffinity()
}


fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.setImageResourceAndClearTint(@DrawableRes id: Int) {
    this.setImageResource(id)
    this.imageTintList = null
}

fun Int.toConvertBytes(): String = convertBytes(this)

fun Fragment.getResourceString(@StringRes id: Int) =
    requireContext().getString(id)

fun LocalDateTime.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분")
    return this.format(formatter).replace("PM", "오후").replace("AM", "오전")
}

fun Fragment.systemBarDark(isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity?.window?.statusBarColor = if (isDark) requireContext().getColor(R.color.black) else requireContext().getColor(
            R.color.white)
        activity?.window?.insetsController?.setSystemBarsAppearance(
            if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        @Suppress("DEPRECATION")
        activity?.window?.decorView?.systemUiVisibility = if(isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun Fragment.downloadFile(
    url: String,
    name: String,
    extension: String,
) {
    try {
        if (!Patterns.WEB_URL.matcher(url).matches()) { // 웹 url인지 유효성 검사
            return
        }
//        val extension = url.split(".").last()
//        val fileName = "${getString(R.string.app_name)}/${getTimeString(extension)}"
//        val fileName = "${getString(R.string.app_name)}/${name}" // "${getString(R.string.app_name)}/${name}"

        val mimeType = when (extension.replace("application/", "")) {
            "jpg", "png", "jpeg", "gif", "bmp", "webp" -> "image/*"
            "plane", "html", "css", "javascript", "txt", "json", "csv" -> "text/*"
            "midi", "mpeg", "wav", "mp3" -> "audio/*"
            "webm", "mp4", "ogg", "avi", "mkv", "flv", "m4p", "m4v" -> "video/*"
            else -> "application/${extension}"
        }



        val downloadManager = DownloadManager.Request(Uri.parse(url))
        val filePath = "/${getString(R.string.app_name)}/${name}"
        downloadManager
            .setTitle(filePath)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // 알림 설정
            .setMimeType(mimeType)
//                .setDestinationInExternalFilesDir(
//                    requireContext(),
//                    Environment.DIRECTORY_DOWNLOADS,
//                    "/${getString(R.string.app_name)}/${name}"
//                )
//                .setDestinationUri(Uri.parse(filePath))
            // 작동하는데, download에도 같이 다운로드됨
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                filePath
            ) // 다운로드 완료 시 보여지는 이름


        val manager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(downloadManager)
    } catch (e: Exception) {
        e.printStackTrace()
//            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    requireContext().shortToast("다운로드를 위해 권환이 필요합니다.")
//                    ActivityCompat.requestPermissions(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 1004);
//                }
//                else {
//                    Toast.makeText(getBaseContext(), "다운로드를 위해\n권한이 필요합니다.", Toast.LENGTH_LONG).show();
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1004);
//                }
//            }
    }
}