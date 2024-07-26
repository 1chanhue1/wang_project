package com.chanhue.dps.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentFilterChipBottomSheetBinding
import com.chanhue.dps.model.ContactManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterChipBottomSheetFragment(
    private val filterType: String,
    private val items : List<String>,
    private val listener: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterChipBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val filteredListByChipAdapter by lazy {
        FilteredListByChipAdapter { item ->
            listener(item)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterChipBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("bottomsheet", "onViewCreated: $items")

//        val behavior = BottomSheetBehavior.from(view.parent as View)
//        val vto = view.viewTreeObserver
//        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                val height = view.height
//                val maxHeight = TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, 450f, resources.displayMetrics
//                ).toInt()
//                behavior.peekHeight = if (height > maxHeight) maxHeight else ViewGroup.LayoutParams.WRAP_CONTENT
//                behavior.isDraggable = false
//                // 레이아웃 리스너를 제거하여 중복 호출을 방지
//                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })

        when (filterType) {
            "Region" -> {
                binding.tvLabelFilterType.text = "지역"
            }
            "Species" -> {
                binding.tvLabelFilterType.text = "종"
            }
            "Age" -> {
                binding.tvLabelFilterType.text = "나이"
            }
        }

//        val parentView = view.parent as View
//        val behavior = BottomSheetBehavior.from(parentView)
//        behavior.isDraggable = false
//        behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
//
//        parentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                parentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                val maxHeight = 450.dpToPx(requireContext())
//                val layoutParams = parentView.layoutParams
//                layoutParams.height = if (parentView.height > maxHeight) maxHeight else ViewGroup.LayoutParams.WRAP_CONTENT
//                parentView.layoutParams = layoutParams
//                behavior.peekHeight = layoutParams.height
//                behavior.isDraggable = false
//            }
//        })

        initRecyclerView()
    }

    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    private fun initRecyclerView() {
        val newItems = items.toMutableList()
        newItems.add(0, "전체")
        binding.rvFilteredList.apply {
            adapter = filteredListByChipAdapter
            filteredListByChipAdapter.submitList(newItems.toList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // newInstance() method 생성
        fun newInstance(
            filterType: String,
            items: List<String>,
            listener: (String) -> Unit
        ) = FilterChipBottomSheetFragment(filterType, items, listener)
    }
}