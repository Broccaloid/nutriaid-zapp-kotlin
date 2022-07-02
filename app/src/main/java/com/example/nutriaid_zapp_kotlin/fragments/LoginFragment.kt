package com.example.nutriaid_zapp_kotlin.fragments

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.MainActivity
import com.example.nutriaid_zapp_kotlin.databinding.FragmentLoginBinding
import com.example.nutriaid_zapp_kotlin.models.algorithm.AlarmReceiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


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

        val nameEditText = binding.usernameText
        val pwEditText = binding.passwordText
        val button = binding.loginButton
        val registerLink = binding.registerLink

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

                        //set alarm if user is valid
                        val calendar = Calendar.getInstance()
                        val calendarNow = Calendar.getInstance()
                        calendar.timeInMillis = System.currentTimeMillis()

                        // Setting the specific time for the alarm manager to trigger the intent
                        calendar.set(Calendar.HOUR_OF_DAY, 20) //fire alarm everyday at 12pm
                        calendar.set(Calendar.MINUTE, 48)
                        calendar.set(Calendar.SECOND, 6)
                        if(calendar.after(calendarNow)) { //so that the alarm doesnt fire instantly
                            val REQUESTCODE = 1
                            // Creating the pending intent to send to the BroadcastReceiver
                            var alarmManager =
                                context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            val intent = Intent(context, AlarmReceiver::class.java)
                            var pendingIntent = PendingIntent.getBroadcast(
                                context,
                                REQUESTCODE,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                            )
                            // Starts the alarm manager
                            alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP,
                                calendar.timeInMillis,
                                60000,
                                pendingIntent
                            )
                            Log.d("mytag", "alarm set")
                        }
                        else{
                            calendar.add(Calendar.DAY_OF_MONTH, 1) //set alarm for tomorrow
                            val REQUESTCODE = 1
                            // Creating the pending intent to send to the BroadcastReceiver
                            var alarmManager =
                                context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            val intent = Intent(context, AlarmReceiver::class.java)
                            var pendingIntent = PendingIntent.getBroadcast(
                                context,
                                REQUESTCODE,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                            )
                            // Starts the alarm manager
                            alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP,
                                calendar.timeInMillis,
                                AlarmManager.INTERVAL_DAY,
                                pendingIntent
                            )
                            Log.d("mytag", "alarm set")
                        }

                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this.context, "Authentification failed",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = nameEditText.text.toString()
                val pw = pwEditText.text.toString()
                logIntoAccount(username, pw)
            }
        })
        registerLink.setOnClickListener(object: View.OnClickListener {
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
