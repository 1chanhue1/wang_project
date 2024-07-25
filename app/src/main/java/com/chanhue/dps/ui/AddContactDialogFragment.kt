package com.chanhue.dps.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import com.chanhue.dps.DialogStateManager
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentAddContactDialogBinding
import com.chanhue.dps.ui.dialog.AgeNumPickerDialog
import com.google.android.material.chip.Chip

class AddContactDialogFragment : DialogFragment(), AgeSelectListener, PersonalityListener {

    private var _binding: FragmentAddContactDialogBinding? = null
    private val binding get() = _binding!!

    private val personalityList = mutableListOf("활발", "온순", "고집이 쎔")
    private var petProfileImageUri = ""
    private val pickMedia =
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

    private var isValidPetProfileImage = false
    private var isValidOwnerName = false
    private var isValidOwnerGender = false
    private var isValidPhoneNumber = false
    private var isValidOwnerAge = false
    private var isValidRegion = false

    private var isValidPetName = false
    private var isValidPetGender = false
    private var isValidPetSpecies = false
    private var isValidPetAge = false
    private var isValidPersonality = false

    private var isValidMemo = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactDialogBinding.inflate(inflater, container, false)
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
        initPhoneNumberEditText()
        initAddPersonalityImageView()
        setPersonalityChips()
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

    private fun initPhoneNumberEditText() {
        binding.etInputPhoneNumber.apply {
            filters = arrayOf(InputFilter.LengthFilter(13)) // 전화번호 입력 시 자동으로 - 추가
            addTextChangedListener(PhoneNumberFormattingTextWatcher()) // 전화번호 입력 시 자동으로 - 추가
        }
    }

    private fun initAddPersonalityImageView() {
        binding.ivAddPersonality.setOnClickListener {
            showPersonalityBottomSheetDialog()
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
        val personalityBottomSheetFragment = PersonalityBottomSheetFragment(personalityList, this)
        personalityBottomSheetFragment.show(parentFragmentManager, personalityBottomSheetFragment.tag)
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
                DialogStateManager.setIsShowing(false)
                dismissWithAnimation()
            }
        })
    }

    private fun dismissWithAnimation() {
        parentFragmentManager.commit {
            setCustomAnimations(0, R.anim.dialog_slide_down)
            remove(this@AddContactDialogFragment)
        }
        DialogStateManager.setIsShowing(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
