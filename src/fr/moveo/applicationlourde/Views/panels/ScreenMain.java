    package fr.moveo.applicationlourde.Views.panels;

    import fr.moveo.applicationlourde.model.User;
    import fr.moveo.applicationlourde.services.ConnectMethods;

    import javax.swing.*;
    import java.awt.*;
    import java.util.ArrayList;
    import javax.swing.JPanel;
    import javax.swing.event.ListSelectionEvent;
    import javax.swing.event.ListSelectionListener;

    /**
     * Main panel of the application
     */
    public class ScreenMain extends JPanel {

        User user;
        ConnectMethods connectMethods = new ConnectMethods();
        ArrayList usersList = new ArrayList();
        ArrayList tripsList = new ArrayList();
        ArrayList commentsList = new ArrayList();
        ArrayList reportedList = new ArrayList();
        ArrayList messageList = new ArrayList();
        JTabbedPane jTabbedPane = new JTabbedPane();
        JPanel jCards = new JPanel();
        JPanel jCard2 = new JPanel();
        JPanel jCard3 = new JPanel();
        JPanel jCard4 = new JPanel();
        JPanel jCard5 = new JPanel();
        CardLayout card = new CardLayout();
        CommentSheet commentSheet;
        UserSheet userSheet;
        TripSheet tripSheet;
        InboxSheet inboxSheet;
        ToReportSheet toReportSheet;
        JLabel message = new JLabel("");
        JList list;

        /**
         * the constructor of the panel
         * @param moderator the moderator's information
         * @param userList the list of the users that use the application
         * @param admin boolean use to know if the panel should be for administrator or moderator
         */
        public ScreenMain(User moderator, ArrayList<User> userList, boolean admin) {
            jCards.setLayout(card);
            jCard2.setLayout(card);
            jCard3.setLayout(card);
            jCard4.setLayout(card);
            jCard5.setLayout(card);
            DefaultListModel listModel = new DefaultListModel();
            usersList = userList;
            for (int i = 0; i < usersList.size(); i++) {
                User user = (User) usersList.get(i);
                userSheet = new UserSheet(user, moderator, admin);
                listModel.addElement(user);
                jCards.add(userSheet, user.toString());
                tripsList = connectMethods.getArrayListTrip(connectMethods.getTripList(Integer.toString(user.getId())));
                tripSheet = new TripSheet(tripsList);
                jCard2.add(tripSheet, user.toString()+"1");
                commentsList = connectMethods.getArrayListComments(connectMethods.getCommentListByUser(Integer.toString(user.getId())));
                commentSheet = new CommentSheet(commentsList);
                jCard3.add(commentSheet, user.toString()+"2");
                reportedList = connectMethods.getArrayListReports(connectMethods.getReportList());
                toReportSheet = new ToReportSheet(reportedList);
                jCard4.add(toReportSheet, user.toString()+"4");
                messageList = connectMethods.getArrayListMessage(connectMethods.getInbox());
                inboxSheet = new InboxSheet(messageList);
                jCard5.add(inboxSheet,user.toString()+"5");
                System.out.println("user : "+user.toString());
            }
            user = new User();
            list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Object obj = list.getSelectedValue();
                    User user = (User) obj;
                    jCards.add(userSheet);
                    CardLayout cL = (CardLayout) (jCards.getLayout());
                    cL.show(jCards, user.toString());
                    jCard2.add(tripSheet);
                    CardLayout cL2 = (CardLayout) (jCard2.getLayout());
                    cL2.show(jCard2, user.toString() + "1");
                    jCard3.add(commentSheet);
                    CardLayout cL3 = (CardLayout) (jCard3.getLayout());
                    cL3.show(jCard3, user.toString() + "2");
                    jCard4.add(toReportSheet);
                    CardLayout cardLayout = (CardLayout) (jCard4.getLayout());
                    cardLayout.show(jCard4, user.toString() + "4");
                    jCard5.add(inboxSheet);
                    CardLayout cardLayoutInbox = (CardLayout) (jCard5.getLayout());
                    cardLayoutInbox.show(jCard5,user.toString()+"5");
                }
            });

            this.setLayout(new BorderLayout());
            this.add(new JScrollPane(list), BorderLayout.WEST);
            this.add(message, BorderLayout.SOUTH);
            this.screenConfiguration();


            // panel des différentes fiches qui s'afficheront en fonction des onglets
            tripSheet.setBackground(Color.RED);

            //rajout des boutons dans le panel d'onglets
            jTabbedPane.addTab("Information", jCards);
            jTabbedPane.addTab("Voyages", jCard2);
            jTabbedPane.addTab("Commentaires", jCard3);
            jTabbedPane.addTab("Signalements",jCard4);
            jTabbedPane.addTab("Message",jCard5);
            this.add(jTabbedPane, BorderLayout.CENTER);

        }

        /**
         * config of the JPanel
         */
        public void screenConfiguration() {
            this.setOpaque(false); // mettre l'écran en transparent
            this.setBackground(Color.GREEN);//Définition de sa couleur de fond
            this.setVisible(true);
        }

        /**
         * method used to get the message in the format JLabel
         * @return the messageButton
         */
        public JLabel getMessage() {
            return message;
        }

    }