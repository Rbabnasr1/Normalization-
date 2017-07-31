/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author rabab
 */
public class TEST {
   public ArrayList NewCandidate(ArrayList<String> CandiditeKeys) {
        int LengthOfPrimary = 200;
        int indexOfPrimary = -1;
        ArrayList<String> NewCandiditeKey = new ArrayList<>();

        for (int i = 0; i < CandiditeKeys.size(); i++) {
            if (LengthOfPrimary > CandiditeKeys.get(i).length()) {
                LengthOfPrimary = CandiditeKeys.get(i).length();
                indexOfPrimary = i;

            }

        }
        String PK = CandiditeKeys.get(indexOfPrimary);
        NewCandiditeKey.add(PK);

        //////////////////////////////////////////////////////////////////////////Review candidite after Removing
        for (int i = 0; i < CandiditeKeys.size(); i++) {
            int lenOfPK = 0;
            for (int j = 0; j < CandiditeKeys.get(i).length(); j++) {
                if (PK.contains(String.valueOf(CandiditeKeys.get(i).charAt(j)))) {
                    lenOfPK++;

                }
            }
            if (lenOfPK != PK.length()) {
                NewCandiditeKey.add(CandiditeKeys.get(i));

            }

        }
        return NewCandiditeKey;

    }
     public void MinCover() throws FileNotFoundException, IOException {

        RandomAccessFile file = new RandomAccessFile("test.txt", "rw");
        ArrayList<String> LHS = new ArrayList<>();
        ArrayList<String> RHS = new ArrayList<>();
        ArrayList<String> RightR = new ArrayList<>();
        String Records = file.readLine();
        String dependancyFunction[] = Records.split(",");
        String ch[] = null;
        //////////////////////////////////////////////////////////////////////////////////split RHS,LFS
        for (int i = 0; i < dependancyFunction.length; i++) {
            ch = dependancyFunction[i].split(">");
            LHS.add(ch[0]);
            RHS.add(ch[1]);
        }
        /////////////////////////////////////////////////////////////////////////////////////////// Right Red 1
        for (int i = 0; i < RHS.size(); i++) {
            if (RHS.get(i).length() > 1) {
                for (int j = 0; j < RHS.get(i).length(); j++) {
                    RightR.add(LHS.get(i) + ">" + RHS.get(i).charAt(j));
                }
            } else {
                RightR.add(LHS.get(i) + ">" + RHS.get(i));
            }
        }
//////////////////////////////////////////////////////////////////////////////  Cond 2
        LHS = new ArrayList<>();
        RHS = new ArrayList<>();
        for (int i = 0; i < RightR.size(); i++) {
            ch = RightR.get(i).split(">");
            LHS.add(ch[0]);
            RHS.add(ch[1]);
        }
        char leftRemoved;
        String left = "";
        String leftAppend = "";
        boolean change = true;
        boolean change2 = true;
        while (change2) {
            change2 = false;
            for (int i = 0; i < LHS.size(); i++) {
                if (LHS.get(i).length() > 1) {
                    for (int j = 0; j < LHS.get(i).length(); j++) {
                        leftRemoved = LHS.get(i).charAt(j);
                        leftAppend = LHS.get(i).substring(0, j);
                        leftAppend += LHS.get(i).substring(j + 1, LHS.get(i).length());
                        left = leftAppend;
                        change = true;
                        while (change) {
                            change = false;
                            for (int k = 0; k < LHS.size(); k++) {
                                int leftCorrectCondition = 1;
                                for (int l = 0; l < LHS.get(k).length(); l++) {
                                    if (!leftAppend.contains(String.valueOf(LHS.get(k).charAt(l)))) {//check more than char
                                        leftCorrectCondition = 0;
                                    }
                                }

                                if ((leftCorrectCondition == 1) && !leftAppend.contains(RHS.get(k))) {
                                    leftAppend += RHS.get(k);
                                    if (leftAppend.contains(RHS.get(i))) {
                                        change = false;
                                        change2 = true;
                                        LHS.set(i, left);
                                        break;
                                    } else {
                                        change = true;
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////non Red
        left = "";

        String right = "";
        leftAppend = "";
        change = true;
        change2 = true;

        int iteration = 0;
        while (iteration < LHS.size()) {

            right = RHS.get(iteration);
            leftAppend = LHS.get(iteration);
            left = leftAppend;

            change = true;
            while (change) {
                change = false;
                for (int k = 0; k < LHS.size(); k++) {
                    if (k != iteration) {
                        int iS = 1;
                        for (int l = 0; l < LHS.get(k).length(); l++) {
                            if (!leftAppend.contains(String.valueOf(LHS.get(k).charAt(l)))) {
                                iS = 0;
                            }
                        }

                        if ((iS == 1) && !leftAppend.contains(RHS.get(k))) {
                            leftAppend += RHS.get(k);
                            if (leftAppend.contains(right)) {
                                change = false;
                                LHS.remove(iteration);
                                RHS.remove(iteration);
                                iteration--;

                                break;
                            } else {
                                change = true;

                            }
                        }

                    }

                }

            }
            iteration++;

        }
        System.out.println("===================================================");
        System.out.println("minCover");
        System.out.println("===================================================");
        for (int i = 0; i < LHS.size(); i++) {

            System.out.println(" ** " + LHS.get(i) + " > " + RHS.get(i));

        }
    }

   
}
