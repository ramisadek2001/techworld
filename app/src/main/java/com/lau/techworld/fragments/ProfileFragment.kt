package com.lau.techworld.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.SnapshotMetadata
import com.lau.techworld.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var Fname : TextView
lateinit var Pnumber : TextView
lateinit var Pemail : TextView

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(com.lau.techworld.R.layout.fragment_profile, container, false)

        Fname = view.findViewById<TextView>(R.id.Pname) as TextView
        Pnumber = view.findViewById<TextView>(R.id.Pphone) as TextView
        Pemail = view.findViewById<TextView>(R.id.Pemail) as TextView
        val Paddress = view.findViewById<TextView>(R.id.Paddress) as TextView
        val fauth = FirebaseAuth.getInstance()
        val fstore = FirebaseFirestore.getInstance()

        val userID = fauth.currentUser!!.uid

        val df : DocumentReference = fstore.collection("users").document(userID)

        df.addSnapshotListener(MetadataChanges.INCLUDE){ snapshot, e ->

            Fname.setText(snapshot!!.getString("fName"))
            Pnumber.setText(snapshot!!.getString("phone"))
            Pemail.setText(snapshot!!.getString("email"))
            Paddress.setText(snapshot!!.getString("addtitle"))


        }

        val editbtn = view.findViewById<AppCompatButton>(R.id.Editbtn)

        editbtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(activity, EditProfile::class.java)
                startActivity(intent)
            }


        })
        val orderbtn = view.findViewById<AppCompatButton>(R.id.Orderhist)

        orderbtn.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(activity, Orders::class.java)
                startActivity(intent)
            }

        })

        val address = view.findViewById<AppCompatButton>(R.id.addressbtn)

        address.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(activity, Address_Page::class.java)
                startActivity(intent)
            }


        })


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}