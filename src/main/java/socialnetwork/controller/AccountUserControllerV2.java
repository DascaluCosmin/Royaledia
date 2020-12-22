package socialnetwork.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socialnetwork.domain.Page;

public class AccountUserControllerV2 {
    private Page userPage;
    private Stage accountUserStage;
    private Stage loginStage;

    @FXML
    Label labelRealName;
    @FXML
    Label labelUsername;
    @FXML
    Pane statisticsPane;
    @FXML
    Pane loggedInUserPane;

    /**
     * @param userPage Page, representing the Page of the logged in User.
     */
    public void setUserPage(Page userPage) {
        this.userPage = userPage;
        labelRealName.setText(userPage.getUser().getFullName());
        labelUsername.setText("@" + userPage.getUserCredentialsService().findOne(userPage.getUser().getId()).getUsername());
    }

    /**
     * @param accountUserStage Stage, representing the AccountUserStage
     */
    public void setAccountUserStage(Stage accountUserStage) {
        this.accountUserStage = accountUserStage;
    }

    /**
     * @param loginStage Stage, representing the LoginStage
     */
    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    /**
     * Method linked to the labelExitApplication's onMouseClicked event.
     * It closes the Application with a 0 status code.
     */
    public void eventExitApplication() {
        System.exit(0);
    }

    /**
     * Method linked to the labelLogout's onMouseClicked event
     * It log outs the user
     */
    public void eventLogout() {
        accountUserStage.close();
        loginStage.show();
    }

    /**
     * Method linked to the labelStatistics onMouseClicked event
     * It shows the Statistics Panel
     */
    public void eventShowStatistics() {
        statisticsPane.setVisible(true);
        loggedInUserPane.setVisible(false);
    }
}
