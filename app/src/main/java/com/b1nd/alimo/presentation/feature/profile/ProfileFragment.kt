package com.b1nd.alimo.presentation.feature.profile

import android.graphics.Paint
import android.os.Build
import androidx.fragment.app.viewModels
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentProfileBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomCategoryCard
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import com.b1nd.alimo.presentation.feature.profile.ProfileViewModel.Companion.ON_CLICK_LOGOUT
import com.b1nd.alimo.presentation.feature.profile.ProfileViewModel.Companion.ON_CLICK_PRIVATE_POLICY
import com.b1nd.alimo.presentation.feature.profile.ProfileViewModel.Companion.ON_CLICK_SERVICE_POLICY
import com.b1nd.alimo.presentation.feature.profile.ProfileViewModel.Companion.ON_CLICK_STUDENT_CODE
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile), ProfileStudentClickListener {

    override val viewModel: ProfileViewModel by viewModels()
    private val dialog: ProfileStudentCodeDialog by lazy {
        ProfileStudentCodeDialog(this)
    }
    override fun initView() {
        mBinding.cardVersion.setDescriptionText(BuildConfig.VERSION_NAME)
        mBinding.textStudentCode.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        val charset = ('0'..'9') + ('a'..'z') + ('A'..'Z')
        for (i in 1..20) {
            val randomName = List(Random.nextInt(1, 7)) { charset.random() }.joinToString().replace(", ", "")
            val card = CustomCategoryCard(requireContext(), null, randomName)
            card.setPadding(0, 8, 8, 0)
            mBinding.layoutCategory.addView(card)
        }

        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_STUDENT_CODE -> {
                        dialog.show(super.getChildFragmentManager(), "dialog")
                    }
                    ON_CLICK_PRIVATE_POLICY -> {

                    }
                    ON_CLICK_SERVICE_POLICY -> {

                    }
                    ON_CLICK_LOGOUT -> {

                    }
                }
            }
        }

        mBinding.cardAlarm.setSwitchOnClickListener {

        }
    }

    override fun onCopy() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            CustomSnackBar(requireView(), "복사에 성공하였습니다!").show()
        }
    }
}