package com.mindease.DTO;

public record ChangePasswordDTO(
  String oldPassword, 
  String newPassword
){  
}
