package com.chanhue.dps.ui.fragment

import ContactAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chanhue.dps.util.Constants
import com.chanhue.dps.ui.activity.DetailActivity
import com.chanhue.dps.util.DialogStateManager
import com.chanhue.dps.R
import com.chanhue.dps.databinding.FragmentContactListBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager
import com.chanhue.dps.ui.adapter.GridViewAdapter
import com.chanhue.dps.ui.extensions.dpToPx
import com.chanhue.dps.ui.listener.ContactUpdateListener
import com.chanhue.dps.viewmodel.ContactViewModel
import java.util.jar.Manifest

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_CONTACT = "contact"

class ContactListFragment : Fragment(), ContactUpdateListener, ContactAdapter.OnContactInteractionListener {

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

        adapter = ContactAdapter(emptyList(), isGridLayout, this) { contact ->
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
        setItemTouchHelper()

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
            binding.tvLabelNoLikeList.visibility = if (contacts.isEmpty()) View.VISIBLE else View.INVISIBLE
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
            adapter = ContactAdapter(emptyList(), isGridLayout, this) { contact ->
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

        Log.d("ContactListFragment", "contact: $contact")

        if (ContactManager.updateFavorite(contact)) {
            contactViewModel.updateContacts(contactViewModel.contacts.value?.sortedByDescending { it.isFavorite } ?: emptyList())
        }
    }

    private fun setItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val contact = adapter.getContactAtPosition(position)
                makeCall(contact.owner.phoneNumber)
                adapter.notifyItemChanged(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    paint.color = ContextCompat.getColor(requireContext(), R.color.light_orange) // 원하는 배경색

                    // 배경색을 먼저 그림
                    if (dX > 0) {
                        c.drawRect(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            itemView.left + dX,
                            itemView.bottom.toFloat(),
                            paint
                        )
                    }

                    // 그 다음에 전화 아이콘을 그림 -> 그래야 안 묻힘
                    val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_call)
                    val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2 // 중앙에 위치
                    val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                    val iconBottom = iconTop + icon.intrinsicHeight // 높이

                    if (dX > 0) {
                        val iconLeft = itemView.left + iconMargin
                        val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        icon.draw(c)
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun makeCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CALL_PHONE),
                PERMISSIONS_CALL_PHONE
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
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
        const val PERMISSIONS_CALL_PHONE = 1
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
                    setFilterText("Region", region)
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
                    setFilterText("Species", species)
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
                    setFilterText("Age", ageRange)
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

    private fun setFilterText(filterType: String, filterText: String) {
        with(binding) {
            if (filterText == "전체") {
                val defaultText = when (filterType) {
                    "Region" -> "지역"
                    "Species" -> "종"
                    "Age" -> "나이"
                    else -> ""
                }
                when (filterType) {
                    "Region" -> tvFilterRegion.text = defaultText
                    "Species" -> tvFilterSpecies.text = defaultText
                    "Age" -> tvFilterAge.text = defaultText
                }
            } else {
                when (filterType) {
                    "Region" -> tvFilterRegion.text = filterText
                    "Species" -> tvFilterSpecies.text = filterText
                    "Age" -> tvFilterAge.text = filterText
                }
            }
        }
    }

    fun onDialogDismissed() {
        view?.requestLayout()
    }

    override fun onResume() {
        super.onResume()
        contactViewModel.updateContacts(ContactManager.getContactListByDogName())
    }

    override fun onCallContact(phoneNumber: String) {
        makeCall(phoneNumber)
    }
}
