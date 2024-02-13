package self_learning.coding_game.appConfig;

import self_learning.coding_game.commands.AttendContestCommand;
import self_learning.coding_game.commands.CommandInvoker;
import self_learning.coding_game.commands.CreateContestCommand;
import self_learning.coding_game.commands.CreateQuestionCommand;
import self_learning.coding_game.commands.CreateUserCommand;
import self_learning.coding_game.commands.LeaderBoardCommand;
import self_learning.coding_game.commands.ListContestCommand;
import self_learning.coding_game.commands.ListQuestionCommand;
import self_learning.coding_game.commands.RunContestCommand;
import self_learning.coding_game.commands.WithdrawContestCommand;
import self_learning.coding_game.repositories.ContestRepository;
import self_learning.coding_game.repositories.IContestRepository;
import self_learning.coding_game.repositories.IQuestionRepository;
import self_learning.coding_game.repositories.IUserRepository;
import self_learning.coding_game.repositories.QuestionRepository;
import self_learning.coding_game.repositories.UserRepository;
import self_learning.coding_game.services.ContestService;
import self_learning.coding_game.services.IContestService;
import self_learning.coding_game.services.IQuestionService;
import self_learning.coding_game.services.IUserService;
import self_learning.coding_game.services.QuestionService;
import self_learning.coding_game.services.UserService;

public class ApplicationConfig {
    private final IQuestionRepository questionRepository = new QuestionRepository();
    private final IUserRepository userRepository = new UserRepository();
    private final IContestRepository contestRepository = new ContestRepository();

    private final IQuestionService questionService = new QuestionService(questionRepository);
    private final IUserService userService = new UserService(userRepository, contestRepository);
    private final IContestService contestService = new ContestService(contestRepository, questionRepository,
            userRepository, userService);

    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final CreateQuestionCommand createQuestionCommand = new CreateQuestionCommand(questionService);
    private final ListQuestionCommand listQuestionCommand = new ListQuestionCommand(questionService);
    private final CreateContestCommand createContestCommand = new CreateContestCommand(contestService);
    private final ListContestCommand listContestCommand = new ListContestCommand(contestService);
    private final AttendContestCommand attendContestCommand = new AttendContestCommand(userService);
    private final RunContestCommand runContestCommand = new RunContestCommand(contestService);
    private final LeaderBoardCommand leaderBoardCommand = new LeaderBoardCommand(userService);
    private final WithdrawContestCommand withdrawContestCommand = new WithdrawContestCommand(userService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("CREATE_USER", createUserCommand);
        commandInvoker.register("CREATE_QUESTION", createQuestionCommand);
        commandInvoker.register("LIST_QUESTION", listQuestionCommand);
        commandInvoker.register("CREATE_CONTEST", createContestCommand);
        commandInvoker.register("LIST_CONTEST", listContestCommand);
        commandInvoker.register("ATTEND_CONTEST", attendContestCommand);
        commandInvoker.register("RUN_CONTEST", runContestCommand);
        commandInvoker.register("LEADERBOARD", leaderBoardCommand);
        commandInvoker.register("WITHDRAW_CONTEST", withdrawContestCommand);
        return commandInvoker;
    }
}
