package com.example.care_e

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {


    var database = MutableLiveData<DatabaseReference>()

    //UI elements
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
//        private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        initialise()

    }

    private fun initialise() {
        etFirstName = findViewById<View>(R.id.first_name) as EditText
        etLastName = findViewById<View>(R.id.last_name) as EditText
        etEmail = findViewById<View>(R.id.email) as EditText
        etPassword = findViewById<View>(R.id.password) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_register) as? Button

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            Toast.makeText(this, "Registering User...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                //
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Successfully Created Account",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    //Verify Email
                    verifyEmail()
                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    updateUserInfoAndUI()

                    val intent = Intent(this@CreateAccountActivity, MainActivity::class.java);
                    intent.putExtra("FirstName", firstName)
                    intent.putExtra("LastName", lastName)
                    intent.putExtra("Email", email)
                    intent.putExtra("Type", "SignUp")
                    startActivity(intent);

                } else {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Failed to Register User",
                        Toast.LENGTH_SHORT
                    ).show()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@CreateAccountActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


}

