package com.chanhue.dps.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentPersonalityBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class PersonalityBottomSheetFragment(
    private var personalityList: List<String>,
    private val listener: PersonalityListener
) : BottomSheetDialogFragment() {

    private var _binding: FragmentPersonalityBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalityBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("PersonalityBottomSheet", "personalityList: $personalityList")

        for (personality in personalityList) {
            val chip = createNewChip(personality)
            binding.chipGroupPersonality.addView(chip)
        }

        binding.btnPersonalityAdd.setOnClickListener {
            val personality = binding.etInputPersonality.text.toString()
            val chip = createNewChip(personality)
            binding.chipGroupPersonality.addView(chip)
            binding.etInputPersonality.text.clear()
            if (personality.isNotEmpty()) {
                listener.onPersonalityUpdated(personality)
            }
        }

        binding.btnPersonalityClose.setOnClickListener {
            dismiss()
        }
    }

    private fun createNewChip(text: String): Chip {
        val chip = layoutInflater.inflate(R.layout.item_chip_with_icon, binding.chipGroupPersonality, false) as Chip
        chip.text = text
        //chip.isCloseIconVisible = false
        chip.setOnCloseIconClickListener {
            // 닫기 아이콘 클릭 시 Chip 제거
            listener.onPersonalityDeleted(text)
            (it.parent as? ViewGroup)?.removeView(it)
        }
        return chip
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}