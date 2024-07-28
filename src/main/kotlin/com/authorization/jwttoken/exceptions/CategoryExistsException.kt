package com.authorization.jwttoken.exceptions

class CategoryExistsException (category: String) : RuntimeException("User already the category $category added")
