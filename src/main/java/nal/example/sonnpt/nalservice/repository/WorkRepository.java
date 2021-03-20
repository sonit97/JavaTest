package nal.example.sonnpt.nalservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import nal.example.sonnpt.nalservice.entity.Work;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
@Repository
public interface WorkRepository extends PagingAndSortingRepository<Work, Long> {

}
