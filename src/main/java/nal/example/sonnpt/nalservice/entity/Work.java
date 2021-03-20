package nal.example.sonnpt.nalservice.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.Data;
import nal.example.sonnpt.nalservice.util.WorkStatus;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
@Data
@Builder
@Getter @Setter
@Entity
@Table(name = "WORK", schema = "public")
public class Work {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long workId;

	@Column(name = "WORK_NAME", length = 255, nullable = false)
	private String workName;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "STARTING_DATE", nullable = true) 
	private Date startingDate;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "ENDING_DATE", nullable = true)
	private Date endingDate;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = true)
	private WorkStatus status;

}
