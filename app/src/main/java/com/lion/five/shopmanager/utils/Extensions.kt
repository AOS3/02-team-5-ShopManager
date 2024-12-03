package com.lion.five.shopmanager.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.lion.five.shopmanager.R
import java.text.DecimalFormat

fun Fragment.replaceFragment(fragment: Fragment, tag: String) {
    parentFragmentManager.commit {
        setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right,
        )
        replace(R.id.container_main, fragment)
        addToBackStack(tag)
    }
}

fun Fragment.popBackstack() {
    parentFragmentManager.popBackStack()
}

// 가격을 천 단위로 표시하는 확장함수
fun Int.toDecimalFormat(): String {
    return DecimalFormat("#,###").format(this)
}

fun View.clearFocusAndHideKeyboard() {
    clearFocus()
    context.hideKeyboard(this)
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.isLogin() =
    getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        .getBoolean("is_logged_in", false)

fun Context.saveAccount(id: String, password: String) {
    getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        .edit {
            putString("account_id", id)
            putString("account_password", password)
        }
}

fun Context.getAccount(): Pair<String, String> =
    getSharedPreferences("user_prefs", Context.MODE_PRIVATE).run {
        val id = getString("account_id", "") ?: ""
        val password = getString("account_password", "") ?: ""
        id to password
    }

fun Context.setupLogin(isLogin: Boolean) {
    getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        .edit {
            putBoolean("is_logged_in", isLogin)
        }
    }

fun Context.clearAccount() {
    getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        .edit {
            clear()
        }
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}