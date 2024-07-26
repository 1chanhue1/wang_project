package com.chanhue.dps.ui.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
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
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chanhue.dps.Constants.ITEM_OBJECT
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ActivityDetailBinding
import com.chanhue.dps.model.Contact
import com.chanhue.dps.ui.adapter.PhotoRecyclerAdapter
import com.chanhue.dps.ui.fragment.AddContactDialogFragment
import com.chanhue.dps.ui.fragment.NotificationDialogFragment
import com.chanhue.dps.ui.listener.ContactUpdateListener
import java.io.File
import kotlin.random.Random

class DetailActivity : AppCompatActivity(), NotificationDialogFragment.FragmentDataListener,
    NotificationDialogFragment.ConfirmDialogInterface, ContactUpdateListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: PhotoRecyclerAdapter

    private var imageFile = File("")
    private var data: Contact? = null
    private var param = ""
    private var delayTime = 0
    private val random = Random
    private val colors = arrayListOf(R.color.light_orange, R.color.light_yellow)
    private var isClicked = false
    private var notificationScheduled = false

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

        data = intent.getParcelableExtra<Contact>(ITEM_OBJECT)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (data != null) {
            val randomNumber = random.nextInt(colors.size)
            adapter = PhotoRecyclerAdapter(data!!.petProfile.dogImageList.toMutableList())

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
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.detail_edit_btn -> {
                                val dataToSend = data
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

                                return@setOnMenuItemClickListener true
                            }
                            R.id.detail_favorite_btn -> {
                                if (isClicked) {
                                    it.setIcon(R.drawable.ic_favorite_full)
                                    isClicked = false
                                    return@setOnMenuItemClickListener true
                                } else {
                                    it.setIcon(R.drawable.ic_favorite)
                                    isClicked = true
                                    return@setOnMenuItemClickListener true
                                }
                            }
                            else -> {
                                val dialog = NotificationDialogFragment(this@DetailActivity, "알람", 1)
                                dialog.isCancelable = false
                                dialog.show(this@DetailActivity.supportFragmentManager, "ConfirmDialog")

                                return@setOnMenuItemClickListener true
                            }
                        }
                    }
                    setBackgroundResource(colors[randomNumber])
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
                    .load(data!!.petProfile.thumbnailImage)
                    .into(detailProfileImg)

                detailNickname.text = data!!.owner.name
                detailGenderAge.text = if (data!!.owner.gender) "여" else "남"
                detailLocation.text = data!!.owner.region
                detailCharacteristic.text = data!!.petProfile.memo

                detailPhoneNumber.apply {
                    text = data!!.owner.phoneNumber
                    setOnClickListener {
                        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this@DetailActivity, arrayOf(Manifest.permission.CALL_PHONE), PERMISSIONS_CALL_PHONE)
                        } else {
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:${data!!.owner.phoneNumber}")
                            startActivity(callIntent)
                        }
                    }
                }
                detailName.text = data!!.petProfile.name
                detailAge.text = data!!.petProfile.age.toString()
                detailSpecies.text = data!!.petProfile.species
                detailGender.text = if (data!!.petProfile.gender) "여" else "남"
                detailCharacter.text = data!!.petProfile.personality
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
        val notificationId = 1
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_mainlogo)
            .setContentTitle("Wang!")
            .setContentText(data)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

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
        // TODO: Not yet implemented
    }
}
