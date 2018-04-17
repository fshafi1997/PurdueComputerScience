import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by farhanshafi on 11/5/16.
 */
public class ViewV2 extends JFrame implements ActionListener {
    private JPanel allPanel = new JPanel(new BorderLayout());
    private JPanel changePanel = new JPanel();
    private JPanel titlePanel = new JPanel(new FlowLayout());
    private JLabel titleLabel = new JLabel();
    private JLabel statusLabel1, statusLabel4, statusLabel5, statusLabel6;
    private String gameTokenTemp, otherPlayerName, name, password, correctAnswer, userSuggestion,userChoice,nextRoundResponse;
    private String userInputToken = null;
    private String helper = null;
    private JTextField usernameInput, gameKeyInput, yourSuggestionTextField, smallTextField;
    private JPasswordField passwordInput;
    private JButton loginButton, registerButton, startGameButton, joinGameButton, joinFinalButton, submitSuggestion, nextRound, startGame, submitOption;
    private JTextArea largeTextArea, largeTextArea1, largeTextArea2, largeTextArea3;
    private JScrollPane scrollPane;
    private JRadioButton[] radioButtons;
    private String[] answers;
    private boolean isLeader = false;
    private int selected = -1;


    CardLayout layout = new CardLayout();
    Controller controller = new Controller();

    public ViewV2() {
        this.setTitle("FoilMaker");
        this.setMinimumSize(new Dimension(300, 450));
        //this.setMinimumSize(new Dimension(276,451));
        this.setResizable(false);

        titleLabel.setText("FoilMaker!");
        titlePanel.add(titleLabel);
        allPanel.add(titlePanel, BorderLayout.NORTH);

        changePanel.setLayout(layout);
        changePanel.add(createHomeScreen(), "1");
        changePanel.add(createStartScreen(), "2");
        changePanel.add(createJoinScreen(), "3");
        changePanel.add(startNewGameHost(), "4");
        changePanel.add(createLaunchGameScreen(), "5");
        changePanel.add(waitScreenPanel(), "6");
        changePanel.add(createReceiveResultsPanel(), "7");
        //changePanel.add(createRadioButtons(),"8");

        allPanel.add(changePanel, BorderLayout.CENTER);
        this.add(allPanel);

        this.pack();
        layout.show(changePanel, "1");
        this.setVisible(true);

    }

    public JPanel createHomeScreen() {
        JPanel allPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JPanel infoPanelWrapper = new JPanel(new GridBagLayout());
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonsPanelWrapper = new JPanel(new GridBagLayout());
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        JPanel statusPanel = new JPanel(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc.insets = new Insets(50, 0, 25, 0);
        gbc2.insets = new Insets(25, 0, 50, 0);

        JLabel nameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameInput = new JTextField(10);
        passwordInput = new JPasswordField(10);
        infoPanel.add(nameLabel);
        infoPanel.add(usernameInput);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordInput);
        infoPanelWrapper.add(infoPanel, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);
        buttonsPanelWrapper.add(buttonsPanel, gbc2);

        mainPanel.add(infoPanelWrapper);
        mainPanel.add(buttonsPanelWrapper);

        statusLabel1 = new JLabel("Login or Register");
        statusPanel.add(statusLabel1, BorderLayout.WEST);

        allPanel.add(mainPanel, BorderLayout.CENTER);
        allPanel.add(statusPanel, BorderLayout.SOUTH);

        return allPanel;
    }

    public JPanel createStartScreen() {
        JPanel allPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JPanel buttonsPanelWrapper = new JPanel(new GridBagLayout());
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JPanel statusPanel = new JPanel(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(75, 0, 75, 0);

        startGameButton = new JButton("Start a New Game");
        startGameButton.addActionListener(this);
        joinGameButton = new JButton("Join a Game");
        joinGameButton.addActionListener(this);
        buttonsPanel.add(startGameButton, BorderLayout.WEST);
        buttonsPanel.add(joinGameButton, BorderLayout.EAST);

        buttonsPanelWrapper.add(buttonsPanel);
        mainPanel.add(buttonsPanelWrapper);

        JLabel statusLabel1 = new JLabel("Welcome!");
        statusPanel.add(statusLabel1, BorderLayout.WEST);

        allPanel.add(mainPanel, BorderLayout.CENTER);
        allPanel.add(statusPanel, BorderLayout.SOUTH);

        return allPanel;
    }

    public JPanel createJoinScreen() {
        JPanel allPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JPanel jWrapper = new JPanel(new GridLayout(3, 1));
        JPanel statusPanel = new JPanel(new BorderLayout());
        JPanel gameKeyInputWrapper = new JPanel();
        JPanel joinFinalButtonWrapper = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 0, 50, 0);

        JLabel instructionLabel = new JLabel("Enter the game key join a game");
        gameKeyInput = new JTextField(3);
        joinFinalButton = new JButton("Join Game");
        joinFinalButton.addActionListener(this);
        gameKeyInputWrapper.add(gameKeyInput);
        joinFinalButtonWrapper.add(joinFinalButton);
        jWrapper.add(instructionLabel);
        jWrapper.add(gameKeyInputWrapper);
        jWrapper.add(joinFinalButtonWrapper);

        mainPanel.add(jWrapper, gbc);

        JLabel statusLabel = new JLabel("Welcome!");
        statusPanel.add(statusLabel, BorderLayout.WEST);

        allPanel.add(mainPanel, BorderLayout.CENTER);
        allPanel.add(statusPanel, BorderLayout.SOUTH);

        return allPanel;
    }

    public JPanel waitScreenPanel() {
        JPanel outerMost = new JPanel(new BorderLayout());
        outerMost.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel centerLabel = new JLabel("Waiting for leader...", SwingConstants.CENTER);

        outerMost.add(centerLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel panelForBottomLabel = new JPanel(new FlowLayout(0, 0, 0));
        panelForBottomLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel bottomLabel = new JLabel("Joined game: waiting for leader");
        panelForBottomLabel.add(bottomLabel);
        bottomPanel.add(panelForBottomLabel, BorderLayout.SOUTH);

        outerMost.add(bottomPanel, BorderLayout.SOUTH);

        return outerMost;

    }

    public JPanel startNewGameHost() {
        startGame = new JButton("Start Game");
        startGame.setEnabled(false);
        startGame.addActionListener(this);
        JLabel comment = new JLabel("Others should use this key to join your Game",SwingConstants.CENTER);
        smallTextField = new JTextField(3);
        smallTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        smallTextField.setEditable(false);
        largeTextArea = new JTextArea();
        largeTextArea.setBackground(Color.ORANGE);
        largeTextArea.setEditable(false);
        statusLabel4 = new JLabel("Game Started: You are the leader");

        JPanel outerMostPanel = new JPanel(new GridLayout(4, 0));
        outerMostPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel panelForComment = new JPanel(new BorderLayout());
        panelForComment.add(comment, BorderLayout.SOUTH);

        JPanel panelForSmallTextField = new JPanel(new FlowLayout(1, 0, 0));
        panelForSmallTextField.add(smallTextField);

        JPanel panelForLargeTextArea = new JPanel(new BorderLayout());
        panelForLargeTextArea.setBorder(BorderFactory.createTitledBorder("Participants"));
        panelForLargeTextArea.setBackground(Color.white);
        panelForLargeTextArea.add(largeTextArea);

        JPanel panelForBottomLabelandButton = new JPanel(new BorderLayout());
        JPanel panelForButton = new JPanel(new FlowLayout(1, 0, 0));
        panelForButton.add(startGame);
        panelForBottomLabelandButton.add(panelForButton, BorderLayout.NORTH);
        JPanel panelForStatusLabel4 = new JPanel(new FlowLayout(0, 0, 0));
        panelForStatusLabel4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        statusLabel4.setText("Game Started: You are the leader");
        panelForStatusLabel4.add(statusLabel4);
        panelForBottomLabelandButton.add(panelForStatusLabel4, BorderLayout.SOUTH);

        outerMostPanel.add(panelForComment);
        outerMostPanel.add(panelForSmallTextField);
        outerMostPanel.add(panelForLargeTextArea);
        outerMostPanel.add(panelForBottomLabelandButton);

        return outerMostPanel;
    }

    public JPanel createLaunchGameScreen() {
        JPanel guiIfLAunchGamePanelOuter = new JPanel(new BorderLayout());
        JPanel guiIfLAunchGamePanel = new JPanel(new GridLayout(2, 0));
        guiIfLAunchGamePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        submitSuggestion = new JButton("Submit Suggestion");
        submitSuggestion.addActionListener(this);
        JLabel comment3 = new JLabel("What is the word for");
        largeTextArea1 = new JTextArea();
        largeTextArea1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        largeTextArea1.setBackground(Color.PINK);
        largeTextArea1.setEditable(false);
        yourSuggestionTextField = new JTextField(10);
        statusLabel5 = new JLabel("Enter your suggestion");

        JPanel panelForCommentAndTextArea = new JPanel(new BorderLayout());

        JPanel panelForComment3 = new JPanel(new FlowLayout(0, 0, 0));
        panelForComment3.add(comment3);

        JPanel panelForLargeTextArea = new JPanel(new GridLayout(1, 0));
        panelForLargeTextArea.add(largeTextArea1);

        panelForCommentAndTextArea.add(panelForComment3, BorderLayout.NORTH);
        panelForCommentAndTextArea.add(panelForLargeTextArea, BorderLayout.CENTER);

        JPanel panelForTextFieldAndSubmitSuggestionButton = new JPanel(new BorderLayout());

        JPanel panelForSmallTextField3 = new JPanel(new GridLayout(4, 0));
        panelForSmallTextField3.setBorder(BorderFactory.createTitledBorder("Your suggestions"));
        panelForSmallTextField3.add(yourSuggestionTextField);

        JPanel panelForSubmitSuggestionButton = new JPanel(new FlowLayout());
        panelForSubmitSuggestionButton.add(submitSuggestion);

        panelForTextFieldAndSubmitSuggestionButton.add(panelForSmallTextField3, BorderLayout.CENTER);
        panelForTextFieldAndSubmitSuggestionButton.add(panelForSubmitSuggestionButton, BorderLayout.SOUTH);

        guiIfLAunchGamePanel.add(panelForCommentAndTextArea);
        guiIfLAunchGamePanel.add(panelForTextFieldAndSubmitSuggestionButton);

        JPanel forStatusLabel = new JPanel(new FlowLayout(0, 0, 0));
        forStatusLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        forStatusLabel.add(statusLabel5);

        guiIfLAunchGamePanelOuter.add(guiIfLAunchGamePanel, BorderLayout.CENTER);
        guiIfLAunchGamePanelOuter.add(forStatusLabel, BorderLayout.SOUTH);

        return guiIfLAunchGamePanelOuter;
    }

    public JPanel createRadioButtons() {

        submitOption = new JButton("Submit Option");
        submitOption.addActionListener(this);

        JPanel panelForRadioButton = new JPanel(new GridLayout(3, 0));
        radioButtons = new JRadioButton[3];
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            radioButtons[i] = new JRadioButton(answers[i]);
            group.add(radioButtons[i]);
            panelForRadioButton.add(radioButtons[i]);
        }

        JPanel outerPanel = new JPanel(new GridLayout(3, 0));
        outerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel panelForTopComment = new JPanel(new FlowLayout(1, 0, 0));
        JLabel comment = new JLabel("Pick your option below", SwingConstants.CENTER);
        panelForTopComment.add(comment);


        outerPanel.add(panelForTopComment);

        JPanel newPanelButtons = new JPanel(new FlowLayout(1, 0, 0));
        newPanelButtons.add(panelForRadioButton);

        outerPanel.add(newPanelButtons);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel panelForButton = new JPanel(new FlowLayout(1, 0, 0));
        panelForButton.add(submitOption);
        bottomPanel.add(panelForButton, BorderLayout.CENTER);

        JPanel panelBottomLabel = new JPanel(new FlowLayout(0, 0, 0));
        panelBottomLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel bottomLabel = new JLabel("Pick your choice");
        panelBottomLabel.add(bottomLabel);
        bottomPanel.add(panelBottomLabel, BorderLayout.SOUTH);

        outerPanel.add(bottomPanel);

        return outerPanel;
    }

    public JPanel createReceiveResultsPanel() {
        nextRound = new JButton("Next Round");
        nextRound.addActionListener(this);
        largeTextArea3 = new JTextArea();
        largeTextArea3.setBackground(Color.PINK);
        largeTextArea3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        largeTextArea3.setEditable(false);
        largeTextArea2 = new JTextArea();
        largeTextArea2.setBackground(Color.ORANGE);
        largeTextArea2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        largeTextArea2.setEditable(false);
        scrollPane = new JScrollPane(largeTextArea2);
        statusLabel6 = new JLabel("Set text for statusLabel6");

        JPanel panelForGUIReceiveResults = new JPanel(new GridLayout(3, 0));
        panelForGUIReceiveResults.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JPanel panelForFirstLArgeTextARea = new JPanel(new GridLayout(1, 0));
        panelForFirstLArgeTextARea.setBackground(Color.white);
        panelForFirstLArgeTextARea.setBorder(BorderFactory.createTitledBorder("Round Results"));
        panelForFirstLArgeTextARea.add(largeTextArea3);

        JPanel panelForTextAreaWithScrollPane = new JPanel(new GridLayout(1, 0));
        panelForTextAreaWithScrollPane.setBackground(Color.white);
        panelForTextAreaWithScrollPane.setBorder(BorderFactory.createTitledBorder("Overall Results"));
        panelForTextAreaWithScrollPane.add(scrollPane);

        JPanel panelForNExtRoundButtonAndBottomLabel = new JPanel(new BorderLayout());
        JPanel panelForbutton = new JPanel(new FlowLayout(1, 0, 0));
        panelForbutton.add(nextRound);
        panelForNExtRoundButtonAndBottomLabel.add(panelForbutton, BorderLayout.CENTER);
        JPanel panelForstatusLabel6 = new JPanel(new FlowLayout(0, 0, 0));
        panelForstatusLabel6.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelForstatusLabel6.add(statusLabel6);
        panelForNExtRoundButtonAndBottomLabel.add(panelForstatusLabel6, BorderLayout.SOUTH);

        panelForGUIReceiveResults.add(panelForFirstLArgeTextARea);
        panelForGUIReceiveResults.add(panelForTextAreaWithScrollPane);
        panelForGUIReceiveResults.add(panelForNExtRoundButtonAndBottomLabel);

        return panelForGUIReceiveResults;
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            if (source == registerButton) {
                name = usernameInput.getText();
                password = new String(passwordInput.getPassword());
                if (controller.registerNewUser(name, password).equals("SUCCESS")) {
                    statusLabel1.setText("New user created");
                }
            } else if (source == loginButton) {
                name = usernameInput.getText();
                password = new String(passwordInput.getPassword());
                if (controller.userLogin(name, password).equals("SUCCESS")) {
                    layout.show(changePanel, "2");
                    titleLabel.setText(name);
                }
            } else if (source == joinGameButton) {
                //controller.joinGame(gameKeyInput.getText()); substring(0, 7).equals("SUCCESS")
                layout.show(changePanel, "3");

            } else if (source == startGameButton) {
                isLeader = true;
                gameTokenTemp = controller.startNewGame();
                if (gameTokenTemp.substring(0, 7).equals("SUCCESS")) {
                    gameTokenTemp = gameTokenTemp.substring(9);
                    smallTextField.setText(gameTokenTemp);
                    layout.show(changePanel, "4");

                }
                start();
            } else if (source == startGame) {
                start2();
            } else if (source == joinFinalButton) {
                userInputToken = gameKeyInput.getText();
                controller.joinGame(userInputToken);
                layout.show(changePanel, "6");
                start();

            } else if (source == submitSuggestion) {
                userSuggestion = yourSuggestionTextField.getText();
                start3();
            }else if (source == submitOption){
                for (int i = 0;i < answers.length;i++){
                    if (radioButtons[i].isSelected()){
                        selected = i;
                    }

                }
                start4();
            }else if (source == nextRound){
                    //System.out.println(nextRoundResponse);
                    nextRoundResponse = nextRoundResponse.substring(13);
                    //System.out.println(response.substring(response.indexOf('-')));
                    correctAnswer = nextRoundResponse.substring(nextRoundResponse.indexOf('-') + 2);
                    //System.out.println(correctAnswer);
                    nextRoundResponse = nextRoundResponse.substring(0, nextRoundResponse.indexOf('-'));
                    largeTextArea1.setText(nextRoundResponse);

                    layout.show(changePanel,"5");

            }

        }
    }



    public void start() {
        SwingWorker worker = new SwingWorker<String, Object>() {
            @Override
            public String doInBackground() {
                String response = "";
                System.out.println("this");

                try {
                    response = controller.in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // TODO : Receive response from the server

                return response;
            }

            @Override
            public void done() {
                try {
                    String response = get(); // Return value of doInBackground
                    System.out.println(response);

                    if (response.substring(3, 4).equals("P")) {
                        response = response.substring(16);
                        response = response.substring(0, response.indexOf('-'));
                        //System.out.println(response);
                        largeTextArea.setText("=> " + response);
                        statusLabel4.setText("Press <Start Game> to start game");
                        startGame.setEnabled(true);
                    } else if (response.substring(3, 4).equals("G")) {
                        System.out.println(response);
                        response = response.substring(13);
                        //System.out.println(response.substring(response.indexOf('-')));
                        correctAnswer = response.substring(response.indexOf('-') + 2);
                        //System.out.println(correctAnswer);
                        response = response.substring(0, response.indexOf('-'));
                        largeTextArea1.setText(response);
                        layout.show(changePanel, "5");
                        repaint();


                    }
                    // TODO : Update UI
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //worker2.execute();
            }
        };
        worker.execute();
    }

    public void start2() {
        SwingWorker worker = new SwingWorker<String, Object>() {
            @Override
            public String doInBackground() {
                String response = "";
                System.out.println("this");
                String launchRequest = "ALLPARTICIPANTSHAVEJOINED--" + Controller.userInfo.getUserToken() + "--" + Controller.userInfo.getGameToken();
                String launchResponse = "";
                try {
                    controller.out.println(launchRequest);
                    response = controller.in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            public void done() {
                try {
                    String response = get(); // Return value of doInBackground
                    System.out.println(response);

                    if (response.substring(3, 4).equals("G")) {
                        System.out.println(response);
                        response = response.substring(13);
                        //System.out.println(response.substring(response.indexOf('-')));
                        correctAnswer = response.substring(response.indexOf('-') + 2);
                        //System.out.println(correctAnswer);
                        response = response.substring(0, response.indexOf('-'));
                        largeTextArea1.setText(response);
                        layout.show(changePanel, "5");
                        repaint();

                    }

                    // TODO : Update UI
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void start3() {
        SwingWorker worker = new SwingWorker<String, Object>() {
            @Override
            public String doInBackground() {
                String response = "";
                String suggestRequest;
                System.out.println("this");
                if (isLeader) {
                    suggestRequest = "PLAYERSUGGESTION--" + controller.userInfo.getUserToken() + "--" + controller.userInfo.getGameToken() + "--" + userSuggestion;
                } else {
                    suggestRequest = "PLAYERSUGGESTION--" + controller.userInfo.getUserToken() + "--" + userInputToken + "--" + userSuggestion;

                }
                controller.out.println(suggestRequest);
                try {
                    response = controller.in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            public void done() {
                try {
                    String response = get(); // Return value of doInBackground
                    System.out.println(response);

                    if (response.substring(5, 6).equals("O")) {
                        response = response.substring(14);
                        System.out.println(response);
                        answers = response.split("--");
                        for (int i = 0; i < answers.length; i++) {
                            System.out.println(answers[i]);
                        }
                        changePanel.add(createRadioButtons(), "8");
                        layout.show(changePanel, "8");
                    }

                    // TODO : Update UI
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    public void start4() {
        SwingWorker worker = new SwingWorker<String, Object>() {
            @Override
            public String doInBackground() {
                userChoice = answers[selected];
                String response = "";
                String choiceRequest;
                System.out.println("this");
                if (isLeader) {
                    choiceRequest = "PLAYERCHOICE--" + controller.userInfo.getUserToken() + "--" + controller.userInfo.getGameToken() + "--" + userChoice;
                } else {
                    choiceRequest = "PLAYERCHOICE--" + controller.userInfo.getUserToken() + "--" + userInputToken + "--" + userChoice;
                }
                controller.out.println(choiceRequest);
                try {
                    response = controller.in.readLine();
                    nextRoundResponse = controller.in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            public void done() {
                try {
                    String response = get(); // Return value of doInBackground
                    System.out.println(response);

                    if (response.substring(5, 6).equals("R")) {
                        String formatted = controller.makeResults(response);
                        if (isLeader){
                            largeTextArea3.setText(controller.firstPlayerResult);
                            largeTextArea2.setText(formatted);
                        }
                        else {
                            largeTextArea3.setText(controller.secondPlayerResult);
                            largeTextArea2.setText(formatted);
                        }
                        statusLabel6.setText("Click <Next Round> when ready");
                        layout.show(changePanel,"7");
                    }

                    if (nextRoundResponse.substring(0,8).equals("GAMEOVER")){
                        statusLabel6.setText("Gameover");
                        nextRound.setEnabled(false);
                    }


                    // TODO : Update UI
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }


    public static void main(String[] args) {
        ViewV2 view = new ViewV2();
    }
}
