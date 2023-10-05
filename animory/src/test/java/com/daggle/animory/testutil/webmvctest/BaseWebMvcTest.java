package com.daggle.animory.testutil.webmvctest;

import com.daggle.animory.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class BaseWebMvcTest {
    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected AccountRepository accountRepository;

}
