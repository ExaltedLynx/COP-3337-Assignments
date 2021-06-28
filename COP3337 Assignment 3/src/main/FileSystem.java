package main;

import javax.swing.*;
import java.awt.*;

//todo search for word function
public class FileSystem
{
    public static void main(String[] args)
    {
        boolean done = false;
        BasicFile bFile = new BasicFile();

        JTextArea fileText = new JTextArea();
        JLabel imageLabel = new JLabel();

        JScrollPane imgScrollPane = new JScrollPane(imageLabel);
        imgScrollPane.setPreferredSize(new Dimension(480, 480));

        JScrollPane scrollPane = new JScrollPane(fileText);
        scrollPane.setPreferredSize(new Dimension(480,480));

        String menu = "Choose an action:" +
                "\n1. Open File" +
                "\n2. Write to File" +
                "\n3. Copy File" +
                "\n4. File Attributes" +
                "\n5. Search for a word in a file" +
                "\n6. Tokenize Text File" +
                "\n7. Back to files" +
                "\n8. Exit";

        String menu2 = "How do you want to add your text:" +
                "\n1. Append" +
                "\n2. Overwrite";

        while(!done)
        {
            String s = JOptionPane.showInputDialog(menu);
            try
            {
                String fileName = bFile.getFileName();
                int i = Integer.parseInt(s);
                switch(i)
                {
                    case 1:
                        if(fileName.contains(".png") | fileName.contains(".jpg"))
                        {
                            bFile.openImageFile(imageLabel, imgScrollPane);
                        }
                        else
                            bFile.openFileWithText(fileText, scrollPane);
                        break;

                    case 2:
                        if(fileName.contains(".png") | fileName.contains(".jpg"))
                        {
                            display("Can't write to an image", "Error");
                        }
                        else
                        {
                            String userText = JOptionPane.showInputDialog("Write message to add to file");
                            int writeType = Integer.parseInt(JOptionPane.showInputDialog(menu2));
                            if(writeType == 1)
                            {
                                bFile.addTextToFile(userText, true);
                            }
                            else
                            {
                                bFile.addTextToFile(userText, false);
                            }
                        }
                        break;

                    case 3:
                        bFile.copyBasicFile();
                        break;

                    case 4:
                        fileText.setText(" ");
                        fileText.append("File Path: " + bFile.getPath());
                        fileText.append("\nFile in this directory: ");
                        for(String file : bFile.getFilesFromDirectory())
                        {
                            fileText.append("\n \t" + file);
                        }
                        fileText.append("\nFile Size: " + bFile.getFileSizeInKiloBytes() + " kb");
                        fileName = bFile.getFileName();
                        if (!(fileName.contains(".png") | fileName.contains(".jpg")))
                        {
                            fileText.append("\nNumber of lines: " + bFile.getLineNumTotal());
                        }
                        JOptionPane.showMessageDialog(null, scrollPane, "File Attributes", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case 5:
                        if(fileName.contains(".png") | fileName.contains(".jpg"))
                        {
                            display("This file does not contain text", "Error");
                        }
                        else
                            bFile.search(fileText, scrollPane);
                        break;

                    case 6:
                        if(fileName.contains(".png") | fileName.contains(".jpg"))
                        {
                            display("This file does not contain text", "Error");
                        }
                        else
                        {
                            bFile.tokenizeFileText(fileText);
                            JOptionPane.showMessageDialog(null, scrollPane, bFile.getFileName(), JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    case 7:
                        bFile.openFileChooser();
                        break;

                    case 8:
                       done = true;
                       break;
                    default:
                        display("That action does not exist", "Error");
                }
            }
            catch(NumberFormatException e)
            {
                display(e.toString(), "Error");
            }
            catch (NullPointerException e)
            {
                System.exit(0);
            }
        }
    }
    static void display(String s, String err)
    {
        JOptionPane.showMessageDialog(null, s, err, JOptionPane.ERROR_MESSAGE);
    }
}
