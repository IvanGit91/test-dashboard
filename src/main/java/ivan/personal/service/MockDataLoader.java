package ivan.personal.service;

import ivan.personal.dto.report.*;
import ivan.personal.dto.report.details.ReportDetails;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MockDataLoader {

    private final ObjectMapper objectMapper;
    private final SimpleDateFormat dateFormat;

    // Cache for loaded data to improve performance
    private List<Project> cachedProjects;
    private Map<String, List<Target>> cachedTargets;
    private ReportStatistics cachedStatistics;
    private Map<String, List<Report>> cachedReports;
    private Map<String, List<TestPerformance>> cachedTestPerformance;
    private Map<String, ReportDetails> cachedReportDetails;
    private List<SutConfiguration> cachedSutConfigurations;

    public MockDataLoader() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    }

    public List<Project> loadProjects() throws IOException {
        if (cachedProjects != null) {
            return cachedProjects;
        }

        try (InputStream is = new ClassPathResource("mockdata/projects.json").getInputStream()) {
            JsonNode jsonArray = objectMapper.readTree(is);
            List<Project> projects = new ArrayList<>();

            for (JsonNode node : jsonArray) {
                Project project = new Project();
                project.setId(node.get("id").asLong());
                project.setName(node.get("name").asText());
                project.setDescription(node.get("description").asText());
                projects.add(project);
            }

            cachedProjects = projects;
            return projects;
        }
    }

    public List<Target> loadTargetsByProject(Long projectId) throws IOException {
        if (cachedTargets == null) {
            loadAllTargets();
        }

        return cachedTargets.getOrDefault(projectId.toString(), new ArrayList<>());
    }

    private void loadAllTargets() throws IOException {
        try (InputStream is = new ClassPathResource("mockdata/targets.json").getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(is);
            cachedTargets = new HashMap<>();

            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String projectId = fieldNames.next();
                JsonNode targetsArray = rootNode.get(projectId);
                List<Target> targets = new ArrayList<>();

                for (JsonNode targetNode : targetsArray) {
                    Target target = new Target();
                    target.setId(targetNode.get("id").asLong());
                    target.setType(targetNode.get("type").asText());
                    target.setName(targetNode.get("name").asText());
                    target.setVariant(targetNode.get("variant").asText());
                    target.setRevision(targetNode.get("revision").asText());
                    target.setRelease(targetNode.get("release").asText());
                    target.setDescription(targetNode.get("description").asText());

                    // Parse the date
                    target.setCreated(parseDate(targetNode.get("created").asText()));

                    targets.add(target);
                }

                cachedTargets.put(projectId, targets);
            }
        }
    }

    public ReportStatistics loadStatistics() throws IOException {
        if (cachedStatistics != null) {
            return cachedStatistics;
        }

        try (InputStream is = new ClassPathResource("mockdata/statistics.json").getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(is);

            ReportStatistics statistics = new ReportStatistics();
            statistics.setTotalTest(rootNode.get("totalTest").asInt());

            // Load clusters
            Collection<StatReportReq> clusters = new ArrayList<>();
            JsonNode clustersArray = rootNode.get("clusters");
            for (JsonNode clusterNode : clustersArray) {
                StatReportReq cluster = new StatReportReq();
                cluster.setName(clusterNode.get("name").asText());
                cluster.setTotal(clusterNode.get("total").asInt());

                // Load test lists for each status
                cluster.setPass(loadTestRowWrappers(clusterNode.get("pass")));
                cluster.setFail(loadTestRowWrappers(clusterNode.get("fail")));
                cluster.setRunning(loadTestRowWrappers(clusterNode.get("running")));
                cluster.setNoRun(loadTestRowWrappers(clusterNode.get("noRun")));

                // Load test IDs
                List<Long> testIds = new ArrayList<>();
                for (JsonNode testId : clusterNode.get("tests")) {
                    testIds.add(testId.asLong());
                }
                cluster.setTests(testIds);

                clusters.add(cluster);
            }
            statistics.setClusters(clusters);

            // Load report group
            Map<String, Integer> reportGroup = new HashMap<>();
            JsonNode reportGroupNode = rootNode.get("reportGroup");
            Iterator<String> fieldNames = reportGroupNode.fieldNames();
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                reportGroup.put(key, reportGroupNode.get(key).asInt());
            }
            statistics.setReportGroup(reportGroup);

            cachedStatistics = statistics;
            return statistics;
        }
    }

    private List<TestRowWrapper> loadTestRowWrappers(JsonNode arrayNode) {
        List<TestRowWrapper> wrappers = new ArrayList<>();
        if (arrayNode != null && arrayNode.isArray()) {
            for (JsonNode node : arrayNode) {
                TestRowWrapper wrapper = new TestRowWrapper();
                if (node.has("testId")) wrapper.setId(node.get("testId").asLong());
                if (node.has("testName")) wrapper.setName(node.get("testName").asText());
                if (node.has("version")) wrapper.setVersion(node.get("version").asLong());
                wrappers.add(wrapper);
            }
        }
        return wrappers;
    }

    public List<Report> loadReportsByProjectAndTarget(Long projectId, Long targetId) throws IOException {
        if (cachedReports == null) {
            loadAllReports();
        }

        String key = projectId + "_" + targetId;
        return cachedReports.getOrDefault(key, new ArrayList<>());
    }

    private void loadAllReports() throws IOException {
        try (InputStream is = new ClassPathResource("mockdata/reports.json").getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(is);
            cachedReports = new HashMap<>();

            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                JsonNode reportsArray = rootNode.get(key);
                List<Report> reports = new ArrayList<>();

                for (JsonNode reportNode : reportsArray) {
                    Report report = mapJsonToReport(reportNode);
                    reports.add(report);
                }

                cachedReports.put(key, reports);
            }
        }
    }

    private Report mapJsonToReport(JsonNode node) {
        Report report = new Report();
        report.setId(node.get("id").asLong());
        report.setVfId(node.get("vfId").asLong());
        report.setStatus(node.get("status").asText());
        report.setName(node.get("name").asText());
        report.setVersion(node.get("version").asLong());
        report.setVfName(node.get("vfName").asText());
        report.setTypeTarget(node.get("typeTarget").asText());
        report.setVariant(node.get("variant").asText());
        report.setRevision(node.get("revision").asText());
        report.setRelease(node.get("release").asText());
        report.setTag(node.get("tag").asText());
        report.setTc(node.get("tc").asText());
        report.setImp(node.get("imp").asInt());
        report.setTime(node.get("time").asText());
        report.setCreated(node.get("created").asText());
        report.setMaker(node.get("maker").asText());
        report.setLauncher(node.get("launcher").asText());
        report.setSut(node.get("sut").asText());
        report.setReason(node.get("reason").asText());
        report.setOpn(node.get("opn").asText());
        report.setOpStatus(node.get("opStatus").asText());
        report.setValNotes(node.get("valNotes").asText());
        report.setOpnReason(node.get("opnReason").asText());
        report.setProjectId(node.get("projectId").asLong());
        report.setIdPlanning(node.get("idPlanning").asText());
        report.setScheduling(node.get("scheduling").asText());
        return report;
    }

    public List<TestPerformance> loadTestPerformanceByProjectAndTarget(Long projectId, Long targetId) throws IOException {
        if (cachedTestPerformance == null) {
            loadAllTestPerformance();
        }

        String key = projectId + "_" + targetId;
        return cachedTestPerformance.getOrDefault(key, new ArrayList<>());
    }

    private void loadAllTestPerformance() throws IOException {
        try (InputStream is = new ClassPathResource("mockdata/testperformance.json").getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(is);
            cachedTestPerformance = new HashMap<>();

            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                JsonNode testsArray = rootNode.get(key);
                List<TestPerformance> tests = new ArrayList<>();

                for (JsonNode testNode : testsArray) {
                    TestPerformance test = mapJsonToTestPerformance(testNode);
                    tests.add(test);
                }

                cachedTestPerformance.put(key, tests);
            }
        }
    }

    public ReportDetails loadReportDetails(Long reportId) throws IOException {
        if (cachedReportDetails == null) {
            loadAllReportDetails();
        }

        return cachedReportDetails.get(reportId.toString());
    }

    private void loadAllReportDetails() throws IOException {
        try (InputStream is = new ClassPathResource("mockdata/reportdetails.json").getInputStream()) {
            JsonNode rootNode = objectMapper.readTree(is);
            cachedReportDetails = new HashMap<>();

            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String reportId = fieldNames.next();
                JsonNode reportNode = rootNode.get(reportId);
                ReportDetails reportDetails = mapJsonToReportDetails(reportNode);
                cachedReportDetails.put(reportId, reportDetails);
            }
        }
    }

    public List<SutConfiguration> loadAvailableSUT() throws IOException {
        if (cachedSutConfigurations != null) {
            return cachedSutConfigurations;
        }

        try (InputStream is = new ClassPathResource("mockdata/sutconfigurations.json").getInputStream()) {
            JsonNode arrayNode = objectMapper.readTree(is);
            List<SutConfiguration> configs = new ArrayList<>();

            for (JsonNode node : arrayNode) {
                SutConfiguration config = mapJsonToSutConfiguration(node);
                configs.add(config);
            }

            cachedSutConfigurations = configs;
            return cachedSutConfigurations;
        }
    }

    private TestPerformance mapJsonToTestPerformance(JsonNode node) {
        TestPerformance test = new TestPerformance();
        if (node.has("id")) test.setId(node.get("id").asLong());
        if (node.has("testName")) test.setTestName(node.get("testName").asText());
        if (node.has("version")) test.setVersion(node.get("version").asLong());
        if (node.has("uuid")) test.setUuid(node.get("uuid").asText());
        if (node.has("delay")) test.setDelay(node.get("delay").asLong());
        if (node.has("sutName")) test.setSutName(node.get("sutName").asText());
        if (node.has("sutId")) test.setSutId(node.get("sutId").asText());

        // Handle target manually if present
        if (node.has("target")) {
            JsonNode targetNode = node.get("target");
            Target target = new Target();
            if (targetNode.has("id")) target.setId(targetNode.get("id").asLong());
            if (targetNode.has("type")) target.setType(targetNode.get("type").asText());
            if (targetNode.has("name")) target.setName(targetNode.get("name").asText());
            if (targetNode.has("variant")) target.setVariant(targetNode.get("variant").asText());
            if (targetNode.has("revision")) target.setRevision(targetNode.get("revision").asText());
            if (targetNode.has("release")) target.setRelease(targetNode.get("release").asText());
            if (targetNode.has("description")) target.setDescription(targetNode.get("description").asText());
            if (targetNode.has("created")) target.setCreated(parseDate(targetNode.get("created").asText()));
            test.setTarget(target);
        }

        // Handle testsRepeat - simplified version
        if (node.has("testsRepeat")) {
            List<TestRepeat> testRepeats = new ArrayList<>();
            JsonNode repeatsArray = node.get("testsRepeat");
            for (JsonNode repeatNode : repeatsArray) {
                TestRepeat repeat = new TestRepeat();
                // Set basic properties - adjust as needed based on your TestRepeat model
                testRepeats.add(repeat);
            }
            test.setTestsRepeat(testRepeats);
        }

        return test;
    }

    private ReportDetails mapJsonToReportDetails(JsonNode node) {
        ReportDetails details = new ReportDetails();
        if (node.has("vfId")) details.setVfId(node.get("vfId").asLong());
        if (node.has("testID")) details.setTestID(node.get("testID").asText());
        if (node.has("name")) details.setName(node.get("name").asText());
        if (node.has("priority")) details.setPriority(node.get("priority").asInt());
        if (node.has("version")) details.setVersion(node.get("version").asLong());
        if (node.has("description")) details.setDescription(node.get("description").asText());
        if (node.has("maker")) details.setMaker(node.get("maker").asLong());
        if (node.has("estimated")) details.setEstimated(node.get("estimated").asLong());
        if (node.has("duration")) details.setDuration(node.get("duration").asLong());

        // Skip complex nested objects for now - they will be null
        // This keeps the stub working while providing basic information
        // You can extend this later if needed

        return details;
    }

    private SutConfiguration mapJsonToSutConfiguration(JsonNode node) {
        SutConfiguration config = new SutConfiguration();
        if (node.has("id")) config.setId(node.get("id").asLong());
        if (node.has("sutId")) config.setSutId(node.get("sutId").asText());
        if (node.has("sutName")) config.setSutName(node.get("sutName").asText());
        if (node.has("componentType")) config.setComponentType(node.get("componentType").asText());
        if (node.has("timezone")) config.setTimezone(node.get("timezone").asText());
        if (node.has("queueNightEnd")) config.setQueueNightEnd(node.get("queueNightEnd").asInt());
        if (node.has("queueNightInit")) config.setQueueNightInit(node.get("queueNightInit").asInt());
        if (node.has("queueDayEnd")) config.setQueueDayEnd(node.get("queueDayEnd").asInt());
        if (node.has("queueDayInit")) config.setQueueDayInit(node.get("queueDayInit").asInt());
        if (node.has("weekend")) config.setWeekend(node.get("weekend").asText());
        if (node.has("stop")) config.setStop(node.get("stop").asBoolean());
        if (node.has("reason")) {
            JsonNode reasonNode = node.get("reason");
            if (!reasonNode.isNull()) config.setReason(reasonNode.asText());
        }
        if (node.has("details")) config.setDetails(node.get("details").asText());
        if (node.has("reserved")) config.setReserved(node.get("reserved").asBoolean());
        if (node.has("connected")) config.setConnected(node.get("connected").asBoolean());

        // Skip complex nested objects (bridge, duts, planningList) for simplicity
        // They can be added later if needed

        return config;
    }

    /**
     * Parse date string with multiple format support
     */
    private Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return new Date();
        }

        // List of supported date formats
        String[] formats = {
                "yyyy-MM-dd HH:mm:ss z",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd"
        };

        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateString);
            } catch (ParseException e) {
                // Continue to next format
            }
        }

        // If no format works, return current date
        System.err.println("Warning: Could not parse date '" + dateString + "', using current date");
        return new Date();
    }

    // Method to clear cache if needed
    public void clearCache() {
        cachedProjects = null;
        cachedTargets = null;
        cachedStatistics = null;
        cachedReports = null;
        cachedTestPerformance = null;
        cachedReportDetails = null;
        cachedSutConfigurations = null;
    }
}
