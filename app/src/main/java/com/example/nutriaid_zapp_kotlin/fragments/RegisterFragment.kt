package com.example.nutriaid_zapp_kotlin.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment: Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = binding.usernameText
        val pwEditText = binding.passwordText
        val repeatPwEditText = binding.repeatPasswordText
        val button = binding.registerButton

        fun updateUI(user: FirebaseUser?){
            if(user != null){
                (activity as MainActivity).replaceFragment(HomeFragment())
            }
        }

        fun createAccount(username: String, pw: String, repeatPw: String){
            if(pw == repeatPw){
                auth.createUserWithEmailAndPassword(username, pw)
                    .addOnCompleteListener(Activity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this.context, " " + task.exception,
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                        //"Authentication failed."
                    }
            }
        }

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = nameEditText.text.toString()
                val pw = pwEditText.text.toString()
                val repeat_pw = repeatPwEditText.text.toString()
                createAccount(username, pw, repeat_pw)




                /*
                    TODO: - check username and passswords
                          - register()
                          - goto different fragment
                 */
            }
        })


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
