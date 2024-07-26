package com.chanhue.dps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.chanhue.dps.databinding.ActivityDetailBinding
import com.chanhue.dps.model.ContactManager.getContactById
import java.io.File
import kotlin.random.Random

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: PhotoRecyclerAdapter

    private var imageFile = File("")

    private val data = getContactById(1)

    private val random = Random
    private val colors = arrayListOf(R.color.light_orange, R.color.light_yellow)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val randomNumber = random.nextInt(colors.size)
        adapter = PhotoRecyclerAdapter(data.petProfile.dogImageList.toMutableList())

        adapter.itemClick = object : PhotoRecyclerAdapter.ItemClick {
            override fun onLongClick(view: View, position: Int) {
                if (position > 0) {
                    adapter.removeItem(position)
                } else {
                    Toast.makeText(this@DetailActivity, "Cannot delete this item", Toast.LENGTH_SHORT).show()
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
            detailPhotoRV.layoutManager = GridLayoutManager(this@DetailActivity, 3, GridLayoutManager.VERTICAL, false)
            detailPhotoRV.adapter = adapter

            detailToolbar.apply {
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        0 -> true
                        else -> false
                    }
                }
                setBackgroundResource(colors[randomNumber])
            }

            detailBackground.setBackgroundResource(colors[randomNumber])
            detailDivider.setBackgroundResource(colors[randomNumber])

            detailBackBtn.setOnClickListener {
                finish()
            }

            Glide.with(detailProfileImg)
                .load(data.petProfile.thumbnailImage)
                .into(detailProfileImg)

            detailNickname.text = data.owner.name
            detailGenderAge.text = if (data.owner.gender) "여" else "남"
            detailLocation.text = data.owner.region
            detailCharacteristic.text = data.petProfile.memo

            detailPhoneNumber.text = data.owner.phoneNumber
            detailName.text = data.petProfile.name
            detailAge.text = data.petProfile.age.toString()
            detailSpecies.text = data.petProfile.species
            detailGender.text = if (data.petProfile.gender) "여" else "남"
            detailCharacter.text = data.petProfile.personality
        }
    }

    private fun getRealPathFromUri(uri: Uri): String? {
        var filePath: String? = null

        // Content URI
        if (uri.scheme.equals("content", ignoreCase = true)) {
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    if (columnIndex != -1) {
                        filePath = cursor.getString(columnIndex)
                    }
                }
            }
        }

        // File URI
        if (filePath == null && uri.scheme.equals("file", ignoreCase = true)) {
            filePath = uri.path
        }

        return filePath
    }
}
