package com.bank.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.model.Transaction;
import com.bank.dao.CustomerDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/downloadPDF")
public class Generatepdf extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=sample.pdf");

        HttpSession session = request.getSession();
        Integer accountNo = (Integer) session.getAttribute("accountNo");

        CustomerDAO customerDAO = new CustomerDAO();
        List<Transaction> transactions = customerDAO.getTransactions(accountNo);

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();


            Paragraph title = new Paragraph("Transaction Report");
            document.add(title);
            document.add(new Paragraph("\n"));


            for (Transaction transaction : transactions) {
                String transactionInfo = String.format("Date: %s | Transaction Type: %s | Amount: %.2f",
                        transaction.getTransactionDate(), transaction.getTransactionType(), transaction.getAmount());
                Paragraph transactionParagraph = new Paragraph(transactionInfo);
                document.add(transactionParagraph);
                document.add(new Paragraph("\n"));
            }

            document.close();

            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray());
        } catch (DocumentException e) {
            e.printStackTrace();
            response.getWriter().write("Error generating PDF.");
        } finally {

            if (document != null) {
                document.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }
}
