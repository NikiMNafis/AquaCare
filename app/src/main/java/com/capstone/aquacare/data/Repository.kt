package com.capstone.aquacare.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class Repository {

    private val userDatabase = FirebaseDatabase.getInstance().getReference("users")
    private val articleDatabase = FirebaseDatabase.getInstance().getReference("article")

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

    suspend fun getArticleData(): List<ArticleData> {
        return try {
            val dataSnapshot = articleDatabase.get().await()
            dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.getValue(ArticleData::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getIdentificationData(userId : String, aquascapeId : String): List<IdentificationData> {
        return try {
            val dataSnapshot = userDatabase.child(userId).child("aquascapes").child(aquascapeId).child("identification").get().await()
            dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.getValue(IdentificationData::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}