package com.unzip;

/**
 * Created by Kubish on 19.03.15.
 */
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Kubish on 05.03.2015.
 */
public class UnZipp {
    public String zipName = "";
    public String unZipName = "";
   // public String outFile = "G:\\out.txt";
    public ArrayList lines = new ArrayList();


    public UnZipp() {                          //инициализируем объект со стандартными параметрами
        zipName = "C:\\Users\\Kubish\\IdeaProjects\\untitled6\\web\\upload\\nam.docx";
        unZipName = "C:\\Users\\Kubish\\IdeaProjects\\untitled6\\web\\upload\\documentsss.xml";
    }

    UnZipp(String zipN, String unZipN) {            //инициализируем объект со  параметрами
        zipName = zipN;
        unZipName = unZipN;
    }

    public void RunZipp(String zipName, String unZipName) {             //Разархивация файлов

        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipName));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) break;
                if (nextEntry.getName().equals("word/document.xml")) {
                    System.out.println("Unzipping " + nextEntry.getName());
                    FileOutputStream fout = null;
                    fout = new FileOutputStream(unZipName);     //Посимвольная запись в файл
                    for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
                        fout.write(c);
                    }
                    zipInputStream.closeEntry();
                    fout.close();
                    System.out.println("File Success Unzipped");
                }

            }

            zipInputStream.close();
        } catch (FileNotFoundException e) { //File not found
            System.out.println("File not found:" + zipName);
            System.exit(1);
        } catch (IOException e) { //Eror zip-read
            System.out.println("Eror UnZip file:" + zipName);
            System.exit(1);

        }

    }

    public String runparse() {


        try {


            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean isWt = false;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) {
                    if ("w:t".equals(qName)) {

                        isWt = true;
                    }

                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (isWt) {
                        lines.add(new String(ch, start, length));  // Массив текста
                        isWt = false;
                    }

                }

            };

            saxParser.parse(unZipName, handler);

        } catch (Exception e) {

            e.printStackTrace();
        }

        String outline = "";                                //формируем текст
        for (int i = 0; i < lines.size(); i++) {
            outline = outline + lines.get(i) + " ";

        }
        System.out.println(outline);
        return outline;

    }

}
