package com.eco.track.eco_tracking_system.util;

import org.springframework.validation.BindingResult;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.validation.ValidationException;

public class Helper
{
    private Helper()
    {
    }

    public static void fieldsValidate(BindingResult result) throws ValidationException
    {
        if (result.hasErrors())
        {
            List<String> errorMessages = result
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

            throw new ValidationException(errorMessages.toString());
        }
    }

    public static String generateFileName(MultipartFile file)
    {
        return UUID.randomUUID() + "-" + StringUtils.cleanPath(
            Objects.requireNonNull(
                file.getOriginalFilename()
            ).replaceAll("\\s", "")
        );
    }

    public static String getFileUri(String filename, String directory)
    {
        return ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/"+directory+"/")
            .path(filename)
            .toUriString();
    }

    public static String uploadFile(
        MultipartFile file,
        String directoryName,
        String uploadDirectory,
        String targetUploadDirectory
    ) throws IOException
    {
        String fileName = generateFileName(file);
        String reportURI = getFileUri(fileName, directoryName);

        Path uploadLocation = Paths.get(uploadDirectory).resolve(fileName);
        file.transferTo(uploadLocation);

        Path targetLocation = Paths.get(targetUploadDirectory).resolve(fileName);
        file.transferTo(targetLocation);

        return reportURI;
    }
}