//author: Sameer Achhab
//project date: 22 January 2022
import java.io.File; //for file operations
import java.util.ArrayList; //for recursive operations, and some file operations.
import Exceptions.directoryNotEmptyException; //custom exception for remove()
public class Directory {
  static File mfile=null; //main file object,
  private static ArrayList<File> containAll=new ArrayList<File>(); //an ArrayList that will contain object of every kind, files and directories.
  private static ArrayList<File> containFiles=new ArrayList<File>(); //an ArrayList that will contain only file objects, not directory objects.
  private static ArrayList<File> containDirs=new ArrayList<File>(); //an ArrayList that will contain only directory objects, not file objects.
  //constructor
  public Directory(File a){
      mfile=a;
  }
  private static int points(String word, String word2){
      int resultInteger=0;
      if(word.length()>word2.length()){
          for(int x=0;x<word2.length();x++){
              if(word.charAt(x)==word2.charAt(x)){
                  resultInteger++;
              }
          }
      }
      else if(word2.length()>word.length()){
          for(int x=0;x<word.length();x++){
              if(word.charAt(x)==word2.charAt(x)){
                resultInteger++;
              }
          }
      }
      else{
          for(int x=0;x<word.length();x++){
              if(word.charAt(x)==word2.charAt(x)){
                  resultInteger++;
              }
          }
      }
      return resultInteger;
  }
  //returns a File object(result of soundex algorithm)
  private static File search(String value, File[] values){
    int[] valueArray=new int[values.length];
    for(int x=0;x<valueArray.length;x++){
        valueArray[x]=points(value,values[x].getName());
    }
    return values[location(valueArray,max(valueArray))];
  }
  //searches for a keyword in a directory recursively. Files and Directories can appear in the results.
  public static String searchAllRecursive(String keyword){
      return search(keyword, recursiveDirAll()).getAbsolutePath();
  }
  //searches for a keyword in a directory, not recursively. Files and Directories can appear in the results.
  public static String searchAll(String keyword){
      return search(keyword, mfile.listFiles()).getAbsolutePath();
  }
  //searches for a keyword in a directory recursively. Only files can appear in the results.
  public static String searchFilesRecursively(String keyword){
      return search(keyword, recursiveDirFiles()).getAbsolutePath();
  }
  //searches for a keyword in a directory recursively. Only directories can appear in the results.
  public static String searchDirsRecursively(String keyword){
      return search(keyword,recursiveDirDirectories()).getAbsolutePath();
  }
  //searches for a keyword in a directory, not recursively. Only directories can appear in the results.
  public static String searchDirs(String keyword){
      return search(keyword,dirDirectories()).getAbsolutePath();
  }
  //searches for a keyword in a directory, not recursively. Only files can appear in the results.
  public static String searchFiles(String keyword){
      return search(keyword, dirFiles()).getAbsolutePath();
  }
  //return a String array, array[0] is the result of a recursive file search, array[1] is the result of a recursive directory search.
  public static String[] search(String keyword){
      return new String[]{searchFilesRecursively(keyword), searchDirsRecursively(keyword)};
  }
  //returns the location of an integer value in an integer array.
  private static int location(int[] array, int value){
      int coord=0;
      for(int x=0;x<array.length;x++){
          if(array[x]==value){
              return coord;
          }
          coord++;
      }
      return coord;
  }
  //returns the max value in an array.
  private static int max(int[] array){
      for(int x=0;x<array.length;x++){
          if(array[0]<array[x]){
              array[0]=array[x];
          }
      }
      return array[0];
  }
  //method with max params(parameters), two implementations of this method have been implemented with less params.
  public static void print(File[] a, boolean newLine, boolean fullPath){
      if(newLine){
          for (File f : a){
              if(fullPath){
                  System.out.println(f.getAbsolutePath());
              }
              else{
                  System.out.println(f.getName());
              }
          }
      }
      else{
          for(File f : a){
              if(fullPath){
                  System.out.print(f.getAbsolutePath() + " ");
              }
              else{
                  System.out.println(f.getName() + " ");
              }
          }
      }
  }
  // the implementation of the method "print" with 3 params, but this one has 2 params, a file array and a boolean value
  // that decides whether the names would be name+\n or without a new line
  public static void print(File[] a, boolean newline){
      if(newline){
          for(File f : a) {
              System.out.println(f.getName());
          }
      }
      else{
          for(File f : a){
              System.out.println(f.getName() + " ");
          }
      }
  }
  //the implementation that receives only one value as param, a file array, this is how default values can be implemented.
  public static void print(File[] a){
      for(File f : a){
          System.out.println(f.getName());
      }
  }
  //method that converts an ArrayList of files to an array of files.
  private static File[] arrayListToArray(ArrayList<File> inputArrayList){
      File[] resultArray=new File[inputArrayList.size()];
      for(int x=0;x<inputArrayList.size();x++){
          resultArray[x]=inputArrayList.get(x);
      }
      return resultArray;
  }
  //fills the ArrayList "containAll" with everything in "mfile".
  private static void fillArrayListWithAllFileObjects(File f){
      for(File a : f.listFiles()){
          if(a.isDirectory()){
              fillArrayListWithAllFileObjects(a);
          }
          containAll.add(a);
      }
  }
  //fills the ArrayList "containFiles" with every file in "mfile".
  private static void fillArrayListWithFileObjectsOnly(File f){
      for(File a : f.listFiles()){
          if(a.isDirectory()){
              fillArrayListWithFileObjectsOnly(a);
          }
          else{
              containFiles.add(a);
          }
      }
  }
  //fills the ArrayList "containDirs" with every directory in "mfile".
  private static void fillArrayListWithDirectoryObjectsOnly(File f){
      for(File a : f.listFiles()){
          if(a.isDirectory()){
              containDirs.add(a);
              fillArrayListWithDirectoryObjectsOnly(f);
          }
      }
  }
  //returns a file array, not recursive.
  public static File[] dir(){
      return mfile.listFiles();
  }
  //returns everything inside a directory with a file array. Files and directories.
  public static File[] recursiveDirAll(){
      fillArrayListWithAllFileObjects(mfile);
   File[] resultFileArray=arrayListToArray(containAll);
   containAll=new ArrayList<File>();
   return resultFileArray;
  }
  //returns all file objects in a directory, not directory objects.
  public static File[] recursiveDirFiles(){
    fillArrayListWithFileObjectsOnly(mfile);
    File[] resultFiles=arrayListToArray(containFiles);
    containFiles=new ArrayList<File>();
    return resultFiles;
  }
  //returns all the directory objects in a directory, not file objects.
  public static File[] recursiveDirDirectories(){
    fillArrayListWithDirectoryObjectsOnly(mfile);
    File[] returnDirs=arrayListToArray(containDirs);
    containDirs=new ArrayList<File>();
    return returnDirs;
  }
  //returns the directory objects in a directory, not recursive.
  public static File[] dirDirectories(){
      ArrayList<File> containDir=new ArrayList<File>();
      for(File a : mfile.listFiles()){
          if(a.isDirectory()){
              containDir.add(a);
          }
      }
      return arrayListToArray(containDirs);
  }
  //removes a directory recursively.
  private static void removeRecursive(File a){
      for(File n : a.listFiles()){
          if(n.isDirectory()){
              removeRecursive(n);
          }
          n.delete();
      }
  }
  public static boolean removeRecursive(){
   removeRecursive(mfile);
   return mfile.exists();
  }
  //removes an empty directory.
  public static boolean remove() throws directoryNotEmptyException {
   if(mfile.listFiles().length>=1){
       throw new directoryNotEmptyException(mfile.getAbsolutePath() + " is not empty.");
   }
   else{
       mfile.delete();
   }
   return mfile.exists();
  }
  //returns the file objects in a directory, not recursive.
  public static File[] dirFiles(){
    ArrayList<File> containFile=new ArrayList<File>();
    for(File a : mfile.listFiles()){
        if(a.isFile()){
            containFile.add(a);
        }
    }
    return arrayListToArray(containFile);
  }
  public static void main(String [] args){
      System.out.println("This class can't be executed directly.");
  }
}