package com.github.ecommerce.service.mypage;

import com.github.ecommerce.data.entity.auth.User;
import com.github.ecommerce.data.entity.cart.Cart;
import com.github.ecommerce.data.entity.payment.Payment;
import com.github.ecommerce.data.repository.cart.CartRepository;
import com.github.ecommerce.data.repository.mypage.UserRepository;
import com.github.ecommerce.data.repository.payment.PaymentRepository;
import com.github.ecommerce.service.exception.BadRequestException;
import com.github.ecommerce.service.exception.NotAcceptException;
import com.github.ecommerce.service.exception.NotFoundException;
import com.github.ecommerce.service.s3Image.AwsS3Service;
import com.github.ecommerce.web.advice.ErrorCode;
import com.github.ecommerce.web.advice.ErrorResult;
import com.github.ecommerce.web.dto.mypage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {
    //user repository
    private final UserRepository userRepository;
    // s3 service
    private final AwsS3Service awsS3Service;

    //cart repository
    private final CartRepository cartRepository;
    //payment repository
    private final PaymentRepository paymentRepository;


    //유저 정보 가지고오기
    public UserInfoDTO getUserInfo(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUNDED));

        UserInfoDTO result = userInfoMapping(user);

        return result;
    }

    //user 를 userInfo로 매핑
    public UserInfoDTO userInfoMapping(User user) {
        UserInfoDTO userInfo = UserInfoDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .profileImage(user.getProfileImage())
                .gender(user.getGender())
                .phone(user.getPhone())
                .zipCode(user.getZipCode())
                .mainAddress(user.getMainAddress())
                .detailsAddress(user.getDetailAddress())
                .build();

        return userInfo;
    }


    //유저 정보 수정하기
    @Transactional(transactionManager = "tmJpa1")
    public UserInfoDTO putUserInfo(String id, UserInfoDTO userInfo, MultipartFile image) {

        Integer userId = Integer.valueOf(id);
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorCode.USER_NOT_FOUNDED));

        //file이 들어온다면?
        if(image != null) {
            //s3 이미지 등록
            String newImageUrl = awsS3Service.upload(image);
            String basicImageUrl = "https://project2-profile.s3.ap-northeast-2.amazonaws.com/basic.jpg";
            if(!basicImageUrl.equals(user.getProfileImage())){
                //이미 기존의 이미지가 있으므로 기존 이미지를 삭제한다.
                awsS3Service.deleteImageFromS3(user.getProfileImage());
            }
            user.setProfileImage(newImageUrl); //프로필사진 업데이트
        }

        if(userInfo.getUserName() != null && !userInfo.getUserName().isEmpty()){
            user.setUserName(userInfo.getUserName()); //이름 업데이트
        }
        if(userInfo.getPhone() != null && !userInfo.getPhone().isEmpty()){
            user.setPhone(userInfo.getPhone()); //전화번호 업데이트
        }
        if(userInfo.getGender() != null && !userInfo.getGender().isEmpty()){
            user.setGender(userInfo.getGender()); //성별 업데이트
        }
        if(userInfo.getZipCode() != null && !userInfo.getZipCode().isEmpty()){
            user.setZipCode(userInfo.getZipCode()); //우편번호 업데이트
        }
        if(userInfo.getMainAddress() != null && !userInfo.getMainAddress().isEmpty()){
            user.setMainAddress(userInfo.getMainAddress()); //기본주소 업데이트
        }
        if(userInfo.getDetailsAddress() != null && !userInfo.getDetailsAddress().isEmpty()){
            user.setDetailAddress(userInfo.getDetailsAddress()); //상세주소 업데이트
        }

        UserInfoDTO result = userInfoMapping(user);

        return result;
    }

    //장바구니 목록 가지고오기
    public CartListDTO getCartItems(Integer userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        List<CartDetailDTO> items = cartList.stream().map(
                //Cart -> CartDetailDTO
                item -> CartDetailDTO.builder()
                        .userId(item.getUser().getUserId())
                        .cartId(item.getCartId())
                        .bookId(item.getBook().getBookId())
                        .title(item.getBook().getTitle())
                        .price((int) item.getBook().getPrice())
                        .author(item.getBook().getAuthor())
                        .publisher(item.getBook().getPublisher())
                        .bookImage(item.getBook().getBookImageUrl())
                        .quantity(item.getQuantity())
                        .stockQuantity(item.getBook().getStockQuantity())
                        .build()
        ).toList();

        CartListDTO result = new CartListDTO();
        result.setCartItems(items);
        return result;
    }

    //장바구니 상세
    public CartListDTO getCartItem(Integer userId, String id) {
        Integer cartId = Integer.valueOf(id);
        Cart item = cartRepository.findByIdFetchJoin(cartId).orElse(null);
        if(item == null) {
            throw new NotFoundException(ErrorCode.CART_NOT_FOUNDED);
        }else if(!item.getUser().getUserId().equals(userId)) {
            throw new NotAcceptException(ErrorCode.USER_ERROR_FORBIDDEN);
        }
        CartDetailDTO cart = CartDetailDTO.builder()
                .userId(item.getUser().getUserId())
                .cartId(item.getCartId())
                .bookId(item.getBook().getBookId())
                .title(item.getBook().getTitle())
                .price((int) item.getBook().getPrice())
                .author(item.getBook().getAuthor())
                .publisher(item.getBook().getPublisher())
                .bookImage(item.getBook().getBookImageUrl())
                .quantity(item.getQuantity())
                .stockQuantity(item.getBook().getStockQuantity())
                .build();


        List<CartDetailDTO> carts = new ArrayList<>();
        carts.add(cart);
        CartListDTO result = new CartListDTO();
        result.setCartItems(carts);

        return result;
    }

    //장바구니 옵션 수정
    @Transactional(transactionManager = "tmJpa1")
    public String putCartOption(Integer userId, CartDetailDTO cartDetailDTO) {
        Cart cartItem = cartRepository.findByIdFetchJoin(cartDetailDTO.getCartId()).orElse(null);
        if(cartItem == null) {
            throw new BadRequestException(ErrorCode.CART_ERROR);
        }else if(!cartItem.getUser().getUserId().equals(userId)){
            throw new NotAcceptException(ErrorCode.USER_ERROR_FORBIDDEN);
        }else if(cartItem.getBook().getStockQuantity() < cartDetailDTO.getQuantity()){
            throw new NotAcceptException(ErrorCode.CART_QUANTITY_ERROR);
        }
        cartItem.setQuantity(cartDetailDTO.getQuantity());
        return "장바구니 정보를 수정하셨습니다.";
    }

    //장바구니 삭제
    @Transactional(transactionManager = "tmJpa1")
    public String deleteCartItems(Integer userId, List<CartDetailDTO>  cartDetailDTOs) {

        // 각 항목에 대해 삭제 처리
        for (CartDetailDTO cartDetailDTO : cartDetailDTOs) {
            Cart cartItem = cartRepository.findById(cartDetailDTO.getCartId()).orElse(null);

            if(cartItem == null) {
                throw new BadRequestException(ErrorCode.CART_ERROR);
            }else if(!cartItem.getUser().getUserId().equals(userId)){
                throw new NotAcceptException(ErrorCode.CART_ID_ACCESS_ERROR);
            }
            cartRepository.delete(cartItem);
        }

        return "선택하신 목록을 지우셨습니다.";
    }



    //결제 내역 목록
    public PaymentListDTO getPaymentList(Integer userId) {
        List<Payment> paymentList = paymentRepository.findAllByUser_UserId(userId);

        List<PaymentDetailDTO> itmes = paymentList.stream().map(
                item -> PaymentDetailDTO.builder()
                        .userId(userId)
                        .paymentId(item.getPaymentId())
                        .paymentCard(item.getPaymentCard())
                        .zipCode(item.getZipCode())
                        .mainAddress(item.getMainAddress())
                        .detailsAddress(item.getDetailsAddress())
                        .totalPrice(Math.round(item.getTotalPrice()) )
                        .receiverName(item.getReceiverName())
                        .receiverPhone(item.getReceiverPhone())
                        .paymentDate(item.getPaymentDate())
                        .expectedDelivery(item.getExpectedDelivery())
                        .paymentProducts(item.getPaymentProducts().stream().map(
                                product -> PaymentProductDTO.builder()
                                        .paymentProductId(product.getPaymentProductId())
                                        .bookId(product.getBook().getBookId())
                                        .title(product.getBook().getTitle())
                                        .publisher(product.getBook().getPublisher())
                                        .bookImage(product.getBook().getBookImageUrl())
                                        .author(product.getBook().getAuthor())
                                        .eachPrice((int) product.getBook().getPrice())
                                        .eachTotalPrice(Math.round(product.getTotalPrice()))
                                        .quantity(product.getQuantity())
                                        .build()
                        ).toList())
                        .build()
        ).toList();

        PaymentListDTO result = new PaymentListDTO();
        result.setPayments(itmes);
        return result;
    }

    //결제내역 상세
    public PaymentListDTO getPaymentDetail(Integer userId, String id) {
        Integer paymentId = Integer.valueOf(id);

        Payment payment = paymentRepository.findByIdJoinPaymentProduct(paymentId).orElseThrow(()->new NotFoundException(ErrorCode.PAYMENT_NOT_FOUNDED));

        PaymentDetailDTO detailInfo = PaymentDetailDTO.builder()
                .userId(userId)
                .paymentId(payment.getPaymentId())
                .paymentCard(payment.getPaymentCard())
                .zipCode(payment.getZipCode())
                .mainAddress(payment.getMainAddress())
                .detailsAddress(payment.getDetailsAddress())
                .totalPrice(Math.round(payment.getTotalPrice()) )
                .receiverName(payment.getReceiverName())
                .receiverPhone(payment.getReceiverPhone())
                .paymentDate(payment.getPaymentDate())
                .expectedDelivery(payment.getExpectedDelivery())
                .paymentProducts(payment.getPaymentProducts().stream().map(
                        product -> PaymentProductDTO.builder()
                                .paymentProductId(product.getPaymentProductId())
                                .bookId(product.getBook().getBookId())
                                .title(product.getBook().getTitle())
                                .publisher(product.getBook().getPublisher())
                                .bookImage(product.getBook().getBookImageUrl())
                                .author(product.getBook().getAuthor())
                                .eachPrice((int) product.getBook().getPrice())
                                .eachTotalPrice(Math.round(product.getTotalPrice()))
                                .quantity(product.getQuantity())
                                .build()
                ).toList())
                .build();


        List<PaymentDetailDTO> payments = new ArrayList<>();
        payments.add(detailInfo);

        PaymentListDTO result = new PaymentListDTO();
        result.setPayments(payments);

        return result;
    }

}
