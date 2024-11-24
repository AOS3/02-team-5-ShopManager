package com.lion.five.shopmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lion.five.shopmanager.databinding.FragmentSearchBinding
import com.lion.five.shopmanager.utils.popBackstack

class SearchFragment: Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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

    /*
    * 리스너들 모음 함수
    * */
    private fun setupListeners() {
        with(binding) {
            toolbarSearch.setNavigationOnClickListener {
                popBackstack()
            }

            btnSearch.setOnClickListener {
                // 검색 결과 화면 구현하기
            }
        }
    }
}