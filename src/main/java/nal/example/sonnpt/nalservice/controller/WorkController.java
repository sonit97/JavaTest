package nal.example.sonnpt.nalservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import nal.example.sonnpt.nalservice.entity.Work;
import nal.example.sonnpt.nalservice.service.WorkService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
@RestController
@RequestMapping("v1/work/")
public class WorkController {

    @Autowired
    private WorkService workService;

    public static final String PAGE_NO = "1";
    public static final String PAGE_SIZE = "10";

    @RequestMapping(value = "creatework", method = RequestMethod.POST)
    public Map<String, Object> createWork(HttpServletRequest request, @RequestBody Work work) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("workObj", workService.addWork(work));
        return map;
    }

    @RequestMapping(value = "/deletework/{workid}", method = RequestMethod.DELETE)
    public String removeWork(HttpServletRequest request, @PathVariable(value = "workid") Long workId) throws Exception {
        workService.deleteWork(workId);
        return "OK";
    }

    @RequestMapping(value = "/editwork", method = RequestMethod.PUT)
    public Map<String, Object> editWork(HttpServletRequest request, @RequestBody Work writeDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("workObj", workService.editWork(writeDto));
        return map;
    }

    @RequestMapping(value = "/list-work", method = RequestMethod.GET)
    public Map<String, Object> getAllEmployees(HttpServletRequest request,
                                               @RequestParam(defaultValue = PAGE_NO) Integer pageNo,
                                               @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize,
                                               @RequestParam(defaultValue = "workId") String sortBy) throws Exception {
        List<Work> list = workService.getlListWork(pageNo, pageSize, sortBy);
        Map<String, Object> map = new HashMap<>();
        map.put("listOfWork", list);
        return map;
    }

}
