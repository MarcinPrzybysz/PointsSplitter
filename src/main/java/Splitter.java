import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Splitter {
    private ArrayList<String[]> points = new ArrayList<String[]>();
    private BufferedReader br = null;
    private String line = "";
    private String csvSplitBy = ",";

    BufferedWriter out;
    String savePath;

    Splitter(File file, String savePath) {
        this.savePath = savePath;
        System.out.println("splitting");
        inputToList(file);

        sortPointsList();
        System.out.println("SORTING");

        divideByFirstChar();
    }

    private void inputToList(File file) {
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                points.add(line.split(csvSplitBy));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortPointsList() {
        Collections.sort(points, new Comparator<String[]>() {
            public int compare(String[] string1, String[] string2) {
                return string1[0].compareTo(string2[0]);
            }
        });
    }


    private void divideByFirstChar() {
        try {
            for (int i = 0; i < points.size(); i++) {
                if (i == 0) {
                    checkIfExistsAndCreateFile(savePath, "pietro_0");
                    for (int j = 0; j < 4; j++) {
                        out.write(points.get(i)[j] + "\t");
                    }
                    out.newLine();
                    out.flush();
                } else {
                    if (!((points.get(i)[0].charAt(0) - '0') < 9)) {
                        if (points.get(i)[0].charAt(0) != points.get(i - 1)[0].charAt(0)) {
                            checkIfExistsAndCreateFile(savePath, "pietro_" + String.valueOf(points.get(i)[0].charAt(0)));
                        }
                    }
                    for (int j = 0; j < 4; j++) {
                        out.write(points.get(i)[j] + "\t");
                    }
                    out.newLine();
                    out.flush();
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException ioe2) {

            }
        }
        System.out.println("SUCCESSFULLY SAVED FILES");
    }

    private void checkIfExistsAndCreateFile(String path, String fileName) throws IOException {
        File file = new File(path + fileName + ".txt");
        if (file.exists()) {
            file.delete();
            System.out.println("file: " + fileName + " exists");
        }
        out = new BufferedWriter(new FileWriter(path + fileName + ".txt", true));
        System.out.println("creating " + fileName + ".txt");
    }
}
