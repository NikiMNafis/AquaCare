package com.capstone.aquacare.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.MainActivity
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
    }

    override fun onStart() {
        super.onStart()
        checkUserLoginStatus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                if (checkForm()) {
//                    submitForm()

                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()

                    signInUser(email, password)
                }
            }

            tvSignup.setOnClickListener{
                val signUpFragment = SignUpFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(
                        R.id.auth_frame_container,
                        signUpFragment,
                        SignUpFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
            }

            btnLoginGoogle.setOnClickListener{
                signInGoogle()
            }

        }

    }

    private fun checkForm(): Boolean {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Enter Email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

//    private fun signInAuth() {
//        val email = binding.edtEmail.text.toString()
//        val password = binding.edtPassword.text.toString()
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success")
//                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
//                    val user = auth.currentUser
//                    val id = user?.uid.toString()
//                    val name = user?.displayName.toString()
//                    val email = user?.email.toString()
//                    val photo = user?.photoUrl.toString()
//
//                    saveLoginSession(id, name, email, photo)
//
//                    startActivity(Intent(activity, MainActivity::class.java))
//                    requireActivity().finish()
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        activity,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                }
//            }
//
//    }

    private fun signInUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if (userData != null && userData.password == password){
                            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()

                            val id = userData.id.toString()
                            val name = userData.name.toString()
                            val email = userData.email.toString()
                            val photo = ""

                            saveLoginSession(id, name, email, photo)

                            startActivity(Intent(activity, MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "Account not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defaultweb_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
//                    val userId = user?.uid.toString()
                    val name = user?.displayName.toString()
                    val email = user?.email.toString()
                    val photo = user?.photoUrl.toString()

                    val password = "Google Account"

                    databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                val id = databaseReference.push().key
                                val userData = UserData(id, name, email, password)
                                databaseReference.child(id!!).setValue(userData)

                                saveLoginSession(id, name, email, photo)

                                Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(activity, MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                for (userSnapshot in dataSnapshot.children) {
                                    val userData = userSnapshot.getValue(UserData::class.java)

                                    val id = userData?.id.toString()
                                    val name = userData?.name.toString()
                                    val email = userData?.email.toString()
                                    val photo = ""

                                    saveLoginSession(id, name, email, photo)

                                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(activity, MainActivity::class.java))
                                    requireActivity().finish()

                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                        }
                    })

//                    startActivity(Intent(activity, MainActivity::class.java))
//                    requireActivity().finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun saveLoginSession(userId: String, name: String, email: String, photo: String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("userId", userId)
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("photo", photo)
        editor?.apply()
    }

    private fun checkUserLoginStatus() {
        val currentUser = auth.currentUser
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "")
        val id = sharedPreferences?.getString("userId", "")
        if (currentUser != null || id != "") {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }


    companion object {
        private const val TAG = "AuthActivity"
        private const val RC_SIGN_IN = 9001

    }
}
