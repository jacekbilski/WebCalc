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
    users.put("mmustermann", new User(UUID.randomUUID(), "mmustermann", "{noop}9786f3gb4508c2393q7y"));
    users.put("user2_name", new User(UUID.randomUUID(), "user2_name", "{noop}user2_pass"));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (users.containsKey(username))
      return users.get(username);
    throw new UsernameNotFoundException("User unknown");
  }
}
