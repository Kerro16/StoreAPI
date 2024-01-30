package com.kerro.Store.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;



@Configuration
public class KeyConfig {

      @Bean

        public static PrivateKey privateKey() throws NoSuchAlgorithmException, InvalidKeySpecException{
            String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCk09KT8tYcKC90\n" +
                    "GyEep9p1DR7ydmOTbKxK7mOfNqZe79jhHkdt1SWFzWtvaoE/8/mrlbdr8fY5DNim\n" +
                    "Uo+O1HUbKhq5W36uW5OpPjH2viIt0SRCKfc5kcxS5nwa+5nh4IIwrc5XJDPn6lQL\n" +
                    "W+RELXygWu4GM1BUcBXZNPlYzhvOtSn8MGJy7PHKmTXYdBel1ZosHtARFsi9vCU3\n" +
                    "dbes0lNzWM3dz9XsBWriycF2M7p31xUURNsVWZo4pM6YcLCM1yi9yLfrk/tPfYCJ\n" +
                    "lP/cD6OcLXY3QZdE+V1hboCdSNSehQx6Bp76y31s0adLorPZS1czf0Njg8UWmMnV\n" +
                    "Kd1OiqyhAgMBAAECggEAQV/YeiDLRlCz/ZeTC88kiN+C4kUNmpf0QCYMMkZhN29m\n" +
                    "9GbnissK/mZq0b6K9Bwm7yTLIs/NynPrbBB76vkeV39cKR9+f9ZHm01VUNm5wbVd\n" +
                    "bbaqXVWUFl9fYu5e6uV6jacqG4r64WWiHv077MSj1RArrx7kIhFwwP1PVqctBU9W\n" +
                    "pLmnc5hw5/VuOuJEdJno28xuIiFAgF/VBEblkEENbgDiuFrg47071otKUhghKiEm\n" +
                    "Jil8k0VU7pMNtHiqssjjZbye3UYmPB0lDgTUBaNfwnZ3G88C2lUrDrl6KkWD++KC\n" +
                    "V6yVPBcEi6mk5mHULBp+Q1/RxH5VZyGByjBkfCXgvwKBgQDlDpjkzrmaTCeOH04B\n" +
                    "24K2xPO7iTSJSD9zNTxlpSQiGksaj6prpjks5saX0yWxXhnlJSIVEL3ke9S8+FiR\n" +
                    "wE+WTAOg5NbT5z8HpZN08X8E7+2ktPJ5AfBMXrkpTy2mckxS4Qc/Pt+DZYviFLjN\n" +
                    "f+MhXhGdIkuFFTHyiXQSXmnb4wKBgQC4NyIkjaSzByd6fEZFoFhrLw7Qujdc/mJu\n" +
                    "HwCeXDcDSk6ZwCrjd/Nf9ZwHfi/gtXUSq7sGnhH+pdTHVkZyT44Bnold7/lCjCIx\n" +
                    "+BpwhbYQtOdtJh/03qIskq9YJQumyOiAN8IK5/8s2vd35IwlNrBm5pvBG94rsWwI\n" +
                    "MTNmd4nEqwKBgQCBqROvYb36s+UoNFxJ/OIvZ6m4nOBaXcVdB+OafjLIAy5EaNLF\n" +
                    "gSCYZEJCq0mI74WlCrISTpnPfrFcDxDD4KKsp3beWuuopBwvzfB8oD+QoehZ9fvT\n" +
                    "t984u94AnktvDwBZYfxiNsr7bD4UyjoaSaxetQsvcbxiJy9Q5bIIu90clQKBgGXu\n" +
                    "I9dEBNkG4WJV93A5TBNqw3DdSRsMqxwxzV7SuaH5cMqEWXbp/WGgfohq20UkQrZl\n" +
                    "DaSYvG5CBh2ScIJ54KKmvW2cBjk7XwR1OvP+GILgBpoPmWkd83Bkbd+BCBRuYK/p\n" +
                    "qlXsjvab5oiRjrZcr3kQqyhMKj/U+WkDCGTvSeznAoGAQiZjZuEIkrY2havoaB3p\n" +
                    "nAumt/Hmpj44qZthHamzcHFQXbugc1BL3Vkcm0WEqqVX/bpcGI/Q040yEyzXGK8Z\n" +
                    "MLomxHcmwbU1ru+aRrBRHf8YP0l7PubmY7gqv9sLDWmEjsh1N7GhBQfKD/QhN0wk\n" +
                    "iPYLlwVbjvuhaPxMqhMVItE=\n" +
                    "-----END PRIVATE KEY-----";

            // Remove the first and last lines and white spaces
            privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");

            // Decode the base64-encoded key
            byte[] decoded = java.util.Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
        }


    }


