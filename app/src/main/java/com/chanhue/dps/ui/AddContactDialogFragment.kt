package com.chanhue.dps.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import com.chanhue.dps.Constants
import com.chanhue.dps.DialogStateManager
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentAddContactDialogBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.model.Owner
import com.chanhue.dps.model.PetProfile
import com.chanhue.dps.ui.dialog.AgeNumPickerDialog
import com.chanhue.dps.ui.extensions.isValidInput
import com.chanhue.dps.ui.extensions.isValidMemo
import com.chanhue.dps.ui.extensions.isValidPersonality
import com.chanhue.dps.ui.extensions.isValidPhoneNumber
import com.google.android.material.chip.Chip

class AddContactDialogFragment(

) : DialogFragment(), AgeSelectListener, PersonalityListener {

    private var listener: ContactUpdateListener? = null
    private var contact: Contact? = null



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
//    private var isValidOwnerGender = false
    private var isValidPhoneNumber = false
    private var isValidOwnerAge = false
    private var isValidRegion = false

    private var isValidPetName = false
//    private var isValidPetGender = false 라디오 버튼이라 항상 true
    private var isValidPetSpecies = false
    private var isValidPetAge = false
    private var isValidPersonality = false

    private var isValidMemo = false

    private var ownerGender = false
    private var petGender = false

    companion object {
        private const val ARG_CONTACT = "contact"

        fun newInstance(contact: Contact, listener: ContactUpdateListener): AddContactDialogFragment {
            return AddContactDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONTACT, contact)
                    Log.d("AddContactDialog", "contact: $contact")
                }
                this.listener = listener
            }
        }
    }

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
        setTextInput()
        initAddContactButton()
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
            filters = arrayOf(InputFilter.LengthFilter(Constants.PHONE_NUMBER_FORMAT_LENGTH)) // 전화번호 입력 시 자동으로 - 추가
            addTextChangedListener(PhoneNumberFormattingTextWatcher()) // 전화번호 입력 시 자동으로 - 추가
        }
    }

    private fun initAddPersonalityImageView() {
        binding.ivAddPersonality.setOnClickListener {
            showPersonalityBottomSheetDialog()
        }
    }

    private fun initAddContactButton() {
        binding.toolbarDialogAddContact.tvToolbarAction.setOnClickListener {
            validateInputs()
            if (isAllInputValid()) {
                createContact().let {
                    Log.d("AddContactDialog", "contact: $it")
                    listener?.onContactUpdated(it)
                    dismiss()
                }
            } else {
                Log.d("AddContactDialog", "입력값이 올바르지 않습니다.")
            }
        }
    }

    private fun validateInputs() {
        with(binding) {
            isValidPetProfileImage = petProfileImageUri.isValidInput()
            isValidOwnerName = etInputOwnerName.text.toString().isValidInput()
            ownerGender = radioBtnOwnerGenderFemale.isChecked
            isValidPhoneNumber = etInputPhoneNumber.text.toString().isValidPhoneNumber()
            isValidOwnerAge = etInputOwnerAge.text.toString().isValidInput()
            isValidRegion = etInputRegion.text.toString().isValidInput()
            isValidPetName = etInputPetName.text.toString().isValidInput()
            petGender = radioBtnPetGenderFemale.isChecked
            isValidPetSpecies = etInputPetSpecies.text.toString().isValidInput()
            isValidPetAge = etInputPetAge.text.toString().isValidInput()
            isValidPersonality = chipGroupDialogPersonality.checkedChipIds
                .map { chipId -> chipGroupDialogPersonality.findViewById<Chip>(chipId).text.toString() }
                .joinToString(", ")
                .isValidPersonality()
            isValidMemo = etInputMemo.text.toString().isValidMemo()
        }
    }

    private fun isAllInputValid(): Boolean {
        return isValidPetProfileImage && isValidOwnerName && isValidPhoneNumber && isValidOwnerAge && isValidRegion &&
                isValidPetName && isValidPetSpecies && isValidPetAge && isValidPersonality && isValidMemo
    }

    private fun createContact(): Contact {
        with(binding) {
            val selectedChipList = chipGroupDialogPersonality.checkedChipIds.map { chipId ->
                chipGroupDialogPersonality.findViewById<Chip>(chipId).text.toString()
            }

            return Contact(
                ContactManager.getContactLastId() + 1,
                PetProfile(
                    ContactManager.getPetProfileLastId() + 1,
                    petProfileImageUri,
                    etInputPetName.text.toString(),
                    petGender,
                    etInputPetAge.text.toString().toInt(),
                    etInputPetSpecies.text.toString(),
                    mutableListOf(),
                    selectedChipList.joinToString(", "),
                    etInputMemo.text.toString()
                ),
                Owner(
                    ContactManager.getOwnerLastId() + 1,
                    etInputOwnerName.text.toString(),
                    ownerGender,
                    etInputPhoneNumber.text.toString(),
                    etInputOwnerAge.text.toString().toInt(),
                    etInputRegion.text.toString()
                )
            )
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

    private fun setTextInput() {
        binding.etInputPhoneNumber.doAfterTextChanged {
            val phoneNumber = it?.toString() ?: ""
            isValidPhoneNumber = phoneNumber.isValidPhoneNumber()
            updateEditTextFocusState(binding.etInputPhoneNumber, isValidPhoneNumber)
        }
    }

    private fun updateEditTextFocusState(editText: EditText, isValid: Boolean) {
        if (!isValid) {
            editText.setBackgroundResource(R.drawable.sel_text_input_background_red)
        } else {
            editText.setBackgroundResource(R.drawable.sel_text_input_background)
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
