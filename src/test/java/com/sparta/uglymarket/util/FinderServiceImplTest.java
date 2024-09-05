package com.sparta.uglymarket.util;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class FinderServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private FinderServiceImpl finderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("핸드폰 번호로 유저 찾기 성공")
    void findUserByPhoneNumber_Success() {
        // given
        User user = mock(User.class);
        when(user.getPhoneNumber()).thenReturn("123456789");
        given(userRepository.findByPhoneNumber(anyString())).willReturn(Optional.of(user));

        // when
        User foundUser = finderService.findUserByPhoneNumber("123456789");

        // then
        assertThat(foundUser).isNotNull();  // 객체가 null이 아님을 확인
        assertThat(foundUser.getPhoneNumber()).isEqualTo("123456789");
        verify(userRepository, times(1)).findByPhoneNumber(anyString());
    }

    @Test
    @DisplayName("핸드폰 번호로 유저 찾기 실패")
    void findUserByPhoneNumber_Fail() {
        // given
        given(userRepository.findByPhoneNumber(anyString())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> finderService
                .findUserByPhoneNumber("123456789"))
                .isInstanceOf(CustomException.class)  // CustomException 발생 여부 확인
                .hasMessage(ErrorMsg.PHONE_NUMBER_INVALID.getDetails());  // 예외 메시지 검증
        verify(userRepository, times(1)).findByPhoneNumber(anyString());
    }

    @Test
    @DisplayName("주문 ID로 주문 찾기 성공")
    void findOrderById_Success() {
        // given
        Orders order = mock(Orders.class);
        when(order.getId()).thenReturn(1L);
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        // when
        Orders foundOrder = finderService.findOrderById(1L);

        // then
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getId()).isEqualTo(1L);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("주문 ID로 주문 찾기 실패")
    void findOrderById_Fail() {
        // given
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> finderService
                .findOrderById(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMsg.ORDER_NOT_FOUND.getDetails());
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("상품 ID로 상품 찾기 성공")
    void findProductById_Success() {
        // given
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        // when
        Product foundProduct = finderService.findProductById(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1L);
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("상품 ID로 상품 찾기 실패")
    void findProductById_Fail() {
        // given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> finderService
                .findProductById(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMsg.PRODUCT_NOT_FOUND.getDetails());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 ID로 리뷰 찾기 성공")
    void findReviewById_Success() {
        // given
        Review review = mock(Review.class);
        when(review.getId()).thenReturn(1L);
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        // when
        Review foundReview = finderService.findReviewById(1L);

        // then
        assertThat(foundReview).isNotNull();
        assertThat(foundReview.getId()).isEqualTo(1L);
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("리뷰 ID로 리뷰 찾기 실패")
    void findReviewById_Fail() {
        // given
        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> finderService
                .findReviewById(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMsg.REVIEW_NOT_FOUND.getDetails());
        verify(reviewRepository, times(1)).findById(anyLong());
    }

}