package com.example.projectesmian


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectesmian.databinding.ActivityHomeBinding
import com.example.projectesmian.databinding.ActivityDeskripsiBinding
//import com.example.projectesmiandatabinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var dataRecyclerView: RecyclerView

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var  klubAdapter: ClubAdapter
    private lateinit var listKlub: MutableList<DataClub>


    private  var  mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        dataRecyclerView = findViewById(R.id.isiData)

        dataRecyclerView.setHasFixedSize(true)

        dataRecyclerView.layoutManager = LinearLayoutManager(this@Home)

        binding.myDataLoaderprogressBar.visibility = View.VISIBLE

        listKlub = ArrayList()
        klubAdapter = ClubAdapter(this@Home, listKlub)
        dataRecyclerView.adapter = klubAdapter
//
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dclub")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Home, error.message, Toast.LENGTH_SHORT).show()
                binding.myDataLoaderprogressBar.visibility = View.VISIBLE
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listKlub.clear()
                for (teacherSnapshot in snapshot.children) {
                    val upload = teacherSnapshot.getValue(DataClub::class.java)
                    upload!!.key = teacherSnapshot.key
                    listKlub.add(upload)
                }

                klubAdapter.notifyDataSetChanged()
                binding.myDataLoaderprogressBar.visibility = View.GONE
            }

        })

        binding.imgLog.setOnClickListener {
            firebaseAuth.signOut()

            Intent(this,  MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }

        }

    }
}

