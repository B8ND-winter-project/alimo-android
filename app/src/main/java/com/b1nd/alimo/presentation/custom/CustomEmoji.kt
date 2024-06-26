package com.b1nd.alimo.presentation.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.CustomEmojiBinding

class CustomEmoji(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    private val binding: CustomEmojiBinding by lazy {
        CustomEmojiBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_emoji, this, false)
        )
    }

    private var _emojiName: String = ""
    val emojiName : String
        get() = _emojiName

    val count
        get() = getCounts()

    init {
        initView()
        initAttrs(attrs)
    }

    private fun initView() {
        addView(binding.root)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_emoji)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(
        typedArray: TypedArray
    ) {
        binding.run {
            textCount.text = typedArray.getString(R.styleable.custom_emoji_count)?: "0"
            imageEmoji.setImageResource(typedArray.getResourceId(R.styleable.custom_emoji_image, 0))
            _emojiName = typedArray.getString(R.styleable.custom_emoji_emojiName)?: ""
            setCountVisible(typedArray.getBoolean(R.styleable.custom_emoji_countVisible, true))
        }
        typedArray.recycle()
    }

    fun getCounts(

    ) = binding.textCount.text.toString()

    fun setCount(
        count: String
    ) {
        binding.textCount.text = count
    }

    fun setEmoji(
        @DrawableRes image: Int
    ) {
        binding.imageEmoji.setImageResource(image)
    }

    fun setCountVisible(
        visible: Boolean
    ) {
        binding.textCount.visibility = if (visible) View.VISIBLE else View.GONE
    }
}