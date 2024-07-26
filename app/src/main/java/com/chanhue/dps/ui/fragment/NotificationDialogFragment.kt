package com.chanhue.dps.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.chanhue.dps.databinding.DialogNotificationBinding

class NotificationDialogFragment(
    private val confirmDialogInterface: ConfirmDialogInterface,
    private val text: String,
    private val id: Int
) : DialogFragment() {

    interface FragmentDataListener {
        fun onDataReceived(data: String, delay: Int)
    }

    interface ConfirmDialogInterface {
        fun onSubmitButtonClick(id: Int)
    }

    private var listener: FragmentDataListener? = null
    private var _binding: DialogNotificationBinding? = null
    private val binding get() = _binding!!
    private var delayTime: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentDataListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogNotificationEt.setText(text)

        binding.in5Minute.setOnClickListener {
            delayTime = 5
            disableOtherButtons()
        }

        binding.in10Minute.setOnClickListener {
            delayTime = 10
            disableOtherButtons()
        }

        binding.in1Hour.setOnClickListener {
            delayTime = 15
            disableOtherButtons()
        }

        binding.dialogNotificaionCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogNotificaionSubmitBtn.setOnClickListener {
            val dataToSend = binding.dialogNotificationEt.text.toString()
            delayTime?.let {
                listener?.onDataReceived(dataToSend, it)
                confirmDialogInterface.onSubmitButtonClick(id)
            }
            dismiss()
        }
    }

    private fun disableOtherButtons() {
        binding.in5Minute.isEnabled = false
        binding.in10Minute.isEnabled = false
        binding.in1Hour.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
