package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.controllers;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services.UserService;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<User> createUserRequest(@RequestBody User user){
        User savedUser = userService.create(user);
        ResponseEntity response = new ResponseEntity(savedUser, HttpStatus.CREATED);
        return response;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        ResponseEntity<List<User>> response = new ResponseEntity<>(users, HttpStatus.OK);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        try{
            User user = userService.getUserById(id);
            ResponseEntity<?> response = new ResponseEntity<>(user, HttpStatus.OK);
            return response;
        } catch (UserException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user){
        try{
            User updatedUser = userService.updateUser(id, user);
            ResponseEntity response = new ResponseEntity(updatedUser,HttpStatus.OK);
            return response;

        } catch(UserException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        try{
            userService.deleteUser(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (UserException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


}
