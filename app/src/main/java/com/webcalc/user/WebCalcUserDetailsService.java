package com.webcalc.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebCalcUserDetailsService implements UserDetailsService {

  private static final Map<String, User> users = new HashMap<>();

  static {
    users.put("mmustermann", new User(UUID.randomUUID(), "mmustermann", "9786f3gb4508c2393q7y"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (users.containsKey(username))
      return users.get(username);
    throw new UsernameNotFoundException("User unknown");
  }
}
