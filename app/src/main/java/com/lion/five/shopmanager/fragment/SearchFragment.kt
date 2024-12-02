package com.lion.five.shopmanager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.adapter.ProductAdapter
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentSearchBinding
import com.lion.five.shopmanager.listener.OnItemClickListener
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment: Fragment(), OnItemClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter: ProductAdapter by lazy { ProductAdapter(this) }

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
        (activity as? MainActivity)?.setBottomNavigationVisibility(false)
        setupListeners()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setBottomNavigationVisibility(true)
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
            // 실시간 검색
            tilSearch.editText?.addTextChangedListener { editable ->
                val keyword = editable.toString()
                searchProductsByName(keyword)
            }

            // 키보드 검색 버튼 리스너
            tilSearch.editText?.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    val keyword = tilSearch.editText?.text.toString()
                    searchProductsByName(keyword)
                    hideKeyboard()
                    true
                }
                else{
                    false
                }

            }

            btnSearch.setOnClickListener {
                // 검색 결과 화면 구현하기
                val keyword = tilSearch.editText?.text.toString()
                searchProductsByName(keyword)
                hideKeyboard()
            }
        }
    }

    override fun onItemClick(product: Product) {
        replaceFragment(DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("product", product)
            }
        }, "DetailFragment")
    }

    /*
    * 검색 리사이클러뷰 어댑터 설정
    */
    private fun setupRecyclerView() {
        binding.rvSearchList.adapter = adapter
    }

    // 이름으로 상품 검색
    fun searchProductsByName(keyword: String) {
        with(binding){
            // 공백을 기준으로 단어들을 분리하여 검색
            val keywords = keyword.trim().split("\\s+".toRegex())

            // 빈 문자열이면 검색하지 않음
            if (keywords.isEmpty() || keywords.first().isEmpty()) {
                rvSearchList.visibility = View.GONE
                tvNoResults.visibility = View.VISIBLE
                tvNoResults.text = ""
                adapter.submitList(emptyList())
                return
            }

            // 백그라운드에서 검색을 처리
            lifecycleScope.launch(Dispatchers.IO) {
                val result = ProductRepository.searchProductByName(requireContext(), keyword)

                // UI 작업은 메인 스레드에서
                launch(Dispatchers.Main) {
                    if (result.isEmpty()) {
                        rvSearchList.visibility = View.GONE
                        tvNoResults.visibility = View.VISIBLE
                        tvNoResults.text = "검색 결과가 없습니다."
                    } else {
                        rvSearchList.visibility = View.VISIBLE
                        tvNoResults.visibility = View.GONE
                    }

                    adapter.submitList(result)
                }
            }
        }
    }

    // 키보드 내리고 포커스 없애기
    fun hideKeyboard() {
        // 키보드 숨기기
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tilSearch.windowToken, 0)

        // EditText에서 포커스 제거
        binding.tilSearch.editText?.clearFocus()
    }

}