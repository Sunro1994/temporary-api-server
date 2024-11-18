package com.ja.finalproject.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ja.finalproject.board.service.BoardService;
import com.ja.finalproject.dto.ArticleDto;
import com.ja.finalproject.dto.ArticleImageDto;
import com.ja.finalproject.dto.UserDto;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.util.StringUtils;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("mainPage")
    public String mainPage(Model model, 
        @RequestParam(value = "searchType", required = false) String searchType,
        @RequestParam(value = "searchWord", required = false) String searchWord,
        @RequestParam(value = "page", defaultValue = "1") int page
    ){

        List<Map<String, Object>> list = boardService.getArticleList(searchType, searchWord, page);
        model.addAttribute("list", list);

        int totalCount = boardService.getTotalArticleCount(searchType, searchWord);
        int lastPageNumber = (int)Math.ceil(totalCount/10.0);
        model.addAttribute("lastPageNumber", lastPageNumber);

        int startPage = ((page-1)/5)*5 + 1;
        int endPage = ((page-1)/5 + 1)*5;

        if(endPage > lastPageNumber) {
            endPage = lastPageNumber;
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("currentPage", page);

        return "board/mainPage";
    }

    @RequestMapping("writeArticlePage")
    public String writeArticlePage() {

        return "board/writeArticlePage";
    }

    @RequestMapping("writeArticleProcess")
    public String writeArticleProcess(HttpSession session, ArticleDto params, @RequestParam("uploadFiles") MultipartFile[] uploadFiles) {

        List<ArticleImageDto> articleImageDtoList = new ArrayList<>();

        // 파일 처리
        for(MultipartFile uploadFile : uploadFiles){
            if(uploadFile.isEmpty()){
                continue;
            }

            String rootPath = "C:/uploadFiles/";

            // 날짜별 폴더(디렉토리) 생성
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String todayPath = sdf.format(new Date());
            
            // rootPath + todayPath = C:/uploadFiles/2024/11/05/
            File todayFolderForCreate = new File(rootPath + todayPath);

            if(!todayFolderForCreate.exists()) {
                todayFolderForCreate.mkdirs();
            }

            // 파일명 충돌 회피 - 랜덤 + 시간 조합
            String originalFilename = uploadFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            long currentTime = System.currentTimeMillis();

            String filename = uuid + "_" + currentTime;

            // 확장자명 추출
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            filename += ext;

            try{
                uploadFile.transferTo(new File(rootPath + todayPath + filename));
            }catch(Exception e){
                e.printStackTrace();
            }
            
            // DB작업용 Dto 생성
            ArticleImageDto articleImageDto = new ArticleImageDto();
            articleImageDto.setOriginal_filename(originalFilename);
            articleImageDto.setLocation(todayPath + filename);
            articleImageDtoList.add(articleImageDto);
        }


        // 로그인 사용자 정보가 필요할때는 session 활용(특히 id)
        UserDto sessionUserInfo = (UserDto)session.getAttribute("sessionUserInfo");
        int userPk = sessionUserInfo.getId();

        params.setUser_id(userPk);

        boardService.registerArticle(params, articleImageDtoList);

        return "redirect:./mainPage";
    }


    @RequestMapping("articleDetailPage")
    public String articleDetailPage(Model model, @RequestParam("id") int id) {
        boardService.increaseReadCount(id);
        Map<String, Object> map = boardService.getArticle(id);
        
        // html escape - 특수문자, 엔터-> <br>
        // 나중에 javascript로 넘어가면 수행안해도됨.
        ArticleDto articleDto = (ArticleDto)map.get("articleDto");
        String content = articleDto.getContent();
        content = StringUtils.escapeXml(content); // 특수문자 처리.. th:text
        content = content.replaceAll("\n", "<br>");
        articleDto.setContent(content);

        model.addAttribute("qwer", map);

        return "board/articleDetailPage";
    }

    @RequestMapping("deleteArticleProcess")
    public String deleteArticleProcess(@RequestParam("id") int id) {

        boardService.deleteArticle(id);

        return "redirect:./mainPage";
    }

    @RequestMapping("updateArticlePage")
    public String updateArticlePage(HttpSession session, Model model, @RequestParam("id") int id) {

        model.addAttribute("yyyy", boardService.getArticle(id));

        return "board/updateArticlePage";
    }

    @RequestMapping("updateArticleProcess")
    public String updateArticleProcess(ArticleDto params) {

        boardService.updateArticle(params);

        return "redirect:./mainPage";
    }

}
