package com.github.guwenk.smuradio;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;
import java.util.Scanner;

/**
 * Created by denis on 25.12.2016.
 */

public class DoHashPass {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("New password: ");
            String pass = in.next();
            System.out.print("Confirm password: ");
            String confirmpass = in.next();
            if (Objects.equals(pass, confirmpass)) {
                try {
                    pass = new SHA_256().hashing(pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Done.");
                System.out.print(pass);
                break;
            } else {
                System.out.println("Passwords do not match.");
                pass = null;
                confirmpass = null;
            }
        }
    }
}
