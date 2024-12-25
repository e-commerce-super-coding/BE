package com.github.ecommerce.web.controller.cart;

import com.github.ecommerce.service.cart.CartService;
import com.github.ecommerce.service.exception.InvalidValueException;
import com.github.ecommerce.service.security.CustomUserDetails;
import com.github.ecommerce.web.advice.ErrorCode;
import com.github.ecommerce.web.dto.cart.CartRequest;
import com.github.ecommerce.web.dto.cart.CartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    // 장바구니 담기
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartRequest request, @AuthenticationPrincipal CustomUserDetails user,
    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidValueException(ErrorCode.CART_ERROR);
        }
        CartResponse response = cartService.addToCart(request,user.getUserId());
        return ResponseEntity.ok(response);
    }
}
