package com.cathalob.medtracker.service;

import com.cathalob.medtracker.Evaluation;
import com.cathalob.medtracker.EvaluationEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class EvaluationDataService {

    private final Map<String, Evaluation> db = new HashMap<>(){{
        put("1",new Evaluation("2024/12/"));
    }};

    public EvaluationDataService() {
    }


    public Evaluation getEvaluation(){
        Evaluation evaluation = db.get("1");
        return evaluation;
    }

    public List<List<Object>> getSystoleEvaluationData(){
        Evaluation evaluation = getEvaluation();
        log.info("getSysEvaluationData started ");
        List<List<Object>> listData = new ArrayList<>();
        for (EvaluationEntry entry : evaluation.getEntries()) {
            listData.add(Arrays.asList(entry.getDate(), entry.getBloodPressureSystole(), EvaluationEntry.BpSystoleUpperBound,
                    EvaluationEntry.BpSystoleLowerBound));
        }

        log.info("getSysEvaluationData completed ");
        return listData;
    }public List<List<Object>> getDiastoleEvaluationData(){
        Evaluation evaluation = getEvaluation();
        log.info("getDiaEvaluationData started ");
        List<List<Object>> listData = new ArrayList<>();
        for (EvaluationEntry entry : evaluation.getEntries()) {
            listData.add(Arrays.asList(entry.getDate(), entry.getBloodPressureDiastole(), EvaluationEntry.BpDiastoleUpperBound,
                    EvaluationEntry.BpDiastoleLowerBound));
        }
        log.info("getDiaEvaluationData completed ");
        return listData;
    }

    public void importEvaluation(MultipartFile excelFile) throws IOException {
        String originalFilename = excelFile.getOriginalFilename();
        log.info("filename: " + originalFilename);
        Evaluation evaluation = new Evaluation();
        db.put("1", evaluation);
        evaluation.setImportedFilename(originalFilename);

        List<EvaluationEntry> entries = new ArrayList<EvaluationEntry>();
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            EvaluationEntry entry = new EvaluationEntry();
            log.info(""+ i);
            XSSFRow row = worksheet.getRow(i);

            Date dateCellValue = row.getCell(0).getDateCellValue();
            entry.setDate(formatDate(dateCellValue));
            XSSFCell bpSystoleCell = row.getCell(10);
            if (bpSystoleCell != null){
                entry.setBloodPressureSystole((((int) bpSystoleCell.getNumericCellValue())));
            }
            XSSFCell bpDiastoleCell = row.getCell(11);
            if (bpDiastoleCell != null){
                entry.setBloodPressureDiastole((((int) bpDiastoleCell.getNumericCellValue())));
            }
            XSSFCell bpHeartRateCell = row.getCell(12);
            if (bpHeartRateCell != null){
                entry.setHeartRate((((int) bpHeartRateCell.getNumericCellValue())));
            }

            entries.add(entry);
        }
        evaluation.setEntries(entries);
    }

    public String getEvaluationOriginFilename(){
        return getEvaluation().getImportedFilename();
    }


    private String formatDate(Date date){
        SimpleDateFormat sdf =
                new SimpleDateFormat("yy-MM-dd");
        return sdf.format(date);
    }
    }
