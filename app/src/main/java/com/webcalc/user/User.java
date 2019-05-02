package com.webcalc.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class User implements UserDetails {

  public final UUID id;

  private final UserDetails springSecUser;

  public User(UUID id, String username, String password) {
    this.id = id;
    springSecUser = org.springframework.security.core.userdetails.User.builder()
        .username(username)
        .password(password)
        .roles()
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return springSecUser.getAuthorities();
  }

  @Override
  public String getPassword() {
    return springSecUser.getPassword();
  }

  @Override
  public String getUsername() {
    return springSecUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return springSecUser.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return springSecUser.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return springSecUser.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return springSecUser.isEnabled();
  }
}
