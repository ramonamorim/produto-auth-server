package br.com.empresa.produto.produtoauthserver.models.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Document
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1740681159986633878L;
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private String id;
	private String username;
	private String password;
	private final boolean enabled;
	private List<String> roles = new ArrayList<String>();

	@Builder
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public User(String id, String username, String password, List<String> roles, Boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>(this.roles.size());
		for (String role : this.roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
