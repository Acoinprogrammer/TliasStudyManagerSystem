package com.example.tlias_study_system.controller;

import cn.hutool.core.util.BooleanUtil;
import com.example.tlias_study_system.pojo.dept;
import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.service.Impl.DeptServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class DeptController {
    public static final Logger log = LoggerFactory.getLogger(DeptController.class);

    public static final String GET_ALL_DEPT_COUNT = "dept:count";

    public static final String GET_ALL_DEPT_COUNT_LOCK = "lockKey";

    public static final ObjectMapper mapper = new ObjectMapper();

    public static final long DEPT_COUNT_TIME_MINUTES = 3L;
    public static final long CONDITION_QUERY_TIME = 300L;//s
    @Autowired
    private DeptServiceImpl deptservice;
    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;
//    @RequestMapping(value="/GetAllDept",method= RequestMethod.GET)
    @GetMapping("/GetAllDept")
    public Restful GetAllDept() throws JsonProcessingException {return Restful.success(deptservice.GetAllDept());}
//    @GetMapping("/GetAllDeptCount")
//    public restful GetAllDeptCount() throws JsonProcessingException {
//        double stime = System.currentTimeMillis();
//        String strcount = stringRedisTemplate.opsForValue().get(GET_ALL_DEPT_COUNT);
//        if(strcount!=null){
//            double etime = System.currentTimeMillis();
//            log.info("缓存获取部门总数量所花时长为:{},count={}\n",(etime-stime)/1000,strcount);
//            return restful.success(mapper.readValue(strcount,Integer.class));
//        }
//        int count = deptservice.GetAllDeptCount();
//        stringRedisTemplate.opsForValue().set(GET_ALL_DEPT_COUNT, mapper.writeValueAsString(count),COUNT_TIME);
//        double etime = System.currentTimeMillis();
//        log.info("获取部门总数量所花时长为:{},count={}\n",(etime-stime)/1000,count);
//        return restful.success(count);
//    }
    @GetMapping("/GetAllDeptCount")
    public Restful GetAllDeptCount() throws JsonProcessingException {
        double stime = System.currentTimeMillis();
        String RedisBufferedDeptCount = stringRedisTemplate.opsForValue().get(GET_ALL_DEPT_COUNT);
        int count;
        if(RedisBufferedDeptCount!=null && !RedisBufferedDeptCount.isEmpty()){
            log.info("当前缓存为:{}",RedisBufferedDeptCount);
            log.info("缓存获取部门总数量所花时长为:{}",(System.currentTimeMillis()-stime)/1000);
            return Restful.success((Object) RedisBufferedDeptCount);
        }
        try{
            if(tryLock(GET_ALL_DEPT_COUNT)){
                Thread.sleep(100);
                return GetAllDeptCount();
            }
            count = deptservice.GetAllDeptCount();
            stringRedisTemplate.opsForValue().set(GET_ALL_DEPT_COUNT,String.valueOf(count), Duration.ofMinutes(DEPT_COUNT_TIME_MINUTES));
        }catch(Exception e){
            log.error("获取员工总数量缓存异常:{}",e.getMessage());
            throw new RuntimeException();
        }finally {unLock(GET_ALL_DEPT_COUNT_LOCK);}
        double etime = System.currentTimeMillis();
        log.info("获取部门总数量所花时长为:{}s, count={}", (etime-stime)/1000, count);
        return Restful.success(count);
    }
    public Boolean tryLock(String lockKey){return BooleanUtil.isTrue(stringRedisTemplate.opsForValue().setIfAbsent(lockKey,"1",Duration.ofSeconds(5L)));}
    public void unLock(String lockKey){stringRedisTemplate.delete(lockKey);}
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/DeleteDept")
    public Restful DeleteDept(@RequestParam String del_name){return deptservice.DeleteDept(del_name)<=0? Restful.error("当前部门不存在,请重新输入"): Restful.success("删除成功");}
    @GetMapping("/GetPageSizeDept")
    public Restful GetPageSizeDept(@RequestParam int page, @RequestParam int size) throws JsonProcessingException {
        int count = deptservice.GetAllDeptCount();
        if(count==0 || (page-1)*size>count){
            log.debug("超出dept长度进行查询");
            return Restful.success();
        }
        List<dept> dept = deptservice.GetPageSizeDept(page,count,size);
        return Restful.success(dept);
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/InsertDept")
    public Restful InsertDept(@RequestBody Map<String,String> params){
        if(params.get("add_name").equals("")){return Restful.error("部门名称不能为空");}
        int is_add;
        try{is_add = deptservice.InsertDept(params.get("add_name"));}
        catch(Exception e){
            log.debug("添加失败,与其他部门重名,请重新命名");
            return Restful.error("添加失败,与其他部门重名,请重新命名");
        }
        return Restful.success("添加成功");
    }
    @GetMapping("/SelectConditionQuery")
    public Restful SelectConditionQuery(@RequestParam int id, @RequestParam String name) throws JsonProcessingException {
        String query_key = id+name;
        dept redis_dept;
//        dept redis_dept = mapper.readValue(stringRedisTemplate.opsForValue().get(query_key),dept.class);
//        if(redis_dept!=null){
//            double etime = System.currentTimeMillis();
//            log.info("缓存条件查询所花时长为:{}s\n",(etime-stime)/1000);
//            return restful.success(new ArrayList<>(Arrays.asList(redis_dept)));
//        }
        try{redis_dept = deptservice.SelectConditionQuery(id,name);}
        catch(Exception e){return Restful.error("查询失败,请重新输入");}
//        stringRedisTemplate.opsForValue().set(query_key,mapper.writeValueAsString(redis_dept),CONDITION_QUERY_TIME);
        return redis_dept!=null? Restful.success(new ArrayList<>(Arrays.asList(redis_dept)),"查询成功"): Restful.error("查询失败,请重新输入");
    }
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/UpdateDept")
    public Restful UpdateDept(@RequestBody dept update_dept){
        if(update_dept.getName().equals("")){
            log.debug(update_dept.toString());
            return Restful.error("部门名称不能为空");
        }
        int up_dept= deptservice.UpdateDept(update_dept.getId(),update_dept.getName());
        return up_dept<=0? Restful.error("更新失败,请重新输入"): Restful.success("更新成功","更新成功");
    }
}
