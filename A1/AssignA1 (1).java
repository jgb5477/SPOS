package AssemblerPass1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class AssignA1 {

    int lc;
    int symIndex, litIndex, poolIndex;
    LinkedHashMap<String, allTabRow> SYMTAB;
    ArrayList<allTabRow> LITTAB;
    ArrayList<Integer> POOLTAB;
    optab ot;

    public AssignA1() {
        SYMTAB = new LinkedHashMap<String, allTabRow>();
        LITTAB = new ArrayList<allTabRow>();
        POOLTAB = new ArrayList<Integer>();
        ot = new optab();
        lc = 0;
        symIndex = 0;
        litIndex = 0;
        poolIndex = 0;
        POOLTAB.add(1);
    }

    public void generateIC() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\src\\Input2.asm"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        String line, icode = null;

        while((line = br.readLine()) != null) {
            String split[] = line.split("\\s+");

            // check if label is present

            if(split[0].length() > 0) {
                // label is present

                if(SYMTAB.containsKey(split[0])) {
                    SYMTAB.put(split[0], new allTabRow(split[0], lc, SYMTAB.get(split[0]).getIndex()));
                }
                else {
                    SYMTAB.put(split[0], new allTabRow(split[0], lc, ++symIndex));
                }
            }

            if(split[1].equals("START")) {
                if(split.length > 2) {
                    lc = Integer.parseInt(split[2]);
                }

                icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(C, " + lc + ")";
                bw.write("\t\t" + icode + "\n");
                System.out.print("\t" + icode + "\n");
            }
            else if(split[1].equals("END")) {
                icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")";
                bw.write("\t\t" + icode + "\n");
                System.out.print("\t" + icode + "\n");

                int start = POOLTAB.get(POOLTAB.size()-1) - 1;

                // Assign address to literals
                for(int i=start; i<LITTAB.size(); i++) {
                    LITTAB.get(i).setAddress(lc);

                    icode = "(DL, 01)\t(C, " + LITTAB.get(i).getSymbol() + ")";
                    bw.write(lc + "\t" + icode + "\n");
                    System.out.print(lc + "\t" + icode + "\n");
                    lc++;
                }
            }
            else if(split[1].equals("ORIGIN")) {
                if(split[2].contains("+")) {
                    String expr[] = split[2].split("\\+");
                    lc = SYMTAB.get(expr[0]).getAddress() + Integer.parseInt(expr[1]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(expr[0]).getIndex() + ")+" + expr[1];
                }
                else if(split[2].contains("-")) {
                    String expr[] = split[2].split("\\-");
                    lc = SYMTAB.get(expr[0]).getAddress() - Integer.parseInt(expr[1]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(expr[0]).getIndex() + ")-" + expr[1];
                }
                else if(split[2].matches("[0-9]+")){
                    // if split[2] contains only digit
                    lc = Integer.parseInt(split[2]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t" + split[2];
                }
                else {
                    // if split[2] contains only symbol name
                    lc = SYMTAB.get(split[2]).getAddress();
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(split[2]).getIndex() + ")";
                }

                bw.write("\t\t" + icode + "\n");
                System.out.print("\t" + icode + "\n");
            }
            else if(split[1].equals("EQU")) {
                int addr;
                if(split[2].contains("+")) {
                    String expr[] = split[2].split("\\+");
                    addr = SYMTAB.get(expr[0]).getAddress() + Integer.parseInt(expr[1]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(expr[0]).getIndex() + ")+" + expr[1];
                }
                else if(split[2].contains("-")) {
                    String expr[] = split[2].split("\\-");
                    addr = SYMTAB.get(expr[0]).getAddress() - Integer.parseInt(expr[1]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(expr[0]).getIndex() + ")-" + expr[1];
                }
                else if(split[2].matches("[0-9]+")){
                    // if split[2] contains only digit
                    addr = Integer.parseInt(split[2]);
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t" + split[2];
                }
                else {
                    // if split[2] contains only symbol name
                    addr = SYMTAB.get(split[2]).getAddress();
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(S, " + SYMTAB.get(split[2]).getIndex() + ")";
                }

                if(SYMTAB.containsKey(split[0])) {
                    SYMTAB.put(split[0], new allTabRow(split[0], addr, SYMTAB.get(split[0]).getIndex()));
                }
                else {
                    SYMTAB.put(split[0], new allTabRow(split[0], addr, ++symIndex));
                }

                bw.write("\t\t" + icode + "\n");
                System.out.print("\t" + icode + "\n");
            }
            else if(split[1].equals("LTORG")) {

                icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")";
                bw.write("\t\t" +  icode + "\n");
                System.out.print("\t" + icode + "\n");

                int start = POOLTAB.get(POOLTAB.size()-1) - 1;

                // Assign address to literals
                for(int i=start; i<LITTAB.size(); i++) {
                    LITTAB.get(i).setAddress(lc);

                    icode = "(DL, 01)\t(C, " + LITTAB.get(i).getSymbol() + ")";
                    bw.write(lc + "\t" + icode + "\n");
                    System.out.print(lc + "\t" + icode + "\n");
                    lc++;
                }

                POOLTAB.add(LITTAB.size()+1);
            }
            else if(split[1].equals("DC")) {
                int constVal = Integer.parseInt(split[2].replace("'", ""));

                icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(C, " + constVal + ")";

                bw.write(lc + "\t" + icode + "\n");
                System.out.print(lc + "\t" + icode + "\n");

                lc++;
            }
            else if(split[1].equals("DS")) {

                int storage = Integer.parseInt(split[2]);

                icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t(C, " + storage + ")";

                bw.write(lc + "\t" + icode + "\n");
                System.out.print(lc + "\t" + icode + "\n");

                lc += storage;
            }
            else if(ot.getType(split[1]).equals("IS")){
                bw.write(lc + "\t");
                System.out.print(lc + "\t");

                if(ot.getCode(split[1]) < 10)
                    icode = "(" + ot.getType(split[1]) + ", 0" + ot.getCode(split[1]) + ")\t";
                else
                    icode = "(" + ot.getType(split[1]) + ", " + ot.getCode(split[1]) + ")\t";

                int j = 2;

                while(j < split.length) {
                    split[j] = split[j].replace(",", "");

                    if(ot.getType(split[j]).equals("REG")) {
                        icode += "(" + ot.getCode(split[j]) + ")\t";
                    }
                    else if(ot.getType(split[j]).equals("CC")) {
                        icode += "(" + ot.getCode(split[j]) + ")\t";
                    }
                    else if(split[j].contains("=")) {
                        split[j] = split[j].replace("=", "").replace("'", "");

                        int alreadyPresent = 0;
                        int prevLitIndex = -1;
                        int start = POOLTAB.get(POOLTAB.size()-1) - 1;

                        // Check if same literal is used in same pool again
                        for(int i=start; i<LITTAB.size(); i++) {

                            if(LITTAB.get(i).getSymbol().equals(split[j])) {
                                alreadyPresent = 1;
                                prevLitIndex = i;
                            }
                        }

                        // Add literal to literal table if it is not added already in current pool
                        if(alreadyPresent == 0) {
                            LITTAB.add(new allTabRow(split[j], -1, ++litIndex));
                            icode += "(L, " + litIndex + ")";
                        }
                        else {
                            icode += "(L, " + LITTAB.get(prevLitIndex).getIndex() + ")";
                        }


                    }
                    else if(SYMTAB.containsKey(split[j])){
                        icode += "(S, " + SYMTAB.get(split[j]).getIndex() + ")";
                    }
                    else {
                        SYMTAB.put(split[j], new allTabRow(split[j], -1, ++symIndex));
                        icode += "(S, " + SYMTAB.get(split[j]).getIndex() + ")";
                    }

                    j++;
                }
                lc++;
                bw.write(icode + "\n");
                System.out.print(icode + "\n");
            }
        }

        br.close();
        bw.close();		// Close the BufferedWriter after writing to output.txt


        // Writing Symbol Table to file SYMTAB.txt

        BufferedWriter symBW = new BufferedWriter(new FileWriter("SYMTAB.txt"));
        System.out.println("\n\nSYMTAB");
        Iterator<String> iterator = SYMTAB.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            allTabRow value = SYMTAB.get(key);

            System.out.println(value.getIndex()+"\t" + value.getSymbol()+"\t"+value.getAddress());
            symBW.write(value.getIndex()+"\t" + value.getSymbol()+"\t"+value.getAddress()+"\n");
        }
        symBW.close();

        BufferedWriter litBW = new BufferedWriter(new FileWriter("LITTAB.txt"));
        System.out.println("\n\nLITTAB");
        for(int i=0; i<LITTAB.size(); i++) {
            allTabRow value = LITTAB.get(i);
            System.out.println(value.getIndex()+"\t='" + value.getSymbol()+"'\t"+value.getAddress());
            litBW.write(value.getIndex()+"\t='" + value.getSymbol()+"'\t"+value.getAddress()+"\n");
        }
        litBW.close();		// Close the BufferedWriter after writing to LITTAB.txt


        // Writing Pool Table to file POOLTAB.txt

        BufferedWriter poolBW = new BufferedWriter(new FileWriter("POOLTAB.txt"));
        System.out.println("\n\nPOOLTAB");
        for(int i=0; i<POOLTAB.size(); i++) {
            System.out.println("#"+POOLTAB.get(i));
            poolBW.write("#"+POOLTAB.get(i)+"\n");
        }
        poolBW.close();		// Close the BufferedWriter after writing to POOLTAB.txt
    }

    public static void main(String[] args) throws Exception {
        AssignA1 passOne = new AssignA1();
        passOne.generateIC();
    }
}