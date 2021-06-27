package main;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicFile
{
    File f;
    JFileChooser fileChooser = new JFileChooser(".");

    public BasicFile()
    {
        openFileChooser();
    }


    void copyBasicFile()
    {
        try
        {
            //replaces first instance of a . character with Copy.
            String newPath = f.getPath().replaceFirst("[.]", "Copy.");
            System.out.println(newPath);
            Path targetPath = Paths.get(newPath);

            FileUtil.copyFile(f.toPath(), targetPath);
        }
        catch(IOException e)
        {
            display("it did something bad", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    void openFileWithText(JTextArea textArea, JScrollPane scrollPane)
    {
        try
        {
            FileUtil.setTextArea(f, textArea);
            JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            display("it did something bad", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    void openImageFile(JLabel imageLabel, JScrollPane scrollPane)
    {
        FileUtil.setLabelImage(f, imageLabel);
        JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
    }


    void openFileChooser()
    {
        try
        {
            int status = fileChooser.showOpenDialog(null);
            if(status != JFileChooser.APPROVE_OPTION)
            {
                throw new IOException();
            }

            f = fileChooser.getSelectedFile();

            if(!f.exists())
            {
                throw new FileNotFoundException();
            }
        }
        catch (FileNotFoundException e)
        {
            display("File was not found", e.toString(), JOptionPane.WARNING_MESSAGE);
        }
        catch(IOException e)
        {
            System.exit(0);
        }
    }

    void tokenizeFileText(JTextArea textArea)
    {
        try
        {
            StreamTokenizer st = new StreamTokenizer(new FileReader(f));

            st.eolIsSignificant(true);
            st.wordChars('"', '"');
            st.wordChars('@', '@');
            st.wordChars(',', ',');
            st.wordChars('\'', '\'');
            st.wordChars('!', '!');
            st.whitespaceChars(' ', ' ');
            st.lowerCaseMode(true);

            while (st.nextToken() != StreamTokenizer.TT_EOF)
            {
                switch (st.ttype)
                {
                    case StreamTokenizer.TT_WORD:
                        textArea.append(st.sval);
                        break;

                    case StreamTokenizer.TT_NUMBER:
                        textArea.append(String.valueOf(st.nval));
                        break;

                    case StreamTokenizer.TT_EOL:
                        textArea.append("\n");
                        break;
                }
            }
        }
        catch(IOException e)
        {
            display("it did something bad", e.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

    void addTextToFile(String userText, boolean writeType)
    {
        //true is append, false is overwrite
        try(FileWriter fw = new FileWriter(f, writeType);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(userText);

        }
        catch (IOException e)
        {
            display("it did something bad", e.toString(), JOptionPane.ERROR_MESSAGE);
        }

    }

    String getPath()
    {
        return f.getAbsolutePath();
    }

    String getFileName()
    {
        return f.getName();
    }

    double getFileSizeInKiloBytes()
    {
        return (double) f.length() / 1024;
    }

    String[] getFilesFromDirectory()
    {
        String fParent = f.getParent();
        File parent = new File(fParent);
        String[] filesInParent;
        return filesInParent = parent.list();
    }


    int getLineNumTotal()
    {
        int lineNum = 0;
        try
        {
            lineNum = FileUtil.getNumberOfLines(f);
        }
        catch(FileNotFoundException e)
        {
            display("File was not found", e.toString(), JOptionPane.WARNING_MESSAGE);
        }
        return lineNum;
    }

    String canRead()
    {
        return (f.canRead())? "This file can be opened for reading": "Cannot read this file";
    }

    String directoryOrFile()
    {
        return (f.isDirectory())? "This is a directory and not an ordinary file": "This is a file and not a directory";
    }

    String exists()
    {
        return (f.exists())? "The physical file exists": "The physical file does not exist";
    }

    private void display(String msg, String s, int t)
    {
        JOptionPane.showMessageDialog(null, msg, s, t);
    }
}

