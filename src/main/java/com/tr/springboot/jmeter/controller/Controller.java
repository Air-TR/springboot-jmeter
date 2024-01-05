package com.tr.springboot.jmeter.controller;

import com.tr.springboot.jmeter.entity.RecordIdentity;
import com.tr.springboot.jmeter.entity.RecordUuid;
import com.tr.springboot.jmeter.jpa.RecordIdentityJpa;
import com.tr.springboot.jmeter.jpa.RecordUuidJpa;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: TR
 */
@RestController
public class Controller {

    @Resource
    private RecordUuidJpa recordUuidJpa;

    @Resource
    private RecordIdentityJpa recordIdentityJpa;

    @GetMapping("/test")
    public void test() {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * jmeter测试结果（测试时间全部 10s）：
     *  1个线程（5279）：4279、2541、4151、4749、4119、4438、4852、4642、5149、4568、3916、5279
     *  2个线程（9414）：8143、8572、6868、7328、9414
     *  3个线程（12691）：8161、10462、12691、11068、11516
     *  4个线程：、、、、
     *  5个线程（17914）：16058、15798、15514、17914、17256
     *  6个线程（19041）：9768、12653、3925、12225、19041
     *  7个线程：、、、、
     *  8个线程：、、、、
     *  9个线程：、、、、
     *  10个线程（21138）：14023、17893、21138、18711、9433
     *  20个线程：、、、、
     *  50个线程（19217）：19217、17866、16838、18210、17476
     *  *100个线程（22155）：7940、14144、13186、17464、22155、2375、8616、7213、10248、19768
     *  200个线程：、、、、
     *  500个线程：、、、、
     *  1000个线程（16137）：6889、9848、9847、16137、15458
     */
    @GetMapping("/test/record/uuid")
    public void uuid() {
        recordUuidJpa.save(new RecordUuid());
    }

    /**
     * jmeter测试结果（测试时间全部 10s）：
     *  1个线程（5854）：4618、5347、5516、4521、5063、4417、5476、5854、5297、4035、5062、5646
     *  2个线程（10410）：9127、7837、8173、10410、10336
     *  3个线程（16824）：16824、12860、13202、13661、12115
     *  4个线程（17622）：12389、15807、13796、13901、17622
     *  5个线程（19518）：17037、11152、18921、16638、19518、16578
     *  6个线程（21061）：9128、9843、15325、21061、16183、13136、16108
     *  7个线程（20595）：14651、20595、12195、17129、19179
     *  8个线程（21514）：13953、17322、21514、18335、20812
     *  9个线程（21071）：17955、5640、4652、10671、9611、13515、19689、21071、11741、15737
     *  10个线程（18388）：6854、11699、13279、12386、18388
     *  15个线程（16068）：15596、12399、16068、14776、15057
     *  20个线程（18357）：10614、13504、15195、18357、17262
     *  30个线程（21204）：13460、21204、18756、19067、15907
     *  40个线程（14936）：14190、13830、14936、11696、12673
     *  50个线程（21110）：12978、12943、17091、18491、17045、12033、14606、15047、13933、21110
     *  60个线程：、、、、
     *  70个线程：、、、、
     *  80个线程：、、、、
     *  90个线程：、、、、
     *  *100个线程（23710）：8575、15753、18313、19874、17622、12583、13771、16640、11958、11703、14274、16293、18046、17635、21164、21712、23710
     *  150个线程：、、、、
     *  200个线程（21334）：17195、14039、16825、21334、21257
     *  250个线程：、、、、
     *  300个线程（19588）：12129、12306、13378、19588、19023
     *  500个线程（20916）：16809、13935、15780、18014、20916
     *  1000个线程（22039）：14902、12070、22039、13126、14636
     *  1500个线程：、、、、
     *  2000个线程（18769）：11237、15907、18769、13952、14071
     *  3000个线程（jmeter会卡住，电脑性能到顶了）：6801、5014、4517、、
     *  5000个线程（jmeter会卡住，电脑性能到顶了）：5458、、、、
     */
    @GetMapping("/test/record/identity")
    public void identity() {
        recordIdentityJpa.save(new RecordIdentity());
    }


    private static volatile int times = 0;

    @Cacheable(value = "identity", key = "#id")
    @GetMapping("/test/record/identity/{id}")
    public void identity(@PathVariable Integer id) {
        // 一旦缓存里有，根本就不进这里，直接返回结果，如果 id 不变，jmeter 查多少次 times 都是 1
        times++;
        recordIdentityJpa.findById(id);
    }

    @GetMapping("/test/record/identity/times")
    public void identityTimes() {
        recordIdentityJpa.findById(++times);
    }

    /**
     * 测试电脑配置（win10，4核，12G内存）
     * 经 jmeter 测试，10s测试结果在 12w+ ~ 13w+
     *  所以支持 12k+ QPS，但不能确定这 12k+ QPS 是本机 jmeter 能发送的峰值，还是 springboot 能处理的峰值
     *  若要确定，需要多台电脑 jmeter 同时怼一个 springboot（ps：根据之前测试 EMQ 服务器性能，应该是 jmeter 发送性能受限在 12k+ QPS，并不是 springboot 能处理的峰值）
     */
    @GetMapping("/add/times")
    public void addTimes() {
        times++;
    }

    /**
     * 测试结果（4核、12G内存）：
     *  1亿次：60+ 毫秒
     *  10亿次：600+ 毫秒
     *  20亿次：1200+ 毫秒
     *
     * 注释掉 num = i; 再测试：
     *  1亿次：30+ 毫秒
     *  10亿次：300+ 毫秒
     *  20亿次：600+ 毫秒
     */
    @GetMapping("/test/springboot")
    public Long springboot() {
        long start = System.currentTimeMillis();
        int num = 0;
        for (int i = 0; i < 20 * 10000 * 10000; i++) {
//            num = i;
        }
        long end = System.currentTimeMillis();
        System.out.println(num);
        return end - start;
    }

    @GetMapping("/get/times")
    public Integer getTimes() {
        return times;
    }

    @GetMapping("/reset/times")
    public void resetTimes() {
        times = 0;
    }

}
