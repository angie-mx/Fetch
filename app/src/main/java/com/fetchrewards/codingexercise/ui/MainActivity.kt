package com.fetchrewards.codingexercise.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fetchrewards.codingexercise.data.Hiring
import com.fetchrewards.codingexercise.data.hiringsDefaultState
import com.fetchrewards.codingexercise.databinding.MainLayoutBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    private lateinit var binding: MainLayoutBinding
    private val viewModelFactory: MainViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map {
                    it.isFetchingData
                }.distinctUntilChanged()
                    .collect {
                        loadContent(viewModel.uiState.value.hirings)
                        showLoadingView(viewModel.uiState.value.isFetchingData)
                        showErrorMessage(viewModel.uiState.value.errorMessageId)
                    }
            }
        }
    }

    private fun loadContent(hirings: Map<String, List<Hiring>>) {
        if (hirings != hiringsDefaultState) {
            val adapter = ExpandableListAdapter(hirings)
            binding.expandableListView.setAdapter(adapter)
            binding.expandableListView.visibility = View.VISIBLE
        } else {
            binding.expandableListView.visibility = View.GONE
        }
    }

    private fun showErrorMessage(id: Int) {
        if (id != 0) {
            binding.errorTextView.text = this.getText(id)
            binding.errorTextView.visibility = View.VISIBLE
        } else {
            binding.errorTextView.visibility = View.GONE
        }
    }

    private fun showLoadingView(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
