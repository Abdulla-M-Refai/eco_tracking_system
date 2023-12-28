package com.eco.track.eco_tracking_system.validator.Implementation;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import com.eco.track.eco_tracking_system.validator.Annotation.ValidReportFile;

@Component
public class ValidReportFileValidator implements ConstraintValidator<ValidReportFile, MultipartFile>
{
    @Override
    public boolean isValid(
        MultipartFile report,
        ConstraintValidatorContext constraintValidatorContext
    )
    {
        return
            report!=null &&
            !report.isEmpty() &&
            (
                Objects.requireNonNull(report.getContentType()).startsWith("application/pdf") ||
                Objects.requireNonNull(report.getContentType()).startsWith("application/msword") ||
                Objects.requireNonNull(report.getContentType()).startsWith("text/plain")
            );
    }
}