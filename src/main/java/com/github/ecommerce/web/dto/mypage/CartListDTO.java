package com.github.ecommerce.web.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartListDTO {
    private DefaultDTO status;
    private List<CartDetailDTO> cartItems;

    public CartListDTO(MyPageStatus myPageStatus) {
        this.status.setMessage(myPageStatus.getMessage());
        this.status.setCode(myPageStatus.getCode());
    }
    public CartListDTO(String msg, int code){
        status.setMessage(msg);
        status.setCode(code);
    }
}