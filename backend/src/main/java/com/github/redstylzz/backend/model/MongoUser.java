package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("user")
public class MongoUser implements UserDetails {

    @Id
    String id;

    String username;
    String password;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;
    private List<String> rights;

    @Transient
    public static MongoUser dtoToUser(LoginDTO dto) {
        return MongoUser.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }

    @Override
    @Transient
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        if (this.rights == null) return List.of();
        return this.rights.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

}
