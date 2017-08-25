package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.User;
import com.example.repsoitory.UserRepository;
import com.example.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {
	
	
	private List<User> users;
   @Autowired
   UserRepository userrep;
	 
    public RestApiController() {
        users = new ArrayList<>();
        users.add(new User(1,"prasad",23,1.1));
        users.add(new User(2,"prasad",24,2.0));        
    }
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> listAllUsers() {
        //List<User> use = users;
        List<User> use = (List<User>) userrep.findAll();
        //List<User> users = new ArrayList<>();
        //users.add(new User(1,"prasad",23,1.1));
        //users.add(new User(2,"prasad",24,2.0));
        System.out.println("haiiiiiiii");
        if (use.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(use, HttpStatus.OK);
    }
	
	
	// -------------------Retrieve Single User------------------------------------------
	@GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        User user = userrep.findOne(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("User with id "+id+"not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
	
	// -------------------Create a User-------------------------------------------
	 
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        
        if (userrep.exists(user.getId())) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        userrep.save(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    

}
