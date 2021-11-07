package com.example.healthapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

private val client = OkHttpClient()

class GoalsViewAdapter(
    val context: Context,
    val goals: List<Goal>

) : RecyclerView.Adapter<GoalsViewAdapter.GoalViewHolder>() {
    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
        val goalNumberTextView = itemView.findViewById<TextView>(R.id.goalNumberTextView)
        val deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

    }

    /*Get item count*/
    override fun getItemCount(): Int {
        return goals.size

    }


    /*Function to create viewholder*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    /*Function to bind view holder*/
    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        with(holder) {
            nameTextView.text = goal.name
            descriptionTextView.text = goal.description
            goalNumberTextView.text = goal.number
            deleteButton.setOnClickListener {
                val id =goal.id
                FirebaseFirestore.getInstance().collection("goals").document(id!!).delete()
            }

        }
    }
}