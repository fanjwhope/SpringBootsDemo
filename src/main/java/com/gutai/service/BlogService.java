package com.gutai.service;

import com.gutai.mapper.UserMapper;
import com.gutai.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 82421 on 2017/8/14.
 */
@Service
public class BlogService {
   @Autowired
    private UserMapper mapper;
    public List<User> getAllBlogList(){
        List<User> list=new ArrayList<User>();
        list.add(new User("厉害了",1));
        list.add(new User("刘总",2));
        list.add(new User("响总",3));
        list.add(new User("段总",4));
       return list;
    }

    public List<User> getAllList() throws Exception{
        return mapper.selectAllUser();
    }
}
