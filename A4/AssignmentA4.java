import javax.print.attribute.IntegerSyntax;
import java.io.*;
import java.util.*;

class DefaultHandle{
    String mnt_name;
    String index;
    String value;

    DefaultHandle(String name, String i, String v){
        mnt_name = name;
        index = i;
        value = v;
    }

    public String getMnt_name(){
        return mnt_name;
    }
    public String getIndex(){
        return index;
    }
    public String getValue(){
        return value;
    }
}

class PassII{
    ArrayList<String> mdt = new ArrayList<String>();
    HashMap<String,String> mnt = new HashMap<String, String>();
    ArrayList<String> formalParams = new ArrayList<String>();
    ArrayList<String> actualParams = new ArrayList<String>();
    ArrayList<DefaultHandle> default1 = new ArrayList<DefaultHandle>();
    int mdt_ptr;

    public String replaceFormalParams(String formalList, String mname){
        formalList = formalList.replaceAll("#","");
        String parameters[] = formalList.split(",");
        int ind;
        String actual="", s="";
        for(String i: parameters){
            ind = Integer.parseInt(i);
            if(ind<=actualParams.size()){
                actual = actualParams.get(ind-1);
            }
            else{
                for(DefaultHandle j: default1){
                    if(j.getMnt_name().equals(mname) & Integer.parseInt(j.getIndex().replace("#",""))==ind){
                        actual = j.getValue();
                    }
                }
                // actual = formalParams.get(ind-1);
            }
            s += actual+",";
        }
        s = s.substring(0,s.length()-1);
        return  s;
    }

    public void readMnt() throws FileNotFoundException{
        int mdtptr = -1;

        File file = new File("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\MNT.txt");
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String data = reader.nextLine();
            String split_words[] = data.split("\t");
            mnt.put(split_words[1],split_words[3]);
        }
        reader.close();
    }

    public void readMdt() throws FileNotFoundException {
        File file = new File("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\MDT.txt");
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String data = reader.nextLine();
            String split_words[] = data.split("\t");
            if(split_words[1].equalsIgnoreCase("MEND")){
                mdt.add(Integer.parseInt(split_words[0]),split_words[1]);
            }
            else{
                mdt.add(Integer.parseInt(split_words[0]),split_words[1]+" "+split_words[2]);
            }
        }
        reader.close();
    }

    public void Default() throws FileNotFoundException {
        File file = new File("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\Default.txt");
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String data = reader.nextLine();
            String split_words[] = data.split("\t");
            default1.add(new DefaultHandle(split_words[0],split_words[1],split_words[2]));
        }
        reader.close();
    }

    public void output() throws IOException {
        PrintWriter passII = new PrintWriter(new FileWriter("PASSII.txt"),true);
        File file = new File("C:\\Users\\jgb54\\IdeaProjects\\FirstJavaProg\\IC.txt");
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String data = reader.nextLine();
            String split_words[] = data.split("\t");
            if(mnt.containsKey(split_words[0])){
                String actualpara[] = split_words[1].split(",");
                for(String i: actualpara){
                    if(i.contains("=")){
                        String par[] = i.split("=");
                        actualParams.add(par[1]);
                    }else{
                        actualParams.add(i);
                    }
                }
                mdt_ptr = Integer.parseInt(mnt.get(split_words[0]));
                String mName="";
                mName = split_words[0];
                mdt_ptr++;
                while(true){
                    String mDef = mdt.get(mdt_ptr);
                    if(mDef.equalsIgnoreCase("MEND")){
                        formalParams.clear();
                        actualParams.clear();
                        mdt_ptr=-1;
                        break;
                    }else{
                        String inst[] = mDef.split(" ");
                        String params = replaceFormalParams(inst[1],mName);
                        System.out.println("+"+inst[0]+" "+params);
                        passII.println("+"+inst[0]+" "+params);
                        mdt_ptr++;
                    }
                }
            }else{
                System.out.println(data);
                passII.println(data);
            }
        }
        reader.close();
    }
}

public class AssignmentA4 {
    public static void main(String args[]) throws IOException {
        PassII p = new PassII();
        p.readMnt();
        p.readMdt();
        p.Default();
        p.output();
    }
}
