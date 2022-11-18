package com.spring.restapp.presentation;

import com.spring.restapp.UserDTO;
import com.spring.restapp.persistence.User;
import com.spring.restapp.persistence.UserRepository;
import com.spring.restapp.presentation.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserControllerUnitTests {
    private UserController userController;

    @Test
    void testGetAllUsers() {
        //Arrange
        userController = new UserController(new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<User> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public Iterable<User> findAll() {
                final List<User> users = new ArrayList<>();
                var user = new User();
                user.setId(1L);
                user.setFirstName("John");
                user.setLastName("Doe");
                try {
                    var date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/1998");
                    user.setDateOfBirth(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                users.add(user);
                return users;
            }

            @Override
            public Iterable<User> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }
        });
        //Act
        final ResponseEntity<List<UserDTO>> result = userController.getAll();
        //Assert
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (var x : result.getBody()) {
            Assertions.assertThat(x.getFirstName()).isNotEmpty();
            Assertions.assertThat(x.getLastName()).isNotEmpty();
            Assertions.assertThat(x.getDateOfBirth()).isBefore(new Date());
            //etc. assertions
        }

    }

    @Test
    void testCreateUser() {
        //Arrange
        userController = new UserController(new UserRepository() {
            @Override
            public <S extends User> S save(S entity) {
                return entity;
            }

            @Override
            public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<User> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public Iterable<User> findAll() {
                return null;
            }

            @Override
            public Iterable<User> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }
        });
        //Act
        final ResponseEntity<UserDTO> result = userController.create(
                new UserDTO("Name", "Surname", new Date()));
        //Assert
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody().getFirstName()).isNotNull();
        Assertions.assertThat(result.getBody().getLastName()).isNotNull();
//        Assertions.assertThat(result.getBody().getDateOfBirth()).isBefore(new Date());//interesting case:)
        //etc. assertions
    }

}
