package com.example.prmanager.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.prmanager.databinding.ActivityMainBinding
import com.example.prmanager.router.ApiResponseInfo
import com.example.prmanager.ui.adapter.PRListAdapter
import com.example.prmanager.utils.Constants.Companion.GITHUB_REPO
import com.example.prmanager.utils.Constants.Companion.GITHUB_USER
import com.example.prmanager.utils.Constants.Companion.PR_STATE
import com.example.prmanager.viewmodel.PRViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<PRViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val prAdapter = PRListAdapter(this)
        val recyclerView: RecyclerView = binding.prRecyclerView
        recyclerView.adapter = prAdapter

        getPrs(PR_STATE.CLOSED)
    }

    private fun getPrs(state: PR_STATE) {

        viewModel.getPrs(GITHUB_USER, GITHUB_REPO, state)

        viewModel.prResponse.observe(this) { response ->

            when (response) {
                is ApiResponseInfo.Loading -> {
                    Log.i("Stuff", "Loading")
                }

                is ApiResponseInfo.Success -> {
                    val adapter = binding.prRecyclerView.adapter as PRListAdapter
                    adapter.submitList(response.data!!)
                }

                is ApiResponseInfo.Error -> {
                    Log.i("Stuff", "error" + response.message)
                }
            }
        }
    }
}