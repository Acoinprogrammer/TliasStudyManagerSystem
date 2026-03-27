package com.example.tlias_study_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TliasStudySystemApplication {
    public static final Logger log = LoggerFactory.getLogger(TliasStudySystemApplication.class);
    public static void main(String[] args) {
        // 启动 natapp
        startNatapp();
        SpringApplication.run(TliasStudySystemApplication.class, args);
    }
    private static void startNatapp() {
        try{
           String redisServerPath = "E:/redis/redis-server.exe";
           String redisCliPath = "E:/redis/redis-cli.exe";
           ProcessBuilder redisServerProcess = new ProcessBuilder(redisServerPath);
//           redisServerProcess.directory(new File("E:/tlias智能学习辅助系统"));
           ProcessBuilder redisCliProcess = new ProcessBuilder(redisCliPath);
//           redisCliProcess.directory(new File("E:/tlias智能学习辅助系统"));
           redisServerProcess.start();
           redisCliProcess.start();
           log.info("🚀 redis 已启动");
        }catch(Exception e){log.error("❌ redis 启动失败:{}",e.getMessage());}
//        try{
//            // natapp 的路径（根据你的实际位置修改）
//            String natappPath = "E:/tlias智能学习辅助系统/natapp.exe",authtoken = "861425584bef4d69";
//            // 构建命令
//            ProcessBuilder pb = new ProcessBuilder(natappPath, "-authtoken=" + authtoken, "-log=stdout");
//            // 设置工作目录为 natapp.exe 所在目录
//            pb.directory(new File("E:/tlias智能学习辅助系统"));
//            // 启动进程
//            Process process = pb.start();
//            log.info("🚀 natapp 已启动");
//        } catch (Exception e) {log.info("❌ natapp 启动失败:{}",e.getMessage());}
//        try{
//            String natappPath = "E:/tlias智能学习辅助系统/natapp.exe";
//            ProcessBuilder pb = new ProcessBuilder(natappPath);
//            pb.directory(new File("E:/tlias智能学习辅助系统"));
//            Process Process = pb.start();
//            log.info("🚀 natapp 已启动");
//        }catch(Exception e){log.error("❌ natapp 启动失败:{}",e.getMessage());}
    }
}
