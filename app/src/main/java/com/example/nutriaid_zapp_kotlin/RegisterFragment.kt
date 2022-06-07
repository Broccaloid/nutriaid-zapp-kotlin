package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name_editText = binding.usernameText
        val pw_editText = binding.passwordText
        val repeat_pw_editText = binding.repeatPasswordText
        val button = binding.registerButton

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = name_editText.text.toString()
                val pw = pw_editText.text.toString()
                val repeat_pw = repeat_pw_editText.text.toString()

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