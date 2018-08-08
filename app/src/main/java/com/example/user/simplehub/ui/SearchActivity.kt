package com.example.user.simplehub.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SearchView
import android.widget.Toast
import com.example.user.simplehub.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView.queryHint="Search View"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }

        })

    }
}