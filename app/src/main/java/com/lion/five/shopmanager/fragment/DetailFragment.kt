package com.lion.five.shopmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lion.five.shopmanager.R
import com.lion.five.shopmanager.data.model.Product
import com.lion.five.shopmanager.databinding.FragmentDetailBinding
import com.lion.five.shopmanager.utils.popBackstack
import com.lion.five.shopmanager.utils.toDecimalFormat

class DetailFragment: Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val product by lazy {
        arguments?.getParcelable<Product>("product")
    }

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
        setupLayout()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLayout() {
        with(binding) {
            product?.images?.first()?.let { ivProductDetailImage.setImageResource(it) }
            tvProductDetailIsBest.visibility = if (product?.isBest == true) View.VISIBLE else View.GONE
            tvProductDetailType.text = product?.type
            tvProductDetailName.text = product?.name
            tvProductDetailDescription.text = product?.description
            tvProductDetailPrice.text = "${product?.price?.toDecimalFormat()}원"
            tvProductDetailStock.text = if (product?.stock == 0) "재고 없음" else "재고 ${product?.stock}"
            tvProductDetailReview.text = if (product?.reviewCount == 0) "리뷰 없음" else "리뷰 ${product?.reviewCount}"
        }
    }

    private fun setupListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                popBackstack()
            }
        }
    }
}