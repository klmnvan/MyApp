package com.example.myapplication.api

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCatalogBinding
import com.example.myapplication.models.Catalog

class AdapterCatalog : RecyclerView.Adapter<AdapterCatalog.Holder>() {
    private var listCatalog = ArrayList<Catalog>()

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemCatalogBinding.bind(item)

        @SuppressLint("SetTextI18n")
        fun bind(catalog: Catalog) {
            with(binding) {
                tName.text = catalog.id.toString() + ". " + catalog.name
                tDescription.text = catalog.description
                tBiomaterial.text = catalog.bio
                tCategory.text = catalog.category
                tPodgotovka.text = catalog.preparation
                tPrice.text = catalog.price.toString() + " р."
                tResults.text = catalog.timeResul
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listCatalog[position])
    }

    override fun getItemCount(): Int = listCatalog.size

    @SuppressLint("NotifyDataSetChanged")
    fun addCatalog(catalog: Catalog) {
        listCatalog.add(catalog)
        //Указывает адаптеру, что полученные ранее данные изменились и следует
        //перерисовать список на экране.
        notifyDataSetChanged()
    }

}