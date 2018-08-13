package partizan.global.receiver;

import partizan.global.model.Data;
import partizan.global.model.Operation;
import partizan.global.model.User;
import partizan.global.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiverTest {

    private static final String NAME = "John Doe";
    private static final String PHONE = "38099001100";
    private static final String EMAIL = "johndoe@gmail.com";
    private static final String SAVED = "saved";
    private static final String DELETED = "deleted";
    private static final String RETRIEVED = "retrieved";

    private Data inputData;
    private User user;
    private List<User> users;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private Receiver receiver;

    @Before
    public void setup() {
        user = createUser();
        users = Arrays.asList(user);
        inputData = new Data();
        inputData.setUsers(users);
    }

    @Test
    public void shouldReceiveData() {
        inputData.setOperation(Operation.RETRIEVE);
        when(userRepository.findAll()).thenReturn(users);
        Data response = receiver.receiveMessage(inputData);

        assertEquals(RETRIEVED, response.getStatus());
        verify(userRepository).findAll();
    }

    @Test
    public void shouldDeleteData() {
        inputData.setOperation(Operation.DELETE);
        Data response = receiver.receiveMessage(inputData);

        assertEquals(DELETED, response.getStatus());
        verify(userRepository).deleteAll(inputData.getUsers());
    }

    @Test
    public void shouldSaveData() {
        inputData.setOperation(Operation.SAVE);
        Data response = receiver.receiveMessage(inputData);

        assertEquals(SAVED, response.getStatus());
        verify(userRepository).saveAll(inputData.getUsers());
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName(NAME);
        user.setPhone(PHONE);
        user.setEmail(EMAIL);
        return user;
    }
}
