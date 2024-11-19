package com.ja.finalproject.domain.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ja.finalproject.domain.board.service.BoardService;
import com.ja.finalproject.domain.board.dto.CommentDto;
import com.ja.finalproject.domain.board.dto.LikeDto;
import com.ja.finalproject.global.response.dto.RestResponseDto;
import com.ja.finalproject.domain.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/board")
public class RestBoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("like")
    public RestResponseDto like(HttpSession session, LikeDto likeDto) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        likeDto.setUser_id(userDto.getId());

        boardService.like(likeDto);

        return responseDto;
    }

    @RequestMapping("unLike")
    public RestResponseDto unLike(HttpSession session, LikeDto likeDto) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        likeDto.setUser_id(userDto.getId());

        boardService.unLike(likeDto);
        
        return responseDto;
    }

    @RequestMapping("getTotalLikeCount")
    public RestResponseDto getTotalLikeCount(@RequestParam("articleId") int articleId) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        responseDto.add("count", boardService.getTotalLikeCount(articleId));

        return responseDto;
    }

    @RequestMapping("isUserLike")
    public RestResponseDto isUserLike(HttpSession session, LikeDto params) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        responseDto.add("isLike", boardService.isUserLike(params));

        return responseDto;
    }


    @RequestMapping("registerComment")
    public RestResponseDto registerComment(HttpSession session, CommentDto params) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");
        
        UserDto userDto = (UserDto)session.getAttribute("sessionUserInfo");
        params.setUser_id(userDto.getId());

        boardService.registerComment(params);

        return responseDto;
    }

    @RequestMapping("deleteComment")
    public RestResponseDto deleteComment(@RequestParam("id") int id) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        boardService.deleteComment(id);

        return responseDto;
    }    

    @RequestMapping("updateComment")
    public RestResponseDto updateComment(CommentDto params) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");

        boardService.updateComment(params);

        return responseDto;
    }    

    @RequestMapping("getCommentList")
    public RestResponseDto getCommentList(@RequestParam("articleId") int articleId) {
        RestResponseDto responseDto = new RestResponseDto();
        responseDto.setResult("success");
        responseDto.add("commentList", boardService.getCommentList(articleId));

        return responseDto;
    }    


}
