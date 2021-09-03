package com.github.presentation.search_repository.view

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.R
import com.github.databinding.ActivityListUserRepositoryBinding
import com.github.databinding.ItemRepositoryBinding
import com.github.domain.utils.Result
import com.github.presentation.base.BaseActivity
import com.github.presentation.base.BaseAdapter
import com.github.presentation.search_repository.UserRepositoryObservable
import com.github.presentation.search_repository.viewmodel.ListUserRepositoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ListUserRepositoryActivity :
    BaseActivity<ActivityListUserRepositoryBinding>(R.layout.activity_list_user_repository) {

    val viewModel by inject<ListUserRepositoryViewModel>()

    override fun init() {
        viewModel.adapter = BaseAdapter(viewModel.baseModels) { genericVH, baseModel ->
            when (genericVH.binding) {
                is ItemRepositoryBinding -> genericVH.binding.item =
                    (baseModel as UserRepositoryObservable).model
            }
        }
        binding.viewmodel = viewModel
        binding.recyclerView.adapter = viewModel.adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@ListUserRepositoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        observeRepositories()
    }

    // Note: Since we're replacing all repositories with new user repositories, we don't need to call notifyItemChanged
    @SuppressLint("NotifyDataSetChanged")
    private fun observeRepositories() {
        viewModel.repositories.observe(this, { result ->
            if (result is Result.Success) {
                result.data?.forEach { viewModel.baseModels.add(UserRepositoryObservable(it)) }
            }
            viewModel.adapter.notifyDataSetChanged()
        })
    }
}