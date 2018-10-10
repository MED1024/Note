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

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditNoteFrame  {

    private static TextArea textArea=new TextArea();
    //private static Stage stageStart=new Stage();
    private static Stage stageEdit=new Stage();
    private static HBox styleRoot=new HBox();
    private static VBox rMain = new VBox();
    private static Button editNote=new Button("edit");

    private static EventHandler<MouseEvent> editFonts;
    private static EventHandler<MouseEvent> editToDB;
    private static Scene scene;


    private static String title="";

    private static PreparedStatement EDIT;


    public void start(Stage primaryStage)
    {
        initEditFonts();
        FONTModule.fillCB();
        setTextArea();
        setFonts();
        setStage(primaryStage);
    }





    public EditNoteFrame()  //FOR EDIT NOTE
    {


        initEditToDB();

        editNote.setMinSize(400, 50);
        editNote.addEventFilter(MouseEvent.MOUSE_CLICKED, editToDB);

        setRoot();
        start(stageEdit);
    }

    private static void edit() throws SQLException, ClassNotFoundException {
        getTitle();
        executeEDIT();
        title="";
        new MainFrame();
        stageEdit.close();
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

    private static void initEditToDB()
    {
        editToDB= event -> {
            try {
                edit();
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
        scene = new Scene(rMain, 400, 300);
        scene.getStylesheets().add(MainFrame.class.getResource("style/style.css").toExternalForm());
    }

    private static void setStage(Stage primaryStage)
    {
        primaryStage.setResizable(false);
        primaryStage.setTitle("NOTE 2.0");

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


    private static void setRoot()
    {
        setStyleRoot();
        setRootMain();
    }

    private static void setStyleRoot()
    {
        styleRoot.setSpacing(5);
        styleRoot.getChildren().addAll(FONTModule.getFonts(),FONTModule.getFontWeight(),FONTModule.getFontSize());
    }

    private static void setRootMain()
    {
        rMain.setSpacing(10);
        rMain.getChildren().addAll(styleRoot,textArea, editNote);
    }


    private static void executeEDIT() throws SQLException, ClassNotFoundException
    {
        DBModule.connect();
        EDIT = DBModule.getConnectionDB().prepareStatement("UPDATE NOTES TITLE=?,TEXT=?,FONTS=?, FONTWEIGHT=? , FONTSIZE=?");
        EDIT.setString(1,"'"+title+"'");
        EDIT.setString(2,"'"+textArea.getText()+"'");
        EDIT.setString(3,FONTModule.getFontValue());
        EDIT.setString(4,FONTModule.getFontWeightValue());
        EDIT.setInt(5,FONTModule.getFontSizeValue());
        EDIT.addBatch();
        EDIT.executeBatch();
        DBModule.disconnect();
    }
}
