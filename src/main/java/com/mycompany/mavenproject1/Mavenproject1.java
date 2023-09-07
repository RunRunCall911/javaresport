/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mavenproject1;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author algor
 */
public class Mavenproject1 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Hello World!");

        String reportSrcFile = "C:\\Users\\algor\\OneDrive\\Escritorio\\Reports\\Blank_A4.jrxml";
        String jsonFilePath = "C:\\Users\\algor\\OneDrive\\Escritorio\\Reports\\datos.json";
        // First, compile jrxml file.
        JasperReport jasperReport;

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            // Crear una instancia de Gson
            Gson gson = new Gson();

            // Leer el JSON desde el archivo y parsearlo en un objeto report
            report reportObj = gson.fromJson(br, report.class);

            // Acceder a los atributos del objeto report
            System.out.println("id_lote: " + reportObj.getId_lote());
            System.out.println("productor: " + reportObj.getProductor());
            System.out.println("peso_bruto: " + reportObj.getPeso_bruto());
            System.out.println("fecha_corte: " + reportObj.getFecha_corte());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            
            Gson gson = new Gson();

            // Leer el JSON desde el archivo y parsearlo en un objeto report
            report reportObj = gson.fromJson(br, report.class);
            
            jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            // Fields for report
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("IdCliente", reportObj.getId_lote());
            parameters.put("fecha_corte", reportObj.getFecha_corte());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            // Make sure the output directory exists.
            JasperExportManager.exportReportToPdfFile(print, "output.pdf");
            // Open the generated PDF report in the default PDF viewer
            JasperViewer.viewReport(print);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
