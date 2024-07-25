package com.chanhue.dps.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
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
    private var petProfileImageUri = ""
    val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.also { imageUri ->
                binding.ivDialogPetProfile.apply {
                    setPadding(0)
                    setImageURI(imageUri)
                }
                petProfileImageUri = imageUri.toString()
                requireActivity().contentResolver.takePersistableUriPermission(
                    imageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }

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
        setLayout()
    }

    override fun onAgeSelected(age: Int, isOwner: Boolean) {
        with(binding) {
            if (isOwner) {
                etInputOwnerAge.setText(age.toString())
            } else {
                etInputPetAge.setText(age.toString())
            }
        }
    }

    override fun onPersonalityUpdated(personality: String) {
        val chip = createNewChip(personality)
        with(binding.chipGroupDialogPersonality) {
            if (childCount > 0) {
                val position = childCount - 1
                addView(chip, position)
            } else {
                addView(chip)
            }
        }
        personalityList.add(personality)
    }

    override fun onPersonalityDeleted(personality: String) {
        deleteChip(personality)
    }

    private fun setLayout() {
        initOwnerAgeEditText()
        initPetAgeEditText()
        setPersonalityChips()
        initAddPersonalityImageView()
        setPetProfileImage()
    }

    private fun initOwnerAgeEditText() {
        with(binding.etInputOwnerAge) {
            setOnClickListener {
                showAgeNumPickerDialog(text.toString().toIntOrNull(), true)
            }
        }
    }

    private fun initPetAgeEditText() {
        with(binding.etInputPetAge) {
            setOnClickListener {
                showAgeNumPickerDialog(text.toString().toIntOrNull(), false)
            }
        }
    }

    private fun setPersonalityChips() {
        for (personality in personalityList) {
            val chip = createNewChip(personality)
            with(binding.chipGroupDialogPersonality) {
                val position = childCount - 1
                addView(chip, position)
            }
        }
    }

    private fun initAddPersonalityImageView() {
        binding.ivAddCategory.setOnClickListener {
            showPersonalityBottomSheetDialog()
        }
    }

    private fun setPetProfileImage() {
        binding.ivDialogPetProfile.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun showAgeNumPickerDialog(num : Int?, isOwner: Boolean) {
        val ageNumPickerDialog = AgeNumPickerDialog(requireContext(), isOwner, num, this)
        ageNumPickerDialog.show()
    }

    private fun showPersonalityBottomSheetDialog() {
        val personalityBottomSheet = PersonalityBottomSheet(personalityList, this)
        personalityBottomSheet.show(parentFragmentManager, tag)
    }

    private fun openGalleryForImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun createNewChip(text: String): Chip {
        val chip = layoutInflater.inflate(R.layout.item_chip, binding.chipGroupDialogPersonality, false) as Chip
        chip.text = text
        return chip
    }

    private fun deleteChip(text: String) {
        with(binding.chipGroupDialogPersonality) {
            for (i in 0 until childCount) {
                val childView = getChildAt(i)
                if (childView is Chip) {
                    val chip = childView as Chip
                    if (chip.text == text) {
                        personalityList.remove(text)
                        removeView(chip)
                        break
                    }
                }
            }
        }
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
