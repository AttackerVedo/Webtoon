package com.attackervedo.webt

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import java.util.PrimitiveIterator

class WebtoonWebViewClient(
    private val progressBar: ProgressBar,
    private val saveData:(String) -> Unit,
    ): WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        //
        if(request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")){
          saveData(request.url.toString())
        }
        //

        return super.shouldOverrideUrlLoading(view, request)
    }

//    // 리턴 트루 : 페이지로딩안함 리턴 폴스 : 페이지로딩 계속 시도
//    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//        return false
//    }

    //페이지 로드가 끝났을 떄
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
    }
    //페이지 로드가 시작됬을 때
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        progressBar.visibility = View.VISIBLE
    }
    //에러 났을 때
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        // 에러페이지를 띄워주거나 토스트띄워주면되겟지
    }

}