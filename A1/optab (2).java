package AssemblerPass1;
import java.util.HashMap;

public class optab {

    HashMap<String, Integer> IS, AD, DL, REG, CC;

    public optab() {
        IS = new HashMap<String, Integer>();
        AD = new HashMap<String, Integer>();
        DL = new HashMap<String, Integer>();
        REG = new HashMap<String, Integer>();
        CC = new HashMap<String, Integer>();

        IS.put("STOP", 0);
        IS.put("ADD", 1);
        IS.put("SUB", 2);
        IS.put("MULT", 3);
        IS.put("MOVER", 4);
        IS.put("MOVEM", 5);
        IS.put("COMP", 6);
        IS.put("BC", 7);
        IS.put("DIV", 8);
        IS.put("READ", 9);
        IS.put("PRINT", 10);

        AD.put("START", 1);
        AD.put("END", 2);
        AD.put("ORIGIN", 3);
        AD.put("EQU", 4);
        AD.put("LTORG", 5);

        DL.put("DC", 1);
        DL.put("DS", 2);

        REG.put("AREG", 1);
        REG.put("BREG", 2);
        REG.put("CREG", 3);
        REG.put("DREG", 4);

        CC.put("LT", 1);
        CC.put("LE", 2);
        CC.put("EQ", 3);
        CC.put("GT", 4);
        CC.put("GE", 5);
        CC.put("ANY", 6);
    }

    public String getType(String str) {

        str = str.toUpperCase();

        if(IS.containsKey(str))
            return "IS";
        else if(AD.containsKey(str))
            return "AD";
        else if(DL.containsKey(str))
            return "DL";
        else if(REG.containsKey(str))
            return "REG";
        else if(CC.containsKey(str))
            return "CC";

        return "";
    }

    public int getCode(String str) {

        str = str.toUpperCase();

        if(IS.containsKey(str))
            return IS.get(str);
        else if(AD.containsKey(str))
            return AD.get(str);
        else if(DL.containsKey(str))
            return DL.get(str);
        else if(REG.containsKey(str))
            return REG.get(str);
        else if(CC.containsKey(str))
            return CC.get(str);

        return -1;
    }
}
