package common.buessiness.problems.oom.usernameautocomplete;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 
 *
 * @since 2020/7/31
 * @author : weizc 
 */
@RequestMapping("usernameautocomplete")
@RestController
@Slf4j
public class UserNameAutocompleteController {

    @Autowired
    UserEntityRepository userEntityRepository;

    /**
     * 我们需要一个 HashMap 来存放这些用户数据，Key 是用户姓名索引，
     * Value 是索引下对应的用户列表。举一个例子，如果有两个用户 aa 和 ab，那么 Key 就有
     * 三个，分别是 a、aa 和 ab。用户输入字母 a 时，就能从 Value 这个 List 中拿到所有字母
     * a 开头的用户，即 aa 和 ab
     */
    ConcurrentMap<String,List<UserDTO>> caches=new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){

        List<UserOOMEntity> users = LongStream.rangeClosed(1, 10_000)
                .mapToObj(i -> new UserOOMEntity(i, randomName()))
                .collect(Collectors.toList());
        userEntityRepository.saveAll(users);

       // wrong();
        right();
    }


    @GetMapping("wrong")
    public void wrong(){


        caches.clear();
        Iterable<UserOOMEntity> all = userEntityRepository.findAll();
        all.forEach(t->{
            String name = t.getName();
            for (int i = 0; i <name.length() ; i++) {
                String key = name.substring(0, i + 1);
                caches.computeIfAbsent(key,(k)->new ArrayList<>()).add(new UserDTO(t.getName()));
            }
        });
        log.info("caches  size:{}, count:{}",caches.size(),caches.values()
                .stream().map(List::size).reduce(0,Integer::sum));

    }



    @GetMapping("right")
    public void right(){

        caches.clear();

        List<UserOOMEntity> all = userEntityRepository.findAll();
        Set<UserDTO> userDTOS = all.stream().map(t -> new UserDTO(t.getName())).collect(Collectors.toSet());

        userDTOS.forEach(t->{
            String name = t.getName();
            for (int i = 0; i <name.length() ; i++) {
                String key = name.substring(0, i + 1);
                caches.computeIfAbsent(key,(k)->new ArrayList<>()).add(t);
            }
        });
        log.info("caches  size:{}, count:{}",caches.size(),caches.values().stream()
                .map(List::size).reduce(0,Integer::sum));

    }

    /**
     * 随机生成长度为6的英文名称，字母包含 abcdefghij
     *
     * @return
     */
    private String randomName() {
        StringBuilder sb=new StringBuilder(toCharsetName().toUpperCase());
        for (int i = 0; i <5 ; i++) {
            sb.append(toCharsetName());
        }
        return sb.toString();
    }

    private String toCharsetName() {
        return String.valueOf(Character.toChars(ThreadLocalRandom.current().nextInt(10) +'a'));
    }
}
