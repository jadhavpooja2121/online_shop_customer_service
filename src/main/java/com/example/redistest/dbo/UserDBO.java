package com.example.redistest.dbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDBO {
  private Long id;
  private String name;
  private String city;
}
