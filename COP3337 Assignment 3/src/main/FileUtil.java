package main;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil
{
    private static FileReader fileReader;
    public static void copyFile(Path source, Path target) throws IOException
    {
         Files.copy(source, target);
    }

    public static void setTextArea(File file, JTextArea textArea) throws IOException
    {
       fileReader = new FileReader(file);
        textArea.read(fileReader, "read file");
    }

    public static void setLabelImage(File file, JLabel imageLabel)
    {
        imageLabel.setIcon(new ImageIcon(file.getAbsolutePath()));
    }

    public static int getNumberOfLines(File file) throws FileNotFoundException
    {
        int lineNum = 0;
        fileReader = new FileReader(file);
        try(LineNumberReader lnReader = new LineNumberReader(fileReader))
        {
            String reading = lnReader.readLine();
            while(reading != null)
            {
                reading = lnReader.readLine();
            }
            lineNum = lnReader.getLineNumber();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "It did something bad", e.toString(), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return lineNum;
    }
}
