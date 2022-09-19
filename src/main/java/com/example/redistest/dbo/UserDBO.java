package com.example.redistest.dbo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="online_users")
public class UserDBO {
  @Id
  private Long id;
  private String name;
  private String city;
}
