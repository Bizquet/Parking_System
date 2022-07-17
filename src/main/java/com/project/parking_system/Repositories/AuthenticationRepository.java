package com.project.parking_system.Repositories;

import com.google.gson.Gson;
import com.project.parking_system.datamodel.EmployeeRegistrationDTO;
import com.project.parking_system.datamodel.LoginDTO;
import com.project.parking_system.datamodel.ResponseDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationRepository {

    private String url="http://springrestserver-env.eba-gywympmc.ap-northeast-1.elasticbeanstalk.com";

    public LoginDTO login(String username,String password){
        url=url+"/login";

        //Create Headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Map Request body in JSON
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);

        Gson gson = new Gson();
        String json = gson.toJson(map);

        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Create a POST Method
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

    public ResponseDTO RegisterEmployee(EmployeeRegistrationDTO registrationDTO, String token){
        url = url+"/api/admin/register";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(registrationDTO);
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("Registration failed for unknown cause",false);
                return responseDTO;
            }
        }catch(HttpClientErrorException ex){
            if(ex.getStatusCode()==HttpStatus.FORBIDDEN){
                return new ResponseDTO("Failed Authentication",false);
            }
            else if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
                ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(),false);
                return responseDTO;
            }
             else{
                return new ResponseDTO(ex.getStatusCode().toString(),false);
            }
        }catch (HttpServerErrorException ex){
            return new ResponseDTO("Server Error",false);
        }
    }
}
