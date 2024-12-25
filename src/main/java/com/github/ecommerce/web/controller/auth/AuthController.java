package com.github.ecommerce.web.controller.auth;


import com.github.ecommerce.service.auth.AuthService;
import com.github.ecommerce.service.exception.InvalidValueException;
import com.github.ecommerce.service.exception.NotAcceptException;
import com.github.ecommerce.service.exception.NotFoundException;
import com.github.ecommerce.web.advice.ErrorCode;
import com.github.ecommerce.web.dto.auth.LoginRequest;
import com.github.ecommerce.web.dto.auth.SignRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/email")
    public ResponseEntity<String> checkEmail(@Valid@RequestBody SignRequest signUpRequest, BindingResult bindingResult){
        return null;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> register(@RequestParam(value = "profileImage", required = false ) MultipartFile profileImage, @Valid@RequestBody SignRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidValueException(ErrorCode.REGISTER_FAILURE);
        }
        boolean isSuccess = authService.signUp(signUpRequest,profileImage);
        return ResponseEntity.ok(isSuccess ? "회원가입 성공하였습니다." : "회원가입 실패하였습니다.");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("Bearer_Token", token);
        return ResponseEntity.ok("로그인이 성공하였습니다.");
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<String> secession() {
        try {
            String message = authService.secession();
            return ResponseEntity.ok(message);
        } catch (NotAcceptException e) {
            throw new NotAcceptException(ErrorCode.USER_SECESSION_FAILURE);
        } catch (NotFoundException e) {
            throw new NotFoundException(ErrorCode.SECESSION_NOT_FOUND);
        }
    }

}