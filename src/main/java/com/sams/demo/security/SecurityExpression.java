package com.sams.demo.security;

public class SecurityExpression {

    public static final String READ_QUESTION_ACL =
            "@authenticationService.checkQuestionOwnerShip(authentication, #questionId) " +
            "or hasAnyAuthority('ADMIN', 'TRANSLATOR', 'MODERATOR')";

    public static final String UPDATE_QUESTION_ACL =
            "@authenticationService.checkQuestionOwnerShip(authentication, #questionId) " +
            "or hasAnyAuthority('ADMIN', 'TRANSLATOR')";

    public static final String DELETE_QUESTION_ACL =
            "@authenticationService.checkQuestionOwnerShip(authentication, #questionId) " +
            "or hasAnyAuthority('ADMIN', 'MODERATOR')";
}