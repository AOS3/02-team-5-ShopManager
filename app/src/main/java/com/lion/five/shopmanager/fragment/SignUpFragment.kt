package com.lion.five.shopmanager.fragment

import android.app.AlertDialog
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
import com.lion.five.shopmanager.utils.getAccount
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.saveAccount
import com.lion.five.shopmanager.utils.showMessage

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private var isId = false
    private var isPassword = false

    private lateinit var appContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

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
                val accountId = tfLoginId.editText?.text?.trim().toString()
                val accountPassword = tfLoginPassword.editText?.text?.trim().toString()
                val (savedId, savedPassword) = appContext.getAccount()

                if (isExistAccount(savedId, savedPassword).not()) {
                    showOverwriteAccountDialog(accountId, accountPassword)
                    return@setOnClickListener
                }

                // 기존 계정이 없는 경우 바로 저장
                appContext.saveAccount(accountId, accountPassword)
                appContext.showMessage("계정이 등록되었습니다")
                popBackstack()
            }
        }
    }

    /*
     * 아이디 텍스트필드를 확인하여 유효성 검사와 버튼 활성화를 담당하는 함수
     * */
    private fun setupAccountIdTextField() {
        binding.tfLoginId.editText?.doAfterTextChanged { text ->
            isId = text?.trim()?.isNotBlank() ?: false
            updateAddButtonState()
        }
    }

    /*
     * 비밀번호 텍스트필드를 확인하여 유효성 검사와 버튼 활성화를 담당하는 함수
     * */
    private fun setupAccountPasswordTextField() {
        binding.tfLoginPassword.editText?.doAfterTextChanged { text ->
            isPassword = updatePasswordState() && text?.trim()?.isNotBlank() == true
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

    /*
    * 등록된 계정이 있는지 확인
    * */
    private fun isExistAccount(savedId: String, savedPassword: String): Boolean {
        return savedId.isBlank() && savedPassword.isBlank()
    }

    /*
    * 기존에 계정이 존재하면
    * 덮어 쓸 것인지 확인하는 dialog
    * */
    private fun showOverwriteAccountDialog(accountId: String, accountPassword: String) {
        AlertDialog.Builder(appContext)
            .setTitle("계정 덮어쓰기")
            .setMessage("이미 존재하는 계정이 있습니다.\n등록을 하면 이전 계정은 지워집니다.")
            .setNegativeButton("아니요", null)
            .setPositiveButton("예") { _, _ ->
                appContext.saveAccount(accountId, accountPassword) // 새로운 계정 정보 저장
                appContext.showMessage("계정이 등록되었습니다")
                popBackstack()
            }
            .show()
    }

}