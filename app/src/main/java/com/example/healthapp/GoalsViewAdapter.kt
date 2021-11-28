package com.example.healthapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.model.Goal
import com.google.firebase.firestore.FirebaseFirestore


class GoalsViewAdapter(
    val context: Context,
    val goals: List<Goal>

) : RecyclerView.Adapter<GoalsViewAdapter.GoalViewHolder>() {
    inner class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalCheckBox = itemView.findViewById<TextView>(R.id.goalCheckBox)
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
            goalCheckBox.text = goal.name
            deleteButton.setOnClickListener {
                val id =goal.id
                FirebaseFirestore.getInstance().collection("goals").document(id!!).delete()
            }
        }
    }
}