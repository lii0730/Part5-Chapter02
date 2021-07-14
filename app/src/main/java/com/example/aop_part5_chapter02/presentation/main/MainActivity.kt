package com.example.aop_part5_chapter02.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.aop_part5_chapter02.R
import com.example.aop_part5_chapter02.databinding.ActivityMainBinding
import com.example.aop_part5_chapter02.databinding.FragmentMyBinding
import com.example.aop_part5_chapter02.presentation.BaseActivity
import com.example.aop_part5_chapter02.presentation.BaseFragment
import com.example.aop_part5_chapter02.presentation.Home.FragmentHome
import com.example.aop_part5_chapter02.presentation.MyPage.FragmentMyPage
import com.example.aop_part5_chapter02.presentation.MyPage.FragmentMyPageViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModel

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val viewModel: MainViewModel by viewModel()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        changeFragment(FragmentHome(), FragmentHome.TAG)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_home -> {
                changeFragment(FragmentHome(), FragmentHome.TAG)
               true
            }
            R.id.menu_myPage -> {
                viewModel.refreshOrderList()
                changeFragment(FragmentMyPage(), FragmentMyPage.TAG)
                true
            }
            else -> false
        }
    }

    private fun changeFragment(fragment: Fragment, tag: String) {

        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }

    }

    override fun observeData() {
        viewModel.mainStateLiveData.observe(this) {
            when(it) {
                is MainState.RefreshOrderList -> {
                    val fragment = supportFragmentManager.findFragmentByTag(FragmentMyPage.TAG)
                    (fragment as? BaseFragment<*, *>)?.viewModel?.fetchData()
                }
            }
        }
    }
}