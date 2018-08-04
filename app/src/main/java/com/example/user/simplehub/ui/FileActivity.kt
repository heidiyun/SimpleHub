package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.user.simplehub.R
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.app_bar_repo.*
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class FileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        toast("file Activity")


        bar_repo_text.text = DirActivity.dirName[DirActivity.dirName.size - 1]

        val repoApi = provideUserApi(this)
        val call = repoApi.getDirContents(RepoActivity.ownerName,
                RepoActivity.repoName, DirActivity.dirName.joinToString(separator = "/"))
        Log.i(FileActivity::class.java.simpleName,
                "이름 : ${DirActivity
                        .dirName.joinToString(separator = "/")}")

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                bar_repo_text.text = it.name
                downloadFiles(it.url)
            }
        }, {

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        DirActivity.dirName.removeAt(DirActivity.dirName.size - 1)
    }

    private fun downloadFiles(url: String) {
        var fullString = ""
        val url = URL(url)

        val reader = BufferedReader(InputStreamReader(url.openStream()))
        var line: String
        while(true) {
            line = reader.readLine()
            if (line == null) break;
            fullString += line
        }

        reader.close()

        Log.i(FileActivity::class.java.simpleName, "text : ${fullString}")
    }
}
