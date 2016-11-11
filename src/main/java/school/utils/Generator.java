package school.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Created by Cheshire on 16.10.2016.
 */
@Component
public class Generator {

//    public static final String SOURCES =
//            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public String simplePassGenerator() {
        System.out.println("*********************ЗАПУСТИЛИ simplePassGenerator()************************");
        String SOURCES =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        SecureRandom secureRandom = new SecureRandom();
        char[] passwordTemp = new char[8];
        for (int i = 0; i < 8; i++) {
            passwordTemp[i] = SOURCES.charAt(secureRandom.nextInt(SOURCES.length()));
        }
        return new String(passwordTemp);
    }

    public String simpleUsernameGenerator(String prefix) {
        System.out.println("*********************ЗАПУСТИЛИ simpleUsernameGenerator()************************");
        String SOURCES =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom secureRandom = new SecureRandom();
        char[] usernameTemp = new char[5];
        for (int i = 0; i < 5; i++) {
            usernameTemp[i] = SOURCES.charAt(secureRandom.nextInt(SOURCES.length()));
        }
        String username = prefix + new String(usernameTemp);
        return username;
    }





//    public static void main(String[] args) {
//        Generator generator = new Generator();
//        System.out.println(generator.simplePassGenerator(SOURCES));
//    }

}
