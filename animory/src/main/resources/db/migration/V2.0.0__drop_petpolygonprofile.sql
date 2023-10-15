-- PetPolygonProfile을 Embedded 타입으로 변경하고 Pet Entity에서 편리하게 관리하기 위한 수정


-- PET 테이블에 Embedded 타입으로 추가
ALTER TABLE pet
    ADD COLUMN activeness   INT NOT NULL,
    ADD COLUMN adaptability INT NOT NULL,
    ADD COLUMN affinity     INT NOT NULL,
    ADD COLUMN athletic     INT NOT NULL,
    ADD COLUMN intelligence INT NOT NULL;

-- PET POLYGON PROFILE 테이블 제거
ALTER TABLE pet_polygon_profile
    DROP FOREIGN KEY FK92d384m861rdgk8wh94mqw448;

DROP TABLE pet_polygon_profile;


