package com.example.care_e

//package com.example.care_e
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthProvider
//import kotlinx.android.synthetic.main.fragment_login.*
//
//
//class Login : Fragment() {
//
//    val RC_SIGN_IN: Int = 1
//    lateinit var mGoogleSignInClient: GoogleSignInClient
//    lateinit var mGoogleSignInOptions: GoogleSignInOptions
//
//    private lateinit var firebaseAuth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)
//        configureGoogleSignIn()
//        setupUI()
//        firebaseAuth = FirebaseAuth.getInstance()
//    }
//
//    companion object {
//        fun getLaunchIntent(from: Context) = Intent(from, Login::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            startActivity(HomeActivity.getLaunchIntent(this))
//            finish()
//        }
//    }
//
//    private fun configureGoogleSignIn() {
//        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
//    }
//
//    private fun setupUI() {
//        loginprompt.setOnClickListener {
//            signIn()
//        }
//    }
//
//    private fun signIn() {
//        val signInIntent: Intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account!!)
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
//
//                startActivity(HomeActivity.getLaunchIntent(this))
//            } else {
//                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//}
