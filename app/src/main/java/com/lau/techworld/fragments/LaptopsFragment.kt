package com.lau.techworld.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lau.techworld.MyAdapter
import com.lau.techworld.Product
import com.lau.techworld.Product_Page
import com.lau.techworld.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var productRecyclerView: RecyclerView
private lateinit var productArraylist : ArrayList<Product>
private val dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/laptops")

/**
 * A simple [Fragment] subclass.
 * Use the [LaptopsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaptopsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(



        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(com.lau.techworld.R.layout.fragment_laptops, container, false)



        productRecyclerView = view.findViewById<View>(com.lau.techworld.R.id.recyclerlap) as RecyclerView
        productRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        productRecyclerView.setHasFixedSize(true)


//
        productArraylist = arrayListOf<Product>()
        getProductData()
        return view
    }
    public fun getProductData(){

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){


                        val product = productSnapshot.getValue(Product::class.java)
                        productArraylist.add(product!!)


                    }
                    var adapter =  MyAdapter(productArraylist)
                    productRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                        override fun OnItemClick(position: Int) {
                            val intent = Intent(context, Product_Page::class.java)
                            intent.putExtra("title", productArraylist[position].title)
                            intent.putExtra("price", productArraylist[position].price.toString())
                            intent.putExtra("image", productArraylist[position].image)
                            intent.putExtra("desc", productArraylist[position].desc)
                            startActivity(intent)
                        }

                    })
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LaptopsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LaptopsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}