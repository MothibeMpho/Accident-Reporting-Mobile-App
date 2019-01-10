/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rims;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class Registration{
   
    String phoneNumber;
    DataHandler dh = new DataHandler();
    
    public void newUser()
    {
        Scanner sc = new Scanner(System.in);
        phoneNumber = sc.next();
        dh.InsertNewUser();
        
    }
}
