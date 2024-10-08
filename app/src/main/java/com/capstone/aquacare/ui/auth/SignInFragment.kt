package com.capstone.aquacare.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.capstone.aquacare.ui.MainActivity
import com.capstone.aquacare.R
import com.capstone.aquacare.data.UserData
import com.capstone.aquacare.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                if (checkForm()) {
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()

//                    signInUser(email, password)
                    signInAuth(email, password)
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

            tvForgotPassword.setOnClickListener{
                val email = binding.edtEmail.text.toString()
                if (email.isEmpty()) {
                    Toast.makeText(activity, getString(R.string.please_enter_email), Toast.LENGTH_SHORT).show()
                } else {
                    forgotPassword(email)
                }
            }

        }

    }

    private fun checkForm(): Boolean {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.edtEmail.error = getString(R.string.please_enter_email)
            return false
        }

        if (TextUtils.isEmpty(password)) {
            binding.edtPassword.error = getString(R.string.please_enter_password)
            return false
        }

        return true
    }

    private fun signInAuth(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
//                    val id = user?.uid.toString()
                    val name = user?.displayName.toString()
//                    val email = user?.email.toString()
                    val photo = ""

                    val userType = "user"

                    val accountType = "Regular Account"

                    databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                val id = databaseReference.push().key
                                val userData = UserData(id, name, email, accountType, userType)
                                databaseReference.child(id!!).setValue(userData)

                                saveLoginSession(id, name, email, photo, userType, accountType)

                                Toast.makeText(activity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(activity, MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                for (userSnapshot in dataSnapshot.children) {
                                    val userData = userSnapshot.getValue(UserData::class.java)

                                    val id = userData?.id.toString()
                                    val name = userData?.name.toString()
                                    val email = userData?.email.toString()
                                    val userType = userData?.userType.toString()

                                    saveLoginSession(id, name, email, photo, userType, accountType)

                                    Toast.makeText(activity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(activity, MainActivity::class.java))
                                    requireActivity().finish()

                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
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
                    val name = user?.displayName.toString()
                    val email = user?.email.toString()
                    val photo = user?.photoUrl.toString()
                    val userType = "user"

                    val accountType = "Google Account"

                    databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                val id = databaseReference.push().key
                                val userData = UserData(id, name, email, accountType, userType)
                                databaseReference.child(id!!).setValue(userData)

                                saveLoginSession(id, name, email, photo, userType, accountType)

                                Toast.makeText(activity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(activity, MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                for (userSnapshot in dataSnapshot.children) {
                                    val userData = userSnapshot.getValue(UserData::class.java)

                                    val id = userData?.id.toString()
                                    val name = userData?.name.toString()
                                    val email = userData?.email.toString()
                                    val userType = userData?.userType.toString()

                                    saveLoginSession(id, name, email, photo, userType, accountType)

                                    Toast.makeText(activity, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(activity, MainActivity::class.java))
                                    requireActivity().finish()

                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(activity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                        }
                    })

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun forgotPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "Reset password email sent.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveLoginSession(userId: String, name: String, email: String, photo: String, userType: String, accountType: String) {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("userId", userId)
        editor?.putString("name", name)
        editor?.putString("email", email)
        editor?.putString("photo", photo)
        editor?.putString("userType", userType)
        editor?.putString("accountType", accountType)
        editor?.apply()
    }

    private fun checkUserLoginStatus() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("userId", "")
        if (id != "") {
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }


    companion object {

        private const val TAG = "AuthActivity"
        private const val RC_SIGN_IN = 9001
    }
}