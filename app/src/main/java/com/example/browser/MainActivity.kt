package com.example.browser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {


    private val refreshLayout: SwipeRefreshLayout by lazy{
        findViewById(R.id.refreshLayout)
    }

    private val homeBtn: ImageButton by lazy {
        findViewById(R.id.homeBtn)
    }

    private val backBtn: ImageButton by lazy {
        findViewById(R.id.backBtn)
    }

    private val forwardBtn: ImageButton by lazy {
        findViewById(R.id.forwardBtn)
    }

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val address: EditText by lazy {
        findViewById(R.id.address)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()

    }

    /*** APP내에서  back 지정 ***/
    override fun onBackPressed() {
        if (webView.canGoBack()) { //뒤로 갈수있는 페이지가 있을경우
            webView.goBack()
        } else {
            super.onBackPressed()  //종료
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        webView.apply {
            webViewClient = WebViewClient()  /* 해당 앱으로 브라우저를 여는 코드 */
            settings.javaScriptEnabled = true /* 스크립트 사용 */
            loadUrl(defaultUrl)
        }
    }

    private fun bindViews() {
        address.setOnEditorActionListener { v, actionID, _ ->
            if (actionID == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
                Log.d("url", v.text.toString())
            }
            return@setOnEditorActionListener false
        }
        /* 버튼을 할당하는 부분 */
        backBtn.setOnClickListener {
            webView.goBack()
            Log.d("bindViews","backBtn")
        }
        forwardBtn.setOnClickListener {
            webView.goForward()
            Log.d("bindViews","forwardBtn")
        }
        homeBtn.setOnClickListener {
            webView.loadUrl(defaultUrl)
            Log.d("bindViews","homeBtn")
        }

        /* Swift Refreshd action 부분 */
        refreshLayout.setOnRefreshListener {
            webView.reload()

        }
    }

    /* 상위 클래스 접근 */
    inner class WebViewClient: android.webkit.WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)  //페이지 로딩 완료

            refreshLayout.isRefreshing = false   // refresh icon remove
        }
    }


    companion object {
        private const val defaultUrl= "https://www.google.com"
    }
}