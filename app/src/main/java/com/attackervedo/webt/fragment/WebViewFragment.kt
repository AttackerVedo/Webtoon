package com.attackervedo.webt.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.attackervedo.webt.WebtoonWebViewClient
import com.attackervedo.webt.databinding.FragmentWebviewBinding

class WebViewFragment(private val position: Int) : Fragment() {

    var listener : OnTabLayoutNameChanged? = null

    private lateinit var binding: FragmentWebviewBinding

    companion object {
         const val SHARD_PREFERENCE = "WEB_HISTORY"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(layoutInflater)
        return binding.root
    }

    // 뷰가 만들어진 이후에 호출되는 함수(생명주기)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //웹뷰 설정
        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar) { url ->
            activity?.getSharedPreferences(SHARD_PREFERENCE, Context.MODE_PRIVATE)?.edit()?.apply {
                putString("tab$position", url)
                apply()
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://comic.naver.com")
        //웹뷰 설정

        binding.backToLastBtn.setOnClickListener {
            val shardPreference =
                activity?.getSharedPreferences(SHARD_PREFERENCE, Context.MODE_PRIVATE)
            val url = shardPreference?.getString("tab${position}", "")
            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장시점이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }

        binding.changeTabNameBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val editText = EditText(context)
            dialog.setView(editText)
            dialog.setPositiveButton("저장") { _, _ ->
                activity?.getSharedPreferences(SHARD_PREFERENCE, Context.MODE_PRIVATE)?.edit()?.apply {
                    putString("tab${position}_name", editText.text.toString())
                    listener?.nameChanged(position, editText.text.toString())
                    apply()
                }
            }
            dialog.setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialog.show()

        }
    }

    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    fun goBack() {
        binding.webView.goBack()
    }
}

interface OnTabLayoutNameChanged{
    fun nameChanged(position: Int,name:String)
}


