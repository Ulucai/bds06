package com.devsuperior.movieflix.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(UserService.class);	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user==null) {
			logger.error("User not found: "+username);
			throw new UsernameNotFoundException("Email not found!");			
		}
		logger.info("User found: "+username);
		return user;
	}
	
	@Transactional(readOnly=true)
	public UserDTO getCurrentUser()
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(username);		
		UserDTO dto = new UserDTO(user);		
		dto.getRoles().addAll(user.getRoles());
		return dto;
	}
	
	@Transactional(readOnly=true)
	public Page<UserDTO> findAllPaged(Pageable pageable)
	{
		Page<User> users = userRepository.findAll(pageable);
		return users.map(x-> new UserDTO(x));
	}
	
	
}
