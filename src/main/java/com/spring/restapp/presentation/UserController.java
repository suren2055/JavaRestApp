package com.spring.restapp.presentation;

import com.spring.restapp.UserDTO;
import com.spring.restapp.persistence.User;
import com.spring.restapp.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDTO>> getAll() {
        var entities = userRepository.findAll();
        var lst = new ArrayList<UserDTO>();
        for (var x : entities) {
            lst.add(new UserDTO(x.getFirstName(),
                    x.getLastName(), x.getDateOfBirth()));
        }
        return ResponseEntity.ok(lst);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {

        final User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getDateOfBirth());
        final User savedUser = userRepository.save(user);
        final UserDTO responseDto = new UserDTO(savedUser.getFirstName(), savedUser.getLastName(), savedUser.getDateOfBirth());
        return ResponseEntity.ok(responseDto);
    }

}
