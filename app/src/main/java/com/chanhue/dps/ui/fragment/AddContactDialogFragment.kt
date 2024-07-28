package com.chanhue.dps.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.chanhue.dps.util.Constants
import com.chanhue.dps.util.DialogStateManager
import com.chanhue.dps.R
import com.chanhue.dps.databinding.DialogExitBinding
import com.chanhue.dps.databinding.FragmentAddContactDialogBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.model.Owner
import com.chanhue.dps.model.PetProfile
import com.chanhue.dps.model.AddContactManager
import com.chanhue.dps.ui.activity.MainActivity
import com.chanhue.dps.ui.dialog.AgeNumPickerDialog
import com.chanhue.dps.ui.extensions.isValidInput
import com.chanhue.dps.ui.extensions.isValidMemo
import com.chanhue.dps.ui.extensions.isValidPersonality
import com.chanhue.dps.ui.extensions.isValidPhoneNumber
import com.chanhue.dps.ui.listener.AgeSelectListener
import com.chanhue.dps.ui.listener.ContactUpdateListener
import com.chanhue.dps.ui.listener.PersonalityListener
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AddContactDialogFragment() : DialogFragment(), AgeSelectListener, PersonalityListener {

    private var listener: ContactUpdateListener? = null
    private var contact: Contact = ContactManager.getDefaultContact()

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
        if (contact.id != -1) {
            setContactInfo(contact)
            initToolbar()
        } else {
            setPersonalityChips()
        }
        initExitButton()
        initOwnerAgeEditText()
        initPetAgeEditText()
        initPhoneNumberEditText()
        initRegionEditText()
        initSpeciesEditText()
        initAddPersonalityImageView()
        setPetProfileImage()
        setTextInput()
        initAddContactButton()
    }

    private fun initToolbar() {
        with(binding.toolbarDialogAddContact) {
            tvToolbarTitleDialog.text = "연락처 수정"
            tvToolbarAction.text = "수정"
        }
    }

    private fun initExitButton() {
        binding.toolbarDialogAddContact.ivToolbarCloseDialog.setOnClickListener {
            val binding = DialogExitBinding.inflate(LayoutInflater.from(requireContext()))
            val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AppExitDialog)
                .setView(binding.root)
                .show()

            binding.btnLabelExitDialogConfirm.setOnClickListener {
                dialog.dismiss()
                dismissWithAnimation()
            }
            binding.btnLabelExitDialogClose.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun setContactInfo(contact: Contact) {

        Log.d("AddContactDialog", "contact: $contact")
        val city = contact.owner.region.split(" ")[0]
        val district = contact.owner.region.split(" ")[1]

        with(binding) {
            petProfileImageUri = contact.petProfile.thumbnailImage
            ivDialogPetProfile.setPadding(0)
            Glide.with(ivDialogPetProfile)
                .load(contact.petProfile.thumbnailImage)
                .into(ivDialogPetProfile)
            etInputOwnerName.setText(contact.owner.name)
            ownerGender = contact.owner.gender
            if (ownerGender) {
                radioBtnOwnerGenderFemale.isChecked = true
            } else {
                radioBtnOwnerGenderMale.isChecked = true
            }
            etInputPhoneNumber.setText(contact.owner.phoneNumber)
            etInputOwnerAge.setText(contact.owner.age.toString())
            etInputRegionCity.setText(city)
            etInputRegionDistrict.setText(district)

            etInputPetName.setText(contact.petProfile.name)
            petGender = contact.petProfile.gender
            if (petGender) {
                radioBtnPetGenderFemale.isChecked = true
            } else {
                radioBtnPetGenderMale.isChecked = true
            }
            etInputPetSpecies.setText(contact.petProfile.species)
            etInputPetAge.setText(contact.petProfile.age.toString())
            etInputMemo.setText(contact.petProfile.memo)

            val personalityList = contact.petProfile.personality.split(", ")
            // personalityList 거꾸로 돌면서 chip 추가
            for (i in personalityList.size - 1 downTo 0) {
                val chip = createNewChip(personalityList[i])
                chip.isChecked = true
                chipGroupDialogPersonality.addView(chip, 0)
            }
        }
    }

    private fun initOwnerAgeEditText() {
        with(binding.etInputOwnerAge) {
            setOnClickListener {
                showAgeNumPickerDialog(text.toString().toIntOrNull(), true)
            }
        }
    }

    private fun initRegionEditText() {
        with(binding) {
            etInputRegionCity.setOnClickListener {
                showRegionListDialog(true, etInputRegionCity)
            }
            etInputRegionDistrict.setOnClickListener {
                if (etInputRegionCity.text.toString().isNotEmpty()) {
                    showRegionListDialog(false, etInputRegionDistrict)
                } else {
                    Toast.makeText(requireContext(), "시를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initSpeciesEditText() {
        binding.etInputPetSpecies.setOnClickListener {
            val speciesList = AddContactManager.getSpeciesList()
            var checkedItem = 0

            MaterialAlertDialogBuilder(requireContext(), R.style.AppBasicDialog)
                .setTitle("종 선택")
                .setNeutralButton("닫기") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("확인") { dialog, which ->
                    binding.etInputPetSpecies.setText(speciesList[checkedItem])
                }
                .setSingleChoiceItems(speciesList, checkedItem) { dialog, which ->
                    checkedItem = which
                }
                .show()
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
                addContact()
            } else {
                Log.d("AddContactDialog", "입력값이 올바르지 않습니다.")
            }
        }
    }

    private fun addContact() {
        createContact().let {
            val isSuccessful = if (contact.id == -1) {
                ContactManager.addContact(it)
            } else {
                ContactManager.updateContact(it)
            }
            if (isSuccessful) {
                listener?.onContactUpdated(it)
                val message = if (contact.id == -1) "연락처가 추가되었습니다." else "연락처가 수정되었습니다."
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "연락처 추가에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
            Log.d("ContactManager", "contact: ${ContactManager.getContactListByDogName()}")
            dismissWithAnimation()
        }
    }

    private fun validateInputs() {
        with(binding) {
            isValidPetProfileImage = petProfileImageUri.isValidInput()
            isValidOwnerName = etInputOwnerName.text.toString().isValidInput()
            ownerGender = radioBtnOwnerGenderFemale.isChecked
            isValidPhoneNumber = etInputPhoneNumber.text.toString().isValidPhoneNumber()
            isValidOwnerAge = etInputOwnerAge.text.toString().isValidInput()
            isValidRegion = etInputRegionDistrict.text.toString().isValidInput()
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

            Log.d("AddContactDialog", "contact: $contact")

            val city = etInputRegionCity.text.toString()
            val district = etInputRegionDistrict.text.toString()
            val region = if (district.isNotEmpty()) {
                "$city $district"
            } else {
                city
            }

            return Contact(
                getContactId("contact"),
                PetProfile(
                    getContactId("pet"),
                    petProfileImageUri,
                    etInputPetName.text.toString(),
                    petGender,
                    etInputPetAge.text.toString().toInt(),
                    etInputPetSpecies.text.toString(),
                    mutableListOf(),
                    etInputMemo.text.toString(),
                    selectedChipList.joinToString(", ")
                ),
                Owner(
                    getContactId("owner"),
                    etInputOwnerName.text.toString(),
                    ownerGender,
                    etInputPhoneNumber.text.toString(),
                    etInputOwnerAge.text.toString().toInt(),
                    region
                ),
                contact.isFavorite
            )
        }
    }

    private fun getContactId(type: String): Int {
        when (type) {
            "contact" -> {
                return if (contact.id == -1) {
                    ContactManager.getContactLastId() + 1
                } else {
                    contact.id
                }
            }
            "owner" -> {
                return if (contact.owner.id == -1) {
                    ContactManager.getOwnerLastId() + 1
                } else {
                    contact.owner.id
                }
            }
            "pet" -> {
                return if (contact.petProfile.id == -1) {
                    ContactManager.getPetProfileLastId() + 1
                } else {
                    contact.petProfile.id
                }
            }
            else -> {
                return -1
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

    private fun showRegionListDialog(isCity: Boolean, editText: EditText) {
        val items = if (isCity) {
            AddContactManager.getCityList()
        } else {
            AddContactManager.getDistrictList(binding.etInputRegionCity.text.toString())
        }
        var checkedItem = 0

        MaterialAlertDialogBuilder(requireContext(), R.style.AppBasicDialog)
            .setTitle("지역 선택")
            //.setMessage("DO NOT SET MESSAGE WHEN YOU USE LIST")
            .setNeutralButton("닫기") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("확인") { dialog, which ->
                editText.setText(items[checkedItem])
            }
            .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                checkedItem = which
            }
            .show()
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (parentFragment as? ContactListFragment)?.onDialogDismissed()
    }

    companion object {
        private const val ARG_CONTACT = "contact"

        fun newInstance(contact: Contact, listener: ContactUpdateListener): AddContactDialogFragment {
            return AddContactDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONTACT, contact)
                    Log.d("AddContactDialog", "contact: $contact")
                }
                this.listener = listener
                this.contact = contact
            }
        }
    }
}
