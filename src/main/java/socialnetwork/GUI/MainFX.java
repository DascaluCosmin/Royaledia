package socialnetwork.GUI;

import com.sun.org.apache.bcel.internal.generic.FMUL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.IntroductionController;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.ProfilePhotoUser;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.User;
import socialnetwork.domain.messages.FriendshipRequest;
import socialnetwork.domain.messages.Message;
import socialnetwork.domain.messages.ReplyMessage;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.UserDBRepository;
import socialnetwork.repository.file.*;
import socialnetwork.service.*;

import java.io.IOException;

public class MainFX extends Application {
    private static UserService userService;
    private static FriendshipService friendshipService;
    private static MessageService messageService;
    private static ReplyMessageService replyMessageService;
    private static FriendshipRequestService friendshipRequestService;
    private static ProfilePhotoUserService profilePhotoUserService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initView(primaryStage);
        primaryStage.setWidth(600);
        primaryStage.setTitle("Berry!");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/berryLogo.jpg")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Configuration
//        String fileNameUsers = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileNameFriendships = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.friendships");
        String fileNameMessage = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.messages");
        String fileNameConversation = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.conversation");
        String fileNameFriendshipRequests = ApplicationContext.getPROPERTIES()
                .getProperty("data.socialnetwork.friendshipRequests");
        String fileNameUserProfilePhotos = ApplicationContext.getPROPERTIES().
                getProperty("data.socialnetwork.userProfilePhotos");
        String username = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        // Repositories
//        Repository<Long, User> userFileRepository = new UserFileRepository(fileNameUsers, new UserValidator());
        Repository<Long, User> userRepository = new UserDBRepository(url, username, password, new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipFileRepository = new FriendshipFileRepository(fileNameFriendships,
                new FriendshipValidator(userRepository), userRepository);
        Repository<Long, Message> messageFileRepository = new MessagesFileRepository(fileNameMessage,
                new MessageValidator(), userRepository);
        Repository<Long, ReplyMessage> replyMessageFileRepository = new ReplyMessageFileRepository(fileNameConversation,
                new ValidatorReplyMessage(), userRepository);
        Repository<Long, FriendshipRequest> friendshipRequestFileRepository = new FriendshipRequestFileRepository(
                fileNameFriendshipRequests, new FriendshipRequestValidator(), userRepository);
        Repository<Long, ProfilePhotoUser> profilePhotoUserFileRepository = new ProfilePhotoUserFileRepository(
                fileNameUserProfilePhotos, new ValidatorProfilePhotoUser());

        // Services
        userService = new UserService(userRepository, friendshipFileRepository);
        friendshipService = new FriendshipService(friendshipFileRepository, userRepository);
        messageService = new MessageService(messageFileRepository);
        replyMessageService = new ReplyMessageService(replyMessageFileRepository);
        friendshipRequestService = new FriendshipRequestService(friendshipRequestFileRepository,
                friendshipFileRepository);
        profilePhotoUserService = new ProfilePhotoUserService(profilePhotoUserFileRepository);
        launch(args);
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/introduction.fxml"));
        AnchorPane layout = loader.load();
        primaryStage.setScene(new Scene(layout));
        IntroductionController introductionController = loader.getController();
        introductionController.setUserService(userService, primaryStage);
        introductionController.setFriendshipService(friendshipService);
        introductionController.setFriendshipRequestService(friendshipRequestService);
        introductionController.setProfilePhotoUserService(profilePhotoUserService);
    }
}
