package com.ja.finalproject.domain.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ja.finalproject.domain.board.dto.ArticleDto;
import com.ja.finalproject.domain.board.dto.ArticleImageDto;
import com.ja.finalproject.domain.board.dto.CommentDto;
import com.ja.finalproject.domain.board.dto.LikeDto;

@Mapper
public interface BoardSqlMapper {
    public void createArticle(ArticleDto articleDto);
    public List<ArticleDto> findAll(
        @Param("searchType") String searchType, 
        @Param("searchWord") String searchWord,
        @Param("pageIndex") int pageIndex
        );

    public int getTotalArticleCount(
        @Param("searchType") String searchType, 
        @Param("searchWord") String searchWord
    );

    public ArticleDto findById(int id);
    public void increaseReadCount(int id);
    
    public void deleteById(int id);
    public void update(ArticleDto articleDto);

    // 이미지
    public void createArticleImage(ArticleImageDto articleImageDto);
    public List<ArticleImageDto> findImageByArticleId(int article_id);

    // 좋아요...
    public void createLike(LikeDto likeDto);
    public void deleteLike(LikeDto likeDto);
    public int getTotalLikeCount(int article_id);
    public int getMyLikeCount(LikeDto likeDto);


    // 댓글
    public void createComment(CommentDto commentDto);
    public void deleteCommentById(int id);
    public void updateComment(CommentDto commentDto);
    public List<CommentDto> selectCommentByArticleId(int article_id);


}
