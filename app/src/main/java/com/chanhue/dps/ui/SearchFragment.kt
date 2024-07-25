package com.chanhue.dps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.chanhue.dps.DialogStateManager
import com.chanhue.dps.MainActivity
import com.chanhue.dps.databinding.FragmentSearchBinding
import com.chanhue.dps.model.ContactManager

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchListAdapter by lazy {
        SearchListAdapter { contact ->
            Toast.makeText(requireContext(), contact.owner.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressedHandler()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvSearch.apply {
            adapter = searchListAdapter
            searchListAdapter.submitList(ContactManager.getContactListByDogName())
        }
    }

    private fun setOnBackPressedHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as? MainActivity)?.hideDetailFragment()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}