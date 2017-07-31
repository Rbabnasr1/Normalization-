/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Assignment1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        FunctionDep depen = new FunctionDep();
//        System.out.println("================== File1 ===============================");
//        depen.Normlization("Normal3.txt");
//        
//        System.out.println("==================== Solve1 ===============================");
//        depen.BCNF("Normal3.txt");
//        System.out.println("====================== File2 =============================");
//        depen.Normlization("te.txt");
//        System.out.println("=====================Solve2==========================");
//        depen.BCNF("te.txt");
//        System.out.println("======================= File3 =============================");
//        depen.Normlization("tt.txt");
//        System.out.println("==================== Solve3 ============================");
//        depen.BCNF("tt.txt");
//        System.out.println("==================== File4===========================");
//        depen.Normlization("t2.txt");
//        System.out.println("=======================Solve4==============================");
//        depen.BCNF("t2.txt");
//        System.out.println("======================File5================================");
//        depen.Normlization("t3.txt");
//        System.out.println("========================== Solve5 ================================");
//        depen.BCNF("t3.txt");
//        System.out.println("=============================== File6 =============================");
//        depen.Normlization("Normal.txt");
//        System.out.println("========================= Solve6 =====================================");
//        depen.BCNF("Normal.txt");
//        System.out.println("=============================== File7 =============================");
//         depen.Normlization("ex.txt");
//           depen.Normlization("ex.txt");
//       System.out.println("========================= Solve7 =====================================");
//       depen.BCNF("ex.txt");
        System.out.println("************************************************************************************************");
         depen.Normlization("t5.txt");
//       System.out.println("========================= Solve8 =====================================");
        depen.BCNF("t5.txt");
        Scanner in = new Scanner(System.in);
        int Select = 0;
        String att1, att2;
//        while (true) {
//            System.out.println("1-Function Dependancy");
//
//            System.out.println("2-Candidate Keys and Primary Key");
//            System.out.println("3-Minimal Cover");
//            System.out.println("4-Normal");
//             System.out.println("5-Exist");
//
//            System.out.println("=========================================");
//            System.out.println("the Choice ");
//            Select = in.nextInt();
//            System.out.println("=========================================");
//            if (Select == 1) {
//                System.out.println("R(A,B,C,D)");
//
//                System.out.println("Enter First Attribute");
//                att1 = in.next();
//
//                System.out.println("Enter Second Attribute");
//                att2 = in.next();
//                if (att1.equals("A") || att1.equals("B") || att1.equals("C") || att1.equals("D") || att1.equals("a") || att1.equals("b") || att1.equals("c") || att1.equals("d") && att2.equals("A") || att2.equals("B") || att2.equals("C") || att2.equals("D") || att2.equals("a") || att2.equals("b") || att2.equals("c") || att2.equals("d")) {
//                    System.out.println(depen.Result(att1.charAt(0), att2.charAt(0)).toUpperCase());
//                } else {
//                    System.out.println("Try again");
//                }
//            } else if (Select == 2) {
//
//                depen.key();
//            } else if (Select == 3) {
//                depen.MinCover();
//              
//            } else if (Select == 4) {
//                depen.Normal();
//            } else if(Select==5) {
//               
//                break;
//            }else{ System.out.println("Try again");}
//        }
    }

}
