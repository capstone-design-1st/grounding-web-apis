package org.example.first.groundingwebapis.service;

import org.example.first.groundingwebapis.dto.UserDto;
import org.example.first.groundingwebapis.entity.User;

import java.util.UUID;

public interface UserService {
    boolean userExistsByEmail(String email);

    boolean userExistsByEmailAndPassword(String email, String password);

    UserDto.LoginResponseDto login(UserDto.LoginRequestDto loginRequestDto);

    boolean userExistsByName(String email);

    UserDto.SignUpResponseDto signUp(UserDto.SignUpRequestDto signUpRequestDto);

    boolean userExistsByPhoneNumber(String phoneNumber);

    void deleteUser(Long userId);

    UserDto.GetEmailResponseDto getEmail(Long userId);

    UserDto.GetPhoneNumberResponseDto getPhoneNumber(Long userId);

    UserDto.GetWalletAddressResponseDto getWallet(Long userId);
}
