package self_learning.coding_game.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import self_learning.coding_game.entities.User;

public class UserRepository implements IUserRepository {

    private final Map<String, User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository() {
        userMap = new HashMap<String, User>();
    }

    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            User u = new User(Integer.toString(autoIncrement), entity.getName(), entity.getScore());
            userMap.put(u.getId(), u);
            return u;
        }
        userMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userMap.values().stream().collect(Collectors.toList());
        return userList;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {

        return false;
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> userList = findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return Optional.ofNullable(user);
            }
        }
        return Optional.empty();
    }

}
