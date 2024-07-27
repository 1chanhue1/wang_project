package com.chanhue.dps.ui.fragment

import ContactAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanhue.dps.Constants
import com.chanhue.dps.ui.activity.DetailActivity
import com.chanhue.dps.DialogStateManager
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentContactListBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.ui.adapter.GridViewAdapter
import com.chanhue.dps.ui.extensions.dpToPx
import com.chanhue.dps.ui.listener.ContactUpdateListener
import com.chanhue.dps.viewmodel.ContactViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_CONTACT = "contact"

class ContactListFragment : Fragment(), ContactUpdateListener {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val contactViewModel: ContactViewModel by activityViewModels()
    private var isGridLayout = false // 기본은 리스트 레이아웃

    private var param: Contact? = null
    private lateinit var adapter: ContactAdapter
    private lateinit var favoriteAdapter: GridViewAdapter

    private val petAgeRangeList = listOf(
        "1-5세", "6-10세", "11-15세", "16-20세", "21-25세"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFloatingButton()
        initLayoutToggleButton()
        initChip()

        adapter = ContactAdapter(emptyList(), isGridLayout) { contact ->
            toggleFavorite(contact)
        }

        // 데이터 보내기
        adapter.onItemClick = { contact ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.ITEM_OBJECT, contact)
            }
            startActivity(intent)
        }

        binding.recyclerViewContacts.adapter = adapter
        setLayoutManager()

        // ViewModel - LiveData 관찰
        contactViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.updateContacts(contacts)
        }

        arguments?.let {
            param = it.getParcelable(ARG_CONTACT)
            Log.d("ContactListFragment1", param.toString())
        }

        favoriteAdapter = GridViewAdapter(mutableListOf())
        binding.hsvFriend.adapter = favoriteAdapter
        contactViewModel.favoriteContacts.observe(viewLifecycleOwner) { contacts ->
            favoriteAdapter.updateContacts(contacts)
        }
    }

    private fun initFloatingButton() {
        binding.ivContact.setOnClickListener {
            showDialog()
        }
    }

    private fun initLayoutToggleButton() {
        binding.toggleLayoutButton.setOnClickListener {
            isGridLayout = !isGridLayout
            setLayoutManager()
            adapter = ContactAdapter(emptyList(), isGridLayout) { contact ->
                toggleFavorite(contact)
            }
            adapter.onItemClick = { contact ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(Constants.ITEM_OBJECT, contact)
                }
                startActivity(intent)
            }
            binding.recyclerViewContacts.adapter = adapter
            contactViewModel.contacts.value?.let { contacts ->
                adapter.updateContacts(contacts) // 업데이트된 데이터 설정
            }
        }
    }

    private fun setLayoutManager() {
        with(binding) {
            if (isGridLayout) {
                recyclerViewContacts.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    setPadding(11.dpToPx(requireContext()), 0, 11.dpToPx(requireContext()), 0)
                }
                toggleLayoutButton.setImageResource(R.drawable.ic_list_view)
            } else {
                recyclerViewContacts.apply {
                    layoutManager = LinearLayoutManager(context)
                    setPadding(0, 0, 0, 0)
                }
                toggleLayoutButton.setImageResource(R.drawable.ic_grid_view)
            }
        }
    }

    private fun toggleFavorite(contact: Contact) {
        contact.isFavorite = !contact.isFavorite
        contactViewModel.updateContacts(contactViewModel.contacts.value?.sortedByDescending { it.isFavorite } ?: emptyList())
    }

    private fun showDialog() {
        if (DialogStateManager.isShowing) return

        val dialogFragment =
            AddContactDialogFragment.newInstance(ContactManager.getDefaultContact(), this)
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            add(android.R.id.content, dialogFragment)
        }
        DialogStateManager.setIsShowing(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(contact: Contact) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONTACT, contact)
                }
            }
    }

    override fun onContactUpdated(contact: Contact) {
        contactViewModel.addContact(contact)
    }

    private fun initChip() {
        with(binding) {
            tvFilterRegion.setOnClickListener {
                val bottomSheetDialogFragment = FilterChipBottomSheetFragment.newInstance(
                    "Region",
                    ContactManager.getRegionList()
                ) { region ->
                    tvFilterRegion.text = region
                    contactViewModel.setRegionFilter(region)
                }
                bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            }
            tvFilterSpecies.setOnClickListener {
                Log.d("ContactListFragment", "speciesList : ${ContactManager.getPetSpeciesList()}")
                val bottomSheetDialogFragment = FilterChipBottomSheetFragment.newInstance(
                    "Species",
                    ContactManager.getPetSpeciesList()
                ) { species ->
                    tvFilterSpecies.text = species
                    contactViewModel.setSpeciesFilter(species)
                }
                bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            }
            tvFilterAge.setOnClickListener {
                Log.d("ContactListFragment", "petAgeRangeList : ${petAgeRangeList}")
                val bottomSheetDialogFragment = FilterChipBottomSheetFragment.newInstance(
                    "Age",
                    petAgeRangeList
                ) { ageRange ->
                    tvFilterAge.text = ageRange
                    contactViewModel.setAgeRangeFilter(ageRange)
                }
                bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            }
            // 초기화 버튼 클릭 시 필터 전부 초기화 하기
            tvFilterAll.setOnClickListener {
                tvFilterRegion.text = "지역"
                tvFilterSpecies.text = "종"
                tvFilterAge.text = "나이"
                contactViewModel.clearFilters()
            }
        }
    }
}
