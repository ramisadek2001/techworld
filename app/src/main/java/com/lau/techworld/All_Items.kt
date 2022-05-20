package com.lau.techworld

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


private lateinit var productArraylist : ArrayList<Product>
private lateinit var tempproductArraylist : ArrayList<Product>
private val dbref1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/mobile")
private val dbref2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/tablets")
private val dbref3 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/laptops")
private val dbref4 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/cameras")
private lateinit var productRecyclerView: RecyclerView

class All_Items : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_items)



        val back = findViewById<ImageView>(R.id.leftIcon)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })




         productRecyclerView = findViewById<RecyclerView>(com.lau.techworld.R.id.recyclerall)
        productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        productRecyclerView.setHasFixedSize(true)


         productArraylist = arrayListOf<Product>()
        tempproductArraylist = arrayListOf<Product>()

        getProductData(applicationContext)
        val searchview = findViewById<SearchView>(R.id.search_V)


        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempproductArraylist.clear()
                val SearchText = newText!!.lowercase(Locale.getDefault())

                if (SearchText.isNotEmpty()){

                    productArraylist.forEach{

                        if(it.title?.lowercase(Locale.getDefault())!!.contains(SearchText)){

                            tempproductArraylist.add(it)

                        }

                    }
                    productRecyclerView.adapter!!.notifyDataSetChanged()
                    var adapter = MyAdapter(tempproductArraylist)
                   productRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                        override fun OnItemClick(position: Int) {
                            val intent = Intent(applicationContext,Product_Page::class.java)
                            intent.putExtra("title", tempproductArraylist[position].title)
                            intent.putExtra("price", tempproductArraylist[position].price.toString())
                            intent.putExtra("image", tempproductArraylist[position].image)
                            intent.putExtra("desc", tempproductArraylist[position].desc)

                            startActivity(intent)
                        }

                    })
                }else{
                    tempproductArraylist.clear()

                    tempproductArraylist.addAll(productArraylist)
                    productRecyclerView.adapter!!.notifyDataSetChanged()
                }




                return false

            }

        })

    }




}


public fun getProductData(context: Context) {

    dbref1.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if(snapshot.exists()){
                for(productSnapshot in snapshot.children){


                    val product = productSnapshot.getValue(Product::class.java)
                    productArraylist.add(product!!)


                }
                var adapter = MyAdapter(productArraylist)
                productRecyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun OnItemClick(position: Int) {
                        val intent = Intent(context,Product_Page::class.java)
                        intent.putExtra("title", productArraylist[position].title)
                        intent.putExtra("price", productArraylist[position].price.toString())
                        intent.putExtra("image", productArraylist[position].image)
                        intent.putExtra("desc", productArraylist[position].desc)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      context.startActivity(intent)
                    }

                })
            }


        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    dbref2.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if(snapshot.exists()){
                for(productSnapshot in snapshot.children){


                    val product = productSnapshot.getValue(Product::class.java)
                    productArraylist.add(product!!)


                }
                var adapter = MyAdapter(productArraylist)
                productRecyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun OnItemClick(position: Int) {
                        val intent = Intent(context,Product_Page::class.java)
                        intent.putExtra("title", productArraylist[position].title)
                        intent.putExtra("price", productArraylist[position].price.toString())
                        intent.putExtra("image", productArraylist[position].image)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent)
                    }

                })
            }


        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    dbref3.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if(snapshot.exists()){
                for(productSnapshot in snapshot.children){


                    val product = productSnapshot.getValue(Product::class.java)
                    productArraylist.add(product!!)


                }
                var adapter = MyAdapter(productArraylist)
                productRecyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun OnItemClick(position: Int) {
                        val intent = Intent(context,Product_Page::class.java)
                        intent.putExtra("title", productArraylist[position].title)
                        intent.putExtra("price", productArraylist[position].price.toString())
                        intent.putExtra("image", productArraylist[position].image)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent)
                    }

                })
            }


        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    dbref4.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            if(snapshot.exists()){
                for(productSnapshot in snapshot.children){


                    val product = productSnapshot.getValue(Product::class.java)
                    productArraylist.add(product!!)


                }
                var adapter = MyAdapter(productArraylist)
                productRecyclerView.adapter = adapter
                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun OnItemClick(position: Int) {
                        val intent = Intent(context,Product_Page::class.java)
                        intent.putExtra("title", productArraylist[position].title)
                        intent.putExtra("price", productArraylist[position].price.toString())
                        intent.putExtra("image", productArraylist[position].image)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent)
                    }

                })
            }


        }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    )

    tempproductArraylist.addAll(productArraylist)

}


