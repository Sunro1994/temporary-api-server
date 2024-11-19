package com.ja.finalproject.domain.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja.finalproject.domain.board.mapper.BoardSqlMapper;
import com.ja.finalproject.domain.board.dto.ArticleDto;
import com.ja.finalproject.domain.board.dto.ArticleImageDto;
import com.ja.finalproject.domain.board.dto.CommentDto;
import com.ja.finalproject.domain.board.dto.LikeDto;
import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.mapper.UserSqlMapper;

@Service
public class BoardService {
    @Autowired
    private BoardSqlMapper boardSqlMapper;
    @Autowired
    private UserSqlMapper userSqlMapper;

    /// 안녕하세요

    public void registerArticle(ArticleDto articleDto, List<ArticleImageDto> articleImageDtoList) {
        boardSqlMapper.createArticle(articleDto);

        for(ArticleImageDto articleImageDto : articleImageDtoList) {
            articleImageDto.setArticle_id(articleDto.getId());
            boardSqlMapper.createArticleImage(articleImageDto);
        }

    }

    public List<Map<String, Object>> getArticleList(String searchType, String searchWord, int page){
        List<Map<String, Object>> result = new ArrayList<>(); 
        List<ArticleDto> articleDtoList = boardSqlMapper.findAll(searchType, searchWord, (page-1)*10);

        for(ArticleDto articleDto : articleDtoList){
            int userPk = articleDto.getUser_id();
            UserDto userDto = userSqlMapper.findById(userPk);
            Map<String, Object> map = new HashMap<>();
            map.put("articleDto", articleDto);
            map.put("userDto", userDto);
            result.add(map);
        }
        return result;
    }

    public int getTotalArticleCount(String searchType, String searchWord) {
        return boardSqlMapper.getTotalArticleCount(searchType, searchWord);
    }


    public Map<String, Object> getArticle(int id) {
        Map<String, Object> map = new HashMap<>();

        ArticleDto articleDto = boardSqlMapper.findById(id);
        UserDto userDto = userSqlMapper.findById(articleDto.getUser_id());
        List<ArticleImageDto> articleImageDtoList = boardSqlMapper.findImageByArticleId(id);

        map.put("articleDto", articleDto);
        map.put("userDto", userDto);
        map.put("articleImageDtoList", articleImageDtoList);

        return map;
    }

    public void increaseReadCount(int id) {
        boardSqlMapper.increaseReadCount(id);
    }

    public void deleteArticle(int id){
        boardSqlMapper.deleteById(id);
    }

    public void updateArticle(ArticleDto articleDto){
        boardSqlMapper.update(articleDto);
    }

    // 좋아요..
    public void like(LikeDto likeDto) {
        boardSqlMapper.createLike(likeDto);
    }

    public void unLike(LikeDto likeDto) {
        boardSqlMapper.deleteLike(likeDto);
    }

    public int getTotalLikeCount(int articleId) {
        return boardSqlMapper.getTotalLikeCount(articleId);
    }

    public boolean isUserLike(LikeDto likeDto) {
        return boardSqlMapper.getMyLikeCount(likeDto) > 0;
    }


    public void registerComment(CommentDto commentDto) {
        boardSqlMapper.createComment(commentDto);
    }

    public void deleteComment(int id) {
        boardSqlMapper.deleteCommentById(id);
    }

    public void updateComment(CommentDto commentDto) {
        boardSqlMapper.updateComment(commentDto);
    }

    public List<Map<String, Object>> getCommentList(int articleId) {
        List<Map<String, Object>> result = new ArrayList<>();

        List<CommentDto> list = boardSqlMapper.selectCommentByArticleId(articleId);

        for(CommentDto commentDto : list) {
            UserDto userDto = userSqlMapper.findById(commentDto.getUser_id());
            Map<String, Object> map = new HashMap<>();
            map.put("commentDto", commentDto);
            map.put("userDto", userDto);
            result.add(map);
        }
        
        return result;
    }

}
