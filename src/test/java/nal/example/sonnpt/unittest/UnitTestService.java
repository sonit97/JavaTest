package nal.example.sonnpt.unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.AssertionFailedError;
import nal.example.sonnpt.nalservice.SonnptApplication;
import nal.example.sonnpt.nalservice.entity.Work;
import nal.example.sonnpt.nalservice.repository.WorkRepository;
import nal.example.sonnpt.nalservice.service.WorkService;
import nal.example.sonnpt.nalservice.util.Common;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SonnptApplication.class)
public class UnitTestService {

    @Autowired(required = true)
    private WorkService workService;

    @Autowired(required = true)
    private WorkRepository workRepository;

    SetupFortest testConfig;
    private static final String PATH = "./src/test/java/nal/example/sonnpt/unittest/TestCase/";

    @Before
    public void contextLoads() {
        testConfig = new SetupFortest();

    }

    @Test
    public void addWork() throws IOException, ParseException {
        System.out.println("__________CREATE WORK________________");
        String folder = PATH + "Add/";
        JSONParser parser = new JSONParser();
        List<String> filesPath = this.testConfig.allFileInFolder(new File(folder));
        for (int i = 0; i < filesPath.size(); i++) {
            System.out.println("__________CASE: " + (i + 1) + "________________");
            Object obj = parser.parse(new FileReader(folder + filesPath.get(i) + ".in"));
            JSONObject jsonObject = (JSONObject) obj;
            processEditTest(jsonObject);

        }

    }

    @Test
    public void editWork() throws IOException, ParseException {
        System.out.println("__________EDIT WORK________________");
        String folder = PATH + "Update/";
        JSONParser parser = new JSONParser();
        List<String> filesPath = this.testConfig.allFileInFolder(new File(folder));
        for (int i = 0; i < filesPath.size(); i++) {
            System.out.println("__________CASE: " + (i + 1) + "________________");
            Object obj = parser.parse(new FileReader(folder + filesPath.get(i) + ".in"));
            JSONObject jsonObject = (JSONObject) obj;
            processEditTest(jsonObject);

        }

    }

    @Test
    public void deleteWork() throws IOException, ParseException {
        System.out.println("__________DELETE WORK________________");
        String folder = PATH + "Delete/";
        JSONParser parser = new JSONParser();
        List<String> filesPath = this.testConfig.allFileInFolder(new File(folder));
        for (int i = 0; i < filesPath.size(); i++) {
            System.out.println("__________CASE: " + (i + 1) + "________________");
            Object obj = parser.parse(new FileReader(folder + filesPath.get(i) + ".in"));
            JSONObject jsonObject = (JSONObject) obj;
            processDeleteTest(jsonObject);

        }

    }

    @Test
    public void getListWork() throws FileNotFoundException, IOException, ParseException {
        System.out.println("__________LIST WORK________________");
        String folder = PATH + "List/";
        JSONParser parser = new JSONParser();
        List<String> filesPath = this.testConfig.allFileInFolder(new File(folder));
        for (int i = 0; i < filesPath.size(); i++) {
            System.out.println("__________CASE: " + (i + 1) + "________________");
            Object obj = parser.parse(new FileReader(folder + filesPath.get(i) + ".in"));
            JSONObject jsonObject = (JSONObject) obj;
            try {
                int pageSize = SetupFortest.setIntConvert(jsonObject, "pageSize");
                int pageNo = SetupFortest.setIntConvert(jsonObject, "pageNo");
                String sortBy = SetupFortest.convertObjectToWork(jsonObject, "sortBy");

                List<Work> listWork = workService.getlListWork(pageNo, pageSize, sortBy);
                assertEquals("List size: ", SetupFortest.setIntConvert(jsonObject, "expected").intValue(),
                        listWork.size());

                System.out.println("__________SUCCESS____________");

            } catch (AssertionError | Exception ex) {
                System.out.println("__________FAIL____________");
            }

        }

    }

    private void processDeleteTest(JSONObject jsonObject) {
        try {

            workService.deleteWork(SetupFortest.setLongConvert(jsonObject));
            Optional<Work> workActual = workRepository.findById(SetupFortest.setLongConvert(jsonObject));
            assertNotEquals("", workActual);
            System.out.println("__________SUCCESS____________");

        } catch (Exception ex) {
            try {
                assertEquals("Message Error", jsonObject.get("expected"), ex.getMessage());
                System.out.println("__________SUCCESS____________");
            } catch (AssertionFailedError e1) {

                System.out.println("__________FAIL____________");

            } catch (AssertionError e) {
                System.out.println("__________FAIL____________");
            }
        }
    }

    private void processEditTest(JSONObject jsonObject) {
        try {
            Work work = Work.builder()
                    .workName(jsonObject.get("workName").toString())
                    .startingDate((Date) jsonObject.get("startingDate"))
                    .endingDate((Date) jsonObject.get("endingDate"))
                    .status(SetupFortest.getActiveStatus("status"))
                    .build();
            Optional<Work> workActual = workRepository.findById(work.getWorkId());

            if (workActual.isPresent()) {
                assertEquals("Id in DB", workActual.get().getWorkId(), work.getWorkId());
                assertAll("Work equality",
                        () -> assertEquals("Work name", jsonObject.get("workName").toString(),
                                workActual.get().getWorkName()),
                        () -> assertEquals("Starting Date", Common.getDate(jsonObject.get("startingDate").toString()),
                                Common.getDate(workActual.get().getStartingDate().toString())),
                        () -> assertEquals("Ending Date", Common.getDate(jsonObject.get("endingDate").toString()),
                                Common.getDate(workActual.get().getEndingDate().toString())),
                        () -> assertEquals("Status", SetupFortest.getActiveStatus("status".toString()),
                                workActual.get().getStatus()));

                System.out.println("__________SUCCESS____________");

            }

        } catch (Exception ex) {
            try {
                assertEquals("Message Error", jsonObject.get("expected"), ex.getMessage());
                System.out.println("__________SUCCESS____________");
            } catch (AssertionFailedError e1) {

                System.out.println("__________FAIL____________");

            } catch (AssertionError e) {
                System.out.println("__________FAIL____________");
            }
        }

    }

}
