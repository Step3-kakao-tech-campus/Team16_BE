CREATE TABLE pet_video_like
(
    id           INT          NOT NULL,
    created_at   datetime     NULL,
    updated_at   datetime     NULL,
    ip_address   VARCHAR(255) NOT NULL,
    pet_video_id INT          NULL,
    CONSTRAINT pk_pet_video_like PRIMARY KEY (id)
);

ALTER TABLE pet_video_like
    ADD CONSTRAINT FK_PET_VIDEO_LIKE_ON_PET_VIDEO FOREIGN KEY (pet_video_id) REFERENCES pet_video (id);