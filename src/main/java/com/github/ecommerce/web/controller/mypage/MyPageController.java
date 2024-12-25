package com.github.ecommerce.web.controller.mypage;

import com.github.ecommerce.service.exception.BadRequestException;
import com.github.ecommerce.service.exception.NotAcceptException;
import com.github.ecommerce.service.exception.S3Exception;
import com.github.ecommerce.service.exception.S3UpLordException;
import com.github.ecommerce.service.mypage.MyPageService;
import com.github.ecommerce.service.security.CustomUserDetails;
import com.github.ecommerce.web.advice.ErrorCode;
import com.github.ecommerce.web.dto.mypage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;


    // 유저정보 가져오기
    @GetMapping("/getUserInfo")
    public ResponseEntity<UserInfoDTO> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();

        //유저 정보 가지고오기
        UserInfoDTO result = myPageService.getUserInfo(userId);
        return ResponseEntity.ok(result);
    }

    // 유저정보 수정
    @PutMapping("/putUserInfo/{id}")
    public ResponseEntity<UserInfoDTO> putUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String id, @ModelAttribute UserInfoDTO userInfo,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {

        Integer userId = userDetails.getUserId();

        if(!userId.equals(Integer.valueOf(id))){
            throw new NotAcceptException(ErrorCode.ACCESS_DENIED);
        }

        UserInfoDTO result = null;
        try{
            //유저 정보 수정하기
            result = myPageService.putUserInfo(id, userInfo, image);
        } catch (S3Exception e) {
            throw new S3UpLordException(ErrorCode.S3_ERROR);
        } catch (Exception e) {
            throw new NotAcceptException(ErrorCode.USER_INFO_PUT_ERROR);
        }
        return ResponseEntity.ok(result);
    }


    //  장바구니 목록
    @GetMapping("/getCartItems")
    public ResponseEntity<CartListDTO> getCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        Integer userId = userDetails.getUserId();

        //장바구니 목록 가지고오기
        CartListDTO result = myPageService.getCartItems(userId);

        return ResponseEntity.ok(result);
    }

    //장바구니 상세
    @GetMapping("/getCartItem/{id}")
    public ResponseEntity<CartListDTO> getCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String id
    ) {

        Integer userId = userDetails.getUserId();

        //장바구니 상세 가지고오기
        CartListDTO result = myPageService.getCartItem(userId, id);

        return ResponseEntity.ok(result);
    }

    // 장바구니 옵션 수정
    @PutMapping("/putCartOption")
    public ResponseEntity<String> putCartOption(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CartDetailDTO cartDetailDTO
    ) {

        Integer userId = userDetails.getUserId();

        //장바구니 옵션 수정
        String result = myPageService.putCartOption(userId, cartDetailDTO);

        return ResponseEntity.ok(result);
    }

    //  장바구니  삭제
    @DeleteMapping("/deleteCartItems")
    public ResponseEntity<String> deleteCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody List<CartDetailDTO> cartDetailDTOs
    ) {

        Integer userId = userDetails.getUserId();

        if(cartDetailDTOs.size() == 0){
            throw new BadRequestException(ErrorCode.CART_ID_IS_NULL);
        }
        //장바구니 삭제
        String result = myPageService.deleteCartItems(userId, cartDetailDTOs);

        return ResponseEntity.ok(result);
    }


    //결제내역
    @GetMapping("/getPaymentList")
    public ResponseEntity<PaymentListDTO> getPaymentList(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer userId = userDetails.getUserId();

        //결제내역 목록 가지고오기
        PaymentListDTO result = myPageService.getPaymentList(userId);

        return ResponseEntity.ok(result);
    }

    // 결제 상세정보
    @GetMapping("/getPaymentDetail/{id}")
    public ResponseEntity<PaymentListDTO> getPaymentDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String id
    ) {
        Integer userId = userDetails.getUserId();

        //결제내역 상세 가지고오기
        PaymentListDTO result = myPageService.getPaymentDetail(userId, id);

        return ResponseEntity.ok(result);
    }
}
