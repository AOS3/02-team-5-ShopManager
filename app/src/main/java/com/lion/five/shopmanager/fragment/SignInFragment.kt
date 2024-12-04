package com.lion.five.shopmanager.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.databinding.FragmentSignInBinding
import com.lion.five.shopmanager.utils.clearAccount
import com.lion.five.shopmanager.utils.clearFocusAndHideKeyboard
import com.lion.five.shopmanager.utils.getAccount
import com.lion.five.shopmanager.utils.hideKeyboard
import com.lion.five.shopmanager.utils.isLogin
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.replaceFragment
import com.lion.five.shopmanager.utils.saveAccount
import com.lion.five.shopmanager.utils.setupLogin
import com.lion.five.shopmanager.utils.showMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var appContext: Context // requireContext()를 반복적으로 호출하고 있어 프로퍼티 정의

    // activity에 fragment가 붙을 때 context를 초기화
    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

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
        (activity as? MainActivity)?.setBottomNavigationVisibility(true)
        updateViewVisibility()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /*
    * 회원가입, 로그아웃, 로그인, 회원 탈퇴 버튼 리스트들 모음
    * */
    private fun setupListeners() {
        with(binding) {
            root.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.clearFocusAndHideKeyboard()
                }
                true
            }

            // 회원가입: 회원가입 화면으로 전환
            btnLoginRegister.setOnClickListener {
                replaceFragment(SignUpFragment(), "SignUpFragment")
            }

            // 로그인: ID, Password 값을 검증하고 로그인 처리
            btnLoginSubmit.setOnClickListener {
                val inputId = tfLoginId.editText?.text?.trim().toString()
                val inputPassword = tfLoginPassword.editText?.text?.trim().toString()
                validateAndUpdateUI(inputId, inputPassword)
                delayTime()
                appContext.hideKeyboard(it)
            }

            // 로그아웃
            btnAccountLogout.setOnClickListener {
                appContext.setupLogin(false)
                updateViewVisibility()
                appContext.showMessage("로그아웃 되었습니다.")
            }

            // 회원탈퇴
            btnAccountDelete.setOnClickListener {
                showDeleteAccountDialog()
                popBackstack()
            }
        }
    }

    /*
    * 저장된 회원이 있는지 확인하고, 입력 값과 비교 후 로그인 처리
    * */
    private fun validateAndUpdateUI(inputId: String, inputPassword: String) {
        val (savedId, savedPassword) = appContext.getAccount()

        if (!validateLoginInput(inputId, inputPassword)) return // 입력값이 부족할 경우 return
        if (isExistAccount(savedId, savedPassword)) return // 가입된 계정이 없는 경우 return

        // 입력값과 저장된 값을 비교하고 로그인 처리
        if (isSameAccount(inputId, inputPassword, savedId, savedPassword)) {
            appContext.setupLogin(true)
            appContext.showMessage("로그인에 성공 했어요")
            updateViewVisibility()
        } else {
            appContext.showMessage("아이디 또는 비밀번호가 일치하지 않아요")
        }
        clearTextField() // 텍스트필드 비우기
    }

    /*
    * 로그인 버튼을 눌렀을 때
    * 아이디, 비밀번호 값이 없으면 error
    * */
    private fun validateLoginInput(inputId: String, inputPassword: String): Boolean {
        var isValid = true

        if (inputId.isBlank()) {
            binding.tfLoginId.error = "아이디를 입력해주세요"
            isValid = false
        }

        if (inputPassword.isBlank()) {
            binding.tfLoginPassword.error = "비밀번호를 입력해주세요"
            isValid = false
        }
        return isValid
    }

    /*
    * 등록된 계정이 있는지 확인
    * */
    private fun isExistAccount(savedId: String, savedPassword: String): Boolean {
        if (savedId.isBlank() && savedPassword.isBlank()) {
            appContext.showMessage("등록된 계정이 없습니다.\n회원가입을 먼저 진행해주세요")
            return true
        }
        return false
    }

    /*
    * 로그인 상태, 로그아웃 상태에 따라 레이아웃을 구성하는 함수
    * */
    private fun updateViewVisibility() {
        with(binding) {
            groupLogin.visibility = if (appContext.isLogin()) View.GONE else View.VISIBLE
            groupDashboard.visibility = if (appContext.isLogin()) View.VISIBLE else View.GONE
        }
    }

    /*
    * 토스트메시지가 키보드 위에서 나타나는 현상을 방지하는 딜레이 함수
    * */
    private fun delayTime() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
        }
    }

    /*
    * 입력값과 저장된 정보가 같은지 확인하고 Boolean을 반환하는 함수
    * */
    private fun isSameAccount(inputId: String, inputPassword: String, savedId: String, savedPassword: String): Boolean {
        return inputId == savedId && inputPassword == savedPassword
    }

    /*
    * 아이디, 패스워드 필드 비우기
    * */
    private fun clearTextField() {
        binding.tfLoginId.editText?.text?.clear()
        binding.tfLoginPassword.editText?.text?.clear()
    }

    /*
    * 회원탈퇴 시 재확인하는 dialog
    * */
    private fun showDeleteAccountDialog() {
        AlertDialog.Builder(appContext)
            .setTitle("계정 삭제")
            .setMessage("삭제하면 해당 계정으로는\n로그인 할 수 없습니다.")
            .setNegativeButton("아니요", null)
            .setPositiveButton("예") { _, _ ->
                appContext.clearAccount()
                appContext.showMessage("계정이 탈퇴 되었습니다")
                updateViewVisibility()
            }
            .show()
    }
}