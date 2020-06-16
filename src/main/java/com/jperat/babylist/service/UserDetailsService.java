package com.jperat.babylist.service;

import com.jperat.babylist.entity.Role;
import com.jperat.babylist.entity.User;
import com.jperat.babylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnable(),
                true, true, true, buildUserAuthority(user.getRoles()));
    }

    private List<GrantedAuthority> buildUserAuthority(Collection<Role> Roles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (Role Role : Roles) {
            setAuths.add(new SimpleGrantedAuthority(Role.getRole()));
        }

        return new ArrayList<GrantedAuthority>(setAuths);
    }
}
