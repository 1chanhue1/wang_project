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
import com.chanhue.dps.ui.dialog.AgeNumPickerDialog

class AddContactDialogFragment : DialogFragment(), AgeSelectListener {

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressedHandler()
        initPetAgeEditText()
    }

    private fun initPetAgeEditText() {
        binding.etInputPetAge.setOnClickListener {
            showAgeNumPickerDialog()
        }
    }

    private fun showAgeNumPickerDialog() {
        val ageNumPickerDialog = AgeNumPickerDialog(requireContext(), this)
        ageNumPickerDialog.show()
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

    override fun onAgeSelected(age: Int) {
        binding.etInputPetAge.setText(age.toString())
    }
}
