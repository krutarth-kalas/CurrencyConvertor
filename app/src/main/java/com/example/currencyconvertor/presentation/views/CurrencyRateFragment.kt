package com.example.currencyconvertor.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconvertor.R
import com.example.currencyconvertor.databinding.FragmentCurrencyRateBinding
import com.example.currencyconvertor.presentation.viewmodels.CurrencyRateViewModel
import com.example.currencyconvertor.presentation.views.adapters.CurrencyRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private lateinit var binding :  FragmentCurrencyRateBinding

@AndroidEntryPoint
class CurrencyRateFragment : Fragment() {
    private val viewModel : CurrencyRateViewModel by activityViewModels()
    private lateinit var adapter: CurrencyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCurrencyRateBinding.inflate(inflater,container,false)
        adapter = CurrencyRecyclerViewAdapter(viewModel.adapterList)
        binding.rvCurr.adapter = adapter
        binding.rvCurr.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loaderState.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                binding.pb.visibility = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorVisible.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                binding.tvError.visibility = it
            }
        }

        viewModel.notifyCallback = {
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.startObserve(viewLifecycleOwner)
    }
}