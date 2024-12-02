package com.lion.five.shopmanager.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lion.five.shopmanager.databinding.FragmentSignInBinding
import com.lion.five.shopmanager.utils.replaceFragment

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        with(binding) {
            btnLoginRegister.setOnClickListener {
                replaceFragment(SignUpFragment(), "SignUpFragment")
            }

            btnLoginSubmit.setOnClickListener {
                val inputId = tfLoginId.editText?.text.toString()
                val inputPassword = tfLoginPassword.editText?.text.toString()

                // SharedPreferences에서 저장된 계정 정보 가져오기
                requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE).run {
                    val savedId = getString("account_id", "") ?: ""
                    val savedPassword = getString("account_password", "") ?: ""

                    if (inputId == savedId && inputPassword == savedPassword) {
                        // 로그인 성공 처리
                        Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show()
                    } else {
                        // 로그인 실패 처리
                        Toast.makeText(requireContext(), "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}