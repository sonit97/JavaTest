package nal.example.sonnpt.nalservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import nal.example.sonnpt.nalservice.entity.Work;
import nal.example.sonnpt.nalservice.repository.WorkRepository;
import nal.example.sonnpt.nalservice.service.WorkService;


/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkRepository workRepository;

    /**
     * Process add work
     *
     * @param workDto the Work ID
	 * @return work
	 */
    @Override
    public Work addWork(Work workDto) throws Exception {
        return workRepository.save(workDto);
    }

    /**
     * Process delete work
     *
     * @param workId the Work ID
     */
    @Override
    public void deleteWork(Long workId) throws Exception {
        workRepository.deleteById(workId);
    }

    /**
     * Process edit work
     *
     * @param workDto the Work
	 * @return work
     */
    @Override
    public Work editWork(Work workDto) throws Exception {
        Work workEdit = getWorkById(workDto.getWorkId());
        workEdit.setWorkName(workDto.getWorkName());
        workEdit.setStatus(workDto.getStatus());
        workEdit.setStartingDate(workDto.getStartingDate());
        workEdit.setEndingDate(workDto.getEndingDate());

        return workRepository.save(workEdit);
    }

    /**
     * Process get list work with workId
     *
     * @param workId the Work ID
	 * @return work
	 *
     */
    private Work getWorkById(Long workId) throws Exception {
        Optional<Work> optional = workRepository.findById(workId);
        return optional.get();
    }

    /**
     * Process get List work page and sort
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
	 * @return list Work
     */
    @Override
    public List<Work> getlListWork(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Work> pagedResult = workRepository.findAll(paging);
        return pagedResult.getContent();
    }

}
