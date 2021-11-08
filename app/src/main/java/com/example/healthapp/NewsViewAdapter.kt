package com.example.healthapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(
    private var titles: List<String>,
    private var names: List<String>,
    private var images: List<String>,
    private var links: List<String>
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemName: TextView = itemView.findViewById(R.id.source)
        val itemImage: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemName.text = names[position]

        Glide.with(holder.itemImage)
            .load(images[position])
            .into(holder.itemImage)
    }
}