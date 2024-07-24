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
import com.google.android.material.chip.Chip

class AddContactDialogFragment : DialogFragment(), AgeSelectListener, PersonalityListener {

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    private val personalityList = mutableListOf("활발", "온순", "고집이 쎔")

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
        setPersonalityChips()
        initAddPersonalityImageView()
    }

    private fun initPetAgeEditText() {
        binding.etInputPetAge.setOnClickListener {
            showAgeNumPickerDialog()
        }
    }

    private fun setPersonalityChips() {
        for (personality in personalityList) {
            val chip = createNewChip(personality)
            val position = binding.chipGroupDialogPersonality.childCount - 1
            binding.chipGroupDialogPersonality.addView(chip, position)
        }
    }

    private fun initAddPersonalityImageView() {
        binding.ivAddCategory.setOnClickListener {
            val personalityBottomSheet = PersonalityBottomSheet(personalityList, this)
            personalityBottomSheet.show(parentFragmentManager, tag)
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

    override fun onPersonalityUpdated(personality: String) {
        val chip = createNewChip(personality)
        if (binding.chipGroupDialogPersonality.childCount > 0) {
            val position = binding.chipGroupDialogPersonality.childCount - 1
            binding.chipGroupDialogPersonality.addView(chip, position)
        } else {
            binding.chipGroupDialogPersonality.addView(chip)
        }
        personalityList.add(personality)
    }

    override fun onPersonalityDeleted(personality: String) {
        deleteChip(personality)
    }

    private fun createNewChip(text: String): Chip {
        val chip = layoutInflater.inflate(R.layout.item_chip, binding.chipGroupDialogPersonality, false) as Chip
        chip.text = text
        return chip
    }

    private fun deleteChip(text: String) {
        for (i in 0 until binding.chipGroupDialogPersonality.childCount) {
            val childView = binding.chipGroupDialogPersonality.getChildAt(i)
            if (childView is Chip) {
                val chip = childView as Chip
                if (chip.text == text) {
                    personalityList.remove(text)
                    binding.chipGroupDialogPersonality.removeView(chip)
                    break
                }
            }
        }
    }
}
