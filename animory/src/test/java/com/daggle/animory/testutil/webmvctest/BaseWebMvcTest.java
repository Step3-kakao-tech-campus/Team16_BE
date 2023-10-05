package com.daggle.animory.testutil.webmvctest;

import com.daggle.animory.common.config.SpringSecurityConfiguration;
import com.daggle.animory.common.security.TokenProvider;
import com.daggle.animory.common.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
    SpringSecurityConfiguration.class,
    UserDetailsServiceImpl.class,
    TokenProvider.class
})
public abstract class BaseWebMvcTest {
    @Autowired
    protected MockMvc mvc;

}
