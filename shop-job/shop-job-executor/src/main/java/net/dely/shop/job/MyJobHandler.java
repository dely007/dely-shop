package net.dely.shop.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D，微信：xdclass6
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/

@Component
public class MyJobHandler  {

    private Logger log = LoggerFactory.getLogger(MyJobHandler.class);

    @XxlJob(value = "demoJobHandler", init = "init", destroy = "destroy")
    public ReturnT<String> execute() {


        String param = XxlJobHelper.getJobParam();
        System.out.println("v2 param=" + param);

        log.info("得利商城 execute 任务触发成功:" + LocalDateTime.now());


        XxlJobHelper.log("v2这个是执行日志：param=" + param);

        //XxlJobHelper.handleFail("自定义错误，任务执行失败");
        XxlJobHelper.handleSuccess("任务执行成功");

//        int i=1/0;

        return ReturnT.SUCCESS;
    }




    @XxlJob(value = "shardingJobHandler")
    public void shardingJobHandler(){

        //当前的执行器编号
        int shardIndex = XxlJobHelper.getShardIndex();

        //总的分片数，就是执行器的集群数量
        int shardTotal = XxlJobHelper.getShardTotal();

        log.info("分片总数:{},当前分片数{}",shardTotal,shardIndex);

        List<Integer> allUserIds = getAllUserIds();


        allUserIds.forEach(obj->{

            if(obj % shardTotal == shardIndex){
                log.info("第{}片，命中分片开始处理用户id={}",shardIndex,obj);
            }

        });

    }




    private List<Integer> getAllUserIds(){

        List<Integer> ids = new ArrayList<>();
        for(int i=0;i<10;i++){
            ids.add(i);
        }

        return ids;
    }









    private void init() {

        log.info("init 方法调用成功");

    }

    private void destroy() {
        log.info("destroy 方法调用成功");
    }

}
