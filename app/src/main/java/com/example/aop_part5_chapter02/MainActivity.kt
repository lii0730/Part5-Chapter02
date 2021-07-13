package com.example.aop_part5_chapter02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.aop_part5_chapter02.databinding.ActivityMainBinding
import com.example.aop_part5_chapter02.presentation.Home.FragmentHome
import com.example.aop_part5_chapter02.presentation.MyPage.FragmentMyPage
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        changeFragment(FragmentHome())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_home -> {
                changeFragment(FragmentHome())
               true
            }
            R.id.menu_myPage -> {
                changeFragment(FragmentMyPage())
                true
            }
            else -> false
        }
    }
}