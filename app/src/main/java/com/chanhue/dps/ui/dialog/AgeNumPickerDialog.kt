package com.chanhue.dps.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.chanhue.dps.databinding.DialogAgeNumPickerBinding
import com.chanhue.dps.ui.AgeSelectListener

class AgeNumPickerDialog(
    context: Context,
    private val listener: AgeSelectListener
) : Dialog(context) {
    private val binding: DialogAgeNumPickerBinding by lazy {
        DialogAgeNumPickerBinding.inflate(layoutInflater)
    }
    private val age: Int
        get() = binding.numberPickerAge.value

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAlarmTimePicker()

        binding.btnNumberPickerConfirm.setOnClickListener {
            listener.onAgeSelected(age)
            dismiss()
        }

        binding.btnNumberPickerClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setAlarmTimePicker() {
        with(binding.numberPickerAge) {
            minValue = 0
            maxValue = 30
            value = 20
            wrapSelectorWheel = false
        }
    }
}