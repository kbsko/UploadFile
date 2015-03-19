package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unzip.UnZipp;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Iterator;

public class FileUploadDemoServlet extends HttpServlet {
    String FilePath="C:\\Users\\Kubish\\IdeaProjects\\untitled6\\web\\upload\\nam.docx";
    String FilePathXML="C:\\Users\\Kubish\\IdeaProjects\\untitled6\\web\\upload\\documentsss.xml";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stroka="abcd";
        UnZipp file = new UnZipp();
        System.out.println(file.zipName);
        System.out.println(file.unZipName);
        file.RunZipp(FilePath,FilePathXML);
        stroka=file.runparse();
        req.setAttribute("namestring",stroka);
        req.getRequestDispatcher("word.jsp").forward(req, resp);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext())
                {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField())
                    {

                        String fileName = item.getName();
                        String root = getServletContext().getRealPath("/");

                        //path where the file will be stored
                        File path = new File("C:\\Users\\Kubish\\IdeaProjects\\untitled6\\web\\upload");
                        if (!path.exists())
                        {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        FilePath=path+"/"+fileName;
                        FilePathXML=path+"/"+"documentsss.xml";
                        System.out.println(uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}