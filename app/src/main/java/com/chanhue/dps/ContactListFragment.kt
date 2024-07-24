package com.chanhue.dps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.chanhue.dps.databinding.FragmentContactListBinding
import com.chanhue.dps.ui.AddContactDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFloatingButton()
    }

    private fun initFloatingButton() {
        binding.ivContact.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        if (DiaglogStateManager.isShowing) return

        val dialogFragment = AddContactDialogFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            setCustomAnimations(
//                R.anim.dialog_slide_up,  // 프래그먼트가 나타날 때의 애니메이션
//                R.anim.dialog_slide_down,  // 프래그먼트가 사라질 때의 애니메이션
//                R.anim.dialog_slide_up,  // 백 스택에서 다시 나타날 때의 애니메이션
//                R.anim.dialog_slide_down  // 백 스택에서 사라질 때의 애니메이션
//            )
            add(android.R.id.content, dialogFragment)
        }
        DiaglogStateManager.setIsShowing(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}