package com.initex.canoe.services;

import com.initex.canoe.domain.result.RaceResultList;
import com.initex.canoe.domain.result.RaceStageResult;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.reports.GrandTotalListService;
import com.initex.canoe.services.reports.PreviewStageResultsService;
import com.initex.canoe.services.reports.PreviewStartListService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isBlank;

@Service
public class ResultsService {

    public static final String YOU_MUST_SELECT_BOAT_CLASS = "You must select boat class!";
    private PreviewStartListService startListService;
    private PreviewStageResultsService stageResultsService;
    private GrandTotalListService grandTotalListService;

    @Autowired
    public ResultsService(PreviewStartListService startListService, PreviewStageResultsService stageResultsService, GrandTotalListService grandTotalListService) {
        this.startListService = startListService;
        this.stageResultsService = stageResultsService;
        this.grandTotalListService = grandTotalListService;
    }

    public ByteArrayOutputStream getReport(ResultQuery q) {
        final String reportName;
        switch (q.getReportType()) {
            case "StartList":
                final List<RaceResultList> startList = startListService.execute(q);
                reportName = q.isTeamMode() ? "TeamAllStartList" : "AllStartList";
                return getPdf(startList, reportName);
            case "HeatResult":
                verifyParameter(q.getSubEvent(), "You must select sub event!");
                final List<RaceStageResult> resultList = stageResultsService.execute(q);
                reportName = q.isTeamMode() ? "TeamStageResults" : "StageResults";
                return getPdf(resultList, reportName);
            case "GrandTotal":
                verifyParameter(q.getBoatClass(), YOU_MUST_SELECT_BOAT_CLASS);
                if (q.getEventFormat() == 1 || q.getEventFormat() == 4 || q.getEventFormat() == 5 || q.getEventFormat() == 6) {
                    final List<RaceResultList> totalSumMergeList = grandTotalListService.executeSumMerge(q);
                    reportName = "GrandTotalMergedList";
                    return getPdf(totalSumMergeList, reportName);
                }
                final List<RaceStageResult> totalsList = grandTotalListService.execute(q);
                reportName = "GrandTotal";
                return getPdf(totalsList, reportName);
            case "GrandTotalGroups":
                verifyParameter(q.getBoatClass(), YOU_MUST_SELECT_BOAT_CLASS);
                if (q.getEventFormat() == 1 || q.getEventFormat() == 4) {
                    final List<RaceResultList> totalSumMergeList = grandTotalListService.executeSumMerge(q);
                    //TODO need report
                    reportName = "GrandTotalMergedList";
                    return getPdf(totalSumMergeList, reportName);
                }
                final List<RaceStageResult> totalsListGroup = grandTotalListService.execute(q);
                reportName = "GrandTotalGroup";
                return getPdf(totalsListGroup, reportName);
            default:
                //get initial StartList;
        }
        throw new CanoeException("Wrong report selection!");
    }

    private void verifyParameter(String value, String message) {
        if (isBlank(value)) {
            throw new CanoeException(message);
        }
    }

    private ByteArrayOutputStream getPdf(List<?> list, String report) {

        if (list.isEmpty()) {
            throw new CanoeException("Processed list is empty!");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String reportUrl = "/reportTemplates/" + report + ".jasper";
            InputStream reportFile = JasperReport.class.getResourceAsStream(reportUrl);
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile, new HashMap<>(), jrDataSource);

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            exporter.exportReport();

        } catch (JRException e) {
            e.printStackTrace();
            throw new CanoeException("Error creating pdf", e);
        }
        return outputStream;
    }
}
