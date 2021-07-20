package com.basic.process.controllers.book;

import com.basic.BasicApiApplication;
import com.basic.config.common.WebConfig;
import com.basic.process.controllers.AbstractControllerTest;
import com.basic.process.controllers.common.HelloApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiTests extends AbstractControllerTest {

    @Autowired
    private UserApi userApi;

    @Override
    protected Object controller() { return userApi; }

    @Test
    public void getUsers() throws Exception {

        String url = "/api/v1/book/users";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void getUser() throws Exception {

        String url = "/api/v1/book/user/46";

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void save() throws Exception {

        String url = "/api/v1/book/user";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name", "테스트다");
        info.add("email", "email@test.com");

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(info))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    public void modify() throws Exception {

        String url = "/api/v1/book/user";

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("name", "테스트다2");
        info.add("email", "email2@test.com");
        info.add("userSeq", "52");

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(info))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    public void delete() throws Exception {

        String url = "/api/v1/book/user/{userSeq}";

        mockMvc.perform(MockMvcRequestBuilders.delete(url, "52", null)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


}
