package com.lau.techworld

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.getField
import com.lau.techworld.fragments.Fname
import com.lau.techworld.fragments.Pemail
import com.lau.techworld.fragments.Pnumber
import com.squareup.picasso.Picasso

class Product_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val Ptitle : TextView = findViewById(R.id.Ptitle)
        val Pprice : TextView = findViewById(R.id.Pprice)
        val Pimage : ImageView = findViewById(R.id.Pimage)
        val Pdesc : TextView = findViewById(R.id.desc)


        val bundle : Bundle ?= intent.extras
        Pimage.clipToOutline=true
        val title = bundle!!.getString("title")
        val price = bundle!!.getString("price")
        val image = bundle!!.getString("image")
        val desc = bundle!!.getString("desc")
        val fauth = FirebaseAuth.getInstance()
        val fstore = FirebaseFirestore.getInstance()
        val userID = fauth.currentUser!!.uid
        Ptitle.text = title
        Pprice.text = price+"$"
        Pdesc.text = desc
        var isInCart : String = "false"

        val df: DocumentReference =fstore.collection("Favorites").document(userID).collection("CurrentUser").document(title.toString())

        var isfavourite : String = "false"
        val favbtn = findViewById<ImageView>(R.id.favbtn)
        var docID : String = ""
        val cart : AppCompatButton = findViewById<AppCompatButton>(R.id.Basketbtn)

        df.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    if (task.result.getString("favorite").toString() == "true") {
                        isfavourite = "true"
                        favbtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        isfavourite = "false"
                        favbtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                    if(task.result.getString("inCart").toString() == "true"){
                        isInCart = "true"
                        cart.setText("Remove form cart")
                    }else{
                        isInCart = "false"
                        cart.setText("Add to cart")

                    }

                }
            }
        }




        cart.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if (isInCart.equals("false")){

                    isInCart = "true"

                    val product = hashMapOf<String, String>()
                    if (title != null) {
                        product.put("title",title)
                    }
                    if (price != null) {
                        product.put("price", price)
                    }
                    if (image != null) {
                        product.put("image",image)
                    }
                    if (desc != null) {
                        product.put("desc",desc)
                    }

                    product.put("total","0")
                    product.put("inCart", "true")
                    product.put("quantity", "1")
                    cart.setText("Remove from cart")

                    df.set(product)
                }else{

                    cart.setText("Add to cart")
                    df.update("inCart","false").addOnSuccessListener {
                        Toast.makeText(applicationContext, "successfully removed", Toast.LENGTH_SHORT).show()
                    }
                    df.update("quantity","0").addOnSuccessListener {
                        Toast.makeText(applicationContext, "successfully removed", Toast.LENGTH_SHORT).show()
                    }


                    isInCart = "false"


                }
            }


        })



        Picasso.get().load(image).resize(800, 800).centerCrop().into(Pimage)

        val back = findViewById<ImageView>(R.id.leftIc)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })

        favbtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                if (isfavourite.equals("false")){

                    isfavourite = "true"

                    val product = hashMapOf<String, String>()
                    if (title != null) {
                        product.put("title",title)
                    }
                    if (price != null) {
                        product.put("price", price)
                    }
                    if (image != null) {
                        product.put("image",image)
                    }
                        if (desc != null) {
                            product.put("desc",desc)
                        }

                    product.put("favorite", "true")
                    favbtn.setImageResource(R.drawable.ic_baseline_favorite_24)

             df.set(product)
                }else{
                    favbtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    df.update("favorite","false").addOnSuccessListener {
                        Toast.makeText(applicationContext, "successfully removed", Toast.LENGTH_SHORT).show()
                    }

                    isfavourite = "false"

                }

                favbtn.backgroundTintMode
            }


        })

    }
}