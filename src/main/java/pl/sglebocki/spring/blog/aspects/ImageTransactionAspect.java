package pl.sglebocki.spring.blog.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import pl.sglebocki.spring.blog.dao.ImageSaver;

@Component
@Aspect
@Order(10)
public class ImageTransactionAspect {

	@Autowired
	private ImageSaver imageSaver;
	
	@Around("@annotation(pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction)")
	public Object aroundImageManipulationTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object returnValue = null;
		try {
			imageSaver.begin();
			returnValue = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			imageSaver.rollback();
			throw e;
		}
		imageSaver.commit();
		return returnValue;
	}
	
}
