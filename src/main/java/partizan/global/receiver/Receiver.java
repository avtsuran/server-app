package partizan.global.receiver;

import partizan.global.model.Data;
import partizan.global.model.Operation;
import partizan.global.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Receiver {

    private static final String DESTINATION_SERVER = "destination.server";
    private static final String DESTINATION_CLIENT = "destination.client";
    private static final String FACTORY = "factory";
    private static final String SAVED = "saved";
    private static final String DELETED = "deleted";
    private static final String RETRIEVE = "retrieved";


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @JmsListener(destination = DESTINATION_SERVER, containerFactory = FACTORY)
    @SendTo(DESTINATION_CLIENT)
    public Data receiveMessage(Data inputData) {
        Data outputData = new Data();
        if (inputData.getOperation() == Operation.SAVE) {
            userRepository.saveAll(inputData.getUsers());
            outputData.setStatus(SAVED);
        } else if (inputData.getOperation() == Operation.DELETE) {
            userRepository.deleteAll(inputData.getUsers());
            outputData.setStatus(DELETED);
        } else if (inputData.getOperation() == Operation.RETRIEVE) {
            outputData.setUsers(userRepository.findAll());
            outputData.setStatus(RETRIEVE);
        }
        return outputData;
    }
}
