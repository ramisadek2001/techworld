package com.lau.techworld

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.lau.techworld.fragments.fstore
import com.lau.techworld.fragments.userID
import com.squareup.picasso.Picasso
import kotlin.math.log
import kotlin.properties.Delegates

class cartAdapter(private val productList : ArrayList<Product>) : RecyclerView.Adapter<cartAdapter.ThisViewHolder>() {

    private lateinit var   plistener: onItemClickListener
    var position by Delegates.notNull<Int>()
lateinit var plusbtn : AppCompatButton
lateinit var minusbtn : AppCompatButton
lateinit var quant : TextView

 var totalprice : Int = 0
    interface  onItemClickListener{
        fun OnItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThisViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)

         plusbtn = itemView.findViewById<AppCompatButton>(R.id.plus)
         minusbtn = itemView.findViewById<AppCompatButton>(R.id.minus)
         quant = itemView.findViewById<TextView>(R.id.quantity)





        return ThisViewHolder(itemView, plistener,plusbtn,minusbtn)
    }

    fun setOnItemClickListener(listener : onItemClickListener){

        plistener = listener

    }





    override fun getItemCount(): Int {
        return productList.size
    }
    inner  class ThisViewHolder(itemView : View, listener: cartAdapter.onItemClickListener,plus : AppCompatButton,minus : AppCompatButton) : RecyclerView.ViewHolder(itemView) {

        val title : TextView = itemView.findViewById(R.id.Ctitle)
        var price : TextView = itemView.findViewById(R.id.Cprice)
        val image : ImageView = itemView.findViewById(R.id.CimgCircle)
        val quant : TextView = itemView.findViewById(R.id.quantity)




        init {
            itemView.setOnClickListener {
                listener.OnItemClick(adapterPosition)
            }





            var pricedb : Int = 0
            plusbtn.setOnClickListener(View.OnClickListener {


                val quanttxt =quant.text.toString()

                var num = quanttxt.toInt()
                if (num <10) {

                    var pricenew = price.text.toString().split(regex = Regex("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"))
                    val pricenw = pricenew[0].toInt()
                    var pricet = pricenw + pricenw/num
                    num = num + 1
                    price.setText(pricet.toString() + "$")
                    totalprice = totalprice + pricet
                    quant.setText(num.toString())
                    pricedb = pricenw
                }
                val df: DocumentReference =fstore.collection("Favorites").document(userID).collection("CurrentUser").document(productList[adapterPosition].title.toString())

                df.update("quantity",num.toString() )

                df.update("price",pricedb.toString() )
            })
            minusbtn.setOnClickListener(View.OnClickListener {
                val quanttxt =quant.text.toString()
                var num = quanttxt.toInt()
                if (num != 0) {
                    var pricenew = price.text.toString().split(regex = Regex("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"))
                    val pricenw = pricenew[0].toInt()
                    var pricet = pricenw - pricenw/num
                    num = num - 1
                    price.setText(pricet.toString()+"$")
                    totalprice = totalprice - pricet

                    quant.setText(num.toString())
                    pricedb = pricenw
                }
                val df: DocumentReference =fstore.collection("Favorites").document(userID).collection("CurrentUser").document(productList[adapterPosition].title.toString())

                df.update("quantity",num.toString() )
                df.update("price",pricedb.toString() )



                if(num == 0){
                    df.update("inCart",false.toString())
                }

            })



        }




    }

    override fun onBindViewHolder(holder: cartAdapter.ThisViewHolder, position: Int) {
        val currentitem = productList[position]

        holder.title.text = currentitem.title
        holder.price.text = currentitem.price.toString()+"$"
        holder.quant.text = currentitem.quantity.toString()
        Picasso.get().load(currentitem.image).into(holder.image)



    }
}