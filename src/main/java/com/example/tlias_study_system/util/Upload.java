package com.example.tlias_study_system.util;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
public class Upload {
    public static final Logger log = LoggerFactory.getLogger(Upload.class);
    public static final String endpoint = "oss-cn-beijing.aliyuncs.com",bucketName = "tlias-manager-system-house",region = "cn-beijing";//服务器域名,Bucket名称,destfile前缀,Bucket所在地域
    public static OSS ossClient;
    static{
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            // 创建OSSClient实例。
            // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
            ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
            clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
            ossClient = OSSClientBuilder.create().endpoint(endpoint).credentialsProvider(credentialsProvider).clientConfiguration(clientBuilderConfiguration).region(region).build();
            log.info("OSS链接建立成功");
        } catch (com.aliyuncs.exceptions.ClientException e) {log.error("OSS链接建立失败:{}",e.getMessage());}
    }
    public static void UploadHeadImg(String filename,MultipartFile multipartFile) throws Exception {
        long stime = System.currentTimeMillis();
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, filename, new ByteArrayInputStream(multipartFile.getBytes()));//Files.readAllBytes(uploadFile.toPath()))
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);//上传操作
//            // ✅ 关键：强制刷新并等待完成
//            ossClient.getObjectMetadata(bucketName, filename);  // 验证文件是否存在
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS,but was rejected with an error response for some reason.\n" +
                    "Error Message:{}\nError Code:{}\nRequest ID:{}\nHost ID:{}",
                    oe.getErrorMessage(),oe.getErrorCode(),oe.getRequestId(),oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.\nError Message:{}",ce.getMessage());
        }
        long etime = System.currentTimeMillis();
        log.info("上传文件{}完成,用时{}秒",filename,(etime-stime)/1000);
    }
    public static void DropHeadImg(String filename) throws Exception{
        log.info(filename);
        long stime = System.currentTimeMillis();
        try{ossClient.deleteObject(bucketName,filename);} catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS,but was rejected with an error response for some reason.\n" +
                      "Error Message:{}\nError Code:{}\nRequest ID:{}\nHost ID:{}",
                    oe.getErrorMessage(),oe.getErrorCode(),oe.getRequestId(),oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.\nError Message:{}",ce.getMessage());
        }
        long etime = System.currentTimeMillis();
        log.info("删除文件{}完成,用时{}秒",filename,(etime-stime)/1000);
    }
    @PreDestroy
    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
            log.info("OSS链接已关闭");
        }
    }
}   