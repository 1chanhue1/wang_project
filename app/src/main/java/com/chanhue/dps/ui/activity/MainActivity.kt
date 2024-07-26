package com.chanhue.dps.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import com.chanhue.dps.R
import com.chanhue.dps.databinding.ActivityMainBinding
import com.chanhue.dps.ui.fragment.SearchFragment
import com.chanhue.dps.ui.adapter.ViewPagerAdapter


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        viewPager.adapter = ViewPagerAdapter(this)

        // TabLayout과 ViewPager2를 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "연락처"
                1 -> tab.text = "마이페이지"
            }
        }.attach()
        // test

        binding.toolbarHome.ivToolbarHomeSearchIcon.setOnClickListener {
            Toast.makeText(this, "검색 버튼 클릭", Toast.LENGTH_SHORT).show()
            showSearchFragment()
        }
    }

    private fun showSearchFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_right_out,
                R.anim.slide_right_in,
                R.anim.slide_right_out
            )
            .replace(R.id.fragment_container_search, SearchFragment())
            .addToBackStack(null)
            .commit()

        with(binding) {
            viewPager.visibility = View.GONE
            fragmentContainerSearch.visibility = View.VISIBLE
        }
    }

    fun hideDetailFragment() {
        supportFragmentManager.popBackStack()
        with(binding) {
            viewPager.visibility = View.VISIBLE
            fragmentContainerSearch.visibility = View.GONE
        }
    }
}
