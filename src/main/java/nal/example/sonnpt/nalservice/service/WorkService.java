package nal.example.sonnpt.nalservice.service;

import java.util.List;
import nal.example.sonnpt.nalservice.entity.Work;

/**
* @author  Son
* @version 1.0
* @since   2020-03-04
*/
public interface WorkService {

	public Work addWork(Work work) throws Exception;

	public void deleteWork(Long workId) throws Exception;

	public Work editWork(Work workDto) throws Exception;

	public List<Work> getlListWork(Integer pageNo, Integer pageSize, String sortBy) throws Exception;

}
