package self_learning.coding_game.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import self_learning.coding_game.dtos.UserRegistrationDto;
import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.ContestStatus;
import self_learning.coding_game.entities.RegistrationStatus;
import self_learning.coding_game.entities.ScoreOrder;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidOperationException;
import self_learning.coding_game.exceptions.UserNotFoundException;
import self_learning.coding_game.repositories.IContestRepository;
import self_learning.coding_game.repositories.IUserRepository;

public class UserService implements IUserService {

        private final IUserRepository userRepository;
        private final IContestRepository contestRepository;

        public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
                this.userRepository = userRepository;
                this.contestRepository = contestRepository;
        }

        @Override
        public User create(String name) {
                User newUser = new User(name, 0);
                return userRepository.save(newUser);
        }

        @Override
        public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder) {
                List<User> userScoreList = userRepository.findAll();
                Comparator<User> scoreComparator = Comparator.comparingInt(User::getScore);

                if (ScoreOrder.ASC.equals(scoreOrder)) {
                        return userScoreList.stream().sorted(scoreComparator)
                                        .collect(Collectors.toList());
                } else {
                        return userScoreList.stream().sorted(scoreComparator.reversed())
                                        .collect(Collectors.toList());
                }
        }

        @Override
        public UserRegistrationDto attendContest(String contestId, String userName)
                        throws ContestNotFoundException, UserNotFoundException,
                        InvalidOperationException {
                Contest contest = contestRepository.findById(contestId)
                                .orElseThrow(() -> new ContestNotFoundException(
                                                "Cannot Attend Contest. Contest for given id:"
                                                                + contestId + " not found!"));
                User user = userRepository.findByName(userName)
                                .orElseThrow(() -> new UserNotFoundException(
                                                "Cannot Attend Contest. User for given name:"
                                                                + userName + " not found!"));
                if (contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)) {
                        throw new InvalidOperationException(
                                        "Cannot Attend Contest. Contest for given id:" + contestId
                                                        + " is in progress!");
                }
                if (contest.getContestStatus().equals(ContestStatus.ENDED)) {
                        throw new InvalidOperationException(
                                        "Cannot Attend Contest. Contest for given id:" + contestId
                                                        + " is ended!");
                }
                if (user.checkIfContestExists(contest)) {
                        throw new InvalidOperationException(
                                        "Cannot Attend Contest. Contest for given id:" + contestId
                                                        + " is already registered!");
                }
                user.addContest(contest);
                userRepository.save(user);
                return new UserRegistrationDto(contest.getName(), user.getName(),
                                RegistrationStatus.REGISTERED);
        }

        @Override
        public UserRegistrationDto withdrawContest(String contestId, String userName)
                        throws ContestNotFoundException, UserNotFoundException,
                        InvalidOperationException {
                Contest contest = contestRepository.findById(contestId)
                                .orElseThrow(() -> new ContestNotFoundException(
                                                "Contest not found with ID: " + contestId));
                User user = userRepository.findByName(userName)
                                .orElseThrow(() -> new UserNotFoundException(
                                                "User not found with name: " + userName));
                if (contest.getContestStatus() == ContestStatus.IN_PROGRESS
                                || contest.getContestStatus() == ContestStatus.ENDED
                                || contest.getCreator().equals(user)
                                || user.checkIfContestExists(contest) == false) {
                        throw new InvalidOperationException(
                                        "Cannot withdraw from contest, Invalid Operation Exception");
                }
                user.deleteContest(contest);
                userRepository.save(user);
                return new UserRegistrationDto(contest.getName(), user.getName(),
                                RegistrationStatus.NOT_REGISTERED);
        }

}
