package com.github.ecommerce.web.dto.mypage;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentListDTO {
    private List<PaymentDetailDTO> payments;
}
