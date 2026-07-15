package com.example.commercepjt.product.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.admin.service.AdminService;
import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.DuplicateException;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.product.dto.request.CreateProductRequest;
import com.example.commercepjt.product.dto.response.*;
import com.example.commercepjt.product.dto.request.UpdateProductRequest;
import com.example.commercepjt.product.dto.request.UpdateProductStatusRequest;
import com.example.commercepjt.product.dto.request.UpdateStockRequest;
import com.example.commercepjt.product.entity.Product;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final OrderRepository orderRepository;

    // 상품 등록
    @Transactional
    public ProductResponse create(Long adminId, CreateProductRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new NotFoundException("관리자를 찾을 수 없습니다."));
        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateException("이미 등록된 상품 이름입니다.");
        }
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                admin
        );

        Product savedProduct = productRepository.save(product);

        return toProductResponse(savedProduct);
    }

    // 상품 단건 조회
    @Transactional(readOnly = true)
    public ProductDetailResponse findOne(Long productId) {
        Product product = findProduct(productId);

        return new ProductDetailResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getAdmin().getName(),
                product.getAdmin().getEmail()
        );
    }

    // 상품 리스트 조회
    @Transactional(readOnly = true)
    public ProductListResponse findAll(String keyword, String category, ProductStatus status, Pageable pageable) {
        Page<Product> productPage = productRepository.search(keyword, category, status, pageable);

        List<ProductResponse> products = productPage.getContent().stream()
                .map(this::toProductResponse)
                .toList();

        PageInfo pageInfo = new PageInfo(productPage);

        return new ProductListResponse(products, pageInfo);
    }

    // 상품 수정
    @Transactional
    public ProductResponse update(
            Long productId,
            UpdateProductRequest request
    ) {
        if (productRepository.existsByNameAndProductIdNot(request.getName(), productId)) {
            throw new DuplicateException("이미 등록된 상품 이름입니다.");
        }
        Product product = findProduct(productId);

        product.update(
                request.getName(),
                request.getCategory(),
                request.getPrice()
        );

        return toProductResponse(product);
    }

    // 상품 재고 수정
    @Transactional
    public UpdateProductStockResponse updateStock(
            Long productId,
            UpdateStockRequest request
    ) {
        Product product = findProduct(productId);

        product.changeStock(request.getStock());

        return new UpdateProductStockResponse(product.getStock());
    }

    // 상품 상태 수정
    @Transactional
    public UpdateProductStatusResponse updateStatus(
            Long productId,
            UpdateProductStatusRequest request
    ) {
        Product product = findProduct(productId);

        product.changeStatus(request.getStatus());

        return new UpdateProductStatusResponse(product.getStatus());
    }

    // 상품 삭제
    @Transactional
    public void delete(Long productId) {
        Product product = findProduct(productId);

        if (orderRepository.existsByProduct_ProductId(productId)) {
            throw new IllegalArgumentException("주문 내역이 존재하는 상품은 삭제할 수 없습니다.");
        }

        productRepository.delete(product);
    }

    //
    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getAdmin().getName()
        );
    }

    //상품 겅증 공통 메서드
    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("상품을 찾을 수 없습니다."));
    }
}