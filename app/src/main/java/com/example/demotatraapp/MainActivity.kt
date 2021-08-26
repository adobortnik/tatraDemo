package com.example.demotatraapp

import EndlessRecyclerViewScrollListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.demotatraapp.data.User
import com.example.demotatraapp.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {
    private var mainViewModel: MainViewModel? = null
    private lateinit var adapter: RecyclerAdapter
    var users: MutableList<User> = arrayListOf()
    var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = RecyclerAdapter(users)
        recyclerView.adapter = adapter
        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        mainViewModel!!.getUsers().observe(this, Observer<List<User>> { users ->
            this.users.addAll(users)
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
            swipeRefresh.isRefreshing = false
        })

        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                currentPage += 1
                adapter.notifyDataSetChanged()
                mainViewModel!!.loadMore(currentPage, 5)

            }
        }
        recyclerView.addOnScrollListener(scrollListener)

        adapter.onItemClick = { user ->
            val intent = Intent(this@MainActivity,ProfileActivity::class.java)
            intent.putExtra("id",user.id)
            startActivity(intent)

        }

        swipeRefresh.setOnRefreshListener {
            currentPage = 1
            users.clear()
            adapter.notifyDataSetChanged()
            scrollListener.resetState()
            mainViewModel!!.loadMore(1, 5)
        }
    }
}