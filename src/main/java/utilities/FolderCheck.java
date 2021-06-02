package utilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FolderCheck {

    private static FolderCheck instance = null;

    public static FolderCheck getInstance(){
        if( instance == null ){
            instance = new FolderCheck();
        }
        return instance;
    }

    private FolderCheck() {}

    /*Si prende value che viene generato dal registro di sistema*/
    public String checkDefaultFolder(String defaultPath){

        defaultPath += "/steamapps/common/OMORI";

        File file = new File(defaultPath);


        if(file.list() != null){
            for (int i = 0; i < file.list().length; i++) {
                if(file.list()[i].equals("OMORI.exe")){
                    return defaultPath;
                }
            }
        }

        return "false";

    }

    /*Viene chiamata quando non trova nulla dentro il path predefinito, quindi accede dentro config
    * e si prende le stringhe incriminate. Se non trova nulla, appare messaggio d'errore.*/
    public String checkOtherFolder(String steamPath){

        ArrayList<String> locationArray = new ArrayList<String>();
        steamPath += "/config";
        try {
            BufferedReader buf = new BufferedReader(new FileReader(steamPath + "/config.vdf"));
            String location = "";
            while ( ( location = buf.readLine()) != null){
                if( location.contains("BaseInstallFolder")){
                    for (int i = 0; i < location.split(" \\\" ").length; i++) {
                        String temp = location.split(" \\\" ")[i].toLowerCase();
                        locationArray.add(temp.split("\\\"")[3]);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < locationArray.size(); i++) {
            String loc = locationArray.get(i) + "/steamapps/common/OMORI";

            File file = new File(loc);

            if(file.list() != null){
                for (int j = 0; j < file.list().length; j++) {
                    if(file.list()[j].equals("OMORI.exe")){
                        return loc;
                    }
                }
            }
        }

        return "false";
    }

    /*Verifica se dentro la cartella selezionata a mano dall'utente, esiste oppure no Omori.exe
    Se non esiste, non fa installare il gioco quando si preme Installa.*/
    public Boolean selectedFolder(String selectedPath){

            File file = new File(selectedPath);

            if(file.list() != null){
                for (int j = 0; j < file.list().length; j++) {
                    if(file.list()[j].equals("OMORI.exe")){
                        return true;
                    }
                }
            }
            return false;
    }

    /*Rinomina il file che dà tanti problemi su GDrive*/
    public void checkfile(String selectedPath){
        selectedPath += "/www/mods/OMORI ITA/plugins";

        boolean changeFile = false;
        boolean deleteFile = false;

        File file = new File(selectedPath);

        if(file.list() != null) {
            for (int j = 0; j < file.list().length; j++) {
                if (file.list()[j].equals("Omori Save & Load.js")) {
                    changeFile = true;
                }
                if (file.list()[j].equals("Omori Save _ Load.js")) {
                    deleteFile = true;
                }
            }
        }

        if(!changeFile || deleteFile){
            try {
                if( changeFile ){
                    Path oldpath = Paths.get(selectedPath + "/Omori Save & Load.js");
                    Files.delete(oldpath);
                }

                Path p = Paths.get(selectedPath +"/Omori Save _ Load.js");

                Files.move(p, p.resolveSibling("Omori Save & Load.js"));

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}


/* COME FUNZIONA? IO TROVO QUESTA STRINGA
* "BaseInstallFolder_1"		"H:\\SteamLibrary"
*  A CUI VA AGGIUNTO POI IL CLASSICO /steamapps/common/OMORI
* E SI VEDE COME SEMPRE SE ESISTE OMORI.EXE
* */