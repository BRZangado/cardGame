package com.letscode.cardgame.security;

import com.letscode.cardgame.entity.Player;
import com.letscode.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player usuario = playerRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Player não encontrado!");
        }

        return new SystemUser(usuario.getName(), usuario.getUsername(), usuario.getPassword(), authorities());
    }

    public Collection<? extends GrantedAuthority> authorities() {
        Collection<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return auths;
    }
}