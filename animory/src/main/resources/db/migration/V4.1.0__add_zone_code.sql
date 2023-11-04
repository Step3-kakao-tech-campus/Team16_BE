-- 보호소 정보 수정을 위한 기존 보호소 주소와 우편번호 조회를 위해 값이 필요하게 되었습니다.

ALTER TABLE shelter
    ADD zone_code varchar(10) NULL;