package com.lion.five.shopmanager.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentSalesBinding
import com.lion.five.shopmanager.utils.isLogin
import com.lion.five.shopmanager.utils.replaceFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SalesFragment : Fragment() {
    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentSalesBinding.inflate(inflater, container, false)

        // 로그인 상태 확인
        if (appContext.isLogin()) {
            // 로그인 되었을 경우 SalesFragment UI 표시
            setupSalesUI()
        } else {
            // 로그인 안 되었을 경우 로그인 안내 화면 표시
            setupLoginPrompt()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setBottomNavigationVisibility(true)

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

    private fun setupSalesUI() {
        // 로그인 상태 확인 후 TextView에 아이디 표시
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val accountId = sharedPreferences.getString("account_id", "")
        // 로그인 후 UI 설정
        if (isLoggedIn && accountId != null) {
            binding.tvSalesMessage.text = "${accountId}님"
        }
        binding.llSalesContent.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
        binding.btnReplaceLogin.visibility = View.GONE
    }

    private fun setupLoginPrompt() {
        // 로그인되지 않았을 경우 안내 메시지와 로그인 버튼 표시
        binding.llSalesContent.visibility = View.GONE
        binding.tvMessage.visibility = View.VISIBLE
        binding.btnReplaceLogin.visibility = View.VISIBLE

        binding.btnReplaceLogin.setOnClickListener {
            // 로그인 화면으로 이동
            val loginFragment = SignInFragment()
            replaceFragment(loginFragment, "LoginFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}