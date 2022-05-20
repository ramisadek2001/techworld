package com.lau.techworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(private val productList : ArrayList<Product>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

     private lateinit var   mlistener: onItemClickListener
     interface  onItemClickListener{
        fun OnItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_shop,parent,false)
        return MyViewHolder(itemView, mlistener)
    }


    fun setOnItemClickListener(listener : onItemClickListener){

        mlistener = listener

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = productList[position]



        holder.title.text = currentitem.title
        holder.price.text = currentitem.price.toString()+"$"
        Picasso.get().load(currentitem.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
  inner  class MyViewHolder(itemView : View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val title : TextView = itemView.findViewById(R.id.title)
        val price : TextView = itemView.findViewById(R.id.price)
        val image : ImageView = itemView.findViewById(R.id.imgCircle)

        init {
            itemView.setOnClickListener {
                listener.OnItemClick(adapterPosition)
            }
        }



        }

    }

