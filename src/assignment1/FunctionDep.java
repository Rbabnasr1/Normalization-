/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FunctionDep {

    public Connection con() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        if (connection != null) {
            return connection;
        } else {

            try {

                String url = "jdbc:mysql://localhost:3306/ass1";
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(url, "root", "root");

                return connection;

            } catch (Exception e) {
                System.out.println("erorr in connection");

            }

        }
        return connection;

    }

    public void ShowData(ArrayList a) {

        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i));

        }

    }

    public void ShowAll(ArrayList a, ArrayList b) {

        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i));
            System.out.print("  pk :   " + b.get(i));
            System.out.println("");
        }
        System.out.println("");

    }

    public void ShowRelation(ArrayList a) {
        System.out.print("R0(");
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i));

        }
        System.out.println(")");

    }

    public String dependency(char Att1, char Att2) throws ClassNotFoundException, SQLException {
        String ResultDepAtt1 = " ";
        ArrayList<String> att1Data = new ArrayList<>();
        ArrayList<String> att2Data = new ArrayList<>();
        Statement st = con().createStatement();
        String sql = "select " + Att1 + " , " + Att2 + " from r";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            if (Att1 == 'A' || Att1 == 'D') {
                att1Data.add(String.valueOf(rs.getInt(String.valueOf(Att1))));
            } else {
                att1Data.add(rs.getString(String.valueOf(Att1)));
            }
            if (Att2 == 'A' || Att2 == 'D') {
                att2Data.add(String.valueOf(rs.getInt(String.valueOf(Att2))));
            } else {
                att2Data.add(rs.getString(String.valueOf(Att2)));
            }

        }
//////////////////////////////////////////////// <> dependancy  Distict Value
        for (int i = 0; i < att1Data.size(); i++) {
            for (int j = 0; j < att2Data.size(); j++) {
                if (att1Data.get(i).equals(att1Data.get(j))) {
                    if (!att2Data.get(i).equals(att2Data.get(j))) {
                        ResultDepAtt1 += "!" + Att1 + "-->" + Att2;
                        break;
                    }
                }
            }

            if (!ResultDepAtt1.equals(" ")) {/////////////////3shan msh append 
                break;
            }
        }
/////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////  dependancy 
        if (ResultDepAtt1.equals(" ")) {
            ResultDepAtt1 += Att1 + "-->" + Att2;
        }
        return ResultDepAtt1;
    }

    public String Result(char Att1, char Att2) throws ClassNotFoundException, SQLException {

        return dependency(Att1, Att2) + dependency(Att2, Att1);

    }

    public ArrayList Remove(ArrayList<Character> a, char b) throws ClassNotFoundException, SQLException {

        for (int i = 0; i < a.size(); i++) {
            if (b == a.get(i)) {
                a.remove(i);
            }

        }
        return a;

    }

    public ArrayList key(String fileD) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

//        RandomAccessFile file = new RandomAccessFile("Normal3.txt", "rw");
        RandomAccessFile file = new RandomAccessFile(fileD, "rw");

        String candidate = "", closure = "";
        ArrayList<String> CandiditeKeys = new ArrayList<>();

        ArrayList<Character> remainders = new ArrayList<>();
        ArrayList<Character> DistinctValue = new ArrayList<>();
        String Record = file.readLine();
        String RecordsFile = Record;
        String FunctionDep[] = null;

        while (Record != null) {///////// More than Record
            Record = file.readLine();
            if (Record != null) {
                RecordsFile += Record;
            }
        }
        FunctionDep = RecordsFile.split(",");////////////////////////////DistinctValue
        for (int i = 0; i < FunctionDep.length; i++) {
            for (int j = 0; j < FunctionDep[i].length(); j++) {
                if (!DistinctValue.contains(FunctionDep[i].charAt(j))) {
                    if (FunctionDep[i].charAt(j) != '>') {
                        DistinctValue.add(FunctionDep[i].charAt(j));
//                    
                    }

                }
            }
        }

        /////////////////////////////////////////////////////// Store RHS 
        ArrayList<Character> RHS = new ArrayList<>();

        for (int i = 0; i < FunctionDep.length; i++) {
            String split[] = FunctionDep[i].split(">");
            for (int j = 0; j < split[1].length(); j++) {
                RHS.add(split[1].charAt(j));
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////
        boolean change = true;
        int index = 0;
        int UseRemainder = 0;
        for (int i = 0; i < FunctionDep.length; i++) {

            remainders = new ArrayList<>();
            for (int j = 0; j < DistinctValue.size(); j++) {
                remainders.add(DistinctValue.get(j));

            }

            //////////////////////////////////first function
            if (candidate == "") {
                for (int j = 0; j < FunctionDep[i].length(); j++) {
                    char characterOfFun = FunctionDep[i].charAt(j);

                    if (characterOfFun == '>') {
                        index = 1;
                    } else {
                        if (index == 0) {
                            candidate += characterOfFun;
                            closure += characterOfFun;
                            remainders = Remove(remainders, characterOfFun);

                        } else {
                            closure += characterOfFun;
                            remainders = Remove(remainders, characterOfFun);

                        }
                    }
                }
            }

/////////////////////////////////////////////////////// in not RHS and not in candidta
            for (int k = 0; k < FunctionDep.length; k++) {

                String split[] = FunctionDep[k].split(">");

                for (int j = 0; j < split[0].length(); j++) {

                    char charOfLeft = split[0].charAt(j);
                    if (!closure.contains(String.valueOf(charOfLeft)) && !RHS.contains(charOfLeft)) {
                        closure += charOfLeft;
                        candidate += charOfLeft;

                        remainders = Remove(remainders, charOfLeft);

                    }
                }
            }

            ///////////////////////////////////////////////////////////////// loop 3la el fn
//            System.out.println(i + "cloure " + closure + "+ Candidtae " + candidate);
            change = true;
            while (change) {
//                System.out.println("======================================================");
                change = false;
                for (int k = 0; k < FunctionDep.length; k++) {
//                    System.out.println("FN :  " + FunctionDep[k]);
                    String split[] = FunctionDep[k].split(">");
                    String charOfLeft = split[0];
                    String charOfRight = split[1];
                    int Exist = 1;
                    for (int j = 0; j < split[0].length(); j++) {
                        if (!closure.contains(String.valueOf(charOfLeft.charAt(j)))) {
                            Exist = 0;
                            break;

                        }
                    }

                    if (Exist == 1 && !closure.contains(String.valueOf(charOfRight))) {
                        for (int j = 0; j < charOfRight.length(); j++) {

                        }
                        closure += charOfRight;
                        for (int j = 0; j < split[0].length(); j++) {
                            remainders = Remove(remainders, charOfLeft.charAt(j));
                        }
                        change = true;
                    }
                }
                if (closure.length() >= DistinctValue.size() && change == false) {
                    break;
                } else if (closure.length() < DistinctValue.size() && change == false) {
                    candidate += remainders.get(closure(remainders, candidate, closure, FunctionDep, DistinctValue));
                    closure += remainders.get(closure(remainders, candidate, closure, FunctionDep, DistinctValue));
                    remainders.remove(closure(remainders, candidate, closure, FunctionDep, DistinctValue));
                }

            }
///////////////////////////////////////////////////////////////////////////sort
            char[] chars = candidate.toCharArray();
            Arrays.sort(chars);
            String newText = new String(chars);
            if (!CandiditeKeys.contains(newText)) {
                CandiditeKeys.add(newText);
            }

            candidate = "";
            closure = "";
            index = 0;

        }///////////////////////////////////////primary key
        int primaryKey = 200;
        int indexOfPrimary = -1;
        for (int i = 0; i < CandiditeKeys.size(); i++) {
            if (primaryKey > CandiditeKeys.get(i).length()) {
                primaryKey = CandiditeKeys.get(i).length();
                indexOfPrimary = i;

            }

        }
//        System.out.println("******************");
//        System.out.println(" dependacy Function");
//        System.out.println("********************");
//        for (int i = 0; i < FunctionDep.length; i++) {
//            System.out.println(FunctionDep[i]);
//
//        }

        return CandiditeKeys;

    }

    public int closure(ArrayList<Character> remaind, String candidate, String closure, String[] Record, ArrayList distinctValue) throws ClassNotFoundException, SQLException {
        boolean change = true;
        int[] candIndex = new int[remaind.size()];
        int index = 0;

        for (int i = 0; i < remaind.size(); i++) {
            String newCand = "";
            newCand = candidate;
            String newClosure = "";
            newClosure = closure;
            newCand += remaind.get(i);
            newClosure += remaind.get(i);
            change = true;
            while (change) {
                change = false;

                for (int k = 0; k < Record.length; k++) {
//                    System.out.println("FN :  " + Record[k]);
                    String split[] = Record[k].split(">");
                    String charOfLeft = split[0];
                    String charOfRight = split[1];
                    int Exist = 1;
                    for (int j = 0; j < split[0].length(); j++) {
                        if (!newClosure.contains(String.valueOf(charOfLeft.charAt(j)))) {
                            Exist = 0;
//                            System.out.println("msh mwgodaaa ");
                            break;

                        }
                    }

                    if (Exist == 1 && !newClosure.contains(String.valueOf(charOfRight))) {
//                        System.out.println("mwgodaa ");
                        for (int j = 0; j < charOfRight.length(); j++) {
                            if (!newClosure.contains(String.valueOf(charOfRight.charAt(j)))) {
                                newClosure += charOfRight.charAt(j);
                            }
                        }
//                        System.out.println("distinct len " + distinctValue.size() + "  y len " + newClosure.length());
                        if (newClosure.length() >= distinctValue.size()) {
//                            System.out.println("a5bark eh ya len ");
                            change = false;
                            break;
                        }

                        change = true;

                    }
                }

            }
            candIndex[i] = newClosure.length();

        }
        int max = -1;

        for (int i = 0; i < candIndex.length; i++) {
            if (max > candIndex[i]) {
                max = candIndex[i];
                index = i;

            }

        }
        return index;

    }

    public boolean Contain(ArrayList can, String lft) {

        char[] chars = lft.toCharArray();
        Arrays.sort(chars);
        String LEFT = new String(chars);
        for (int i = 0; i < can.size(); i++) {
            if (can.get(i).equals(LEFT)) {
                return true;

            }
        }
        return false;

    }

    public boolean SuperKey(ArrayList<String> can, String lft) {

        char[] chars = lft.toCharArray();
        Arrays.sort(chars);
        String LEFT = new String(chars);
        for (int i = 0; i < can.size(); i++) {
            int Counter = 0;
            for (int j = 0; j < can.get(i).length(); j++) {
                if (LEFT.contains(String.valueOf(can.get(i).charAt(j)))) {
                    Counter++;
                }
            }
            if (Counter == can.get(i).length() && Counter < LEFT.length()) {
                return true;
            }
        }
        return false;

    }

    public ArrayList CandidateKeys(ArrayList<String> Candidate) {
        int LengthOfPrimary = 500;
        ArrayList<String> CandiditeKey = new ArrayList<>();
        ArrayList<String> PK = new ArrayList<>();
////////////////////////////////////len of PK
        for (int i = 0; i < Candidate.size(); i++) {
            if (LengthOfPrimary > Candidate.get(i).length()) {
                LengthOfPrimary = Candidate.get(i).length();

            }

        }
//////////////////////////////////////////////////////////All Of primary
        for (int i = 0; i < Candidate.size(); i++) {
            if (Candidate.get(i).length() == LengthOfPrimary) {
                CandiditeKey.add(Candidate.get(i));
                PK.add(Candidate.get(i));
            }
        }

        ///////////////////////////////////////////////////////////////////////      
        for (int i = 0; i < Candidate.size(); i++) {
            int IsFound = 0;
            for (int k = 0; k < PK.size(); k++) {
                int lenOfPK = 0;
                for (int j = 0; j < Candidate.get(i).length(); j++) {
                    if (PK.get(k).contains(String.valueOf(Candidate.get(i).charAt(j)))) {
                        lenOfPK++;

                    }
                }

                if (lenOfPK != PK.get(k).length() && lenOfPK != 0) {
                    if (!Contain(CandiditeKey, Candidate.get(i))) {
                        CandiditeKey.add(Candidate.get(i));
                        break;
                    }
                }
                if (lenOfPK == 0) {
                    IsFound++;
                }
            }

            if (IsFound == PK.size()) {
                if (!Contain(CandiditeKey, Candidate.get(i))) {
                    CandiditeKey.add(Candidate.get(i));
                }
            }
        }

        return CandiditeKey;

    }

    public void Normlization(String fileD) throws IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        RandomAccessFile file = new RandomAccessFile(fileD, "rw");
        ArrayList<Character> DistinctValue = new ArrayList<>();
        String Record = file.readLine();
        String RecordsFile = Record;
        String FunctionDep[] = null;
        String HighstNormal = "FirstNForm";
        ArrayList<String> CandidateKey = new ArrayList<>();
        ArrayList<String> SecondN = new ArrayList<>();
        ArrayList<String> PrimeAttribute = new ArrayList<>();
        CandidateKey = CandidateKeys(key(fileD));

        System.out.println("The Condidite Keys:  " + CandidateKey);
        /////////////////////////////////////////////////////////////////////prime       
        for (int i = 0; i < CandidateKey.size(); i++) {
            for (int j = 0; j < CandidateKey.get(i).length(); j++) {
                if (!PrimeAttribute.contains(CandidateKey.get(i).charAt(j))) {
                    PrimeAttribute.add(String.valueOf(CandidateKey.get(i).charAt(j)));

                }
            }
        }
        System.out.println("Prime  "+PrimeAttribute);
////////////////////////////DistinctValue
        FunctionDep = RecordsFile.split(",");
        for (int i = 0; i < FunctionDep.length; i++) {
            for (int j = 0; j < FunctionDep[i].length(); j++) {
                if (!DistinctValue.contains(FunctionDep[i].charAt(j))) {
                    if (FunctionDep[i].charAt(j) != '>') {
                        DistinctValue.add(FunctionDep[i].charAt(j));

                    }

                }
            }
        }
        System.out.println("==============================================");
        System.out.println("Function Dependancy");
        System.out.println("================================================");
        for (int i = 0; i < FunctionDep.length; i++) {
            System.out.println(FunctionDep[i]);

        }

        //////////////////////////////////////////////////RHS AND LHS
        ArrayList<String> RHS = new ArrayList<>();
        ArrayList<String> LHS = new ArrayList<>();
        for (int i = 0; i < FunctionDep.length; i++) {
            String split[] = FunctionDep[i].split(">");
            RHS.add(split[1]);
            LHS.add(split[0]);
        }

        boolean violate = false;
        int NumberOFattributeInLeft = 0;
///////////////////////////Second Check
        for (int k = 0; k < CandidateKey.size(); k++) {

            for (int i = 0; i < LHS.size(); i++) {
                NumberOFattributeInLeft = 0;
                if (!Contain(CandidateKey, LHS.get(i))) {
                    for (int j = 0; j < CandidateKey.get(k).length(); j++) {
                        if (LHS.get(i).contains(String.valueOf(CandidateKey.get(k).charAt(j)))) {
                            NumberOFattributeInLeft++;
                        }
                    }
                    if (NumberOFattributeInLeft < CandidateKey.get(k).length() && NumberOFattributeInLeft != 0 && NumberOFattributeInLeft == LHS.get(i).length()) {
                        for (int j = 0; j < RHS.get(i).length(); j++) {
                            if (!PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(j)))) {
                                violate = true;
                                break;
                            }
                        }

                    }
                }
            }
        }
        if (violate == false) {
            HighstNormal = "SecondNfORM";
        }

        if (HighstNormal.equals("SecondNfORM")) {
///////////////////////////////////////////////////////////////////////Third Check
            for (int i = 0; i < LHS.size(); i++) {
                if (!Contain(CandidateKey, LHS.get(i))) {
                    if (!SuperKey(CandidateKey, LHS.get(i))) {
                        for (int j = 0; j < LHS.get(i).length(); j++) {
                            if (!PrimeAttribute.contains(String.valueOf(LHS.get(i).charAt(j)))) {
                                for (int k = 0; k < RHS.get(i).length(); k++) {
                                    if (!PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(k)))) {

                                        violate = true;
                                        break;
                                    }
                                }

                            }

                        }
                    }
                }
            }
            if (!violate) {
                HighstNormal = "ThirdNForm";
            }
        }

        if (HighstNormal.equals("ThirdNForm")) {
///////////////////////////////////////////////////////////////BCNF CHECK
            for (int i = 0; i < LHS.size(); i++) {
                if (!Contain(CandidateKey, LHS.get(i))) {
                    if (!SuperKey(CandidateKey, LHS.get(i))) {
//                        for (int j = 0; j < LHS.get(i).length(); j++) {
//                            if (!PrimeAttribute.contains(String.valueOf(LHS.get(i).charAt(j)))) {
//                                for (int k = 0; k < RHS.get(i).length(); k++) {
//                                    if (PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(k)))) {
//                                        violate = true;
//                                        break;
//                                    }
//                                }
//                            }
//
//                        }
 violate = true;
                    }
                }
            }
        }
        if (!violate) {
            HighstNormal = "BCNF";
        }

        System.out.println("The Highest Normal Form Is :" + HighstNormal);

    }

    public void BCNF(String fileD) throws IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        ArrayList<String> NCandiditeKey = new ArrayList<>();
        ArrayList<String> BCNF = new ArrayList<>();
        ArrayList<String> SecondN = new ArrayList<>();
        ArrayList<Character> MainRelation = new ArrayList<>();
        ArrayList<String> PrimeAttribute = new ArrayList<>();
        RandomAccessFile file = new RandomAccessFile(fileD, "rw");
        ArrayList<Character> DistinctValue = new ArrayList<>();
        String Record = file.readLine();
        String RecordsFile = Record;
        String FunctionDep[] = null;
        String Highst = "FirstNForm";
        NCandiditeKey = CandidateKeys(key(fileD));
////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < NCandiditeKey.size(); i++) {
            for (int j = 0; j < NCandiditeKey.get(i).length(); j++) {
                if (!PrimeAttribute.contains(String.valueOf(NCandiditeKey.get(i).charAt(j)))) {
                    PrimeAttribute.add(String.valueOf(NCandiditeKey.get(i).charAt(j)));

                }
            }
        }

        FunctionDep = RecordsFile.split(",");////////////////////////////DistinctValue
        for (int i = 0; i < FunctionDep.length; i++) {
            for (int j = 0; j < FunctionDep[i].length(); j++) {
                if (!DistinctValue.contains(FunctionDep[i].charAt(j))) {
                    if (FunctionDep[i].charAt(j) != '>') {
                        DistinctValue.add(FunctionDep[i].charAt(j));

                    }

                }
            }
        }

        //////////////////////////////////////////////////RHS AND LHS
        ArrayList<String> RHS = new ArrayList<>();
        ArrayList<String> LHS = new ArrayList<>();
        for (int i = 0; i < FunctionDep.length; i++) {
            String split[] = FunctionDep[i].split(">");
            if (!LHS.contains(split[0])) {
                RHS.add(split[1]);
                LHS.add(split[0]);

            } else {
                for (int j = 0; j < LHS.size(); j++) {
                    if (LHS.get(j).equals(split[0])) {
                        RHS.set(j, RHS.get(j) + split[1]);
                    }

                }

            }

        }

        MainRelation = DistinctValue;

        boolean change = true;
        int counter = 0;
        while (change) {
            change = false;
            for (int k = 0; k < NCandiditeKey.size(); k++) {
                counter = 0;
                for (int i = 0; i < LHS.size(); i++) {
                    if (!Contain(NCandiditeKey, LHS.get(i))) {

                        for (int j = 0; j < NCandiditeKey.get(k).length(); j++) {
                            if (LHS.get(i).contains(String.valueOf(NCandiditeKey.get(k).charAt(j)))) {
                                counter++;
                            }
                        }

                        if (counter < NCandiditeKey.get(k).length() && counter != 0 && counter == LHS.get(i).length()) {
                            for (int j = 0; j < RHS.get(i).length(); j++) {
                                if (!PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(j)))) {
                                    SecondN.add(LHS.get(i) + ">" + RHS.get(i));
                                    LHS.remove(i);
                                    RHS.remove(i);

                                    change = true;
                                    break;

                                }
                            }

                        }

                    }
                }
            }
        }
        int len = 0;
        if (SecondN.size() > 0) {
            System.out.println("===========After Second=======");
            System.out.println("" + SecondN);
            len = SecondN.size();
        }
        change = true;
        while (change) {
            change = false;
            for (int i = 0; i < LHS.size(); i++) {
                if (!Contain(NCandiditeKey, LHS.get(i))) {
                    if (!SuperKey(NCandiditeKey, LHS.get(i))) {
                        for (int j = 0; j < LHS.get(i).length(); j++) {

                            if (!PrimeAttribute.contains(String.valueOf(LHS.get(i).charAt(j)))) {
                                change = true;
                                break;
                            }

                        }
                        if (change) {
                            for (int j = 0; j < RHS.get(i).length(); j++) {
                                if (!PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(j)))) {
                                    SecondN.add(LHS.get(i) + ">" + RHS.get(i));
                                    LHS.remove(i);
                                    RHS.remove(i);
                                    change = true;
                                    break;
                                } else {
                                    change = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (SecondN.size() > len) {
            System.out.println("===========After Third=======");
            System.out.println("" + SecondN);
            len = SecondN.size();
        }

        change = true;
        while (change) {
            change = false;
            for (int i = 0; i < LHS.size(); i++) {
                if (!Contain(NCandiditeKey, LHS.get(i))) {
                    if (!SuperKey(NCandiditeKey, LHS.get(i))) {
//                        for (int j = 0; j < LHS.get(i).length(); j++) {
//                            if (!PrimeAttribute.contains(String.valueOf(LHS.get(i).charAt(j)))) {
                                change = true;
//                                break;
//                            }
//
//                        }
                        if (change) {
                            for (int j = 0; j < RHS.get(i).length(); j++) {
                                if (PrimeAttribute.contains(String.valueOf(RHS.get(i).charAt(j)))) {
                                    BCNF.add(LHS.get(i) + ">" + RHS.get(i));
                                    LHS.remove(i);
                                    RHS.remove(i);
                                    change = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (BCNF.size() > 0) {
            System.out.println("===========After BCNF=======");
            System.out.println("" + SecondN + " " + BCNF);

        }
        ArrayList<String> Relation = new ArrayList<>();

        ///////////////////////////////////////b7otohm fe shakl Relation R(,,,) lel seconf w el third
        for (int i = 0; i < SecondN.size(); i++) {

            String split[] = SecondN.get(i).split(">");
            String RelationForm = "";
            String pkOfRelation = "";

            for (int k = 0; k < split.length; k++) {
                for (int l = 0; l < split[k].length(); l++) {
                    if (!RelationForm.contains(String.valueOf(split[k].charAt(l)))) {
                        RelationForm += split[k].charAt(l);
                        if (k == 0) {
                            pkOfRelation = split[0];
                        }
                    }
                    if (k == 1) {
                        for (int m = 0; m < MainRelation.size(); m++) {
                            if (MainRelation.get(m).equals(split[1].charAt(l))) {
                                MainRelation.remove(m);
                                break;

                            }
                        }

                    }

                }

            }
            Relation.add("R" + ": (" + RelationForm + ") PK : " + pkOfRelation);

        }
        ///////////////////////////////////////b7otohm fe shakl Relation R(,,,) lel BCNF
        for (int i = 0; i < BCNF.size(); i++) {

            String split[] = BCNF.get(i).split(">");
            String RelationBCNF = "";
            String pk = "";
            for (int k = 0; k < split.length; k++) {
                for (int l = 0; l < split[k].length(); l++) {
                    if (!RelationBCNF.contains(String.valueOf(split[k].charAt(l)))) {
                        RelationBCNF += split[k].charAt(l);
                        if (k == 0) {
                            pk = split[0];
                        }

                    }
                    if (k == 0) {
                        for (int m = 0; m < MainRelation.size(); m++) {
                            if (MainRelation.get(m).equals(split[k].charAt(l))) {
                                MainRelation.remove(m);
                                break;

                            }
                        }

                    }

                }

            }
            Relation.add("  R" + (i + 1) + "(" + RelationBCNF + ")   PK : " + pk);
            System.out.println("");

        }
        System.out.println("====================================================");
        System.out.println(" Final ");
        System.out.println("==========");
        ShowRelation(MainRelation);
        if (Relation.size() > 0) {
            System.out.println(Relation);
        }

        System.out.println("------------------------------------------------------------------------------");

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

    }

}
