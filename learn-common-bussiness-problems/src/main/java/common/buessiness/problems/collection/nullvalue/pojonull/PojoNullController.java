package common.buessiness.problems.collection.nullvalue.pojonull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@RequestMapping("pojonull")
@RestController
@Slf4j
public class PojoNullController {

    @Autowired
    PersonEntityRepository personEntityRepository;

    @Autowired
    PersonNullableEntityRepository personNullableEntityRepository;


    @PostMapping("wrong")
    public PersonNullableEntity wrong(@RequestBody PersonNullableEntity personNullable) {

        personNullable.setNickname(String.format("guest-%s", personNullable.getName()));
        return personNullableEntityRepository.save(personNullable);

    }


    @PostMapping("right")
    public PersonEntity right(@RequestBody PersonDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("用户Id不能为空");
        }

        PersonEntity personEntity = personEntityRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        if (dto.getName().isPresent()) {
            personEntity.setName(dto.getName().orElse(""));
        }
        personEntity.setNickname(String.format("guest-%s", personEntity.getName()));
        if (dto.getAge().isPresent()) {
            personEntity.setAge(dto.getAge().orElseThrow(() -> new IllegalArgumentException("年龄不能为空")));
        }

        return personEntityRepository.save(personEntity);

    }
}
