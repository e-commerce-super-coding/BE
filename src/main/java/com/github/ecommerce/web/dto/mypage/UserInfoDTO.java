package com.github.ecommerce.web.dto.mypage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
    private Integer userId;
    private String email;
    private String userName;
    private String profileImage;
    private String gender;
    private String phone;
    private String zipCode;
    private String mainAddress;
    private String detailsAddress;
}
