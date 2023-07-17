package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import javax.xml.transform.Source;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SpringDataRedisTest
 * Package: com.sky.test
 * Description:
 *
 * @Author: Jane
 * @Create: 2023/7/16 - 20:19
 * @version: v1.0
 */
// @SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
        // 操作redis中数据的对象
        // 操作字符串类型的数据
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 操作hash类型数据
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * 操作字符串类型的数据
     */
    @Test
    public void testString(){
        // SET key value			设置指定key的值
        // GET key			        获取指定key的值
        // SETEX key seconds value	设置指定key的值，并将 key 的过期时间设为 seconds 秒
        // SETNX key value		    只有在 key 不存在时设置 key 的值
        redisTemplate.opsForValue().set("city","深圳");
        String city = (String) redisTemplate.opsForValue().get("city");
        System.out.println(city);
        redisTemplate.opsForValue().set("code","1234",3, TimeUnit.MINUTES);
        // value是Object类型 set命令是操作字符串的 在这里变成Object了
        // 在调用参数时 value可以给任意类型 最终会把这个对象序列化 最终都会转成redis中的字符串
        redisTemplate.opsForValue().setIfAbsent("lock","1");
        redisTemplate.opsForValue().setIfAbsent("lock","2");
    }

    /**
     * 操作哈希类型的数据
     */
    @Test
    public void testHash(){
        // HSET key field value 	将哈希表 key 中的字段 field 的值设为 value
        // HGET key field 	        获取存储在哈希表中指定字段的值
        // HDEL key field		    删除存储在哈希表中的指定字段
        // HKEYS key 		        获取哈希表中所有字段
        // HVALS key 		        获取哈希表中所有值
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.put("1001","name","Jane");
        hashOperations.put("1001","age","26");

        String name = (String) hashOperations.get("1001", "name");
        System.out.println(name);

        Set keys = hashOperations.keys("1001");
        System.out.println(keys);

        List values = hashOperations.values("1001");
        System.out.println(values);

        hashOperations.delete("1001","age");
        
    }

    /**
     * 操作列表类型的数据
     */
    @Test
    public void testList(){
        // LPUSH key value1 [value2] 	将一个或多个值插入到列表头部(左边)
        // LRANGE key start stop 		获取列表指定范围内的元素
        // RPOP key 			        移除并获取列表最后一个元素(右边)
        // LLEN key 			        获取列表长度
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPushAll("mylist","a","b","c");
        listOperations.leftPush("mylist","d");

        List mylist = listOperations.range("mylist",0,-1);
        System.out.println(mylist);

        listOperations.rightPop("mylist");

        Long size = listOperations.size("mylist");
        System.out.println(size);
    }

    /**
     * 操作集合类型的数据
     */
    @Test
    public void testSet(){
        // Redis set 是string类型的无序集合。集合成员是唯一的，集合中不能出现重复的数据，常用命令：
        // SADD key member1 [member2] 	向集合添加一个或多个成员
        // SMEMBERS key 		返回集合中的所有成员
        // SCARD key 			获取集合的成员数
        // SINTER key1 [key2] 		返回给定所有集合的交集
        // SUNION key1 [key2] 		返回所有给定集合的并集
        // SREM key member1 [member2] 	删除集合中一个或多个成
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("set1","a","b","c","d");
        setOperations.add("set2","a","b","x","y");

        Set members = setOperations.members("set1");
        System.out.println(members);

        Long size = setOperations.size("set1");
        System.out.println(size);

        Set intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);

        Set union = setOperations.union("set1", "set2");
        System.out.println(union);

        setOperations.remove("set1","a","b");
    }

    /**
     * 操作有序集合类型的数据
     */
    @Test
    public void testZset(){
        // Redis有序集合是string类型元素的集合，且不允许有重复成员。每个元素都会关联一个double类型的分数。常用命令：
        // ZADD key score1 member1 [score2 member2] 	向有序集合添加一个或多个成员
        // ZRANGE key start stop [WITHSCORES] 		通过索引区间返回有序集合中指定区间内的成员
        // ZINCRBY key increment member 			有序集合中对指定成员的分数加上增量 increment
        // ZREM key member [member ...] 			移除有序集合中的一个或多个成员
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset1","a",10);
        zSetOperations.add("zset1","b",12);
        zSetOperations.add("zset1","c",9);

        Set zset1 = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset1);

        zSetOperations.incrementScore("zset1","c",10);
        zSetOperations.remove("zset1","a","b");

    }

    /**
     * 通用命令操作
     */
    public void testCommon(){
        // Redis的通用命令是不分数据类型的，都可以使用的命令：
        // KEYS pattern 		查找所有符合给定模式( pattern)的 key
        // EXISTS key 		    检查给定 key 是否存在
        // TYPE key 		    返回 key 所储存的值的类型
        // DEL key 		        该命令用于在 key 存在是删除 key
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        redisTemplate.delete("mylist");
    }
}
