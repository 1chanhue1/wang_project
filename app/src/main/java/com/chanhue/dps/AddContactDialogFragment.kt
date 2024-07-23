package com.chanhue.dps

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import com.chanhue.dps.databinding.DialogAddContactBinding

class AddContactDialogFragment : DialogFragment() {

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setWindowAnimations(R.style.AppDialogAnimation) // 다이얼로그 애니메이션 설정
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvAddContact.setOnClickListener {
            // Do something
            dismissWithAnimation()
        }
    }

    private fun dismissWithAnimation() {
        parentFragmentManager.commit {
            setCustomAnimations(0, R.anim.dialog_slide_down)
            remove(this@AddContactDialogFragment)
        }
        DiaglogStateManager.setIsShowing(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
