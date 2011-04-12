package co.com.activetek.genericmenu.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Clase util para el manejo de archivos y directorios
 * @author crincon
 */
public class FileUtil {

    /**
     * Retorna una cadena de caracteres con el contenido del archivo dado
     * @param is
     * @return String
     */
    public static String getFileContent(InputStream is){
          StringBuffer fileContent = new StringBuffer();
          try {
              BufferedReader br = new BufferedReader(new InputStreamReader(is));
              String line;
              while( (line = br.readLine()) != null){
                  fileContent.append(line+"\n");
              }
              br.close();
          }catch (FileNotFoundException e) {
              e.printStackTrace();
          }catch (IOException e) {
              e.printStackTrace();
          }
          
          return fileContent.toString();
      }
    
    /**
     * Retorna una cadena de caracteres con el contenido del archivo dado
     * @param filename
     * @return String
     */
    public static String getFileContent(String filename){
          StringBuffer fileContent = new StringBuffer();
          try {
              BufferedReader br = new BufferedReader(new FileReader(filename));
              String line;
              while( (line = br.readLine()) != null){
                  fileContent.append(line+"\n");
              }
              br.close();
          }catch (FileNotFoundException e) {
              e.printStackTrace();
          }catch (IOException e) {
              e.printStackTrace();
          }
          
          return fileContent.toString();
      }
       
    /**
     * Copia el contenido del archivo fuente, al final del archivo destino
     * 
     * @param srcFile
     * @param destFile
     */
    public static void copyContentFile(String srcFile, String destFile){
       File inputFile = new File(srcFile);
	   File outputFile = new File(destFile);
        
        try{
            FileReader in = new FileReader(inputFile);
            FileWriter out = new FileWriter(outputFile, true);
            int c;

            while ((c = in.read()) != -1)
               out.write(c);

            in.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Escribe en el archivo dado, el contenido ingresado por parametro, teniendo
     * en cuenta si reescribe el archivo o si hace append del contenido. Si el 
     * archivo no esta creado, este lo crea
     * @param filename
     * @param fileContent
     * @param append
     */
   public static void writeFile(String filename, String fileContent, boolean append){
       try {
            FileWriter out = new FileWriter(filename, append);
            out.write(fileContent);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
   }
       
    /**
     * Elimina el archivo con nombre dado, si el archivo no existe no hace nada
     * @param filename
     */
    public static void deleteFile(String filename){
       File file = new File(filename);
       if( file.exists() ){
    	   file.delete();
       }
    }

    /**
     * Construye el directorio dado, si allParents es true, construye la carpeta con todas
     * sus carpetas padres si estas no existen o en caso contrario unicamente intenta 
     * construir la carpeta indicada
     * @return boolean true si la carpeta pudo ser creada, false en caso contratio
     */
    public static boolean mkdir(String dirPath, boolean allParents) {
        if(allParents){
            return new File(dirPath).mkdirs();
        }
        
        return new File(dirPath).mkdir();
    }

    /**
     * Retorna el tamaño del archivo con path dado
     * @param localFileName
     * @return long
     */
	public static long getFileSize(String localFileName) {
		File f = new File(localFileName);
		return f.length();
	}
	
	/**
	 * Renombra un archivo o un directorio
	 * @param oldfilename
	 * @param newfilename
	 * @return boolean
	 */
	public static boolean renameFile(String oldfilename, String newfilename){
		// File (or directory) with old name
	    File file = new File( oldfilename );
	    
	    // File (or directory) with new name
	    File file2 = new File( newfilename );
	    
	    // Rename file (or directory)
	    boolean success = file.renameTo(file2);
	    return success;
	}
}
