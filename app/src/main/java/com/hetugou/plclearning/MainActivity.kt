package com.hetugou.plclearning

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * 应用唯一的 Activity：承载内置 assets 中的教学页面。
 *
 * 安全与离线约束（对照 AGENTS.md）：
 * - 只允许加载 file:///android_asset/ 下的本地资源，外部网页一律拦断；
 * - 关闭文件系统与 content:// 访问，仅保留 assets 资源；
 * - 禁止混合内容、禁止网络加载，即使将来误加 INTERNET 权限也不会联网。
 */
class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        webView = WebView(this).apply {
            setBackgroundColor(Color.parseColor(BACKGROUND_COLOR))
            configureSettings(settings)
            webViewClient = LocalOnlyWebViewClient()
        }

        // 内容绘制在系统栏下方，因此按系统栏与刘海区域补齐内边距
        ViewCompat.setOnApplyWindowInsetsListener(webView) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom
            )
            insets
        }

        setContentView(webView)

        if (savedInstanceState == null) {
            webView.loadUrl(ASSET_INDEX)
        } else {
            webView.restoreState(savedInstanceState)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun configureSettings(settings: WebSettings) = with(settings) {
        javaScriptEnabled = true
        domStorageEnabled = true

        // 禁止脚本自行弹窗/开新窗口，所有跳转都必须经过 WebViewClient 校验
        setSupportMultipleWindows(false)
        javaScriptCanOpenWindowsAutomatically = false

        // 不开放不必要的文件访问能力
        allowFileAccess = false
        allowContentAccess = false
        @Suppress("DEPRECATION")
        allowFileAccessFromFileURLs = false
        @Suppress("DEPRECATION")
        allowUniversalAccessFromFileURLs = false

        // 禁止混合内容与任何网络加载
        mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        blockNetworkLoads = true
        blockNetworkImage = true

        // 视口适配
        useWideViewPort = true
        loadWithOverviewMode = true
        builtInZoomControls = false
        displayZoomControls = false
    }

    private inner class LocalOnlyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean = !isLocalAssetUrl(request.url)
    }

    /**
     * 仅放行 file:///android_asset/ 下的资源。
     * 任何 http/https/javascript/data 等 scheme 一律视为外部跳转并拦截。
     */
    private fun isLocalAssetUrl(uri: Uri): Boolean =
        uri.scheme == "file" && uri.path?.startsWith(ASSET_PATH_PREFIX) == true

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onDestroy() {
        (webView.parent as? android.view.ViewGroup)?.removeView(webView)
        webView.destroy()
        super.onDestroy()
    }

    private companion object {
        const val ASSET_BASE = "file:///android_asset/"
        const val ASSET_PATH_PREFIX = "/android_asset/"
        const val ASSET_INDEX = "${ASSET_BASE}index.html"
        const val BACKGROUND_COLOR = "#0F172A"
    }
}
