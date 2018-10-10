import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

    public class MainFrame extends Application
    {
        private static Stage addStage;

        private static EventHandler<MouseEvent> createNewNoteHandler;
        private static EventHandler<MouseEvent> editNotesCreatedHandler;

        private static ArrayList<HBox> notesСreated;


        private static VBox rootMain = new VBox();

        private static Scene sceneMain;

        private static Button createNewNote = new Button(" create new note ");


        public static void main(String[] args) { launch(args); }

        @Override
        public void start(Stage primaryStage) throws SQLException, ClassNotFoundException
        {

            initIcon(primaryStage);
            initHandlers(primaryStage);

            setCreateNewNote();
            showNotesCreated();


            setRootMain();

            setPrimaryStage(primaryStage);
        }


        private static void setCreateNewNote()
        {
            createNewNote.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, createNewNoteHandler);
            createNewNote.setMinSize(500, 50);
        }

        private static void initIcon(Stage stage)
        {
            stage.getIcons().add(new Image("style/icon.jpg"));
        }

        private static void showNotesCreated() throws SQLException, ClassNotFoundException
        {
            initNotesCreated();
            for (int i = 0; i < NoteFrame.returnLenNotePanel(); i++)
                {
                    //        notesСreated.get(i).addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, editNotesCreatedHandler);
                    rootMain.getChildren().add(notesСreated.get(i));
                }
        }

        private static void updateShowNoteCreated() throws SQLException, ClassNotFoundException
        {
            initNotesCreated();
            rootMain.getChildren().add(notesСreated.get(notesСreated.size()-1));
            rootMain.getChildren().remove(createNewNote);
            addStage=new Stage();

            createNewNote = new Button(" create new note ");
            initCreateNewNoteHandler(addStage);
            setCreateNewNote();
            rootMain.getChildren().add(createNewNote);


            }

        private static void initHandlers(Stage primaryStage)
        {
            initCreateNewNoteHandler(primaryStage);
          //  initEditNotesCreatedHandler(primaryStage);
        }

        private static void initCreateNewNoteHandler(Stage primaryStage)
        {
            createNewNoteHandler = event -> {
                new WriteNoteFrame();
            };
        }

      /*  private static void initEditNotesCreatedHandler(Stage primaryStage)
        {
            editNotesCreatedHandler = event ->
            {
                new EditNoteFrame();
            };
        }
*/
        private static void initNotesCreated() throws SQLException, ClassNotFoundException
        {
            notesСreated = NoteFrame.getNotePanel();
        }

        //private static void delNotesCreated(){
       //     rootMain.getChildren().removeAll(notesСreated);
      //  }

        public static void updateNotesCreated() throws SQLException, ClassNotFoundException
        {
            //notesСreated=new ArrayList<>();
            updateShowNoteCreated();
        }



        private static void setRootMain()
        {
            rootMain.setSpacing(10);
            rootMain.getChildren().add(createNewNote);
        }

        private static void setPrimaryStage(Stage primaryStage)
        {
        //    primaryStage.setResizable(false);
            primaryStage.setTitle("Zen.Note");

                setSceneMain();

            primaryStage.setScene(sceneMain);
            primaryStage.show();
        }

        private static void setSceneMain()
        {
            sceneMain = new Scene(rootMain, 500, 600);
            sceneMain.getStylesheets().add(MainFrame.class.getResource("style/style.css").toExternalForm());
        }

        private static Stage transStage(Stage primaryStage)
        {
            return primaryStage;
        }
    }
