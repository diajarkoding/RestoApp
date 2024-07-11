package com.example.restoapp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restoapp.databinding.ItemRowRestoBinding
import com.example.restoapp.model.Resto
import com.example.restoapp.ui.DetailActivity

class ListRestoAdapter(private val listRestos: List<Resto>) : RecyclerView.Adapter<ListRestoAdapter.ListViewHolder>() {
    class ListViewHolder(var binidng: ItemRowRestoBinding) : RecyclerView.ViewHolder(binidng.root)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowRestoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listRestos.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val resto = listRestos[position]
        Log.d("ListRestoAdapter", "Resto photo ID: ${resto.pictureId}")
        Glide.with(holder.itemView.context).load(resto.pictureId).into(holder.binidng.imgItemPhoto)
        holder.binidng.tvItemName.text = resto.name
        holder.binidng.tvRating.text = resto.rating.toString()
        holder.binidng.tvItemDescription.text = resto.description
        holder.itemView.setOnClickListener {
            var goDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            goDetail.putExtra(DetailActivity.EXTRA_RESTO, resto)
            holder.itemView.context.startActivity(goDetail)
        }
    }

}