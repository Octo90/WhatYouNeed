package com.shop.service;

import com.shop.dto.AcBoardDto;
import com.shop.entity.AcBoard;
import com.shop.entity.Member;
import com.shop.repository.AcBoardRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AcBoardService {
    @Value("${acboardImgLocation}") //properties -> itemImgLocation=C:/shop1/item
    private String acboardImgLocation;
    private final AcBoardRepository acboardRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final HttpService httpService;

    public List<AcBoard> acboardlist() {
        return acboardRepository.findAll();
    }

    public void writeBoard(AcBoard acboard, MultipartFile acboardImgFile) throws Exception {
        if (acboardImgFile != null && !acboardImgFile.isEmpty()) {
            String oriImgName = acboardImgFile.getOriginalFilename();
            String imgName = "";
            String imgUrl = "";

            if (!StringUtils.isEmpty(oriImgName)) {
                imgName = fileService.uploadFile(acboardImgLocation, oriImgName, acboardImgFile.getBytes());
                imgUrl = "/images/acboard/" + imgName;
                acboard.updateAcBoardImg(oriImgName, imgName, imgUrl);
            }
        }
        acboardRepository.save(acboard);
    }



    public AcBoard viewboard(Long id) {

        return acboardRepository.findById(id).get();
    }


    public void deleteBoard(Long id) {
        acboardRepository.deleteById(id);
    }


    public Optional<AcBoard> getBoardById(Long id) {
        return acboardRepository.findById(id);
    }

    public Member findMembername(Principal principal) {
        String prinEmail = httpService.principalEmail(principal);
        return memberRepository.findByEmail(prinEmail).orElseThrow();
    }

    public List<AcBoardDto> findAll(String email) {//파인드 올은 대부분 엔티티 한태서 온다.
        List<AcBoard> boardEntityList = acboardRepository.findAll();
        List<AcBoardDto> AcboardDTOList = new ArrayList<>();
        for (AcBoard AcboardEntity : boardEntityList) {
            AcboardDTOList.add(AcBoardDto.toAcBoardDTO(AcboardEntity));
        }
        return AcboardDTOList;
    }

    public AcBoardDto findById(Long id) {
        Optional<AcBoard> optionalAcBoardEntity = acboardRepository.findById(id);
        if (optionalAcBoardEntity.isPresent()) {
            AcBoard AcboardEntity = optionalAcBoardEntity.get();
            AcBoardDto AcboardDTO = AcBoardDto.toAcBoardDTO(AcboardEntity);
            return AcboardDTO;
        } else {
            return null;
        }
    }
}
