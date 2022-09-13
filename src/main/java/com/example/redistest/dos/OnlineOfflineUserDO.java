package com.example.redistest.dos;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnlineOfflineUserDO {
  private Set<Long> offlineOfflineUserIds;
}
