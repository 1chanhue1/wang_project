package com.chanhue.dps

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chanhue.dps.databinding.DialogEditProfileBinding
import com.chanhue.dps.databinding.FragmentMyPageBinding
import com.chanhue.dps.model.MyProfile
import com.chanhue.dps.model.MyProfileManager

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val view = binding.root

        // 프로필 정보 설정
        updateProfileUI()

        // 수정 버튼 클릭 처리
        binding.editButton.setOnClickListener {
            showEditProfileDialog()
        }

        return view
    }

    private fun updateProfileUI() { // profileUI 업데이트가 필요함 취소 버튼을 눌렀을 경우에
        val profile = MyProfileManager.getProfile()
        profile?.let {
            binding.nameTextView.text = "이름 | " + it.name
            binding.phoneNumberTextView.text = "전화번호 | " + it.phoneNumber
            binding.regionTextView.text = "사는곳 | " + it.region
            binding.emergencyNumberTextView.text = "긴급연락망 | " + it.emergencyNumber
        }
    }

    private fun showEditProfileDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogEditProfileBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        // 최신 프로필 데이터로 EditText 초기화
        val profile = MyProfileManager.getProfile()
        profile?.let {
            dialogBinding.editName.setText(it.name)
            dialogBinding.editPhoneNumber.setText(it.phoneNumber)
            dialogBinding.editRegion.setText(it.region)
            dialogBinding.editEmergencyNumber.setText(it.emergencyNumber)
        }

        // 저장 버튼 클릭 했을 경우
        dialogBinding.saveButton.setOnClickListener {
            val newName = dialogBinding.editName.text.toString()
            val newPhoneNumber = dialogBinding.editPhoneNumber.text.toString()
            val newRegion = dialogBinding.editRegion.text.toString()
            val newEmergencyNumber = dialogBinding.editEmergencyNumber.text.toString()

            val newProfile = MyProfile(newName, newPhoneNumber, newRegion, newEmergencyNumber)
            MyProfileManager.setProfile(newProfile)

            updateProfileUI()

            dialog.dismiss()
        }

        // 취소 버튼 클릭 했을 경우
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
