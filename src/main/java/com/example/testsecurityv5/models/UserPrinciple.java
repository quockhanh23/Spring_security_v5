package com.example.testsecurityv5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    // Trả về danh sách các quyền (authorities) của người dùng.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<String> list = new ArrayList<>();
        String admin = "ROLE_ADMIN";
        String user = "ROLE_USER";
        list.add(admin);
        list.add(user);

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(admin);
        SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(user);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        authorities.add(simpleGrantedAuthority1);

        return roles;
    }

    // Trả về mật khẩu của người dùng.
    @Override
    public String getPassword() {
        return password;
    }

    //  Trả về tên người dùng (username) của người dùng.
    @Override
    public String getUsername() {
        return username;
    }

    //  Trả về true nếu tài khoản của người dùng chưa hết hạn, false nếu đã hết hạn.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Trả về true nếu tài khoản của người dùng không bị khóa, false nếu bị khóa.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Trả về true nếu thông tin đăng nhập của người dùng chưa hết hạn, false nếu đã hết hạn.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Trả về true nếu tài khoản của người dùng được kích hoạt, false nếu tài khoản bị vô hiệu hóa
    @Override
    public boolean isEnabled() {
        return true;
    }
}
