package com.authorization.jwttoken.exceptions

class UserExistsException(email: String) : RuntimeException("User with email $email already exists")
