package com.chanhue.dps

import ContactAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanhue.dps.databinding.FragmentContactListBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.model.Owner
import com.chanhue.dps.model.PetProfile
import com.chanhue.dps.ui.AddContactDialogFragment
import com.chanhue.dps.ui.ContactUpdateListener
import com.chanhue.dps.ui.FilterChipBottomSheetFragment
import com.chanhue.dps.viewmodel.ContactViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_CONTACT = "contact"

class ContactListFragment : Fragment(), ContactUpdateListener {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val contactViewModel: ContactViewModel by activityViewModels()
    private var isGridLayout = false // 기본은 리스트 레이아웃

    private lateinit var adapter: ContactAdapter

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

        adapter = ContactAdapter(emptyList())
        binding.recyclerViewContacts.adapter = adapter

        contactViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.updateContacts(contacts)
        }

        setLayoutManager()

        // ViewModel - LiveData
//        contactViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
//            val adapter = ContactAdapter(contacts)
//            binding.recyclerViewContacts.adapter = adapter
//            setLayoutManager()
//        }

        binding.hsvFriend.adapter = GridViewAdapter(ContactManager.getContactListByDogName())

        initChip()
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
        }
    }

    private fun setLayoutManager() {
        binding.recyclerViewContacts.layoutManager = if (isGridLayout) {
            GridLayoutManager(context, 2) // 2열로 세팅( 그리드 레이아웃)
        } else {
            LinearLayoutManager(context) // (리스트 레이아웃)
        }
    }

    private fun showDialog() {
        if (DialogStateManager.isShowing) return

        val dialogFragment = AddContactDialogFragment.newInstance(ContactManager.getDefaultContact(), this)
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

        /*fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
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
                }
                bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            }
        }
    }
}
