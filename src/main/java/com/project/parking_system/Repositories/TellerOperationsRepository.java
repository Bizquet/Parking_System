package com.project.parking_system.Repositories;

import com.google.gson.Gson;
import com.project.parking_system.datamodel.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    public ResponseDTO AddUser(UserDTO userDTO,String token){
        url = url+"/api/teller/add";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(userDTO);

        //Compact it into entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("User Creation failed for unknown cause",false);
                return responseDTO;
            }
        }catch(HttpClientErrorException ex){
            if(ex.getStatusCode()==HttpStatus.FORBIDDEN){
                return new ResponseDTO("Failed Authentication",false);
            }else if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
                return new ResponseDTO("Incorrect User Credentials",false);
            }else if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
                return new ResponseDTO("User already created",false);
            }
            else{
                return new ResponseDTO(ex.getStatusCode().toString(),false);
            }
        }catch (HttpServerErrorException ex){
            return new ResponseDTO("Server Error",false);
        }
    }

    public ResponseDTO getAllUsers(String token) {
        url = url + "/api/teller/getallusers";
        //Create Headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization", token);

        //Compact to Entity
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class);

            Gson gson = new Gson();
            List<UserDTOWithTimestampList> response = Arrays.asList(gson.fromJson(responseEntity.getBody(), UserDTOWithTimestampList[].class));
            return new ResponseDTO("Success", true, response);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
                return new ResponseDTO("Failed Authentication", false);
            } else {
                return new ResponseDTO(ex.getStatusCode().toString(), false);
            }
        } catch (HttpServerErrorException ex) {
            return new ResponseDTO("Server Error", false);
        }
    }
    public ResponseDTO getUserInfo(String uid,String token){
        url=url+"/api/teller/getuserinfo";
        //Create Headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        Gson gson = new Gson();
        String json = gson.toJson(map);

        //Compact to Entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class);

            Gson gson1 = new Gson();
            UserDTO response = gson1.fromJson(responseEntity.getBody(),UserDTO.class);
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

    public ResponseDTO SignoutUser(SignOutDTO signOutDTO, String token){
        url = url+"api/teller/signout";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(signOutDTO);

        //Compact it into entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("Signout failed for unknown cause",false);
                return responseDTO;
            }
        }catch(HttpClientErrorException ex){
            if(ex.getStatusCode()==HttpStatus.FORBIDDEN){
                return new ResponseDTO("Failed Authentication",false);
            }else if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
                return new ResponseDTO("Incorrect User Credentials",false);
            }else if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
                return new ResponseDTO("Already Signed out",false);
            }
            else{
                return new ResponseDTO(ex.getStatusCode().toString(),false);
            }
        }catch (HttpServerErrorException ex){
            return new ResponseDTO("Server Error",false);
        }
    }

    public ResponseDTO SigninUser(SigninDTO signinDTO, String token){
        url = url+"api/teller/signin";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(signinDTO);

        //Compact it into entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("Signin failed for unknown cause",false);
                return responseDTO;
            }
        }catch(HttpClientErrorException ex){
            if(ex.getStatusCode()==HttpStatus.FORBIDDEN){
                return new ResponseDTO("Failed Authentication",false);
            }else if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
                return new ResponseDTO("Incorrect User Credentials",false);
            }else if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
                return new ResponseDTO("Already Signed in",false);
            }
            else{
                return new ResponseDTO(ex.getStatusCode().toString(),false);
            }
        }catch (HttpServerErrorException ex){
            return new ResponseDTO("Server Error",false);
        }
    }
}
