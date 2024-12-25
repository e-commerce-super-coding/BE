package com.github.ecommerce.web.dto.mypage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDTO {
    private Integer userId;
    private Integer cartId;
    private Integer bookId;
    private String title;
    private String bookImage;
    private String publisher;
    private String author;
    private Integer price;
    private Integer quantity;
    private Integer stockQuantity;
}
