package com.estacionamentos.segs.estacionamentosegs.veiculo;

import com.estacionamentos.segs.estacionamentosegs.veiculo.registro.Registro;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioService {

    //Método recebe duas listas veiculo e registros e retorna um ARRAY DE BYTES que representa o conteúdo do relatório em formato Excel
    public byte[] gerarRelatorio(List<Veiculo> veiculos, List<Registro> registros) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) { //XSSFWorkbook representa um arquivo Excel
            Sheet sheet = workbook.createSheet("Veiculo_Registros"); //Aqui é nome que vai ser representado dentro do excel

            // Criar um estilo para o cabeçalho
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());  // Cor de fundo preto
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            // Adicionar bordas ao cabeçalho
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            // Criar o cabeçalho e cada campo é preenchido pelo header
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Placa", "Modelo", "Tipo do Veiculo", "Entrada", "Saída", "Valor", "Status"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Criar estilo para células de dados
            CellStyle dataStyle = workbook.createCellStyle();

            // Criar data rows
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            //Para cada registro na lista de registro, uma nova linha é criada na planilha
            for (int i = 0; i < registros.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                Registro registro = registros.get(i);

                // Criar todas as células com o estilo dataStyle
                for (int j = 0; j < headers.length; j++) {
                    Cell cell = dataRow.createCell(j);
                    cell.setCellStyle(dataStyle);
                }

                dataRow.getCell(0).setCellValue(registro.getVeiculos().getPlaca());
                dataRow.getCell(1).setCellValue(registro.getVeiculos().getModelo());
                dataRow.getCell(2).setCellValue(registro.getVeiculos().getTipo());

                Cell entradaCell = dataRow.createCell(3);
                entradaCell.setCellValue(dateFormatter.format(registro.getEntrada()));

                Cell saidaCell = dataRow.createCell(4);
                saidaCell.setCellValue(registro.getSaida() != null ? dateFormatter.format(registro.getSaida()) : "");

                dataRow.getCell(5).setCellValue("R$" + registro.getValor());
                dataRow.getCell(6).setCellValue(registro.getSaida() != null ? "Concluído" : "Em Andamento");
            }

            // Ajustar a largura das colunas para acomodar o conteúdo
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Aqui ele retorna
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }
}
