/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author rabab
 */
public class hello {
    
     public void key() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        RandomAccessFile file = new RandomAccessFile("new.txt", "rw");
        String candidate = "", clousre = "";
        ArrayList<Character> DistinctValue = new ArrayList<>();
        ArrayList<String> CandiditeKeys = new ArrayList<>();

        ArrayList<Character> po = new ArrayList<>();

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
                        po.add(FunctionDep[i].charAt(j));

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
        for (int i = 0; i < FunctionDep.length; i++) {
            //////////////////////////////////first function
            if (candidate == "") {
                for (int j = 0; j < FunctionDep[i].length(); j++) {
                    char characterOfFun = FunctionDep[i].charAt(j);

                    if (characterOfFun == '>') {
                        index = 1;
                    } else {
                        if (index == 0) {
                            candidate += characterOfFun;
                            clousre += characterOfFun;
                            po = Remove(po, characterOfFun);

                        } else {
                            clousre += characterOfFun;
                            po = Remove(po, characterOfFun);

                        }
                    }
                }
            }
/////////////////////////////////////////////////////// in not RHS and not in candidta
            for (int k = 0; k < FunctionDep.length; k++) {

                String split[] = FunctionDep[k].split(">");
                for (int j = 0; j < split[0].length(); j++) {

                    char charOfLeft = split[0].charAt(j);
                    if (!clousre.contains(String.valueOf(charOfLeft)) && !RHS.contains(charOfLeft)) {
                        clousre += charOfLeft;
                        candidate += charOfLeft;

                        po = Remove(po, charOfLeft);

                    }
                }
            }
            ///////////////////////////////////////////////////////// Sort
//
//            System.out.println("cloure " + clousre);
//            change = true;
//            System.out.println("po"+po);
//            while (change) {
//                change = false;
//                for (int k = 0; k < FunctionDep.length; k++) {
//                    String split[] = FunctionDep[k].split(">");
//                    String charOfLeft = split[0];
//                    int iS = 1;
//
//                    for (int j = 0; j < split[0].length(); j++) {
//
//                        if (!clousre.contains(String.valueOf(charOfLeft))) {
//                            iS = 0;
//
//                        }
//                    }
//
//                    if (iS == 1 && !RHS.contains(charOfLeft)) {
//                        clousre += charOfLeft;
//                         for (int j = 0; j < split[0].length(); j++) {
//                        po = Remove(po,charOfLeft.charAt(j));
//                         }
//                        change = true;
//                        break;
//                    }
//                    
////                    if (clousre.length() == DistinctValue.size()) {
////                        change = false;
////                    }
//                }
//                
//                String newc =candidate;
//                String newcl=clousre;
//            }

            char[] chars = candidate.toCharArray();
            Arrays.sort(chars);
            String newText = new String(chars);
            if (!CandiditeKeys.contains(newText)) {
                CandiditeKeys.add(newText);
            }

            candidate = "";
            clousre = "";
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
        System.out.println("******************");
        System.out.println(" dependacy Function");
        System.out.println("********************");
        for (int i = 0; i < FunctionDep.length; i++) {
            System.out.println(FunctionDep[i]);

        }
        System.out.println("******************");
        System.out.println("Distinct value : ");
        System.out.println("******************");
        ShowData(DistinctValue);
        System.out.println("******************");
        System.out.println("Candidate Key : ");
        System.out.println("******************");
        ShowData(CandiditeKeys);
        System.out.println("******************");
        System.out.println(" the primary key is : " + CandiditeKeys.get(indexOfPrimary));
        System.out.println("******************");

    }
public ArrayList Remove(ArrayList<Character> a, char b) throws ClassNotFoundException, SQLException {

        for (int i = 0; i < a.size(); i++) {
            if (b == a.get(i)) {
                a.remove(i);
            }

        }
        return a;

    }
 public void ShowData(ArrayList a) {

        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i));

        }

    }

    
}
