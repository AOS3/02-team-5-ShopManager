package com.lion.five.shopmanager.utils

import androidx.fragment.app.Fragment

fun Fragment.popBackstack() {
    parentFragmentManager.popBackStack()
}