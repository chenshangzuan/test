/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.controller;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.ServerSideEncryption;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
public class MinioController {

    private MinioClient minioClient = null;

    {
        // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
        try {
            minioClient = new MinioClient("http://172.16.5.56:8081", "admin", "12345678");
        } catch (InvalidEndpointException ex) {
            ex.printStackTrace();
        } catch (InvalidPortException ex) {
            ex.printStackTrace();
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public void uploadFile2Minio(@RequestParam("file")MultipartFile multipartFile){
        try {
            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists("kled");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket("kled");
            }

            // Generate a new 256 bit AES key - This key must be remembered by the client.
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);

            // To test SSE-C
            ServerSideEncryption sse = ServerSideEncryption.withCustomerKey(keyGen.generateKey());

            PutObjectOptions options = new PutObjectOptions(multipartFile.getInputStream().available(), -1);
            //options.setSse(sse);
            // Upload the zip file to the bucket with putObject
            //重要！文件名称可加前缀生成多级目录，例如 aa/bb/asiaphotos.zip
            minioClient.putObject("kled", multipartFile.getOriginalFilename(), multipartFile.getInputStream(), options);
            //minioClient.putObject("asiatrip","aa/bb/asiaphotos.zip", "/Users/kled/git/test/src/main/resources/books.xml", null);
            System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
        } catch( Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }


    @GetMapping("/list")
    @ResponseBody
    public void listObjects(HttpServletResponse response) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        boolean found = minioClient.bucketExists("kled");
        if (found) {
            // List objects from 'my-bucketname'
            Iterable<Result<Item>> myObjects = minioClient.listObjects("kled");
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());
            }
        } else {
            System.out.println("my-bucketname does not exist");
        }
    }

    @GetMapping("/download")
    @ResponseBody
    public void downloadFromMinio(HttpServletResponse response) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        InputStream stream = minioClient.getObject("kled", "file");
        response.getOutputStream().write(IOUtils.toByteArray(stream));
    }

    @DeleteMapping()
    @ResponseBody
    public void deleteObject(HttpServletResponse response) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        minioClient.removeBucket("test");
        minioClient.removeObject("kled", "file");
    }

    public static void main(String[] args) {
        try {
            MinioClient minioClient = new MinioClient("http://172.16.5.56:8081", "admin", "12345678");
            minioClient.listObjects("test1", "aa").forEach(x -> {
                try {
                    System.out.println(x.get().objectName());
                } catch (ErrorResponseException e) {
                    e.printStackTrace();
                } catch (InsufficientDataException e) {
                    e.printStackTrace();
                } catch (InternalException e) {
                    e.printStackTrace();
                } catch (InvalidBucketNameException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidResponseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (XmlParserException e) {
                    e.printStackTrace();
                }
            });
        } catch (InvalidEndpointException ex) {
            ex.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

}
