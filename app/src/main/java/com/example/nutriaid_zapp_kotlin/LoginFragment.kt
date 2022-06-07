package com.example.nutriaid_zapp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nutriaid_zapp_kotlin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name_editText = binding.usernameText
        val pw_editText = binding.passwordText
        val button = binding.loginButton
        val register_link = binding.registerLink

        button.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val username = name_editText.text.toString()
                val pw = pw_editText.text.toString()

                /*
                    TODO: - check login data
                          - login()
                          - goto different fragment
                 */
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