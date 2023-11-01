package com.myweb.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	   @Override
	   protected Class<?>[] getRootConfigClasses() {
	      // TODO Auto-generated method stub
	      return new Class[] {RootConfig.class, SecurityConfig.class};
	   }

	   @Override
	   protected Class<?>[] getServletConfigClasses() {
	      // TODO Auto-generated method stub
	      return new Class[] {ServletConfiguration.class};
	   }

	   @Override
	   protected String[] getServletMappings() {
	      // servlet 맵핑 루트로 설정
	      return new String[] {"/"};
	   }

	   @Override
	   protected Filter[] getServletFilters() {
	      // 인코딩 필터 설정
	      CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
	      encodingFilter.setEncoding("UTF-8");
	      encodingFilter.setForceEncoding(true); // 외부로 나가는 데이터도 인코딩 설정
	      
	      return new Filter[] {encodingFilter};
	   }

	   @Override
	   protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	      // 그 외 기타 사용자 설정
	      // 사용자 지정 exception 설정을 할것인지 처리
	      registration.setInitParameter("throwExceptionIfNoHandlerFound", "true"); // exception handler를 사용한다는 구문.
	      
	      // 파일 업로드 설정
	      // 경로 , maxFileSize , maxRequestSize, fileSizeThreshold <= 값을 줘야함
	      String uploadLocation = "D:\\_myweb\\_java\\fileupload";
	      int maxFileSize = 1024*1024*20; // 20MB 
	      int maxReqSize = maxFileSize*2; // 40MB
	      int fileSizeThreshold = maxFileSize; // 20MB (maxFileSize와 동일하게)
	      
	      MultipartConfigElement mutipartConfig = new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
	      
	      registration.setMultipartConfig(mutipartConfig);
	   }
	   
	   
	}