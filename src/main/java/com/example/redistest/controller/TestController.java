package com.example.redistest.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import com.example.redistest.config.AsyncConfig;
import com.example.redistest.dao.OnlineOfflineUserDAO;
import com.example.redistest.dbo.UserDBO;
import com.example.redistest.dos.OnlineOfflineUserDO;
import com.example.redistest.repository.UserRepository;
import com.example.redistest.service.LuaTestService;
import com.example.redistest.service.OnlineOfflineUsersService;
import com.example.redistest.service.OnlineUsersService;
import com.example.redistest.service.SendResponseService;
import com.example.redistest.service.UserService;
import com.fantasy.clash.framework.http.controller.BaseController;
import com.fantasy.clash.framework.http.dos.BaseResponseDO;
import com.fantasy.clash.framework.http.header.dos.LoginContext;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.utils.JacksonUtils;
import com.fantasy.clash.framework.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("v1/core_engine")
public class TestController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(TestController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private OnlineUsersService onlineUsersService;

  @Autowired
  private OnlineOfflineUsersService onlineOfflineUsersService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LuaTestService luaTestService;

  @Autowired
  private SendResponseService sendResponseService;

  @Autowired
  private AsyncConfig asyncConfig;
  @GetMapping(value = "/users/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> getUsersByCity(@PathVariable String city,
      HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/users/{city}";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      userService.getUsersByCity(city, cf);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  @GetMapping(value = "/online_users/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> getOnlineUsersByCity(@PathVariable String city,
      HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/online_users/{city}";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      onlineUsersService.getOnlineUsersByCity(city, cf);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  @GetMapping(value = "/online_offline_users", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> getOnlineUsersByCity(HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/online_offline_users";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      onlineOfflineUsersService.getUsersByCity("Nashik", "Nashik", cf, DataSource.REDIS);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  @GetMapping(value = "/user_data", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> storeUsersData(HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/users";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      userService.saveDataIntoRedis(cf, DataSource.REDIS);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> verifyLua(HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/users";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      luaTestService.testLua("name", "Pooja", cf);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  // send response test
  @GetMapping(value = "/send_response", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> sendResponse(HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/users";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      sendResponseService.getInventoryItems(cf);
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }

  // Get response test
  @GetMapping(value = "/check_response", produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<ResponseEntity<?>> checkResponse(HttpServletRequest request) {
    Long startTime = System.currentTimeMillis();
    String apiEndPoint = "/v1/core_engine/users";
    DeferredResult<ResponseEntity<?>> df = new DeferredResult<ResponseEntity<?>>();
    try {
      LoginContext loginContext = getLoginContext(request);
      CompletableFuture<ResponseEntity<?>> cf = new CompletableFuture<ResponseEntity<?>>();
      var cfr = CompletableFuture.supplyAsync(() -> {
        try {
          return sendResponseService.getInventoryItems(cf);
        } catch (JsonMappingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (JsonProcessingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return null;
      }, asyncConfig.asyncTaskExecutor());
       System.out.println(cfr.toString());
        //     ResponseEntity<?> sendServiceResponse = cf.get();
//      String response = sendServiceResponse.getBody().toString();
//      BaseResponseDO baseResponse = JacksonUtils.fromJson(response, BaseResponseDO.class);
//      if (baseResponse.code == HttpStatus.OK.value()) {
//        System.out.println("Code works");
//      } else {
//        System.out.println("Code failed");
//      }
      this.processDeferredResult(df, cf, apiEndPoint, startTime, loginContext.getReqId());
    } catch (Exception e) {
      logger.error(StringUtils.printStackTrace(e));
    }
    return df;
  }


  @GetMapping("/mongotest")
  public List<UserDBO> getUsers() {
    return userRepository.findAll();
  }

  @PostMapping("/insert")
  public ResponseEntity<?> add(@RequestBody UserDBO user) {
    UserDBO save = this.userRepository.save(user);
    return ResponseEntity.ok(save);
  }
}
