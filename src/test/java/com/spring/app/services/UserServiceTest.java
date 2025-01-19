import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import com.spring.app.models.User;
import com.spring.app.repositories.UserRepository;
import com.spring.app.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void createUser() {
        User testUser = new User();
        when(userRepository.save(testUser)).thenReturn(testUser);
        User user = userService.createUser(testUser);
        assertNotNull(user);
    }

    @Test
    void deleteUser(){
        User testUser = new User();
        userService.deleteUser(testUser);
        verify(userRepository).delete(testUser);
    }

    @Test
    void getAllUsers() {
        List<User> test_users = Arrays.asList(
                new User(),
                new User()
        );
        when(userRepository.findAll()).thenReturn(test_users);
        List<User> users = userService.getAllUsers();
        assertEquals(users, test_users);
        verify(userRepository).findAll();
    }

    @Test
    void getUserCount() {
        when(userRepository.count()).thenReturn(Long.valueOf(5));
        long count = userService.getUserCount();
        assertEquals(count,Long.valueOf(5));
        verify(userRepository).count();
    }
}
