package com.github.presentation.search_repository.view

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.R
import com.github.databinding.ActivityListUserRepositoryBinding
import com.github.databinding.ItemRepositoryBinding
import com.github.presentation.base.BaseActivity
import com.github.presentation.base.BaseAdapter
import com.github.presentation.search_repository.UserRepositoryObservable
import com.github.presentation.search_repository.viewmodel.ListUserRepositoryViewModel
import org.koin.android.ext.android.inject


class ListUserRepositoryActivity :
    BaseActivity<ActivityListUserRepositoryBinding>(R.layout.activity_list_user_repository) {

    val viewModel by inject<ListUserRepositoryViewModel>()

    override fun init() {
        viewModel.adapter = BaseAdapter(viewModel.baseModels){ genericVH, baseModel ->
            when(genericVH.binding){
                is ItemRepositoryBinding -> genericVH.binding.item =
                    (baseModel as UserRepositoryObservable).model
            }

        }
        binding.viewmodel = viewModel
        binding.recyclerView.adapter = viewModel.adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this@ListUserRepositoryActivity,  DividerItemDecoration.VERTICAL))
    }
}