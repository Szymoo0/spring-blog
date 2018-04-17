package pl.sglebocki.spring.blog.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DatePeriodDTO {

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fromDate;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date toDate;
	
	public DatePeriodDTO() {}
	public DatePeriodDTO(Date fromDate, Date toDate) {
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
}
