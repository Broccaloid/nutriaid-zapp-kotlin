package com.example.nutriaid_zapp_kotlin

import android.app.Activity
import android.app.Application
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name_editText = binding.usernameText
        val pw_editText = binding.passwordText
        val button = binding.loginButton
        val register_link = binding.registerLink

        fun updateUI(user : FirebaseUser?){
            if(user != null){
                (activity as MainActivity).replaceFragment(HomeFragment())
            }
        }

        fun logIntoAccount(username: String, pw: String){
            auth.signInWithEmailAndPassword(username, pw)
                .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this.context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = name_editText.text.toString()
                val pw = pw_editText.text.toString()
                logIntoAccount(username, pw)
            }
        })
        register_link.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                (activity as MainActivity).replaceFragment(RegisterFragment())
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}