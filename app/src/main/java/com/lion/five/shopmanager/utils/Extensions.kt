package com.lion.five.shopmanager.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.lion.five.shopmanager.R
import java.text.DecimalFormat

fun Fragment.replaceFragment(fragment: Fragment, tag: String) {
    Log.d("", fragment.toString())
    parentFragmentManager.commit {
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