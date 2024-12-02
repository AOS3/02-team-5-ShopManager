package com.lion.five.shopmanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentSalesBinding
import com.lion.five.shopmanager.utils.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SalesFragment : Fragment() {
    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 베스트 상품 5개를 불러오기
        loadBestProducts()
    }

    private fun loadBestProducts() {
        lifecycleScope.launch(Dispatchers.IO) {
            val bestProducts = ProductRepository.getBestProducts(requireContext(), 5)

            withContext(Dispatchers.Main) {
                // 가져온 상품이 부족하면 나머지는 없음으로 표시
                val productNames = bestProducts.map { it.name } + List(5 - bestProducts.size) { "없음" }

                // TextView에 각각 상품 이름 설정
                binding.tvProductBest1.text = "1. ${productNames.getOrNull(0)}"
                binding.tvProductBest2.text = "2. ${productNames.getOrNull(1)}"
                binding.tvProductBest3.text = "3. ${productNames.getOrNull(2)}"
                binding.tvProductBest4.text = "4. ${productNames.getOrNull(3)}"
                binding.tvProductBest5.text = "5. ${productNames.getOrNull(4)}"

                // 각 TextView 클릭 시 DetailFragment 화면으로 이동
                setupProductClickListeners(bestProducts)
            }
        }
    }

    // TextView 클릭 리스너
    private fun setupProductClickListeners(products: List<Product>) {
        binding.tvProductBest1.setOnClickListener {
            navigateToProductDetail(products.getOrNull(0))
        }

        binding.tvProductBest2.setOnClickListener {
            navigateToProductDetail(products.getOrNull(1))
        }

        binding.tvProductBest3.setOnClickListener {
            navigateToProductDetail(products.getOrNull(2))
        }

        binding.tvProductBest4.setOnClickListener {
            navigateToProductDetail(products.getOrNull(3))
        }

        binding.tvProductBest5.setOnClickListener {
            navigateToProductDetail(products.getOrNull(4))
        }
    }

    // DetailFragment 화면으로 이동
    private fun navigateToProductDetail(product: Product?) {
        product?.let {
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("product", product)
                }
            }
            replaceFragment(fragment, "DetailFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}