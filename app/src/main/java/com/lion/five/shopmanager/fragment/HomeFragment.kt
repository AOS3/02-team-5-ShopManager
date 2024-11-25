package com.lion.five.shopmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.adapter.ProductAdapter
import com.lion.five.shopmanager.data.Storage
import com.lion.five.shopmanager.data.model.ProductCategory
import com.lion.five.shopmanager.databinding.FragmentHomeBinding
import com.lion.five.shopmanager.utils.replaceFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter: ProductAdapter by lazy { ProductAdapter() }

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
        setupRecyclerView()
        setupChips()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * 버튼, 칩 리스너 등 UI 이벤트 처리
    */
    private fun setListeners() {
        setupToolbarListener()
        setupFabListener()
        setupChipGroupListener()
    }

    /*
    * 상단 툴바의 검색 메뉴 클릭 리스너
    */
    private fun setupToolbarListener() {
        binding.toolbarHome.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_search -> replaceFragment(SearchFragment(), "SearchFragment")
            }
            true
        }
    }

    /*
    * 상품 추가 FAB 클릭 리스너
    */
    private fun setupFabListener() {
        binding.btnProductAddAction.setOnClickListener {
            replaceFragment(AddProductFragment(), "AddProductFragment")
        }
    }

    /*
    * 카테고리 칩 선택 상태 변경 리스너
    */
    private fun setupChipGroupListener() {
        binding.chipGroupCategory.setOnCheckedStateChangeListener { _, checkedIds ->
            updateProductList(checkedIds)
            updateChipsStroke(checkedIds)
        }
    }

    /*
    * 선택된 카테고리에 따른 상품 리스트 필터링
    */
    private fun updateProductList(checkedIds: List<Int>) {
        val filteredList = if (checkedIds.isEmpty()) {
            Storage.products
        } else {
            val selectedChip = binding.chipGroupCategory.findViewById<Chip>(checkedIds.first())
            when (selectedChip.text) {
                ProductCategory.ALL.categoryName -> Storage.products
                else -> Storage.products.filter { it.type == selectedChip.text }
            }
        }
        adapter.submitList(filteredList)
    }

    /*
    * 선택된 칩의 테두리 스타일 업데이트
    */
    private fun updateChipsStroke(checkedIds: List<Int>) {
        for (i in 0 until binding.chipGroupCategory.childCount) {
            val chip = binding.chipGroupCategory.getChildAt(i) as Chip
            if (chip.id in checkedIds) {
                chip.chipStrokeWidth = 1f
                chip.setChipStrokeColorResource(R.color.gray_800)
            } else {
                chip.chipStrokeWidth = 0f
                chip.chipStrokeColor = null
            }
        }
    }

    /*
    * 리사이클러뷰 어댑터 설정 및 초기 데이터 로드
    */
    private fun setupRecyclerView() {
        binding.rvProductList.adapter = adapter
        adapter.submitList(Storage.products)
    }

    /*
    * 카테고리 칩 초기화 및 기본 선택 설정
    */
    private fun setupChips() {
        ProductCategory.entries.forEach { category ->
            val chip = Chip(requireContext()).apply {
                text = category.categoryName
                isCheckable = true
                chipStrokeColor = null
                chipStrokeWidth = 0.0f
                shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, 12f)
                    .build()
                setChipBackgroundColorResource(R.color.gray_200)

                if (category == ProductCategory.ALL) {
                    isChecked = true
                    chipStrokeWidth = 1f
                    setChipStrokeColorResource(R.color.black)
                }
            }
            binding.chipGroupCategory.addView(chip)
        }
    }
}