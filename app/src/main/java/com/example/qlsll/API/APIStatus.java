package com.example.qlsll.API;

public enum  APIStatus {
    OK(200, "OK"),
    NO_RESULT(201, "No more result."),
    //////////////////
    // CLIENT SIDE  //
    //////////////////
    ERR_BAD_REQUEST(400, "Bad request"),
    ERR_UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),
    ERR_FORBIDDEN(403, "Forbidden! Access denied"),
    ERR_BAD_PARAMS(406, "Bad parameters"),
    ERR_ALREADY_EXISTED(407, "Already exsited."),
    //////////////////
    // SERVER SIDE  //
    //////////////////
    ERR_INTERNAL_SERVER(500, "Internal Server Error"),
    ERR_CREATE_MODEL(501, "Create model error"),
    //////////////////
    // SESSION SIDE //
    //////////////////
    ERR_TOKEN_NOT_FOUND(600, "Access token not found"),
    ERR_INVALID_TOKEN(601, "Access token is invalid"),
    ERR_TOKEN_EXPIRED(602, "Access token is expired"),
    ERR_SESSION_DATA_INVALID (603, "Invalid session data"),
    ERR_SESSION_NOT_FOUND(604, "Session not found"),
    ERR_ACCOUNT_INVALID (605, "Invalid account"),
    ERR_CREATE_USER_SESSION(606, "Create User Session fail"),
    ERR_CREATE_ADMIN_SESSION(607, "Create Admin Session fail"),
    //////////////////
    // DATABASE SIDE//
    //////////////////
    ERR_INCORRECT_MODEL_DATA(700, "Incorrect model data"),
    ERR_USER_NOT_FOUND(701, "User not found."),
    ERR_PASSWORD_NOT_MATCH(702, "Password doesn't match"),
    ERR_EMAIL_ALREADY_EXISTS(703, "Email already exists"),
    ERR_FOLDER_NOT_FOUND(704, "Folder not found"),
    ERR_CREATE_CLOUD_USER(705, "Create Box cloud user fail"),
    ERR_CREATE_USER(706, "Create User fail"),
    ERR_EMAIL_INVALID(709, "Email is invalid"),
    ERR_SIGN_UP(710, "Sign up User Error"),
    ERR_INVALID_PARAM(711, "Param is invalid"),
    ERR_EXIST_EMAIL(712, "Email already exists"),
    ERR_EXIST_USER_NAME(713, "User Name already exists"),
    ERR_RESET_CODE(714, "User reset code Error"),
    ERR_FORGOT_PASS(715, "Forgot password Error"),
    ERR_RESET_CODE_EXPIRY(716, "Reset code expiry"),
    ERR_OLD_PASS(717, "Old password incorect"),
    ERR_ADDRESSBOKK_NOT_FOUND(719,"Address book not found"),
    ERR_PHONE_INVALID(720,"Phone is invalid"),
    ERR_GROUP_NOT_FOUND(721,"Group address book not found"),
    ERR_GROUP_NEW_NOT_FOUND(722,"New Group book not found"),
    ERR_UPDATE_ADDRESSBOOK_ERROR(723,"Update address book error"),
    ERR_UPDATE_GROUP_ADDRESSBOOK_ERROR(724,"Update group address book error");
    private final int code;
    private final String description;

    APIStatus(int s, String v) {
        code = s;
        description = v;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
