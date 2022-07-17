package com.project.parking_system.Repositories;

import com.google.gson.Gson;
import com.project.parking_system.datamodel.ResponseDTO;
import com.project.parking_system.datamodel.UserDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TellerOperationsRepository {

    private String url="http://springrestserver-env.eba-gywympmc.ap-northeast-1.elasticbeanstalk.com";

    public ResponseDTO getAllParkedUsers(String token){
        url=url+"/api/teller/getparkedusers";
        //Create Headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Compact to Entity
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            Gson gson = new Gson();
            List<UserDTO> response = Arrays.asList(gson.fromJson(responseEntity.getBody(),UserDTO[].class));
            return new ResponseDTO("Success",true,response);
        }catch(HttpClientErrorException ex){
            if(ex.getStatusCode()==HttpStatus.FORBIDDEN){
                return new ResponseDTO("Failed Authentication",false);
            }else{
                return new ResponseDTO(ex.getStatusCode().toString(),false);
            }
        }catch (HttpServerErrorException ex){
            return new ResponseDTO("Server Error",false);
        }
    }
}
