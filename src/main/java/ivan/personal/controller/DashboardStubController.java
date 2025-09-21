package ivan.personal.controller;

import ivan.personal.dto.report.*;
import ivan.personal.dto.report.details.ReportDetails;
import ivan.personal.service.MockDataLoader;
import ivan.personal.utilities.PdfUtility;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * This controller provides static JSON responses
 * instead of calling external services.
 */
@RestController
@RequiredArgsConstructor
public class DashboardStubController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardStubController.class);

    private final MockDataLoader mockDataLoader;

    /**
     * Get all Dashboard projects
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/projects")
    public ResponseEntity<List<Project>> getDashboardProjects() {
        logger.info("STUB: Getting Dashboard projects");

        try {
            List<Project> listProjects = mockDataLoader.loadProjects();
            logger.info("STUB: Returning {} projects", listProjects.size());
            return new ResponseEntity<>(listProjects, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading projects mock data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get targets by project ID
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/targets/{id}")
    public ResponseEntity<List<Target>> getDashboardTargets(@PathVariable("id") final Long id) {
        logger.info("STUB: Getting Dashboard targets for project ID: {}", id);

        try {
            List<Target> listTargets = mockDataLoader.loadTargetsByProject(id);
            logger.info("STUB: Returning {} targets for project {}", listTargets.size(), id);
            return new ResponseEntity<>(listTargets, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading targets mock data for project {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get statistics for project, target and level
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/statistics/{idProject}/{idTarget}/{level}")
    public ResponseEntity<ReportStatistics> getDashboardStatistics(
            @PathVariable("idProject") final Long idProject,
            @PathVariable("idTarget") final Long idTarget,
            @PathVariable("level") final Integer level) {

        logger.info("STUB: Getting Dashboard statistics for project: {}, target: {}, level: {}", idProject, idTarget, level);

        try {
            ReportStatistics statistics = mockDataLoader.loadStatistics();
            logger.info("STUB: Returning statistics with {} total tests", statistics.getTotalTest());
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading statistics mock data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Generate PDF from report details
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/reportDetailToPdf")
    public ResponseEntity<Resource> getDashboardJsonToPdf(@RequestBody String data) throws IOException {
        logger.info("STUB: Generating PDF from report details");

        // Parse the JSON to get report ID
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(data);
        JsonObject report = je.getAsJsonObject();
        Long idReport = report.get("id").getAsLong();

        logger.info("STUB: Processing report ID: {}", idReport);

        String fileName;
        try {
            // Load mock report details
            ReportDetails reportDetails = mockDataLoader.loadReportDetails(idReport);
            if (reportDetails == null) {
                logger.warn("STUB: No report details found for ID: {}", idReport);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Generate PDF using existing utility
            PdfUtility pdfUtility = new PdfUtility();
            fileName = pdfUtility.createReportDetailsPdf(report, reportDetails);
            logger.info("STUB: Generated PDF: {}", fileName);

        } catch (Exception e) {
            logger.error("STUB: Error generating PDF for report {}", idReport, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return the generated PDF file
        File file = new File(fileName);
        if (!file.exists()) {
            logger.error("STUB: Generated PDF file not found: {}", fileName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file.toPath()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }

    /**
     * Convert JSON to PDF
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/jsonToPdf")
    public ResponseEntity<String> setDetailReportToPdf(@RequestBody String data) {
        logger.info("STUB: Converting JSON to PDF");

        // Parse and prettify JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(data);
        String prettyJsonString = gson.toJson(je);

        // Generate PDF
        String fileName = PdfUtility.createPdf(prettyJsonString);
        logger.info("STUB: Generated PDF file: {}", fileName);

        // Return response with filename
        JsonObject response = new JsonObject();
        response.addProperty("filename", fileName);
        String payload = gson.toJson(response);

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    /**
     * Get reports by project and target
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/reports/{idProject}/{idTarget}")
    public ResponseEntity<List<Report>> getDashboardReports(
            @PathVariable("idProject") final Long idProject,
            @PathVariable("idTarget") final Long idTarget) {

        logger.info("STUB: Getting Dashboard reports for project: {}, target: {}", idProject, idTarget);

        try {
            List<Report> reports = mockDataLoader.loadReportsByProjectAndTarget(idProject, idTarget);
            logger.info("STUB: Returning {} reports", reports.size());
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading reports mock data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get performance tests by project and target
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/testsPerformance/{idProject}/{idTarget}")
    public ResponseEntity<List<TestPerformance>> getTestsPerformance(
            @PathVariable("idProject") final Long idProject,
            @PathVariable("idTarget") final Long idTarget) {

        logger.info("STUB: Getting performance tests for project: {}, target: {}", idProject, idTarget);

        try {
            List<TestPerformance> testsPerformance = mockDataLoader.loadTestPerformanceByProjectAndTarget(idProject, idTarget);
            logger.info("STUB: Returning {} performance tests", testsPerformance.size());
            return new ResponseEntity<>(testsPerformance, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading test performance mock data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get report details by report ID
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/reportDetails/{idReport}")
    public ResponseEntity<ReportDetails> getDashboardReportDetails(@PathVariable("idReport") final Long idReport) {
        logger.info("STUB: Getting report details for ID: {}", idReport);

        try {
            ReportDetails reportDetails = mockDataLoader.loadReportDetails(idReport);
            if (reportDetails == null) {
                logger.warn("STUB: No report details found for ID: {}", idReport);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("STUB: Returning report details for: {}", reportDetails.getName());
            return new ResponseEntity<>(reportDetails, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading report details mock data for ID: {}", idReport, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get available SUT configurations
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/availablesuts")
    public ResponseEntity<List<SutConfiguration>> getDashboardAvailableSUT() {
        logger.info("STUB: Getting available SUT configurations");

        try {
            List<SutConfiguration> listTests = mockDataLoader.loadAvailableSUT();
            logger.info("STUB: Returning {} SUT configurations", listTests.size());
            return new ResponseEntity<>(listTests, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("STUB: Error loading SUT configurations mock data", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Health check endpoint to verify the stub controller is working
     */
    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("STUB: Health check called");
        JsonObject response = new JsonObject();
        response.addProperty("status", "UP");
        response.addProperty("service", "Dashboard Stub Controller");
        response.addProperty("message", "Mock data service is running");

        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }

    /**
     * Clear cache endpoint for development purposes
     */
    @PostMapping(value = "/clear-cache")
    public ResponseEntity<String> clearCache() {
        logger.info("STUB: Clearing mock data cache");
        mockDataLoader.clearCache();

        JsonObject response = new JsonObject();
        response.addProperty("status", "SUCCESS");
        response.addProperty("message", "Cache cleared successfully");

        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(response), HttpStatus.OK);
    }
}