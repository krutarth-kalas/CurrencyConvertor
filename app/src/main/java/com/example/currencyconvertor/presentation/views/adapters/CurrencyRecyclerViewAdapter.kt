package com.example.currencyconvertor.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertor.data.model.CurrencyNameValue
import com.example.currencyconvertor.databinding.RecyclerItemBinding

class CurrencyRecyclerViewAdapter(private val items : List<CurrencyNameValue>) :
    RecyclerView.Adapter<CurrencyRecyclerViewAdapter.CurrencyViewHolder>() {
    data class CurrencyViewHolder(val binding : RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.binding.name.text = items[position].currencyName
        holder.binding.sign.text = items[position].currencySymbol
        holder.binding.value.text = items[position].currencyValue.toString()
    }

}