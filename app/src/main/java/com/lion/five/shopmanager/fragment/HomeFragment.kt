package com.lion.five.shopmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.databinding.FragmentHomeBinding
import com.lion.five.shopmanager.utils.replaceFragment

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * 리스너들 모음
    * */
    private fun setListeners() {
        with(binding) {
            // 메뉴 클릭시
            toolbarHome.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.navigation_search -> replaceFragment(SearchFragment(), "SearchFragment")
                }
                true
            }

            // 상품 추가 버튼을 눌렀을 때
            btnProductAddAction.setOnClickListener {
                replaceFragment(AddProductFragment(), "AddProductFragment")
            }
        }
    }
}