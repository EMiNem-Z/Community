package com.community.community.Service;

import com.community.community.dto.QuestionDTO;
import com.community.community.entity.Question;
import com.community.community.entity.User;
import com.community.community.mapper.QuestionMapper;
import com.community.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public void addQuestion(Question question){
        questionMapper.Insert(question);
    }

    public PageInfo questionDTOList(String search,Integer pageNum,Integer pageSize){
        if(search !=null && search.length()!=0){
            search.replace(" ","|");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList = questionMapper.selectBySearch(search);
        PageInfo pageInfo = new PageInfo(questionList);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findUserById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        return pageInfo;
    }

    public PageInfo questionDTOList(Integer userId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList = questionMapper.findByUserId(userId);
        PageInfo pageInfo = new PageInfo(questionList);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findUserById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setList(questionDTOList);
        return pageInfo;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.findById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            addQuestion(question);
        }else{
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void incViewCount(Integer id){
        questionMapper.incrViewCount(id);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        QuestionDTO query = new QuestionDTO();
        query.setId(queryDTO.getId());
        query.setTag(queryDTO.getTag().replace(",","|"));
        List<Question> questionList = questionMapper.findRelatedBytag(query);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question : questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public List<Question> hotTitle() {
        List<Question> hotTitles = questionMapper.selectHot();
        return hotTitles;
    }
}
