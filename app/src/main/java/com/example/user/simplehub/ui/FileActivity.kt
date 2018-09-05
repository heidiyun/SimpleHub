package com.example.user.simplehub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import br.tiagohm.markdownview.css.styles.Github
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_file.*
import kotlinx.android.synthetic.main.app_bar_repo.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class FileActivity : AppCompatActivity() {

    var dirName = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val bundle = intent.extras
        val repoName = bundle.getString("repoName")
        val ownerName = bundle.getString("ownerName")
        dirName = bundle.getStringArrayList("dirName")

        file.movementMethod = ScrollingMovementMethod.getInstance()

        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        bar_repo_text.text = dirName[dirName.size - 1]
        val fileName = dirName.last()
        val fileNameList = fileName.split(".")

        val repoApi = provideUserApi(this)
        val call = repoApi.getDirContents(ownerName,
                repoName, dirName.joinToString(separator = "/"))

        Log.i(FileActivity::class.java.simpleName,
                "이름 : ${dirName.joinToString(separator = "/")}")

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                bar_repo_text.text = it.name
                Log.i("FileActivity", "url : ${it.url}")


                Thread {
                    var fullString = ""
                    val url = URL(it.url)
                    val reader = BufferedReader(InputStreamReader(url.openStream()))

                    var line: String?
                    while (true) {
                        line = reader.readLine()
                        if (line == null) break
                        fullString += line
                        fullString += "\n"
                    }

                    reader.close()

                    runOnUiThread {
                        if (fileNameList.last() == "md") {
                            markdownView.visibility = View.VISIBLE
                            markdownView.addStyleSheet(Github())
                            markdownView.loadMarkdown(fullString)
                        } else if(fileNameList.last() == "png" ||
                                fileNameList.last() == "jpg" ||
                                fileNameList.last() == "gif") {
                            webView.visibility = View.VISIBLE
                            webView.loadUrl(it.url)
                        } else{
                            file.text = fullString
                            fileProgressBar.visibility = View.GONE
                        }
                    }
                }.start()
            }
        }, {

        })
    }

    override fun onBackPressed() {
        dirName.removeAt(dirName.size - 1)
        val intent = intent
        intent.putStringArrayListExtra("dirName", dirName)
        setResult(RESULT_OK, intent)
        finish()
    }


}
