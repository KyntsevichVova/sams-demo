package com.sams.demo;

import com.sams.demo.service.impl.QuestionService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest(classes = {DemoApplication.class})
@SqlGroup({
        @Sql(scripts = {"/sql/schema.sql", "/sql/data.sql"}),
        @Sql(scripts = "/sql/drop.sql", executionPhase = AFTER_TEST_METHOD)
})
public abstract class AbstractUnitTest {

    private Pair<String, String> user = new ImmutablePair<>("user@demo.com", "user");
    private Pair<String, String> admin = new ImmutablePair<>("admin@demo.com", "admin");
    private Pair<String, String> moderator = new ImmutablePair<>("moderator@demo.com", "moderator");
    private Pair<String, String> translator = new ImmutablePair<>("translator@demo.com", "translator");

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    protected QuestionService questionService;

    @AfterEach
    public void cleanUpAuthentication() {

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected void authenticateAsUser() {
        authenticate(user.getKey(), user.getValue());
    }

    protected void authenticateAsAdmin() {
        authenticate(admin.getKey(), admin.getValue());
    }

    protected void authenticateAsModerator() {
        authenticate(moderator.getKey(), moderator.getValue());
    }

    protected void authenticateAsTranslator() {
        authenticate(translator.getKey(), translator.getValue());
    }

    private void authenticate(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}