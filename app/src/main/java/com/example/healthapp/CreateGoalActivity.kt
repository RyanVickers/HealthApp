package com.example.healthapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapp.databinding.ActivityCreateGoalBinding
import com.example.healthapp.model.Goal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //add goal button
        binding.AddGoalButton.setOnClickListener {
            if (binding.goalName.text.toString().isNotEmpty()
            ) {
                //creating goal object
                val goal = Goal()
                goal.userId = FirebaseAuth.getInstance().currentUser!!.uid
                goal.name = binding.goalName.text.toString()


                //Adding goal to collection in firebase
                val db = FirebaseFirestore.getInstance().collection("goals")
                goal.id = db.document().id

                db.document(goal.id!!).set(goal)
                    .addOnSuccessListener {
                        //show confirmation and clear the fields
                        Toast.makeText(this, "Goal Added", Toast.LENGTH_LONG).show()
                        binding.goalName.setText("")

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "Goal Title Needed", Toast.LENGTH_LONG).show()

            }

        }
    }
}