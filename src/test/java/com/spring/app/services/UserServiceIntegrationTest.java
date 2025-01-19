package com.spring.app.services;

import com.spring.app.config.AbstractIntegrationTest;
import com.spring.app.models.User;
import com.spring.app.enums.user_enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.dao.DataIntegrityViolationException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        userService.getAllUsers().forEach(userService::deleteUser);
    }

    @Test
    void createUser_shouldSaveToDatabase() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setGender(Gender.MALE);

        // Act
        User savedUser = userService.createUser(user);

        // Assert
        assertThat(savedUser.getId()).isNotZero();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    void getAllUsers_shouldReturnCorrectCount() {
        // Arrange
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@test.com");
        user1.setGender(Gender.FEMALE);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@test.com");
        user2.setGender(Gender.OTHER);

        userService.createUser(user1);
        userService.createUser(user2);

        // Act
        long count = userService.getUserCount();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    void deleteUser_shouldReturnZeroUserCount() {
        User user0 = new User();
        user0.setUsername("bla bla");
        user0.setGender(Gender.MALE);
        user0.setEmail("bla bla");

        userService.createUser(user0);
        userService.deleteUser(user0);
        long count = userService.getUserCount();
        assertThat(count).isEqualTo(0);
    }
    @Test
    void uniqueEmailCheck_shouldNotRegisterSecondUser() {
        User user0 = new User();
        user0.setUsername("bsda");
        user0.setGender(Gender.MALE);
        user0.setEmail("asd");

        User user1 = new User();
        user1.setUsername("bla bla");
        user1.setGender(Gender.MALE);
        user1.setEmail("asd");

        userService.createUser(user0);
        assertThatThrownBy(() -> userService.createUser(user1)).isInstanceOf(DataIntegrityViolationException.class);
    }
    @Test
    void uniqueUsernameCheck_shouldNotRegisterSecondUser(){
        User user0 = new User();
        user0.setUsername("bsda");
        user0.setGender(Gender.MALE);
        user0.setEmail("asd");

        User user1 = new User();
        user1.setUsername("bsda");
        user1.setGender(Gender.MALE);
        user1.setEmail("asdasd");

        userService.createUser(user0);
        assertThatThrownBy(() -> userService.createUser(user1)).isInstanceOf(DataIntegrityViolationException.class);
    }
    @Test
    void getAllUsers_shouldBringCorrectUsers(){
        User user0 = new User();
        user0.setUsername("bsda");
        user0.setGender(Gender.MALE);
        user0.setEmail("asd");

        User user1 = new User();
        user1.setUsername("bsda3");
        user1.setGender(Gender.MALE);
        user1.setEmail("asdasd");

        userService.createUser(user0);
        userService.createUser(user1);

        long count = userService.getUserCount();
        List<User> users = userService.getAllUsers();
        assertThat(users).extracting(User::getUsername).containsExactlyInAnyOrder("bsda3","bsda");
    }
}