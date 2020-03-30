package cn.cqu.ccc1z.community.serviece;

import cn.cqu.ccc1z.community.dto.QuestionDTO;
import cn.cqu.ccc1z.community.mapper.QuestionMapper;
import cn.cqu.ccc1z.community.mapper.UserMapper;
import cn.cqu.ccc1z.community.model.Question;
import cn.cqu.ccc1z.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ccc1Z 2020/03/30
 */
@Service
public class QuestionService {
    @Autowired(required = false)
    QuestionMapper questionMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
           User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    //目的：在里面可以同时使用QuestionMapper和UserMapper
    //起到一个组装的作用(这个中间层就叫做Service
}
