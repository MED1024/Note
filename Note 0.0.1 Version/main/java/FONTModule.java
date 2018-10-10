import javafx.scene.control.ComboBox;

public class FONTModule {
    private static ComboBox<String> fonts=new ComboBox();
    private static ComboBox<String> fontWeight=new ComboBox();
    private static ComboBox<Integer> fontSize=new ComboBox();

    private static void fillFonts()
    {

        fonts.getItems().add("Arial");
        fonts.getItems().add("Arial Black");
        fonts.getItems().add("Tahoma");
        fonts.getItems().add("Verdana");
        fonts.getItems().add("Lucida Sans Unicode");
        fonts.getItems().add("Trebuchet MS");
        fonts.getItems().add("MS Sans Serif");
        fonts.getItems().add("Impact");
        fonts.getItems().add("Century Gothic");
        fonts.getItems().add("Times New Roman");
        fonts.getItems().add("Georgia");
        fonts.getItems().add("Palatino Linotype");
        fonts.getItems().add("MS Serif");
        fonts.getItems().add("Sylfaen");
        fonts.getItems().add("Garamond");
        fonts.getItems().add("Century");
        fonts.getItems().add("Courier New");
        fonts.getItems().add("Lucida Console");
        fonts.getItems().add("Consolas");
        fonts.getItems().add("Courier New");
        fonts.getItems().add("Сomic Sans MS");
        fonts.getItems().add("Monotype Corsiva");
        fonts.getItems().add("Mistral");

        //

        fonts.getSelectionModel().select(0);
    }

    private static void fillFontsWeight()
    {
        fontWeight.getItems().add("normal");
        fontWeight.getItems().add("bold");
        fontWeight.getItems().add("italics");

        fontWeight.getSelectionModel().select(0);
    }

    private static void fillFontSize()
    {
        for (int i = 0,j=1; i <51 ; i++,j++) {
            fontSize.getItems().add(i,j);
        }

        fontSize.getSelectionModel().select(13);
    }

    public static void fillCB()
    {
        fillFonts();
        fillFontSize();
        fillFontsWeight();
    }

    public static ComboBox<Integer> getFontSize() {
        return fontSize;
    }

    public static ComboBox<String> getFonts() {
        return fonts;
    }

    public static ComboBox<String> getFontWeight() {
        return fontWeight;
    }

    public static String getFontValue(){
        return fonts.getValue();
    }

    public static String getFontWeightValue(){
        return fontWeight.getValue();
    }

    public static int getFontSizeValue(){
        return fontSize.getValue();
    }
}
