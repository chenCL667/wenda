package com.example.wenda.service;

import com.example.wenda.dao.CommentDAO;
import com.example.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by chen on 2018/12/1.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;


    @Autowired
    SensitiveService sensitiveService;

    public List<Comment> getCommentsByEntity(int entity_id, int entity_type){
        return commentDAO.selectCommentByEntity(entity_id, entity_type);
    }

    public int addComment(Comment comment){

        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));

        return (commentDAO.addComment(comment) > 0 ? comment.getId() : 0);
    }

    public int getCommentCount(int entity_id, int entity_type){
        return commentDAO.getCommentCount(entity_id, entity_type);
    }

    public int getUserCommentCount(int userId) {
        return commentDAO.getUserCommentCount(userId);
    }


    /**
     * 删除一条评论，实际是将status状态置为1，无效
     * @param commentId
     */
    public void deleteComment(int commentId){
        commentDAO.updateStatus(commentId, 1) ;
    }

    public Comment getCommentById(int id){
        return commentDAO.getCommentById(id);
    }
}
