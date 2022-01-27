package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.syr.module_custom.R

class CodeInputView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var fieldCount = DEFAULT_FIELD_COUNT
    private var fieldWidth = DEFAULT_FIELD_HEIGHT
    private var fieldHeight = DEFAULT_FIELD_HEIGHT
    private var fieldMargin = DEFAULT_FIELD_MARGIN
    private var fieldShowKeyboard = true //是否使用系统键盘

    @DrawableRes
    private var fieldBackground = -1

    @FieldInputType
    private var inputType = FieldInputType.text

    @IntDef(
        FieldInputType.text,
        FieldInputType.number,
        FieldInputType.password,
        FieldInputType.numPassword
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class FieldInputType {
        companion object {
            const val text = 0x00000001
            const val number = 0x00000002
            const val password = 0x00000003
            const val numPassword = 0x00000004
        }
    }

    var onTextChangeListener: OnTextChangeListener? = null
    private val stringBuilder = StringBuilder()

    // 当前有焦点的edittext
    private var focusIndex = 0

    companion object {
        const val DEFAULT_FIELD_COUNT = 6
        const val DEFAULT_FIELD_HEIGHT = 100
        const val DEFAULT_FIELD_MARGIN = 0
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CodeInputView)
        fieldCount = a.getInt(R.styleable.CodeInputView_field_count, fieldCount)
        fieldMargin = a.getDimensionPixelSize(R.styleable.CodeInputView_field_margin, fieldMargin)
        fieldWidth =
            a.getDimensionPixelSize(R.styleable.CodeInputView_field_width, fieldWidth)
        fieldHeight =
            a.getDimensionPixelSize(R.styleable.CodeInputView_field_height, fieldHeight)

        inputType = a.getInt(R.styleable.CodeInputView_inputType, inputType)
        fieldBackground =
            a.getResourceId(R.styleable.CodeInputView_field_background, fieldBackground)
        fieldShowKeyboard =
            a.getBoolean(R.styleable.CodeInputView_field_showKeyboard, fieldShowKeyboard)
        a.recycle()

        initViews()
    }

    private fun initViews() {
        for (i in 0 until fieldCount) {
            val editText = EditText(context)
            val layoutParams = LinearLayout.LayoutParams(fieldWidth, fieldHeight)
            layoutParams.leftMargin = fieldMargin
            layoutParams.rightMargin = fieldMargin
            layoutParams.gravity = Gravity.CENTER
            if (fieldBackground != -1) {
                editText.setBackgroundResource(fieldBackground)
            }
            if (i == 0) {
                editText.isFocusable = true
                editText.requestFocus()
            }
            editText.showSoftInputOnFocus = fieldShowKeyboard
            if (fieldShowKeyboard) {
                // TODO 自动调起系统键盘

            }
            editText.setTextColor(Color.BLACK)
            editText.layoutParams = layoutParams
            editText.gravity = Gravity.CENTER
            editText.filters = arrayOf<InputFilter>(LengthFilter(1))
            when (inputType) {
                FieldInputType.text -> editText.inputType = InputType.TYPE_CLASS_TEXT
                FieldInputType.number -> editText.inputType = InputType.TYPE_CLASS_NUMBER
                FieldInputType.password -> editText.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                FieldInputType.numPassword -> {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                }
                else -> editText.inputType = InputType.TYPE_CLASS_TEXT
            }
            editText.id = i
            editText.setEms(1)

            editText.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    backFocus(editText, i)
                }
                false
            }
            editText.setOnFocusChangeListener { _, b ->
                if (b) focusIndex = i
            }
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotBlank()) {
                        editText.setSelection(editText.text.length)
                        focus(i)
                        checkCode()
                    }
                }
            })

            addView(editText, i)
        }
    }

    // 上一位 删除
    private fun backFocus(editText: EditText, index: Int) {
        val preEditText = getChildAt(index - 1) as? EditText
        if (index == focusIndex) {
            if (editText.text.isBlank()) {
                preEditText?.setText("")
                preEditText?.requestFocus()
            }
        } else if (index > 0) {
            preEditText?.setText("")
            preEditText?.requestFocus()
        }
    }

    // 下一位
    private fun focus(index: Int) {
        for (i in index + 1 until childCount) {
            val nextEditText = getChildAt(i) as? EditText
            if (nextEditText?.text.isNullOrBlank()) {
                nextEditText?.requestFocus()
                return
            }
            val lastEditText = getChildAt(childCount - 1) as? EditText
            lastEditText?.requestFocus()
        }
    }

    private fun checkCode(): String {
        stringBuilder.clear()
        for (i in 0 until fieldCount) {
            val editText = getChildAt(i) as? EditText
            val content = editText?.text.toString()
            stringBuilder.append(content)
        }
        onTextChangeListener?.onTextChange(
            stringBuilder.toString(),
            stringBuilder.length == fieldCount
        )
        return stringBuilder.toString()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentWidth = measuredWidth
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
        if (count > 0) {
            val child = getChildAt(0)
            val cWidth = child.measuredWidth
            var maxWidth = cWidth * count + fieldMargin * (count - 1)

            if (maxWidth > parentWidth) {
                // 重新计算padding
                fieldMargin = (parentWidth - cWidth * count) / (count - 1)
                maxWidth = cWidth * count + fieldMargin * (count - 1)
            }

            setMeasuredDimension(
                resolveSize(maxWidth + paddingStart + paddingEnd, widthMeasureSpec),
                resolveSize(child.measuredHeight + paddingTop + paddingBottom, heightMeasureSpec)
            )
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.visibility = VISIBLE
            val cWidth = child.measuredWidth
            val cHeight = child.measuredHeight
            val cl = paddingStart + i * (cWidth + fieldMargin)
            val cr = cl + cWidth
            val ct = paddingTop
            val cb = ct + cHeight
            child.layout(cl, ct, cr, cb)
        }
    }

    // 添加当前focus位
    fun setText(text: String) {
        val editText = getChildAt(focusIndex) as? EditText
        if (editText?.text.isNullOrBlank()) { // 防止最后一位一直修改
            editText?.setText(text)
        }
    }

    // 删除当前focus位
    fun deleteText() {
        val editText = getChildAt(focusIndex)
        if (editText is EditText) {
            if (editText.id == focusIndex && editText.text.isBlank()) {
                backFocus(editText, focusIndex)
            } else if (editText.id > 0) {
                val keyEvent = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)
                editText.onKeyDown(keyEvent.keyCode, keyEvent)
            }
        }
    }

    // 返回code
    fun getText(): String {
        return checkCode()
    }

    // 清空
    fun clear() {
        for (i in 0 until childCount) {
            val child = getChildAt(i) as? EditText
            child?.setText("")
            if (i == 0) {
                child?.isFocusable = true
                child?.requestFocus()
            }
        }
    }

    interface OnTextChangeListener {
        fun onTextChange(verifyCode: String, isComplete: Boolean)
    }
}