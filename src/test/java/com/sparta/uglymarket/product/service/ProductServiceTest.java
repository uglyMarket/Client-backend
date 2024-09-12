//package com.sparta.uglymarket.product.service;
//
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
//import com.sparta.uglymarket.product.dto.ProductResponse;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.product.repository.ProductRepository;
//import com.sparta.uglymarket.review.entity.Review;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("전체 상품 조회 성공")
//    void getAllProducts_Success() {
//        // given
//        Product product1 = mock(Product.class);
//        given(product1.getId()).willReturn(1L);
//        given(product1.getTitle()).willReturn("상품 1");
//        given(product1.getImage()).willReturn("이미지 1.jpg");
//        given(product1.getDeliveryType()).willReturn("준비중");
//        given(product1.getPrice()).willReturn("10000");
//
//        Product product2 = mock(Product.class);
//        given(product2.getId()).willReturn(2L);
//        given(product2.getTitle()).willReturn("상품 2");
//        given(product2.getImage()).willReturn("이미지 2.jpg");
//        given(product2.getDeliveryType()).willReturn("배송중");
//        given(product2.getPrice()).willReturn("50000");
//
//        List<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//
//        given(productRepository.findAll()).willReturn(products);
//
//        // when
//        List<GetAllProductsResponse> responses = productService.getAllProducts();
//
//        // then
//        assertNotNull(responses);
//        assertEquals(2, responses.size());
//
//        // 각 상품의 정보 올바르게 반환되는지 검증
//        assertEquals(1L, responses.get(0).getId());
//        assertEquals("상품 1", responses.get(0).getTitle());
//        assertEquals("이미지 1.jpg", responses.get(0).getImage());
//        assertEquals("준비중", responses.get(0).getDeliveryType());
//        assertEquals("10000", responses.get(0).getPrice());
//
//        assertEquals(2L, responses.get(1).getId());
//        assertEquals("상품 2", responses.get(1).getTitle());
//        assertEquals("이미지 2.jpg", responses.get(1).getImage());
//        assertEquals("배송중", responses.get(1).getDeliveryType());
//        assertEquals("50000", responses.get(1).getPrice());
//    }
//
//
//    @Test
//    @DisplayName("특정 상품 조회 성공")
//    void getProductById_Success() {
//        // given
//        Product product = mock(Product.class);
//        given(product.getId()).willReturn(1L);
//        given(product.getTitle()).willReturn("상품 1");
//        given(product.getImage()).willReturn("이미지 1.jpg");
//        given(product.getDeliveryType()).willReturn("준비중");
//        given(product.getPrice()).willReturn("10000");
//
//        Review review1 = mock(Review.class);
//        Review review2 = mock(Review.class);
//        List<Review> reviews = List.of(review1, review2);
//        given(product.getReviews()).willReturn(reviews);
//
//        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
//
//        // when
//        ProductResponse response = productService.getProductById(1L);
//
//        // then
//        assertNotNull(response);
//        assertEquals(1L, response.getId());
//        assertEquals("상품 1", response.getTitle());
//        assertEquals("이미지 1.jpg", response.getImage());
//        assertEquals("준비중", response.getDeliveryType());
//        assertEquals("10000", response.getPrice());
//        assertEquals(2, response.getReviews().size());
//
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    @DisplayName("특정 상품 조회 실패: 상품이 없는 경우")
//    void getProductById_Fail() {
//        // given
//        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            productService.getProductById(1L);
//        });
//
//        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND, exception.getErrorMsg());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//}