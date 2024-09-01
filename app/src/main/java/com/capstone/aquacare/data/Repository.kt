package com.capstone.aquacare.data

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

    suspend fun addNewAquascape(userId: String, aquascapeData: AquascapeData): Result<Unit> {
        return try {
            val aquascapeReference = userDatabase.child(userId).child("aquascapes")
            val newAquascapeId = aquascapeReference.push().key

            if (newAquascapeId != null) {
                aquascapeData.id = newAquascapeId
                aquascapeReference.child(newAquascapeId).setValue(aquascapeData).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to generate Aquascape Id"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun editAquascape(userId: String, aquascapeId: String, name: String, style: String): Result<Unit> {
        return try {
            val aquascapeReference = userDatabase.child(userId).child("aquascapes")
            val dataSnapshot = aquascapeReference.orderByChild("id").equalTo(aquascapeId).get().await()

            if (dataSnapshot.exists()) {
                for (snapshot in dataSnapshot.children) {
                    val aquascapeData = snapshot.getValue(AquascapeData::class.java)
                    if (aquascapeData != null) {
                        val createDate = aquascapeData.createDate.toString()

                        val updateData = mapOf("name" to name, "style" to style, "createDate" to createDate)
                        aquascapeReference.child(aquascapeId).updateChildren(updateData).await()
                        return Result.success(Unit)
                    }
                }
            }
            Result.failure(Exception("No aquascape data"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAquascape(userId: String, aquascapeId: String): Result<Unit> {
        return try {
            userDatabase.child(userId).child("aquascapes").child(aquascapeId).removeValue().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
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

    suspend fun addNewIdentification(userId: String, aquascapeId: String, identificationData: IdentificationData): Result<Unit> {
        return try {
            val identificationReference = userDatabase.child(userId).child("aquascapes").child(aquascapeId).child("identification")
            val newIdentificationId = identificationReference.push().key

            if (newIdentificationId != null) {
                identificationData.id = newIdentificationId
                identificationReference.child(newIdentificationId).setValue(identificationData).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to generate Identification Id"))
            }
        } catch (e: Exception) {
            Result.failure(e)
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

    suspend fun addArticle(articleData: ArticleData): Result<Unit> {
        return try {
            val newArticleId = articleDatabase.push().key

            if (newArticleId != null) {
                articleData.id = newArticleId
                articleDatabase.child(newArticleId).setValue(articleData).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to generate Aquascape Id"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteArticle(articleId: String): Result<Unit> {
        return try {
            articleDatabase.child(articleId).removeValue().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}