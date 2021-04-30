package com.debanshu777.newsapp.ui.fragments

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.ui.NewsActivity
import com.debanshu777.newsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*


class ArticleFragment:Fragment(R.layout.fragment_article){

    private fun getClient(): WebViewClient {
        return object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }


            @RequiresApi(Build.VERSION_CODES.R)
            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                if(url == null){
                    return null
                }
                return if(url.toLowerCase(Locale.ROOT).contains(".jpg") || url.toLowerCase(Locale.ROOT).contains(
                        ".jpeg"
                    )){
                    val bitmap = Glide.with(webView).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(
                        url
                    ).submit().get()
                    WebResourceResponse(
                        "image/jpg", "UTF-8", getBitmapInputStream(
                            bitmap,
                            Bitmap.CompressFormat.JPEG
                        )
                    )
                }else if(url.toLowerCase(Locale.ROOT).contains(".png")){
                    val bitmap = Glide.with(webView).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(
                        url
                    ).submit().get()
                    WebResourceResponse(
                        "image/png", "UTF-8", getBitmapInputStream(
                            bitmap,
                            Bitmap.CompressFormat.PNG
                        )
                    )
                }else if(url.toLowerCase(Locale.ROOT).contains(".webp")){
                    val bitmap = Glide.with(webView).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).load(
                        url
                    ).submit().get()
                    WebResourceResponse(
                        "image/webp", "UTF-8", getBitmapInputStream(
                            bitmap,
                            Bitmap.CompressFormat.WEBP_LOSSY
                        )
                    )
                }else{
                    return null
                }

            }
        }
    }

    private fun getBitmapInputStream(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat): InputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, 80, byteArrayOutputStream)
        val bitmapData: ByteArray = byteArrayOutputStream.toByteArray()
        return ByteArrayInputStream(bitmapData)
    }

    lateinit var viewModel: NewsViewModel
    private val args:ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        val article=args.article
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(
                webView.settings,
                WebSettingsCompat.FORCE_DARK_ON
            )
        }


        webView.apply {
            webViewClient= getClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view, "News Saved", Snackbar.LENGTH_LONG).show()
        }
    }
}