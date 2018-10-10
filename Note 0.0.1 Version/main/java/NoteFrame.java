import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.sql.*;
import java.util.ArrayList;

    public class NoteFrame
    {

        private static int count = 0;
        private static int countDone = 0;
        private static int countDel = 0;

        private static HBox boxNotesCreating;
        private static TextField titleNote;
        private static Button doneButton;
        private static Button delButton;


        private static PreparedStatement WRITE;
        private static PreparedStatement DELETE;


        private static ResultSet dataFromDB;

        private static ArrayList<HBox> notePanel = new ArrayList<>();
        private static ArrayList<HBox> UpdateNotePanel = new ArrayList<>();

        private static ArrayList<String> titleArray = new ArrayList<>();
        private static ArrayList<String> textArray = new ArrayList<>();

        private static ArrayList<String> UnnSet=new ArrayList<>();

        private static ArrayList<String> fontArray = new ArrayList<>();
        private static ArrayList<String> fontWeightArray = new ArrayList<>();
        private static ArrayList<Integer> fontSizeArray = new ArrayList<>();


        private static EventHandler<MouseEvent> eventHandlerEdit = event ->
        {

//      psDELETE=connection.prepareStatement(" ");
        };

        private static EventHandler<MouseEvent> doneButtonHandler;
        private static EventHandler<MouseEvent> delButtonHandler;


        private static void initButtonHandler(int i){
            doneButtonHandler = event ->
            {
                try {
                    excuteDELETE(i);
                //    updateNotePanel();
                //    MainFrame.updateNotesCreated();
                    System.out.println("+");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            delButtonHandler = event ->
            {
                try {
                    excuteDELETE(i);
               //     updateNotePanel();
               //     MainFrame.updateNotesCreated();
                    countDone++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDel++;
            };
        }

        private static void excuteDELETE(int i) throws SQLException, ClassNotFoundException {
            DBModule.connect();
            DELETE = DBModule.getConnectionDB().prepareStatement("DELETE FROM NOTES WHERE UNN==?");
            DELETE.setString(1, UnnSet.get(i));
            DELETE.execute();
            DBModule.disconnect();
            new MainFrame();
        }


        public static ArrayList<HBox> getNotePanel() throws SQLException, ClassNotFoundException
        {
            fillNotePanel();
            return notePanel;
        }

        private static void fillNotePanel() throws SQLException, ClassNotFoundException
        {
            executeQuery();
            for (int i = 0; i < titleArray.size(); i++)
            {
                createNewBoxElement(i);
            }
        }

        public static int returnLenNotePanel()
        {
            return notePanel.size();
        }


        private static void executeQuery() throws SQLException, ClassNotFoundException
        {
            DBModule.connect();
            getDataFromDB();
            DBModule.disconnect();
        }

        private static void setTitleNote()
        {
            titleNote.setMinSize(300, 50);
            titleNote.setEditable(false);
            titleNote.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerEdit);
        }

        private static void setDoneButton()
        {
            doneButton.setMinSize(90, 50);
            doneButton.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, doneButtonHandler);
        }



        private static void setDelButton()
        {
            delButton.setMinSize(90, 50);
            delButton.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, delButtonHandler);
        }

        private static void setButtons(int i)
        {

            initButtonHandler(i);

            doneButton=new Button("\uD83D\uDE04");
            delButton=new Button("☹");

            setDoneButton();
            setDelButton();
        }


        private static void setBoxNotesCreating()
        {
            boxNotesCreating.setSpacing(10);
            boxNotesCreating.getChildren().addAll(titleNote,doneButton,delButton);
        }

        private static void createNewBoxElement(int i)
        {
            boxNotesCreating = new HBox();
            titleNote = new TextField(titleArray.get(i));
            setButtons(i);
            setTitleNote();

            setBoxNotesCreating();
            notePanel.add(i, boxNotesCreating);
        }

        private static void getDataFromDB() throws SQLException
        {
            WRITE = DBModule.getConnectionDB().prepareStatement("SELECT * FROM NOTES");
            dataFromDB = WRITE.executeQuery();
            while (dataFromDB.next())
            {
                ArrayFill();
            }
        }

        private static void updateDataFromDB() throws SQLException
        {
            updateArrays();
            WRITE = DBModule.getConnectionDB().prepareStatement("SELECT * FROM NOTES");
            dataFromDB = WRITE.executeQuery();
            while (dataFromDB.next()) {
                ArrayFill();
            }
        }

        private static void updateArrays()
        {
            notePanel = new ArrayList<>();

            titleArray = new ArrayList<>();
            textArray = new ArrayList<>();

            UnnSet=new ArrayList<>();

            fontArray = new ArrayList<>();
            fontWeightArray = new ArrayList<>();
            fontSizeArray = new ArrayList<>();
            count=0;
        }

        private static void executeUpdate() throws SQLException, ClassNotFoundException
        {
            DBModule.connect();
            updateDataFromDB();
            DBModule.disconnect();
        }

        public static void updateNotePanel() throws SQLException, ClassNotFoundException
        {
            notePanel = new ArrayList<>();
            executeUpdate();
            for (int i = 0; i < titleArray.size(); i++)
            {
                createNewBoxElement(i);
            }
        }


        private static void ArrayFill() throws SQLException
        {
            TArray();
            FArray();
            UnnSetFill();
            count++;
        }

        private static void TArray() throws SQLException
        {
            titleArrayFill();
            textArrayFill();
        }

        private static void FArray() throws SQLException
        {
            fontArrayFill();
            fontWeightArrayFill();
            fontSizeArrayFill();
        }

        private static void titleArrayFill() throws SQLException
        {
            titleArray.add(count, dataFromDB.getString("TITLE"));
        }

        private static void textArrayFill() throws SQLException
        {
            textArray.add(count, dataFromDB.getString("TEXT"));
        }

        private static void fontArrayFill() throws SQLException
        {
            fontArray.add(count, dataFromDB.getString("FONTS"));
        }

        private static void fontWeightArrayFill() throws SQLException
        {
            fontWeightArray.add(count, dataFromDB.getString("FONTWEIGHT"));
        }

        private static void fontSizeArrayFill() throws SQLException
        {
            fontSizeArray.add(count, dataFromDB.getInt("FONTSIZE"));
        }

        private static void UnnSetFill() throws SQLException
        {
            UnnSet.add(count, dataFromDB.getString("UNN"));

        }
    }
