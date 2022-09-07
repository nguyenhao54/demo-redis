import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import java.util.List;

public class RedisJava {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        //Connecting to Redis server on localhost
        Jedis jedisClient = new Jedis("redis://localhost:6379");
        log.info("Connecting to server sucessfully!");
        log.info("The server is running {} ", jedisClient.ping());

        //<editor-fold desc="String">
        log.trace("STRING");
        jedisClient.set("name", "kyle");
        jedisClient.set("age", "23");
        log.info("Value of name: {} ", jedisClient.get("name"));
        log.info("Value of age: {} ", jedisClient.get("age"));
        jedisClient.del("name");
        log.info("Deleted name");
        log.info("All keys: {}", jedisClient.keys("*"));
        log.info("Check if exits name: {}", jedisClient.exists("name"));
        //</editor-fold>
        //<editor-fold desc="List">
        log.trace("LIST");
        jedisClient.lpush("friends", "Kyle");
        jedisClient.lpush("friends", "Saly");
        jedisClient.rpush("friends", "Mathew");
        List<String> listFriend = jedisClient.lrange("friends", 0, 5);
        log.info("List friends: ");
        for (int i = 0; i < listFriend.size(); i++) {
            log.info(i + 1 + ". " + listFriend.get(i));
        }
        log.info("Top element of list friends: {}", jedisClient.lpop("friends"));
        //</editor-fold>
        //<editor-fold desc="Set">
        log.trace("SET");
        jedisClient.sadd("hobbies", "weight lifting");
        jedisClient.sadd("hobbies", "wave surfing");
        jedisClient.sadd("drinks", "americano");
        jedisClient.sadd("drinks", "matcha latte");
        log.info("Members of set hobbies : {}", jedisClient.smembers("hobbies"));
        log.info("Members of set drinks : {}", jedisClient.smembers("drinks"));
        //</editor-fold>
        //<editor-fold desc="Hash">
        log.info("HASH");
        jedisClient.hset("person", "name", "kyle");
        jedisClient.hset("person", "age", "23");
        jedisClient.hset("person", "job", "Developer");
        log.info("Info about person: {}", jedisClient.hgetAll("person"));
        //</editor-fold>
        //<editor-fold desc="Sorted set">
        log.trace("SORTED SET");
        jedisClient.zadd("studentHeight", 167.2, "Kate");
        jedisClient.zadd("studentHeight", 182.7, "Kyle");
        jedisClient.zadd("studentHeight", 162.4, "Sophia");
        log.info("Height of student Kate: {}", jedisClient.zscore("studentHeight", "Kate"));
        log.info("Ascending sort: {}", jedisClient.zrangeByScore("studentHeight", 100, 200));
        log.info("Remove Kate: {}", (jedisClient.zrem("studentHeight", "Kate") == 1 ? "successfully" : "failed"));
        log.info("Number of person taller than 170 cm: {}", jedisClient.zcount("studentHeight", 170, 200));
        //</editor-fold>

        jedisClient.flushAll();
        log.trace("Flushall");
    }
}
