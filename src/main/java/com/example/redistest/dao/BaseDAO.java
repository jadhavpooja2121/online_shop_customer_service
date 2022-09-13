package com.example.redistest.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseDAO {
  @Autowired
  @Qualifier("sqlSessionTemplateMaster")
  protected SqlSessionTemplate sqlSessionTemplateMaster;

  @Autowired
  @Qualifier("sqlSessionTemplateSlave")
  protected SqlSessionTemplate sqlSessionTemplateSlave;

}
