package com.lion.five.shopmanager.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.lion.five.shopmanager.MainActivity
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.adapter.ProductDetailAdapter
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentDetailBinding
import com.lion.five.shopmanager.retrofit.RetrofitClient
import com.lion.five.shopmanager.utils.FileUtil
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.replaceFragment
import com.lion.five.shopmanager.utils.toDecimalFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment: Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val adapter: ProductDetailAdapter by lazy { ProductDetailAdapter() }

    private val product by lazy {
        arguments?.getParcelable<Product>("product")
    }

    private lateinit var detailProduct: Product


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setBottomNavigationVisibility(false)

        lifecycleScope.launch(Dispatchers.IO) {
            detailProduct = ProductRepository.searchProductById(requireContext(), product!!.id)
            withContext(Dispatchers.Main) {
                setupLayout()
                setupListeners()
                setupViewPager()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLayout() {
        with(binding) {
            tvProductDetailIsBest.visibility = if (detailProduct.isBest) View.VISIBLE else View.GONE
            tvProductDetailType.text = detailProduct.type
            tvProductDetailName.text = detailProduct.name
            tvProductDetailDescription.text = detailProduct.description
            tvProductDetailPrice.text = "${detailProduct.price.toDecimalFormat()}"
            tvProductDetailStock.text = if (detailProduct.stock == 0) "재고 없음" else "재고 ${detailProduct.stock.toDecimalFormat()}"
            tvProductDetailReview.text = if (detailProduct.reviewCount == 0) "리뷰 없음" else "리뷰 ${detailProduct.reviewCount.toDecimalFormat()}"

            lifecycleScope.launch {
                checkMovieName { movieDetails ->
                    tvProductDetailDescription.text = "${detailProduct.description}\n\n$movieDetails"
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                popBackstack()
            }

            toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_edit -> actionEdit()
                    R.id.menu_delete -> actionDelete()
                }
                true
            }
        }
    }

    private fun actionEdit() {
        val fragment = EditProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable("product", detailProduct)
            }
        }
        replaceFragment(fragment, "EditProductFragment")
    }

    private fun setupViewPager() {
        binding.viewPagerProductImage.adapter = adapter

        TabLayoutMediator(
            binding.viewpagerDetailIndicator,
            binding.viewPagerProductImage
        ) { _, _ -> }.attach()

        adapter.submitList(detailProduct.images)

    }

    private fun actionDelete(){
        // 다이얼로그를 띄워준다.
        AlertDialog.Builder(requireContext())
            .setTitle("정보 삭제")
            .setMessage("삭제를 할 경우 복원이 불가능합니다.")
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    // 이미지 파일 삭제
                    detailProduct.images.forEach { fileName ->
                        FileUtil.deleteImage(requireContext(), fileName)
                    }

                    // Room DB에서 product삭제
                    product?.id?.let { ProductRepository.deleteProductById(requireContext(), it) }
                }
                popBackstack()
            }
        }
            .show()
    }

    private suspend fun checkMovieName(onResult: (String) -> Unit) {
        if (detailProduct.movieName.isEmpty()) {
            onResult("영화 정보가 없습니다.")
            return
        }

        runCatching {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.getMovieList(
                    movieName = detailProduct.movieName
                )
            }

            val movieListResult = response.movieListResult
            val movie = movieListResult.movieList.firstOrNull()

            if (movie != null) {
                val updatedMovieName = movie.movieNm
                detailProduct = detailProduct.copy(movieName = updatedMovieName)
                val director = movie.directors.firstOrNull()?.peopleNm ?: "정보 없음"

                val movieDetails = """
                영화 이름: ${movie.movieNm}
                감독: $director
                제작년도: ${movie.openDt}
                장르: ${movie.genreAlt}
            """.trimIndent()
                onResult(movieDetails)
            } else {
                onResult("영화 정보를 찾을 수 없습니다.")
            }
        }.onFailure { e ->
            Log.e("MovieAPI", "API 호출 실패", e)
            onResult("API 호출 실패: ${e.message}")
        }
    }
}