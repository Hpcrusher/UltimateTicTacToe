package hpcrusher.services;

import hpcrusher.exception.ValidationRuntimeException;
import hpcrusher.model.Person;
import hpcrusher.model.User;
import hpcrusher.repository.PersonRepository;
import hpcrusher.repository.UserRepository;
import hpcrusher.util.TextKey;
import hpcrusher.web.model.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vm
 */

@Service
public class RegistrationService {

    private PersonRepository personRepository;
    private UserRepository userRepository;

    @Autowired
    public RegistrationService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public void register(final SignupDto signupDto) {
        if (signupDto.username.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "username");
        }
        if (signupDto.password.trim().isEmpty()) {
            throw new ValidationRuntimeException(new TextKey("validation.notEmpty"), "password");
        }
        if (userRepository.findByUserName(signupDto.username) != null) {
            throw new ValidationRuntimeException(new TextKey("validation.usernameAlreadyExists"), "username");
        }
        Person person = Person.builder().createPerson();
        personRepository.save(person);
        User user = User.builder().setPerson(person).setUserName(signupDto.username.trim()).setPassword(signupDto.password.trim()).createUser();
        userRepository.save(user);
    }

}
