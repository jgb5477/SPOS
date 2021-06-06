package AssemblerPass1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AssignA2 {

    ArrayList<allTabRow> SYMTAB;
    ArrayList<allTabRow> LITTAB;

    public AssignA2() {
        SYMTAB = new ArrayList<allTabRow>();
        LITTAB = new ArrayList<allTabRow>();
    }

    public void readTabs() {
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\src\\SYMTAB.txt"));
            while((line = br.readLine()) != null) {
                String split[] = line.split("\\s+");
                SYMTAB.add(new allTabRow(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[0])));
            }
            br.close();

            br = new BufferedReader(new FileReader("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\src\\LITTAB.txt"));
            while((line = br.readLine()) != null) {
                String split[] = line.split("\\s+");
                LITTAB.add(new allTabRow(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[0])));
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateMCode() throws Exception {
        readTabs();

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\src\\input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        String line, icode;

        while((line = br.readLine()) != null) {
            String split[] = line.split("\\t+");

            bw.write(split[0] + "\t");

            if(split[1].contains("AD") || split[1].contains("DL, 02")) {
                if(split[1].contains("AD"))
                    bw.write("\t");

                bw.write("+\n");

                continue;
            }
            else if(split.length == 2 && split[1].contains("IS")) {
                int op = Integer.parseInt(split[1].replaceAll("\\D", ""));
                icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", 0) + "\n";
                bw.write(icode);
            }
            else if(split.length == 3) {
                if(split[1].contains("DL, 01")) {
                    int constant = Integer.parseInt(split[2].replaceAll("\\D", ""));
                    icode = "+\t00\t0\t" + String.format("%03d", constant) + "\n";
                    bw.write(icode);
                }
                else if(split[1].contains("IS")) {
                    int op = Integer.parseInt(split[1].replaceAll("\\D", ""));

                    if(op == 10) {
                        if(split[2].contains("S")) {
                            int symIndex = Integer.parseInt(split[2].replaceAll("\\D", ""));
                            icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", SYMTAB.get(symIndex-1).getAddress()) + "\n";
                            bw.write(icode);
                        }
                        else if(split[2].contains("L")) {
                            int litIndex = Integer.parseInt(split[2].replaceAll("\\D", ""));
                            icode = "+\t" + String.format("%02d", op) + "\t0\t" + String.format("%03d", LITTAB.get(litIndex-1).getAddress()) + "\n";
                            bw.write(icode);
                        }
                    }
                }
            }
            else if(split.length == 4 && split[1].contains("IS")) {
                int op = Integer.parseInt(split[1].replaceAll("\\D", ""));
                int reg = Integer.parseInt(split[2].replaceAll("\\D", ""));

                if(split[3].contains("S")) {
                    int symIndex = Integer.parseInt(split[3].replaceAll("\\D", ""));
                    icode = "+\t" + String.format("%02d", op) + "\t" + reg + "\t" + String.format("%03d", SYMTAB.get(symIndex-1).getAddress()) + "\n";
                    bw.write(icode);
                }
                else if(split[3].contains("L")) {
                    int litIndex = Integer.parseInt(split[3].replaceAll("\\D", ""));
                    icode = "+\t" + String.format("%02d", op) + "\t" + reg + "\t" + String.format("%03d", LITTAB.get(litIndex-1).getAddress()) + "\n";
                    bw.write(icode);
                }
            }
        }
        bw.close();
        br.close();
    }

    public static void main(String args[]) throws Exception {
        AssignA2 passOne = new AssignA2();
        passOne.generateMCode();
    }
}
