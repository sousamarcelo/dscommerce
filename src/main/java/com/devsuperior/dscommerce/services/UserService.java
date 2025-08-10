package com.devsuperior.dscommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projactions.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection>	result = userRepository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}
		
		//montando um objeto usuario que as permissões/roles, dessa forma evitar o problema do lazy da jpa e evita ter que alterar a consulta para .EAGUER que nesse caso que é uma relação muitos para muitos deixando lento
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		for(UserDetailsProjection projection : result) { //pegando as regras do usuarios
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		return user;
	}
	
	//Metoto auxiliar que retorna o usuario logado
	protected User authenticated() {
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //busca o objetio do tipo "Authentication" no contexto do "spring-segurity", ou seja se tiver um user autenticado será capturado nessa variavel do tipo Authentication
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal(); // o tipo "Jwt" consegue ter acesso ao nome de usuario logado, porque ele tem acesso aos "Claims" que foram configurados la na classe "AuthorizationServerConfig" inserindo o username, ou seja consegue buscar o email do user que está no token
			String username = jwtPrincipal.getClaim("username");
			
			return userRepository.findByEmail(username).get(); //precisa do get() porque é uma lista "Optional"
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}		
	}
	
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}
}
