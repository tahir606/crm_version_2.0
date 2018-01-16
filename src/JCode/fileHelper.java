package JCode;

import objects.ESetting;
import objects.Users;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class fileHelper {

    private static final String FADD = "C:/Users/" + System.getProperty("user.name") + "/Bits/CRM/";

    public fileHelper() {

    }

    public void makeFolders() {

        if (!new File(FADD + "text.txt").exists()) {
            File file = new File(FADD);
            file.mkdirs();

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(
                        new File(FADD + "text.txt"));

                writer.write("");

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    public String[] getNetworkDetails() {
        String text = "";
        InputStreamReader isReader = null;
        try {
            isReader = new InputStreamReader(
                    this.getClass().getResourceAsStream("/res/network.txt"));
            BufferedReader br = new BufferedReader(isReader);

            text = br.readLine();
            String[] t = text.split("\\^");
            return t;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isReader.close();
            } catch (IOException ex) {

            }
        }
        return null;
    }

    public boolean WriteUserDetails(Users user, ArrayList<Users.uRights> rights) {

        String Details = user.getUCODE() + "*" + user.getFNAME() + "*" + user.getUNAME() + "*" + user.isEmail() + "^";

        for (Users.uRights right : rights) {
            Details = Details + right.getRCODE() + "*";
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(
                    new File(FADD + "uDets.txt"));

            writer.write(Details);

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            writer.close();
        }

    }

    public Users ReadUserDetails() {
        String text = "";
        InputStreamReader isReader = null;
        Users user = new Users();
        try {

            isReader =
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(FADD + "uDets.txt")));
            BufferedReader br = new BufferedReader(isReader);

            text = br.readLine();

            if (text == null) {
                return null;
            }

            String[] t = text.split("\\^");

            String[] u = t[0].split("\\*");

            user.setUCODE(Integer.parseInt(u[0]));
            user.setFNAME(u[1]);
            user.setUNAME(u[2]);
            user.setEmailBool(Boolean.parseBoolean(u[3]));

            user.setRights(t[1].split("\\*"));

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
//            try {
//                isReader.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }
    }

    public void DeleteUserDetails() {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(
                    new File(FADD + "uDets.txt"));

            writer.write("");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            writer.close();
        }
    }


    //-----------------Filters----------------
    public boolean WriteFilter(String where) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(
                    new File(FADD + "filters.txt"));

            writer.write(where);

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            writer.close();
        }
    }

    public String ReadFilter() {
        String text = "";
        InputStreamReader isReader = null;
        try {
            isReader =
                    new InputStreamReader(new FileInputStream(new File(FADD + "filters.txt")));
            BufferedReader br = new BufferedReader(isReader);

            text = br.readLine();

            if (text == null) {
                return null;
            }

            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                isReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //-----------------Email Settings----------------
    public boolean WriteESettings(ESetting es) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(
                    new File(FADD + "eSet.txt"));

            writer.write(es.getHost() + "^" + es.getEmail() + "^" + es.getPass());

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            writer.close();
        }
    }

    public ESetting ReadESettings() {
        String text = "";
        InputStreamReader isReader = null;
        try {
            isReader =
                    new InputStreamReader(new FileInputStream(new File(FADD + "eSet.txt")));
            BufferedReader br = new BufferedReader(isReader);

            text = br.readLine();

            if (text == null) {
                return null;
            }

            String[] ar = text.split("\\^");

            return new ESetting(ar[0], ar[1], ar[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                isReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean DeleteESettings() {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(
                    new File(FADD + "eSet.txt"));

            writer.write("");

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            writer.close();
        }
    }

}
