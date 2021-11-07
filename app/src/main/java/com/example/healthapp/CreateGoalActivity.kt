package com.example.healthapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapp.databinding.ActivityCreateGoalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_goal.*
import kotlinx.android.synthetic.main.item_goal.*

class CreateGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // access the items of the list
        val languages = resources.getStringArray(R.array.goalChoices)

        //add goal button
        binding.AddGoalButton.setOnClickListener {
            if (binding.goalName.text.toString().isNotEmpty() && binding.goalName.text.toString()
                    .isNotEmpty()
            ) {
                //creating goal object
                val goal = Goal()
                goal.userId = FirebaseAuth.getInstance().currentUser!!.uid
                goal.name = binding.goalName.text.toString()
                goal.description = binding.goalDescription.text.toString()
                goal.number = binding.goalNumber.text.toString()


                //Adding goal to collection in firebase
                val db = FirebaseFirestore.getInstance().collection("goals")
                goal.id = db.document().id

                db.document(goal.id!!).set(goal)
                    .addOnSuccessListener {
                        //show confirmation and clear the fields
                        Toast.makeText(this, "Goal Added", Toast.LENGTH_LONG).show()
                        binding.goalName.setText("")
                        binding.goalDescription.setText("")
                        binding.goalNumber.setText("")
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Goal Title and Description Needed", Toast.LENGTH_LONG).show()

            }

        }
    }
}