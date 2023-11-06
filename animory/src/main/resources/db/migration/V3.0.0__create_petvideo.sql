-- 좋아요 기능 추가를 위해, PetVideo 테이블을 분리하였습니다.

-- PetVideo 테이블 생성
CREATE TABLE pet_video
(
    id         INT AUTO_INCREMENT NOT NULL,
    video_url  VARCHAR(255)       NOT NULL,
    like_count INT                NOT NULL,
    pet_id     INT                NULL,
    CONSTRAINT pk_pet_video PRIMARY KEY (id)
);

-- pet 테이블의 데이터를 pet_video 테이블에 복사
INSERT INTO pet_video (video_url, like_count, pet_id)
SELECT profile_short_form_url, 0, id
FROM pet;

ALTER TABLE pet_video
    ADD CONSTRAINT FK_PET_VIDEO_ON_PET FOREIGN KEY (pet_id) REFERENCES pet (id);

-- pet 테이블에서 profile_short_form_url 컬럼 삭제
ALTER TABLE pet
    DROP COLUMN profile_short_form_url;
