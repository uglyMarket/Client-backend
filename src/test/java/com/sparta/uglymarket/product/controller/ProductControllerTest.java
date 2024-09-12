//package com.sparta.uglymarket.product.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.product.dto.GetAllProductsResponse;
//import com.sparta.uglymarket.product.dto.ProductResponse;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.product.service.ProductService;
//import com.sparta.uglymarket.util.JwtUtil;
//import com.sparta.uglymarket.util.TokenService;
//import io.jsonwebtoken.Claims;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ProductController.class)
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private final String token = "Bearer jwt_token";
//
//
//    @BeforeEach
//    public void setUp() {
//        given(jwtUtil.validateToken(anyString())).willReturn(true);
//
//        Claims claims = mock(Claims.class);
//        given(claims.getSubject()).willReturn("123456789");
//        given(jwtUtil.getAllClaimsFromToken(anyString())).willReturn(claims);
//    }
//
//    @Test
//    @DisplayName("전체 상품 목록 조회 성공")
//    void getAllProducts_Success() throws Exception {
//        // given
//        Product product1 = mock(Product.class);
//        given(product1.getId()).willReturn(1L);
//        given(product1.getTitle()).willReturn("상품 1");
//        given(product1.getContent()).willReturn("상품 설명 1");
//        given(product1.getImage()).willReturn("image1.jpg");
//        given(product1.getDeliveryType()).willReturn("준비중");
//        given(product1.getPrice()).willReturn("10000");
//        given(product1.isUgly()).willReturn(true);
//
//        Product product2 = mock(Product.class);
//        given(product2.getId()).willReturn(2L);
//        given(product2.getTitle()).willReturn("상품 2");
//        given(product2.getContent()).willReturn("상품 설명 2");
//        given(product2.getImage()).willReturn("image2.jpg");
//        given(product2.getDeliveryType()).willReturn("배송중");
//        given(product2.getPrice()).willReturn("50000");
//        given(product2.isUgly()).willReturn(false);
//
//        List<GetAllProductsResponse> products = List.of(
//                new GetAllProductsResponse(product1),
//                new GetAllProductsResponse(product2)
//        );
//
//        given(productService.getAllProducts()).willReturn(products);
//
//        // when & then
//        mockMvc.perform(get("/products")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // 200 OK
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].title").value("상품 1"))
//                .andExpect(jsonPath("$[0].content").value("상품 설명 1"))
//                .andExpect(jsonPath("$[0].image").value("image1.jpg"))
//                .andExpect(jsonPath("$[0].deliveryType").value("준비중"))
//                .andExpect(jsonPath("$[0].price").value("10000"))
//                .andExpect(jsonPath("$[0].ugly").value(true))
//
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].title").value("상품 2"))
//                .andExpect(jsonPath("$[1].content").value("상품 설명 2"))
//                .andExpect(jsonPath("$[1].image").value("image2.jpg"))
//                .andExpect(jsonPath("$[1].deliveryType").value("배송중"))
//                .andExpect(jsonPath("$[1].price").value("50000"))
//                .andExpect(jsonPath("$[1].ugly").value(false));
//    }
//
//    @Test
//    @DisplayName("특정 상품 조회 성공")
//    void getProductById_Success() throws Exception {
//        // given
//        ProductResponse productResponse = mock(ProductResponse.class);
//        given(productResponse.getId()).willReturn(1L);
//        given(productResponse.getTitle()).willReturn("상품 1");
//        given(productResponse.getContent()).willReturn("상품 설명 1");
//        given(productResponse.getImage()).willReturn("image1.jpg");
//        given(productResponse.getDeliveryType()).willReturn("준비중");
//        given(productResponse.getPrice()).willReturn("10000");
//        given(productResponse.isUgly()).willReturn(true);
//
//        given(productService.getProductById(anyLong())).willReturn(productResponse);
//
//        // when & then
//        mockMvc.perform(get("/products/{productId}", 1L)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // 200 OK
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("상품 1"))
//                .andExpect(jsonPath("$.content").value("상품 설명 1"))
//                .andExpect(jsonPath("$.image").value("image1.jpg"))
//                .andExpect(jsonPath("$.deliveryType").value("준비중"))
//                .andExpect(jsonPath("$.price").value("10000"))
//                .andExpect(jsonPath("$.ugly").value(true));
//    }
//
//    @Test
//    @DisplayName("특정 상품 조회 실패: 상품 없음")
//    void getProductById_NotFound() throws Exception {
//        // given
//        given(productService.getProductById(anyLong())).willThrow(new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
//
//        // when & then
//        mockMvc.perform(get("/products/{productId}", 999L)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//}