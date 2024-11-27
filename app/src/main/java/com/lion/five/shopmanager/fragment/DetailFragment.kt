package com.lion.five.shopmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.adapter.ProductDetailAdapter
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.data.repository.ProductRepository
import com.lion.five.shopmanager.databinding.FragmentDetailBinding
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
            tvProductDetailPrice.text = "${detailProduct.price?.toDecimalFormat()}"
            tvProductDetailStock.text = if (detailProduct.stock == 0) "재고 없음" else "재고 ${product?.stock}"
            tvProductDetailReview.text = if (detailProduct.reviewCount == 0) "리뷰 없음" else "리뷰 ${product?.reviewCount}"
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
        adapter.submitList(detailProduct.images)
    }
}