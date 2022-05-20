package com.lau.techworld

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ordersAdapter(private val orderList : ArrayList<order>) : RecyclerView.Adapter<ordersAdapter.MyViewHolder>() {


    interface  onItemClickListener{
        fun OnItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orders,parent,false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentorder = orderList[position]



        if (currentorder.delivered.equals("false")){
            holder.status.setTextColor(Color.parseColor("#FF0000"))
            holder.status.text = "not delivered"
        }
        holder.orderdet.text = currentorder.order


    }

    override fun getItemCount(): Int {
        return orderList.size
    }
    inner  class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val orderdet : TextView = itemView.findViewById(R.id.order)
        val status : TextView = itemView.findViewById(R.id.status)






    }

}

