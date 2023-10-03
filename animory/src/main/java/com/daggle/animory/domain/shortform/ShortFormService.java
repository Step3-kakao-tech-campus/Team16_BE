package com.daggle.animory.domain.shortform;

import com.daggle.animory.common.error.exception.BadRequest400;
import com.daggle.animory.domain.pet.PetRepository;
import com.daggle.animory.domain.shortform.dto.request.ShortFormSearchCondition;
import com.daggle.animory.domain.shortform.dto.response.CategoryShortFormPage;
import com.daggle.animory.domain.shortform.dto.response.HomeShortFormPage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;

@Service
@RequiredArgsConstructor
public class ShortFormService {

    private final PetRepository petRepository;
    private final String UPLOAD_DIR = ".\\src\\main\\resources\\temporaryVideo\\";
    public CategoryShortFormPage getCategoryShortFormPage(final ShortFormSearchCondition searchCondition) {
        return CategoryShortFormPage.of(
            buildCategoryPageTitle(searchCondition),
            petRepository.findSliceBy(
                searchCondition.type(),
                searchCondition.area(),
                searchCondition.pageable()
            )
        );
    }

    public HomeShortFormPage getHomeShortFormPage(final int page) {
        // TODO: 홈 화면 숏폼 영상은 어떤 순서, 어떤 기준으로 보여줄 것인가?
        return HomeShortFormPage.of(
            petRepository.findSliceBy(PageRequest.of(page, 10)) // TODO: 하드코딩된 Page 숫자
        );
    }

    public Resource getShortFormByURL(final String fileName){
        String url = UPLOAD_DIR + fileName;
        try{
            return new InputStreamResource(new FileInputStream(url));
        }
        catch(FileNotFoundException ex){
            throw new BadRequest400("파일을 찾을 수 없습니다.");
        }
    }

    private String buildCategoryPageTitle(final ShortFormSearchCondition searchCondition) {
        return searchCondition.area().getFullProvinceName() + " 기준 " + searchCondition.type().getKoreanName() + " 친구들";
    }
}
