package francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.services;

import francis.janaj.spring.boot.realestatelistingserver.domain.core.exceptions.UserException;
import francis.janaj.spring.boot.realestatelistingserver.domain.realEstateListing.models.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User getUserById(Integer id) throws UserException;
    List<User> getAllUsers();
    User updateUser(Integer id, User user) throws UserException;
    Boolean deleteUser(Integer id) throws UserException;
}
