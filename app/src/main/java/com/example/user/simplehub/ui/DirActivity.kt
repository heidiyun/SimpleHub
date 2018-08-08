package com.example.user.simplehub.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.user.simplehub.R
import com.example.user.simplehub.api.model.GithubRepoContents
import com.example.user.simplehub.api.provideUserApi
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_dir.*
import kotlinx.android.synthetic.main.app_bar_dir.*
import kotlinx.android.synthetic.main.item_repo_contents.view.*


class DirActivity : AppCompatActivity() {


    class DirContentsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo_contents, parent, false)
    )

    inner class DirListAdapter : RecyclerView.Adapter<DirContentsViewHolder>() {
        var items: List<GithubRepoContents> = emptyList()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirContentsViewHolder {
            return DirContentsViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: DirContentsViewHolder, position: Int) {
            val item = items[position]

            with(holder.itemView) {
                content_repo_name.text = item.name


                if (item.type == "file") {
                    Glide.with(this).load(R.drawable.ic_book).into(content_repo_image)
                    card_repo.setOnClickListener {
                        Log.i(DirActivity::class.java.simpleName, "click!!!")
                        startFileAct(item.name)
                    }
                }

                if (item.type == "dir") {
                    Glide.with(this).load(R.drawable.ic_baseline_folder_24px).into(content_repo_image)
                    card_repo.setOnClickListener {
                        Log.i(DirActivity::class.java.simpleName, "click dir!!!")

                        startAct(item.name)
                    }
                }


            }
        }


    }

//    companion object {
//        var dirName: MutableList<String> = mutableListOf()
//    }

    lateinit var listAdapter: DirListAdapter
    var repoName = ""
    var ownerName = ""
    var dirName = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dir)

        val bundle = intent.extras
        repoName = bundle.getString("repoName")
        ownerName = bundle.getString("ownerName")
        dirName = bundle.getStringArrayList("dirName")

        listAdapter = DirListAdapter()

        dirContentsView.adapter = listAdapter
        dirContentsView.layoutManager = LinearLayoutManager(this)

        val repoApi = provideUserApi(this)

//        dirName?.let {
        bar_dir_text.text = dirName[dirName.size - 1]

        val call = repoApi.getRepoContents(ownerName, repoName, dirName.joinToString(separator = "/"))

        call.enqueue({ response ->
            val result = response.body()
            result?.let {
                listAdapter.items = it
                listAdapter.notifyDataSetChanged()
//                for(i in 0..result.size-1)
//                    Log.i(RepoActivity::class.java.simpleName, "repo name: ${it[i].name}")
            }
        }, {

        })
//        }
    }


    override fun onBackPressed() {
        dirName.removeAt(dirName.size - 1)
        val intent = getIntent()
        intent.putStringArrayListExtra("dirName", dirName)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun startAct(dirName: String) {
        this.dirName.add(dirName)
        val intent = Intent(this, DirActivity::class.java)
        intent.putStringArrayListExtra("dirName", this.dirName)
        intent.putExtra("ownerName", ownerName)
        intent.putExtra("repoName", repoName)
        startActivityForResult(intent, 123)
//        startActivity(intent)
//        this.dirName.removeAt(this.dirName.size - 1)
    }

    fun startFileAct(fileName: String) {
//            this.dirName.add(fileName)
        this.dirName.add(fileName)
        val intent = Intent(this, FileActivity::class.java)
        intent.putStringArrayListExtra("dirName", this.dirName)
        intent.putExtra("ownerName", ownerName)
        intent.putExtra("repoName", repoName)
//        startActivity(intent)
        startActivityForResult(intent, 123)
//        this.dirName.removeAt(this.dirName.size - 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("dirActivity", "dirName22: $requestCode, $resultCode, $data")


        if (requestCode == 123 || resultCode == RESULT_OK) {
            Log.i("dirActivity", "dirName33")
            data?.let {
                this.dirName = it.getStringArrayListExtra("dirName")
                for(i in 0..dirName.size-1) {
                    Log.i("dirActivity", "dirName: ${dirName[i]}")
                }
            }
        }
    }
}

