package self_learning.coding_game.repositories;

import java.util.Optional;

import self_learning.coding_game.entities.User;

public interface IUserRepository extends CRUDRepository<User, String> {
    public Optional<User> findByName(String name);
}
