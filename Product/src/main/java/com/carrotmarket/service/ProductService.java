package com.carrotmarket.service;

import com.carrotmarket.exception.CustomException;
import com.carrotmarket.model.Product;
import com.carrotmarket.model.ProductStatus;
import com.carrotmarket.model.ProductUpdateVO;
import com.carrotmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.carrotmarket.exception.ExceptionCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductUpdateVO productUpdateVO) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        findProduct.update(productUpdateVO);

        return findProduct;
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        product.delete();
    }

    @Transactional(readOnly = true)
    public Product getProductForAdmin(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
    }

    public void changeProductStatus(Long productId, int productStatusOrdinary) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        ProductStatus newStatus = ProductStatus.getByOrdinary(productStatusOrdinary);
        product.changeStatus(newStatus);
    }

}