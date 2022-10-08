package com.example.redistest.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.redistest.config.AsyncConfig;
import com.fantasy.clash.framework.http.dos.BaseResponseDO;
import com.fantasy.clash.framework.http.dos.ErrorResponseDO;
import com.fantasy.clash.framework.http.dos.MessageResponseDO;
import com.fantasy.clash.framework.http.dos.OkResponseDO;
import com.fantasy.clash.framework.utils.JacksonUtils;
import com.fantasy.clash.framework.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class SendResponseService {
  @Autowired
  private AsyncConfig asyncConfig;

  @NoArgsConstructor
  @Data
  @AllArgsConstructor
  private static class Demo {
    String color;
    String type;
  }

  public static final Logger logger = LoggerFactory.getLogger(SendResponseService.class);

  @Async("asyncExecutor")
  public void getInventoryItems(CompletableFuture<ResponseEntity<?>> cf)
      throws JsonMappingException, JsonProcessingException {
    try {

      // String json = "{ \"color\" : \"Black\", \"type\" : \"raw\" }";
      // Demo demo = JacksonUtils.fromJson(json, Demo.class);

      // cf.complete(ResponseEntity.ok(new OkResponseDO<>(demo)));
      cf.complete(ResponseEntity.ok(new OkResponseDO(sendDemo())));
    } catch (Exception e) {
      logger.info(StringUtils.printStackTrace(e));
    }
    return;
  }

  public BaseResponseDO sendDemo() throws JsonMappingException, JsonProcessingException {
    String json = "{ \"color\" : \"Black\", \"type\" : \"raw\" }";
    Demo demo = JacksonUtils.fromJson(json, Demo.class);
    // return demo;
    return new OkResponseDO(demo);
  }

 
  public Void checkInventoryItems(CompletableFuture<ResponseEntity<?>> cf) {
    try {
      BaseResponseDO demo = sendDemo();
      System.out.println(demo.code);
      if (demo == null) {
        cf.complete(ResponseEntity.ok(new ErrorResponseDO(11, "error")));
      } else {
        cf.complete(ResponseEntity.ok(new OkResponseDO(demo)));
      }
      CompletableFuture<Void> cfr = CompletableFuture.runAsync(() -> {
        try {
          List<String> sample = List.of("a", "b");
          System.out.println(sample);
          getInventoryItems(cf);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }, asyncConfig.asyncTaskExecutor());
      cfr.toCompletableFuture();
      System.out.println(cfr.get());
      return cfr.get();
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return null;
  }

  // test supplyAsync
  
  public BaseResponseDO findInventoryItems(CompletableFuture<ResponseEntity<?>> cf) {
    try {
      BaseResponseDO demo = sendDemo();
   //   System.out.println(demo.code);
      if (demo == null) {
       OkResponseDO okResponseDO = new OkResponseDO(demo);
        cf.complete(ResponseEntity.ok(okResponseDO));
        return okResponseDO;
     
      } else {
        ErrorResponseDO errorResponseDO =  new ErrorResponseDO(11, "error");
        cf.complete(ResponseEntity.ok(errorResponseDO));
        return errorResponseDO;
      }
    /*  CompletableFuture<BaseResponseDO> cfr = CompletableFuture.supplyAsync(() -> {
        BaseResponseDO abc;
        try {
          abc = sendDemo();
          return abc;
        } catch (JsonMappingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (JsonProcessingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return null;
      }, asyncConfig.asyncTaskExecutor());
      cfr.toCompletableFuture();
      System.out.println(cfr.get());
      return cfr.get();  */
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return null;
   
  }

}
