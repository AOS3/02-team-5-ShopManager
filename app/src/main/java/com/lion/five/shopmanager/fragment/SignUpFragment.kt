package com.lion.five.shopmanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.databinding.FragmentSignUpBinding
import com.lion.five.shopmanager.utils.clearFocusAndHideKeyboard
import com.lion.five.shopmanager.utils.popBackstack

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private var isId = false
    private var isPassword = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setBottomNavigationVisibility(false)
        setupLayout()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? MainActivity)?.setBottomNavigationVisibility(true)
    }

    private fun setupLayout() {
        setupAccountIdTextField()
        setupAccountPasswordTextField()
        setupAccountPasswordConfirmTextField()
    }

    private fun setupListeners() {
        with(binding) {
            root.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.clearFocusAndHideKeyboard()
                }
                true
            }

            toolbarSignUp.setNavigationOnClickListener {
                popBackstack()
            }

            btnLoginSubmit.setOnClickListener {
                val accountId = tfLoginId.editText?.text.toString()
                val accountPassword = tfLoginPassword.editText?.text.toString()

                // 계정 정보를 SharedPreferences에 저장
                requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit {
                        putString("account_id", accountId)
                        putString("account_password", accountPassword)
                    }

                // 저장 완료 후 처리
                popBackstack() // 이전 화면으로 돌아가기
            }
        }
    }


    /*
     * 아이디 텍스트필드를 확인하여 유효성 검사와 버튼 활성화를 담당하는 함수
     * */
    private fun setupAccountIdTextField() {
        binding.tfLoginId.editText?.doAfterTextChanged { text ->
            isId = text?.isNotBlank() ?: false
            updateAddButtonState()
        }
    }

    /*
     * 비밀번호 텍스트필드를 확인하여 유효성 검사와 버튼 활성화를 담당하는 함수
     * */
    private fun setupAccountPasswordTextField() {
        binding.tfLoginPassword.editText?.doAfterTextChanged { text ->
            isPassword = updatePasswordState() && text?.isNotBlank() == true
            confirmPassword()
            updateAddButtonState()
        }
    }

    /*
     * 비밀번호 확인 텍스트필드를 확인하여 유효성 검사와 버튼 활성화를 담당하는 함수
     * */
    private fun setupAccountPasswordConfirmTextField() {
        binding.tfLoginPasswordConfirm.editText?.doAfterTextChanged { text ->
            isPassword = updatePasswordState() && text?.isNotBlank() == true
            confirmPassword()
            updateAddButtonState()
        }
    }

    /*
    * 패스워드가 다를때 tfLoginPasswordConfirm에 에러 메시지를 보여주는 함수.
    * */
    private fun confirmPassword() {
        when(isPassword) {
            true -> binding.tfLoginPasswordConfirm.error = null
            else -> binding.tfLoginPasswordConfirm.error = "비밀번호가 일치하지 않습니다."
        }
    }

    /*
     * 패스워드와 패스워드 확인 필드 값이 같은지 확인하는 함수
     *  */
    private fun updatePasswordState(): Boolean
        = binding.tfLoginPassword.editText?.text.toString() == binding.tfLoginPasswordConfirm.editText?.text.toString()


    /**
     * 계정 등록 버튼 활성화 상태 업데이트
     */
    private fun updateAddButtonState() {
        binding.btnLoginSubmit.isEnabled = isId && isPassword
    }

}