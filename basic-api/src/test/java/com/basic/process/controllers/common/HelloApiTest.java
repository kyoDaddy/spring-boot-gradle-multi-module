package com.basic.process.controllers.common;

import com.basic.BasicApiApplication;
import com.basic.process.controllers.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= BasicApiApplication.class)
@WebMvcTest(HelloApi.class)
public class HelloApiTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {

        String url = "/api/hello/kyo";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "제목");

        mockMvc.perform(
                    MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
                )
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    System.out.println(response.getContentAsString());
                });

    }

}
