package org.bitcamp.project.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.ListBoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.dto.BoardImageDTO;
import org.bitcamp.project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/boards")
@RestController
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Value("C:\\file")
    private String path;

    @GetMapping("/list")
    public ResponseEntity<ListResponseDTO<BoardDTO>> list(ListRequestDTO listRequestDTO) {

        return ResponseEntity.ok(boardService.list(listRequestDTO));
    }

    // create
    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody BoardDTO boardDTO) {

        Long bno = boardService.create(boardDTO);

        return ResponseEntity.ok(bno);
    }

    // read 번호로 찾는경우 경로에 담으면댐
    @GetMapping("/{bno}")
    public ResponseEntity<BoardDTO> read(@PathVariable Long bno) {

        BoardDTO result = boardService.read(bno);

        return ResponseEntity.ok().body(result);
    }

    // delete
    @DeleteMapping("/{bno}")
    public ResponseEntity<Long> delete(@PathVariable Long bno) {

        Long delete = boardService.delete(bno);

        return ResponseEntity.ok().body(delete);
    }


    //update
    // {} 몇번째 있는걸 찾아서 바꿈
    // bno / 바꾸는값은 {}
    @PutMapping("/{bno}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long bno, @RequestBody BoardDTO boardDTO) {
        boardDTO.setBno(bno);
        boardService.update(boardDTO);
        return ResponseEntity.ok(boardDTO);
    }

    @PutMapping("/reply/{bno}")
    public ResponseEntity<ReplyDTO> replyUpdate(@PathVariable Long bno , @RequestBody ReplyDTO replyDTO){
        log.info("리플라이들어옴");
        // 제이슨 번호 넘겨준상태
        boardService.replyUpdate(bno,replyDTO);
        return ResponseEntity.ok(replyDTO);
    }

    @ResponseBody
    @GetMapping(value = "/down")
    public ResponseEntity<byte[]> down(String file) {

        log.info("--------------------down: " + file);
        // File() path int prefixLength 값이 들어간다
        File target = new File(path, file);

        String mimeType = null;
        try {
            mimeType = Files.probeContentType(target.toPath());

            if (mimeType.startsWith("image") == false) {
                //startsWith = 이 문자열 인스턴스가 지정한 문자로 시작하는지를 확인합니다.
                // 즉 "image~~" 로 시작하는지 if문 돌리는중
                // 거짓이면~~
                mimeType = "application/octet-stream";
            }
            //FileCopyUtils.copyToByteArray 지정한 Input인 FIle의 내용을 새로운 byte[]로 복사한다.
            //리턴값은 copy 된 새로운 byte[]이다
            // target 을 byte[] 바꿔준다고 보면될것같다 원래는 File
            byte[] arr = FileCopyUtils.copyToByteArray(target);

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(arr);
            //문법적인부분같다 리턴시 구글링해봐도 전부똑같이 리턴한다 .contentType(MediaType.parseMediaType
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    @ResponseBody
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoardImageDTO> upload(MultipartFile[] files) {
        //컨트롤러에서 업로드한 파일은 MultipartFile 변수를 사용하여 전달받습니다.
        // MultipartFile 클래스는 파일에 대한 정보(파일 이름, 크기등)와 파일 관련 메서드(ex. 파일 저장)를 제공합니다.
        log.warn(path + "경로 체크");
        log.info("경로 체크");
        // ArrayList 주입시켜서 새로운 값이들어오면 점점늘어나게..
        List<BoardImageDTO> result = new ArrayList<>();

        // files 에있는것들을 한개씩 꺼내서 담는과정
        for (MultipartFile file:files) {
            log.warn(file);
            // 파일명 추출
            String fileName = file.getOriginalFilename();
            //UUID.randomUUID().toString() 유일한 식별자를 생성
            String uuid = UUID.randomUUID().toString();

            File outFile = new File(path,uuid+"_"+fileName);
            // 바깥파일경로?
            File thumbFile = new File(path,"s_"+uuid+"_"+fileName);
            // 썸내일 파일경로 각각 두개를 만들

            try {
                // getInputStream 업로드 된 파일에서 InputStream 객체를 반환합니다.
                InputStream fin = file.getInputStream();
                // Files.copy 새 파일에 기존 파일을 복사합니다.
                Files.copy(fin, outFile.toPath());

                // BufferedImage 이미지의 객체를 생성?
                //ImageIO 사이즈 를 바꿔주기위한 메소드.. read 읽어줌 outFile 보여지는 사진을 바꾸기위하여
                BufferedImage originalImage = ImageIO.read(outFile);
                // 사이즈를 정해줌 100 100
                BufferedImage thumbnail = Thumbnails.of(originalImage)
                        .size(100,100)
                        .asBufferedImage();
                // 이미지 파일생성  ImageIO.write
                ImageIO.write(thumbnail, "JPG", thumbFile);

                fin.close(); // <-- 추가
//
            } catch (IOException e) {

            }
            // 디티오에저장
            result.add(BoardImageDTO.builder().uuid(uuid).fileName(fileName).build());


        }

        return result;
    }

    @GetMapping("/getList")
    public ResponseEntity<List<ListBoardDTO>> getList() {

        boardService.getList();

      return   ResponseEntity.ok(boardService.getList());
    }
// 업로드할때 디비에 어떻게 저장할것인가..

}
