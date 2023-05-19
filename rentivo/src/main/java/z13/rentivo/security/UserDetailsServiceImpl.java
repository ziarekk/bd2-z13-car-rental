package z13.rentivo.security;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import z13.rentivo.entities.User;
import z13.rentivo.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    public UserDetailsServiceImpl() {
        this.passwordEncoder = passwordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByLogin(username);
        if(users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        
        User user = users.get(0);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getLogin(), passwordEncoder.encode(user.getHashedPassword()), getAuthority(user));
    }

    private List<GrantedAuthority> getAuthority(User user) {
        // return Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        return Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().getName()));
    }
}
