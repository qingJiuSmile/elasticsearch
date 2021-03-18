package com.weds.demo.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.weds.demo.elasticsearch.entity.DormUser;
import com.weds.demo.elasticsearch.entity.es.EsReturnData;
import com.weds.demo.elasticsearch.util.EsClientUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTests {

    @Autowired
    private EsClientUtil clientUtil;

    @Test
   public void contextLoads() {
    }

    @Test
    public void  test() throws IOException {
        String[] arr = {"1","2"};

        EsReturnData byIds = clientUtil.getDocByIds(null,null,"",arr, "qs","name3");
        System.out.println(byIds);
    }

    @Test
    public void  testTermQuery() throws IOException {
        EsReturnData esReturnData = clientUtil.termQuery(null, null, "",
                "userName", "大威天龙", "util");
        System.out.println(esReturnData);
    }

    @Test
    public void  testTermsQuery() throws IOException {
        String[] arr = {"大威天龙","最强法"};
        EsReturnData esReturnData = clientUtil.termsQuery(null, null, "",
                "userName", arr, "util");
        System.out.println(esReturnData);
    }

    @Test
    public void  testMatchAllQuery() throws IOException {
        EsReturnData esReturnData = clientUtil.matchAllQuery(0, 2, "", "dorm");
        System.out.println(esReturnData);
    }

    @Test
    public void  testMatchQuery() throws IOException {
        EsReturnData esReturnData = clientUtil.matchQuery(0, 10, "","userName" ,
                "大","name3","util");
        System.out.println(esReturnData);
    }

    @Test
    public void  testMatchOperatorQuery() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("大");
        list.add("龙");
        EsReturnData esReturnData = clientUtil.matchOperatorQuery(0, 10, "","userName" , Operator.AND,
                list,"qos1");
        System.out.println(esReturnData);
    }

    @Test
    public void  testMultiMatchQuery() throws IOException {
        EsReturnData esReturnData = clientUtil.multiMatchQuery(0, 10, "",new String[]{"sex"} ,
                "1","name3","util");
        System.out.println(esReturnData);
    }

    @Test
    public void  testRangeQuery() throws IOException {
        EsReturnData esReturnData = clientUtil.rangeNumQuery(null, null, "",
                "userNo","5","4",true,true,"");
        System.out.println(esReturnData);
    }

    @Test
    public void testUpdateDoc() throws IOException {

        List<DormUser> list = new ArrayList<>();
        DormUser dormUser = new DormUser();
        dormUser.setBeginDate(new Date());
        dormUser.setBedId(1);
        dormUser.setClassName("我改名了班级名称");
        dormUser.setUserName("我也改名了用户");
        list.add(dormUser);
        BulkRequest request = new BulkRequest();
        // 一个别名指向多个索引时，进行修改 报错  ↓↓↓
        // false or the alias points to multiple indices without one being designated as a write index
     /*   for (DormUser aList : list) {
            request.add(new UpdateRequest("dorm", aList.getBedId().toString())
                    .doc(JSON.toJSONString(aList), XContentType.JSON));
        }*/
        for (DormUser aList : list) {
            request.add(new UpdateRequest("uipdorm-2020-7-17", aList.getBedId().toString())
                    .doc(JSON.toJSONString(aList), XContentType.JSON));
        }

        boolean b = clientUtil.bulkUpdateDocument(request);
        System.out.println(b);

    }

    @Test
    public  void testAddIndex() throws IOException {
        List<DormUser> list = new ArrayList<>();
        DormUser dormUser = new DormUser();
        dormUser.setBeginDate(new Date());
        dormUser.setBedId(1);
        dormUser.setClassName("123");
        dormUser.setUserName("测试名称1");
        list.add(dormUser);
        DormUser dormUser1 = new DormUser();
        dormUser1.setBeginDate(new Date());
        dormUser1.setBedId(2);
        dormUser1.setClassName("321");
        dormUser1.setUserName("测试名称2");
        list.add(dormUser1);

        // 自定义id
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            request.add(new IndexRequest("uipdorm-2020-7-18")
                    .source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        boolean testaa = clientUtil.bulkAddDocument(request);
        // 默认生成id
        //boolean testaa = clientUtil.bulkAddDocument("uipdorm-2020-7-17",list);
        System.out.println(testaa);
    }

    @Test
    public void testAddIndexTmp() throws IOException {
        boolean b = clientUtil.addIndexTemplate("uipdorm-template",
                "redis", Arrays.asList("uipdorm-*"),"dorm",null);
        System.out.println(b);
    }

    @Test
    public void testDelIndexTmp() throws IOException {
        boolean b = clientUtil.delIndexTemplate("my-template");
        System.out.println(b);
    }

    @Test
    public void testDelIndex() throws IOException {
        boolean b = clientUtil.deleteIndex("uipdorm-2020-7-17");
        System.out.println(b);
    }

    @Test
    public void testExistsIndex() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("uipdorm-template");
        boolean b = clientUtil.existsIndexTemplate(list);
        System.out.println(b);
    }
}
