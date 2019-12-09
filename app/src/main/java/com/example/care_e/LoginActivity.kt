package com.example.care_e

//
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var dataManager: ViewModel

    private val TAG = "LoginActivity"
    //global variables
    private var email: String? = null
    private var password: String? = null
    //UI elements
    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    private var btnLogin: Button? = null
    private var btnCreateAccount: Button? = null
    private var btnForgotPassword: Button? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialise()

    }

    private fun initialise() {
        //tvForgotPassword = findViewById<View>(R.id.tv_forgot_password) as TextView
//        etEmail = findViewById<View>(R.id.email_login) as EditText
//        etPassword = findViewById<View>(R.id.passwrod_login) as EditText
//        btnLogin = findViewById<View>(R.id.loginButton) as Button
//        btnCreateAccount = findViewById<View>(R.id.CreateAccountBtn) as Button
//        btnForgotPassword = findViewById<View>(R.id.forgotPasswordBtn) as Button

        //  tvForgotPassword = findViewById<View>(R.id.forgot_password) as TextView
//        etEmail = findViewById<View>(R.id.emailText) as EditText
//        etPassword = findViewById<View>(R.id.passwordText) as EditText
//        btnLogin = findViewById<View>(R.id.btn_login) as Button
//        btnCreateAccount = findViewById<View>(R.id.btn_register_account) as Button
        //mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()



        forgot_password.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    ForgotPasswordActivity::class.java
                )
            )
        }

        btn_register_account
            .setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        CreateAccountActivity::class.java
                    )
                )
            }
        btn_login.setOnClickListener { loginUser() }
    }


    private fun loginUser() {
        email = emailText.text.toString()
        password = passwordText.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(this@LoginActivity, "Loging in...", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                       // val userId = mAuth!!.currentUser!!.uid
                      //  var user = UserandCarInfo()
                     //   user.uid = userId
                        dataManager.name.value = email
                        updateUI()
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@LoginActivity, "Invalid Username or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}