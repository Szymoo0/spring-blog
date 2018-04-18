package pl.sglebocki.spring.blog.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import pl.sglebocki.spring.blog.dao.ImageDAO;

@Component
@Aspect
@Order(10)
public class ImageTransactionAspect {

	@Autowired
	private ImageDAO imageDAO;
	
	@Around("@annotation(pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction)")
	public Object aroundImageManipulationTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object returnValue = null;
		try {
			imageDAO.begin();
			returnValue = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			imageDAO.rollback();
			throw e;
		}
		imageDAO.commit();
		return returnValue;
	}
	
}
