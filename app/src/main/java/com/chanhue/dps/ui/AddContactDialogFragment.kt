package com.chanhue.dps.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import com.chanhue.dps.DiaglogStateManager
import com.chanhue.dps.R
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
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setWindowAnimations(R.style.AppDialogAnimation) // 다이얼로그 애니메이션 설정
        // R.style.AppFullScreenDialog 적용하기
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        //dialog.window?.attributes?.windowAnimations = R.style.AppFullScreenDialog
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressedHandler()
        // AgeNumPickerDialog를 띄운다.
        binding.etInputPetAge.setOnClickListener {
            val ageNumPickerDialog = AgeNumPickerDialog(requireContext())
            ageNumPickerDialog.show()
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

    private fun setOnBackPressedHandler() {
        // 뒤로가기 버튼을 눌렀을 때 다이얼로그를 닫는다.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                DiaglogStateManager.setIsShowing(false)
                dismissWithAnimation()
            }
        })
    }
}
