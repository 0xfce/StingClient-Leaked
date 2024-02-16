package me.sting.client.product.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.sting.client.product.Sting;
import me.sting.client.product.module.Module;
import me.sting.client.product.module.utilities.AntiStaffs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class APIUtil
{
    public static String[] USERS;
    public static List<String> STAFFS_COLLECTED;
    private static File collection;
    public static String CLOUDFLARE_KEY;
    public static final String API_WEBSITE;
    public static final String API_WEBSITE_AUTHENTICATION;
    public static String url = null;
    
    public static boolean getUSERS() {
        return false;
    }

    public APIUtil() {
        AntiStaffs antiStaffs = new AntiStaffs();
        if (APIUtil.collection.exists() && antiStaffs.security) {
            try {
                APIUtil.collection.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void fetchStaffs() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Display a message indicating staff collection is in progress
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "AntiStaffs: " + EnumChatFormatting.GRAY + "just a moment, collecting..."));

                    // Clear the list of collected staff members
                    APIUtil.STAFFS_COLLECTED.clear();

                    // Make an HTTP GET request to retrieve staff information
                    HttpURLConnection connection = (HttpURLConnection) new URL(url + "/staffs").openConnection();
                    connection.setRequestMethod("GET");

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // Read the response and collect staff names
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] split = line.split("e\":\"");
                            for (String s : split) {
                                String staffName = s.split("\"")[0].replace("[{", "");
                                if (!APIUtil.STAFFS_COLLECTED.contains(staffName)) {
                                    APIUtil.STAFFS_COLLECTED.add(staffName);
                                }
                            }
                        }
                        reader.close();

                        // Display a success message in the chat
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "AntiStaffs: " + EnumChatFormatting.GREEN + "staffs successfully collected."));
                    } else {
                        // If the response code is not OK, handle the error
                        handleCollectionError();
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    // Handle IO exception
                    handleCollectionError();
                    e.printStackTrace();
                }
            }


        });
        if (Sting.antiStaffs.security) {
            thread.setDaemon(true);
            thread.start();
        }
    }

    public static void handleCollectionError() {
        try {
            // Try to read staff information from a file if it exists
            if (collection.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(collection));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!APIUtil.STAFFS_COLLECTED.contains(line)) {
                        APIUtil.STAFFS_COLLECTED.add(line);
                    }
                }
                reader.close();

                // Display a success message in the chat
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "AntiStaffs: "
                                + EnumChatFormatting.GREEN + "staffs successfully collected."));
            } else {
                // If the file does not exist, display an error message
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                        Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "AntiStaffs: "
                                + EnumChatFormatting.RED + "there's a problem for collecting, come back later."));
                // Disable the AntiStaffs module if there's an error
                Module.getModule((Class) AntiStaffs.class).setState(false);
            }
        } catch (IOException ex) {
            // Handle IO exception
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    Sting.CLIENT_PREFIX + EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "AntiStaffs: "
                            + EnumChatFormatting.RED + "there's a problem for collecting, come back later."));
            Module.getModule((Class) AntiStaffs.class).setState(false);
            ex.printStackTrace();
        }
    }

    // public static String getUserHWID() {
    //     String string = System.getenv("NUMBER_OF_PROCESSORS") + System.getenv("PROCESSOR_ARCHITEW6432")
    //             + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_IDENTIFIER")
    //             + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION")
    //             + System.getenv("PROCESSOR_REVISION");
    //     try {
    //         return Obfuscate(Hashing(string));
    //     } catch (NullPointerException ex) {
    //         ex.printStackTrace();
    //         return "";
    //     }
    // }

//    public static String Hashing(final String s) {
//        try {
//            byte[] digest = MessageDigest.getInstance("SHA-512")
//                    .digest(("*Qra1gAtQB9I5@zB" + s + "J%@1RgnV853bfwpX").getBytes());
//            StringBuilder stringBuilder = new StringBuilder();
//            for (byte b : digest) {
//                stringBuilder.append(Integer.toString(b & 0x1FF, 16).substring(1));
//            }
//            return stringBuilder.toString();
//        } catch (NoSuchAlgorithmException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String Token(final String s, final String s2, final String s3) {
//        try {
//            String string = s + ":" + s2 + ":" + s3;
//            Mac instance = Mac.getInstance("HmacSHA256");
//            instance.init(new SecretKeySpec("haI605C4%!^AKMkg".getBytes(), "HmacSHA256"));
//            Base64.Encoder encoder = Base64.getEncoder();
//            Mac mac = instance;
//            return encoder.encodeToString(mac.doFinal(string.getBytes()));
//        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String Obfuscate(final String s) {
//        StringBuilder string = new StringBuilder();
//        for (char c : s.toCharArray()) {
//            string.append((int) c);
//        }
//        return string.toString();
//    }
//
//    public static String JavaHashing() {
//        try {
//            String string = Sting.class.getProtectionDomain().getCodeSource().getLocation().toString();
//            File file = new File(string.substring(string.indexOf("file:/") + 6, string.lastIndexOf("!")));
//            MessageDigest instance = MessageDigest.getInstance("SHA-256");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] array = new byte[1024];
//            int read;
//            while ((read = fileInputStream.read(array)) != -1) {
//                instance.update(array, 0, read);
//            }
//            fileInputStream.close();
//            Mac instance2 = Mac.getInstance("HmacSHA256");
//            instance2.init(new SecretKeySpec("AMVnn72!Hjm7*Rgi".getBytes(), "HmacSHA256"));
//            instance2.update("kydd8fr4lQ86%dHC".getBytes());
//            byte[] doFinal = instance2.doFinal(instance.digest());
//            StringBuilder stringBuilder = new StringBuilder();
//            for (byte b : doFinal) {
//                stringBuilder.append(String.format("%02x", b));
//            }
//            return stringBuilder.toString();
//        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

    static {
        APIUtil.USERS = new String[] {
                "56100101101499756100100100555010152525653989797495210210098975798549753985656511019998505697511025710153994810051579797101100519948102565597561025698981015710210210053101102569955995454549810097559950565448485753102579849",
                "991011025457485157495210253579856569953531015510256101529754101101579999995599571005257975749101100511021029710156555199979952101569956505010210110010199501025310210299991004910097491015453102529810156",
                "57101102101971019956491001001011005352975698985753525398531015549975310199519953100541025754985610098561015710010156975397481001025698101505610298100979851559855555710197541001024898565048495397",
                "98975651100100541025599519897974898979810056101495651102975098981025556979755515750100579855102559849985698545553985052569956535710297515653985310197975299565056102571005110249102979857975648985410050999910198102" };
        APIUtil.STAFFS_COLLECTED = new ArrayList();
        APIUtil.collection = new File(Sting.CLIENT_FILE_VERSION + "\\Staffs Collection.txt");
        APIUtil.CLOUDFLARE_KEY = null;
        API_WEBSITE = "https://stingclient.com".replace("https", "http") + "/api";
        API_WEBSITE_AUTHENTICATION = APIUtil.API_WEBSITE + "/authentication";
    }

}
