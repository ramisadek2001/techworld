package com.lau.techworld.fragments

import android.content.Intent
import android.icu.lang.UCharacter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.lau.techworld.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productArraylist : ArrayList<Product>
    val fauth = FirebaseAuth.getInstance()
    val fstore = FirebaseFirestore.getInstance()
    val userID = fauth.currentUser!!.uid

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
        val view: View =
            inflater.inflate(com.lau.techworld.R.layout.fragment_cart, container, false)
        val image1 = view.findViewById<View>(R.id.sal)
        val image2 = view.findViewById<View>(R.id.no)


        val Checkout = view.findViewById<View>(R.id.Checkout) as AppCompatButton
        Checkout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(context, checkout::class.java)
                startActivity(intent)
            }

        })


        productRecyclerView = view.findViewById<View>(com.lau.techworld.R.id.recyclercart) as RecyclerView

        productRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        productRecyclerView.setHasFixedSize(true)


//
        productArraylist = arrayListOf<Product>()

        fstore.collection("Favorites").document(userID).collection("CurrentUser").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                for (documentSnapshot : DocumentSnapshot in task.getResult().documents ){
                    if (documentSnapshot.getString("inCart").toString() == "true"){
                        val product : Product = documentSnapshot.toObject(Product :: class.java)!!
                        image1.isVisible = false
                        image2.isVisible = false
                        productRecyclerView.isVisible = true
                        productArraylist.add(product)
                        var adapter = cartAdapter(productArraylist)
                        productRecyclerView.adapter = adapter

                        adapter.setOnItemClickListener(object : cartAdapter.onItemClickListener{
                            override fun OnItemClick(position: Int) {
                                val intent = Intent(context, Product_Page::class.java)
                                intent.putExtra("title", productArraylist[position].title)
                                intent.putExtra("price", productArraylist[position].price.toString())
                                intent.putExtra("image", productArraylist[position].image)
                                intent.putExtra("desc", productArraylist[position].desc)

                                startActivity(intent)
                            }

                        })



//                        adapter.setOnPClickListener(object : cartAdapter.onPlusClickListener{
//                            override fun onpBtnClick(position: Int) {
//                                val df: DocumentReference =fstore.collection("Favorites").document(userID).collection("CurrentUser").document(productArraylist[position].title.toString())
//                                val quant = productArraylist[position].quantity!!.toInt()
//                                quant + 1
//                                df.update("quantity",quant.toString() )
//                            }
//                        })
//                        adapter.setOnMClickListener(object : cartAdapter.onMinusClickListener{
//                            override fun onmBtnClick(position: Int) {
//                                val df: DocumentReference =fstore.collection("Favorites").document(userID).collection("CurrentUser").document(productArraylist[position].title.toString())
//                                val quant = productArraylist[position].quantity!!.toInt()
//                                quant - 1
//                                df.update("quantity",quant.toString() )
//                                if (quant == 0){
//                                    df.update("inCart","false")
//                                }
//                            }


//                        })
                    }


                }
                if (productArraylist.isEmpty()){
                    productRecyclerView.isVisible = false

                    image1.isVisible = true
                    image2.isVisible = true
                }
            }


        }




        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


