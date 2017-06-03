package hpcrusher.services;

import hpcrusher.model.Person;
import hpcrusher.model.User;
import hpcrusher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liebl
 */

@Service
public class NameResolverService {

    private final UserRepository userRepository;

    @Autowired
    public NameResolverService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername(Person person) {
        User user = userRepository.findByPerson(person);
        if (user == null) {
            return null;
        } else {
            return user.getUserName();
        }
    }
}
