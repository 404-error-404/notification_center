package general.notification_center.controller;

import general.notification_center.dao.intf.HelloWorldDao;
import general.notification_center.entity.HelloWorldEntity;
import general.notification_center.mapper.HelloWorldMapper;
import general.notification_center.utils.HttpResult;
import general.notification_center.wrapper.HelloWorldQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 小乐乐
 * @date 2022/2/16 21:13
 */
@RestController
public class TestDatabase {

    @Resource
    HelloWorldDao helloWorldDao;
    @Resource
    HelloWorldMapper helloWorldMapper;

    /**
     * 根据ID查询数据：方法1
     *
     * @param id id
     */
    @GetMapping("/getByIdOne")
    public HelloWorldEntity getByIdOne(Integer id) {
        return helloWorldDao.selectById(id);
    }

    /**
     * 根据ID查询数据：方法2
     *
     * @param id id
     */
    @GetMapping("/getByIdTwo")
    public HelloWorldEntity getByIdTwo(Integer id) {
        HelloWorldQuery query = new HelloWorldQuery().where.id().eq(id).end();
        return helloWorldMapper.findOne(query);
    }

    /**
     * 根据ID删除
     *
     * @param id id
     */
    @GetMapping("/deleteById")
    public HttpResult deleteById(Integer id) {
        helloWorldDao.deleteById(id);
        return new HttpResult(200, "success");
    }

    /**
     * 根据ID进行更新
     *
     * @param helloWorldEntity: 用户信息
     */
    @PostMapping("/updateById")
    public HelloWorldEntity updateById(@RequestBody HelloWorldEntity helloWorldEntity) {
        boolean b = helloWorldDao.updateById(helloWorldEntity);
        if (b) {
            return helloWorldDao.selectById(helloWorldEntity.getId());
        }
        return null;
    }

    /**
     * 新增
     *
     * @param helloWorldEntity: 用户信息
     */
    @PostMapping("/insert")
    public Integer insert(@RequestBody HelloWorldEntity helloWorldEntity) {
        return helloWorldDao.save(helloWorldEntity);
    }

}
