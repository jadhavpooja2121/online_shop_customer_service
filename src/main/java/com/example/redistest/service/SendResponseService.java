package com.example.redistest.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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
  
  @NoArgsConstructor
  @Data
  @AllArgsConstructor
  private static class Demo {
    String color;
    String type;
  }

  public static final Logger logger = LoggerFactory.getLogger(SendResponseService.class);

  @Async("asyncExecutor")
  public String getInventoryItems(CompletableFuture<ResponseEntity<?>> cf)
      throws JsonMappingException, JsonProcessingException {
    String result = null;
    try {
      String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
      Demo demo = JacksonUtils.fromJson(json, Demo.class);
      cf.complete(ResponseEntity.ok(new OkResponseDO<>(demo)));
      result = cf.get().toString();
    } catch (Exception e) {
      logger.info(StringUtils.printStackTrace(e));
    }
    return result;
  }
}
