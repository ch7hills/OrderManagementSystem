package com.hills.jwt.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hills.jwt.models.AuthenticationRequest;
import com.hills.jwt.models.AuthenticationResponse;
import com.hills.jwt.models.JwtUtil;
import com.hills.jwt.models.MyUserDetailsService;

@RestController
public class UserController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtTokenUtil;
	
	 @GetMapping("/")
	 public String home() {
		 return ("<h1>Welcome to HOME</h1>");
	 }
	 
	 @GetMapping("/user")
	 public String getUserDeatils() {
		 return ("<h1>Welcome to User</h1>");
	 }
	 
	 @GetMapping("/admin")
	 public String getAdminDetails() {
		 return ("<h1>Welcome to ADMIN</h1>");
	 }
	 
	 @PostMapping("/login")
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

			try {
				System.out.println(" authenticationRequest :"+authenticationRequest);
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
			}
			catch (BadCredentialsException e) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@/n"+e.getMessage());
				System.out.println(e);
				throw new Exception("Incorrect username or password", e);
			}


			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());

			final String jwt = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		}
}
