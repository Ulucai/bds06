package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;
	
	@PreAuthorize(value = "hasAnyRole('ROLE_VISITOR','ROLE_MEMBER')")
	@GetMapping("/profile")
	public UserDTO getCurrentUser(){		
		return service.getCurrentUser();
	}
	
	@GetMapping	
	public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable){
		Page<UserDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
}
