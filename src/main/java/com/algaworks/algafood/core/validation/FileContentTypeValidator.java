package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private Set<String> contentTypeAlloweds;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.contentTypeAlloweds = new HashSet<>(Arrays.asList(constraintAnnotation.allowed()));
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

		return this.contentTypeAlloweds.contains(value.getContentType());
	}

}
