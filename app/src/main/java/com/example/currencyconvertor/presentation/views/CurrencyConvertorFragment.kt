package com.example.currencyconvertor.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvertor.R
import com.example.currencyconvertor.databinding.FragmentCurrencyConvertorBinding
import com.example.currencyconvertor.domain.statesandevents.Events
import com.example.currencyconvertor.presentation.viewmodels.CurrencyRateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrencyConvertorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CurrencyConvertorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel : CurrencyRateViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrencyConvertorBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyConvertorBinding.inflate(inflater,container,false)
        binding.etAmount.doOnTextChanged { text, start, before, count ->
            viewModel.events(Events.AMOUNT_ENTER(text.toString()))
        }

        adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.symbols
        )

        binding.spnFrom.adapter = adapter
        binding.spnTo.adapter = adapter

        binding.spnTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.events(Events.TO_SELECTED(viewModel.symbols[p2]))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spnFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.events(Events.FROM_SELECTED(viewModel.symbols[p2]))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        viewModel.symbolsCallback = {
            adapter.notifyDataSetChanged()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.conversionText.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                binding.tvValue.text = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.amountPb.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                binding.pbAmount.visibility = it
            }
        }

        return binding.root
    }

}