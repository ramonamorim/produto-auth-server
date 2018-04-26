package br.com.empresa.produto.produtoauthserver.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Getter;
import lombok.Setter;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8143826185541283249L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private final String username;
	private final String firstname;
	private final String lastname;
	private final String password;
	private final String email;
	private List<String> roles = new ArrayList<String>();
	private final boolean enabled;
	private final Date lastPasswordResetDate;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public User(ObjectId id, String username, String firstname, String lastname, String email, String password,
			List<String> roles, boolean enabled, Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.roles = roles;
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

}
