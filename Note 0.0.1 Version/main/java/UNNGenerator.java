import java.util.ArrayList;

public class UNNGenerator {

    //UNN - Unique Number Note

    private static ArrayList alphabet=new ArrayList(36);
    private static ArrayList UNNSet=new ArrayList();
    private static String unn;

    public static void fill_alphabet(){
        alphabet.add(0,'A');
        alphabet.add(1,'B');
        alphabet.add(2,'C');
        alphabet.add(3,'D');
        alphabet.add(4,'E');
        alphabet.add(5,'F');
        alphabet.add(6,'G');
        alphabet.add(7,'H');
        alphabet.add(8,'I');
        alphabet.add(9,'J');
        alphabet.add(10,'K');
        alphabet.add(11,'L');
        alphabet.add(12,'M');
        alphabet.add(13,'N');
        alphabet.add(14,'O');
        alphabet.add(15,'P');
        alphabet.add(16,'Q');
        alphabet.add(17,'R');
        alphabet.add(18,'S');
        alphabet.add(19,'T');
        alphabet.add(20,'U');
        alphabet.add(21,'V');
        alphabet.add(22,'W');
        alphabet.add(23,'X');
        alphabet.add(24,'Y');
        alphabet.add(25,'Z');
        alphabet.add(26,'0');
        alphabet.add(27,'1');
        alphabet.add(28,'2');
        alphabet.add(29,'3');
        alphabet.add(30,'4');
        alphabet.add(31,'5');
        alphabet.add(32,'6');
        alphabet.add(33,'7');
        alphabet.add(34,'8');
        alphabet.add(35,'9');

    }

    public static String generateUNN(){
        fill_alphabet();
        unn=new String();
        for (int i = 0; i < 9; i++) {
            unn+=alphabet.get(0+(int) (Math.random()*alphabet.size()));

        }

        return unn;
    }

}
