package com.chanhue.dps.ui.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chanhue.dps.util.Constants.ITEM_OBJECT
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ActivityDetailBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.ui.adapter.PhotoRecyclerAdapter
import com.chanhue.dps.ui.fragment.AddContactDialogFragment
import com.chanhue.dps.ui.fragment.NotificationDialogFragment
import com.chanhue.dps.ui.listener.ContactUpdateListener
import com.chanhue.dps.util.Constants
import com.chanhue.dps.viewmodel.ContactViewModel
import java.io.File
import kotlin.random.Random

class DetailActivity : AppCompatActivity(), NotificationDialogFragment.FragmentDataListener,
    NotificationDialogFragment.ConfirmDialogInterface, ContactUpdateListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: PhotoRecyclerAdapter

    private var imageFile = File("")
    private var originData: Contact? = null
    private var param = ""
    private var delayTime = 0
    private val random = Random
    private val colors = arrayListOf(R.color.light_orange, R.color.light_yellow)
    private var isClicked = false
    private var notificationScheduled = false

    private val contactViewModel: ContactViewModel by viewModels()

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                createNotificationChannel()
            }
        }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                }
                imageLauncher.launch(intent)
            }
        }

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageUri?.let {
                    val realPath = getRealPathFromUri(it)
                    realPath?.let { path ->
                        imageFile = File(path)
                        adapter.addItem(imageFile.toString())
                    }
                }
            }
        }

    companion object {
        const val PERMISSIONS_CALL_PHONE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        originData = intent.getParcelableExtra<Contact>(ITEM_OBJECT)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (originData != null) {
            val randomNumber = random.nextInt(colors.size)
            adapter = PhotoRecyclerAdapter(originData!!.petProfile.dogImageList.toMutableList())

            adapter.itemClick = object : PhotoRecyclerAdapter.ItemClick {
                override fun onLongClick(view: View, position: Int) {
                    Log.d("DetailActivity", "onLongClick position: $position")
                    if (position > 0) {
                        adapter.removeItem(position)
                    }
                }

                override fun onClick(view: View, position: Int) {
                    if (position == 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            }

            with(binding) {
                with(detailToolbar) {
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.detail_edit_btn -> {
                                detailFragmenrContainer.visibility = View.VISIBLE
                                val dataToSend = originData
                                val dialogFragment = dataToSend?.let { it1 ->
                                    AddContactDialogFragment.newInstance(
                                        it1, this@DetailActivity)
                                }
                                val fragmentManager = supportFragmentManager
                                fragmentManager.commit {
                                    setReorderingAllowed(true)
                                    addToBackStack(null)
                                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    if (dialogFragment != null) {
                                        add(R.id.detail_fragmenr_container, dialogFragment)
                                    }
                                }
                                true
                            }
                            R.id.detail_favorite_btn -> {
                                originData?.let { contact ->
                                    contactViewModel.toggleFavorite(contact.id)
                                    updateFavoriteIcon(menuItem, !contact.isFavorite)
                                    Toast.makeText(this@DetailActivity, "즐겨찾기에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                                true
                            }
                            else -> {
                                val text = "${originData!!.petProfile.name} | ${originData!!.owner.name} 님과 곧 산책할 시간이에요!"
                                val dialog = NotificationDialogFragment(this@DetailActivity, text, 1)
                                dialog.isCancelable = false
                                dialog.show(this@DetailActivity.supportFragmentManager, "ConfirmDialog")
                                true
                            }
                        }
                    }
                    setBackgroundResource(colors[randomNumber])

                    // Set the correct favorite icon on creation
                    menu.findItem(R.id.detail_favorite_btn)?.let { menuItem ->
                        originData?.let { contact ->
                            updateFavoriteIcon(menuItem, contact.isFavorite)
                        }
                    }
                }

                detailPhotoRV.layoutManager =
                    GridLayoutManager(this@DetailActivity, 3, GridLayoutManager.VERTICAL, false)
                detailPhotoRV.adapter = adapter

                detailBackground.setBackgroundResource(colors[randomNumber])
                detailDivider.setBackgroundResource(colors[randomNumber])

                detailBackBtn.setOnClickListener {
                    finish()
                }

                Glide.with(detailProfileImg)
                    .load(originData!!.petProfile.thumbnailImage)
                    .into(detailProfileImg)

                detailNickname.text = originData!!.owner.name
                detailGenderAge.text = if (originData!!.owner.gender) "여" else "남"
                detailLocation.text = originData!!.owner.region
                detailCharacteristic.text = originData!!.petProfile.memo

                detailPhoneNumber.apply {
                    text = originData!!.owner.phoneNumber
                    setOnClickListener {
                        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this@DetailActivity, arrayOf(Manifest.permission.CALL_PHONE), PERMISSIONS_CALL_PHONE)
                        } else {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:${originData!!.owner.phoneNumber}")
                            startActivity(callIntent)
                        }
                    }
                }
                detailName.text = originData!!.petProfile.name
                detailAge.text = "${originData!!.petProfile.age}세"
                detailSpecies.text = originData!!.petProfile.species
                detailGender.text = if (originData!!.petProfile.gender) "여" else "남"
                detailCharacter.text = originData!!.petProfile.personality
            }
        } else {
            finish()
        }

        // Request notification permission on startup
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            createNotificationChannel()
        }
    }

    private fun updateFavoriteIcon(menuItem: MenuItem, isFavorite: Boolean) {
        if (isFavorite) {
            menuItem.setIcon(R.drawable.ic_favorite_full)
        } else {
            menuItem.setIcon(R.drawable.ic_favorite)
        }
    }

    private fun getRealPathFromUri(uri: Uri): String? {
        var filePath: String? = null

        if (uri.scheme.equals("content", ignoreCase = true)) {
            contentResolver.query(uri, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    if (columnIndex != -1) {
                        filePath = cursor.getString(columnIndex)
                    }
                }
            }
        }

        if (filePath == null && uri.scheme.equals("file", ignoreCase = true)) {
            filePath = uri.path
        }

        return filePath
    }

    private val channelID = "default"

    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelID, "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Description of the channel"
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(data: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(Constants.ITEM_OBJECT, originData)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        Log.d("DetailActivity", "pendingIntent: $pendingIntent")

        val notificationId = 1
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_mainlogo)
            .setContentTitle("Wang!")
            .setContentText(data)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)  // 알림을 클릭하면 자동으로 제거되도록 설정

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(notificationId, builder.build())
        }
    }

    fun scheduleNotification(data: String, delay: Int) {
        if (!notificationScheduled) {
            notificationScheduled = true
            Handler(Looper.getMainLooper()).postDelayed({
                showNotification(data)
                notificationScheduled = false
            }, delay * 1000L)
        } else {
        }
    }

    override fun onSubmitButtonClick(id: Int) {
        scheduleNotification(param, delayTime)
    }

    override fun onDataReceived(data: String, delay: Int) {
        Log.d("DetailActivity", "Received data: $data with delay: $delay seconds")
        param = data
        delayTime = delay
    }

    override fun onContactUpdated(contact: Contact) {
        if (contact.id == originData?.id) {
            //Toast.makeText(this, "성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show()
            originData = contact
            updateContact(contact)
            // 뷰모델에 변경된 연락처 정보를 전달
            contactViewModel.updateContact(contact)
        } else {
            Toast.makeText(this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateContact(contact: Contact) {
        with(binding) {
            Glide.with(detailProfileImg)
                .load(contact.petProfile.thumbnailImage)
                .into(detailProfileImg)

            detailNickname.text = contact.owner.name
            detailGenderAge.text = if (contact.owner.gender) "여" else "남"
            detailLocation.text = contact.owner.region
            detailCharacteristic.text = contact.petProfile.memo

            detailPhoneNumber.apply {
                text = contact.owner.phoneNumber
                setOnClickListener {
                    if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this@DetailActivity, arrayOf(Manifest.permission.CALL_PHONE), PERMISSIONS_CALL_PHONE)
                    } else {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:${contact.owner.phoneNumber}")
                        startActivity(callIntent)
                    }
                }
            }
            detailName.text = contact.petProfile.name
            detailAge.text = contact.petProfile.age.toString()
            detailSpecies.text = contact.petProfile.species
            detailGender.text = if (contact.petProfile.gender) "여" else "남"
            detailCharacter.text = contact.petProfile.personality
        }
    }

    fun onDialogDismissed() {
        Log.d("DetailActivity", "Dialog dismissed.")
        binding.detailFragmenrContainer.visibility = View.GONE
    }
}
