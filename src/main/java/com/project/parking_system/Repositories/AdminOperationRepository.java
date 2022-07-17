package com.project.parking_system.Repositories;

import com.google.gson.Gson;
import com.project.parking_system.datamodel.EmployeeDTO;
import com.project.parking_system.datamodel.EmployeeRegistrationDTO;
import com.project.parking_system.datamodel.ResponseDTO;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminOperationRepository {

    private String url="http://springrestserver-env.eba-gywympmc.ap-northeast-1.elasticbeanstalk.com";

    public ResponseDTO getAllEmployees(String token){
        url=url+"/api/admin/GetAllEmployees";
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
            List<EmployeeDTO> response = Arrays.asList(gson.fromJson(responseEntity.getBody(),EmployeeDTO[].class));
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

    public ResponseDTO DeleteEmployee(EmployeeDTO employeeDTO,String token){
        url = url+"/api/admin/DeleteEmployee";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(employeeDTO);

        //Compact it into entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else if(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),false);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("Deletion failed for unknown cause",false);
                return responseDTO;
            }
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

    public ResponseDTO ChangeEmployeePassword(EmployeeRegistrationDTO employeeDTO, String token){
        url = url+"/api/admin/ChangePassword";

        //Set Normal headers
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        //Add Authentication here
        httpHeaders.set("Authorization",token);

        //Set Request Body
        Gson gson = new Gson();
        String json = gson.toJson(employeeDTO);

        //Compact it into entity
        HttpEntity<String> entity = new HttpEntity<>(json,httpHeaders);

        //Post request
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,entity,String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),true);
                return responseDTO;
            }else if(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST){
                ResponseDTO responseDTO = new ResponseDTO(responseEntity.getBody(),false);
                return responseDTO;
            }else{
                ResponseDTO responseDTO = new ResponseDTO("Update failed for unknown cause",false);
                return responseDTO;
            }
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
