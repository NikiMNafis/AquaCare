package com.capstone.aquacare.data

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class Repository {

    private val userDatabase = FirebaseDatabase.getInstance().getReference("users")

    suspend fun getAquascapeData(userId: String): List<AquascapeData> {
        return try {
            val dataSnapshot = userDatabase.child(userId).child("aquascapes").get().await()
            dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.getValue(AquascapeData::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}