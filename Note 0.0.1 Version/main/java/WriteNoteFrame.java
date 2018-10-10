import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;


public class WriteNoteFrame  {

    private static TextArea textArea=new TextArea();
    private static Button createNote=new Button("create");
    private static Stage stageStart=new Stage();
    private static HBox styleRoot=new HBox();
    private static VBox rootMain = new VBox();
    private static EventHandler<MouseEvent> editFonts;
    private static EventHandler<MouseEvent> writeToDB;
    private static Scene scene;


    private static String title="";

    private static PreparedStatement WRITE;

    private static void flushArea()
    {
        textArea.clear();
    }


    public void start(Stage primaryStage)
    {

        initEditFonts();
        FONTModule.fillCB();
        setTextArea();
        setFonts();
        setStage(primaryStage);
    }


    public WriteNoteFrame()
    {
        initWriteToDB();

        setCreateNote();

        setRoot();
        stageStart=new Stage();
        start(stageStart);
    }

    private static void write() throws SQLException, ClassNotFoundException
    {
        getTitle();
        executeWRITE();
        title="";
        flushArea();
        new MainFrame();
        NoteFrame.updateNotePanel();
        MainFrame.updateNotesCreated();
        stageStart.close();
    }


    private static void getTitle()
    {
        title=textArea.getText();

        /*
        String string=textArea.getText();
        char[] chars=string.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            title+=chars[i];
            if (i+1<chars.length ){
                if (chars[i+1] ==' '){
                    break;
                }
            }
        }
        */
    }

    private static void initEditFonts(){
        editFonts= event -> setFontPanel();
    }


    private static void initWriteToDB()
    {
        writeToDB= event -> {
            try {
                write();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        };
    }

    private static void setFontPanel()
    {
        String font = FONTModule.getFontValue();
        int size = FONTModule.getFontSizeValue();
        if (FONTModule.getFontWeightValue() == "normal") {
            textArea.setFont(Font.font(font, FontWeight.NORMAL, size));
        } else if (FONTModule.getFontWeight().getValue() == "bold") {
            textArea.setFont(Font.font(font, FontWeight.BOLD, size));
        } else {
            textArea.setFont(Font.font(font, FontWeight.findByName("Italic"), size));
        }
    }

    private static void setScene()
    {
        scene = new Scene(rootMain, 600, 295);
        scene.getStylesheets().add(MainFrame.class.getResource("style/style.css").toExternalForm());
    }

    private static void setStage(Stage primaryStage)
    {
        //primaryStage.setResizable(false);
        primaryStage.setTitle("Zen.Note");

        setScene();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void setFonts()
    {
        FONTModule.getFonts().addEventFilter(MouseEvent.MOUSE_CLICKED, editFonts);
        setFontsWeight();
        setFontsSize();
    }

    private static void setFontsWeight()
    {
        FONTModule.getFontWeight().addEventFilter(MouseEvent.MOUSE_CLICKED, editFonts);
    }

    private static void setFontsSize()
    {
        FONTModule.getFontSize().addEventFilter(MouseEvent.MOUSE_CLICKED, editFonts);
    }

    private static void setTextArea()
    {
        textArea.addEventFilter(MouseEvent.MOUSE_CLICKED, editFonts);
        textArea.setWrapText(true);
    }

    private static void setCreateNote()
    {
        createNote.setMinSize(600,50);
        createNote.addEventFilter(MouseEvent.MOUSE_CLICKED, writeToDB);
    }

    private static void setRoot()
    {
       setStyleRoot();
       setRootMain();
    }

    private static void setStyleRoot()
    {
        styleRoot=new HBox();
        styleRoot.setSpacing(5);
        styleRoot.getChildren().addAll(FONTModule.getFonts(),FONTModule.getFontWeight(),FONTModule.getFontSize());
    }

    private static void setRootMain()
    {
        rootMain = new VBox();
        rootMain.setSpacing(10);
        rootMain.getChildren().addAll(styleRoot,textArea, createNote);
    }

    private static void executeWRITE() throws SQLException, ClassNotFoundException
    {
        DBModule.connect();
        WRITE = DBModule.getConnectionDB().prepareStatement("INSERT INTO NOTES (TITLE,TEXT,FONTS,FONTWEIGHT,FONTSIZE,UNN) VAlUES (?,?,?,?,?,?)");
        WRITE.setString(1,title);
        WRITE.setString(2,textArea.getText());
        WRITE.setString(3,FONTModule.getFontValue());
        WRITE.setString(4,FONTModule.getFontWeightValue());
        WRITE.setInt(5,FONTModule.getFontSizeValue());
        WRITE.setString(6,UNNGenerator.generateUNN());
        WRITE.addBatch();
        WRITE.executeBatch();
        DBModule.disconnect();
    }

}
