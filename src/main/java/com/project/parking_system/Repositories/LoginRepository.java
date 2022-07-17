package com.project.parking_system.Repositories;

import com.google.gson.Gson;
import com.project.parking_system.datamodel.LoginDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginRepository {

    private final String url="http://springrestserver-env.eba-gywympmc.ap-northeast-1.elasticbeanstalk.com/login";

    public LoginDTO login(String username,String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);

        Gson gson = new Gson();
        String json = gson.toJson(map);

        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

        LoginDTO loginDTO = new LoginDTO();
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            loginDTO.setLogin_token(responseEntity.getHeaders().getFirst("Authorization"));
            loginDTO.setRole(responseEntity.getHeaders().getFirst("Expect"));
        }else{
            loginDTO.setRole(null);
            loginDTO.setLogin_token(null);
        }
        return loginDTO;
    }
}
