package com.chanhue.dps.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.chanhue.dps.ui.activity.MainActivity
import com.chanhue.dps.databinding.FragmentSearchBinding
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.ui.activity.DetailActivity
import com.chanhue.dps.ui.adapter.SearchListAdapter
import com.chanhue.dps.util.Constants

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchListAdapter by lazy {
        SearchListAdapter { contact ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.ITEM_OBJECT, contact)
            startActivity(intent)
            (activity as? MainActivity)?.hideSearchFragment()
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
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchListAdapter.filter.filter(newText)
                return true
            }
        })
        binding.ivSearchArrowBack.setOnClickListener {
            (activity as? MainActivity)?.hideSearchFragment()
        }
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
                (activity as? MainActivity)?.hideSearchFragment()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}