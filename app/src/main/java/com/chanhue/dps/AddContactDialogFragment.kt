package com.chanhue.dps

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AddContactDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("확인")
            .setPositiveButton("확인") { _,_ -> }
            .create()

    companion object {
        const val TAG = "AddContactDialogFragment"
    }
}