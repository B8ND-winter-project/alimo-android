package com.b1nd.alimo.feature.detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentDetailBinding
import com.b1nd.alimo.feature.detail.DetailViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.feature.detail.DetailViewModel.Companion.ON_CLICK_SEND
import com.b1nd.alimo.utiles.onSuccessEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DetailFragment: BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        Log.d("TAG", "initView: back")
                    }
                    ON_CLICK_SEND -> {
                        Log.d("TAG", "initView: send")
                        with(mBinding) {
                            val text = editSend.text.toString()
                            if (text.isNullOrBlank()) {
                                return@onSuccessEvent
                            }
                            editSend.text.clear()

                        }
                    }
                }
            }
        }

        val adapter = DetailCommentRv()
        mBinding.rvComment.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            val testData = PagingData.from(listOf(testData(0), testData(1, true), testData(2), ))
            adapter.submitData(testData)
        }
    }

    private fun testData(id: Int, comments: Boolean = false) =
        DetailCommentItem(
            id = id,
            "test",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            LocalDateTime.now(),
            "testcontent\n알빠노\n라고할뻔",
            if (comments) listOf(DetailCommentItem(
                id = id,
                "test",
                "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
                LocalDateTime.now(),
                "testcontent\n알빠노\n라고할뻔",
                null)
            ) else null
        )
}