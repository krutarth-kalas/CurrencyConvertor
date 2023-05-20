package com.example.currencyconvertor.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import com.example.currencyconvertor.R
import com.example.currencyconvertor.databinding.ActivityMainBinding
import com.example.currencyconvertor.presentation.views.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.pager.adapter = ViewPagerAdapter(listOf(CurrencyRateFragment(),CurrencyConvertorFragment()),
            supportFragmentManager,this.lifecycle)
        TabLayoutMediator(binding.tabLayout,binding.pager){tab,position ->
            tab.text = when(position){
                0 -> resources.getString(R.string.curr_rate)
                else -> resources.getString(R.string.curr_conv)
            }
        }.attach()
    }
}