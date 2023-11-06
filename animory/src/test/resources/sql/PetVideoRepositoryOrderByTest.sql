SET REFERENTIAL_INTEGRITY FALSE;

SET @in_a_week = TIMESTAMPADD(DAY, -3, @now);
SET @after_a_week = TIMESTAMPADD(DAY, -8, @now);

-- 일주일 이내의 데이터 10개
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (1, 'https://video.com/video1.mp4', 1, 1, '2023-10-25 00:20:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (2, 'https://video.com/video2.mp4', 100, 2, '2023-10-25 01:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (3, 'https://video.com/video3.mp4', 30, 3, '2023-10-25 02:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (4, 'https://video.com/video4.mp4', 40, 4, '2023-10-25 03:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (5, 'https://video.com/video5.mp4', 5000, 5, '2023-10-25 04:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (6, 'https://video.com/video6.mp4', 60, 6, '2023-10-25 05:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (7, 'https://video.com/video7.mp4', 70, 7, '2023-10-25 06:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (8, 'https://video.com/video8.mp4', 800, 8, '2023-10-25 07:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (9, 'https://video.com/video9.mp4', 90, 9, '2023-10-25 08:00:00', @in_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (10, 'https://video.com/video10.mp4', 100, 10, '2023-10-25 09:00:00', @in_a_week);

-- 일주일 이상 지난 데이터 10개
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (11, 'https://video.com/video11.mp4', 10, 11, '2023-10-25 10:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (12, 'https://video.com/video12.mp4', 120, 12, '2023-10-25 11:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (13, 'https://video.com/video13.mp4', 130, 13, '2023-10-25 12:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (14, 'https://video.com/video14.mp4', 250, 14, '2023-10-25 13:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (15, 'https://video.com/video15.mp4', 150, 15, '2023-10-25 14:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (16, 'https://video.com/video16.mp4', 160, 16, '2023-10-25 15:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (17, 'https://video.com/video17.mp4', 170, 17, '2023-10-25 16:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (18, 'https://video.com/video18.mp4', 180, 18, '2023-10-25 17:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (19, 'https://video.com/video19.mp4', 2000, 19, '2023-10-25 18:00:00', @after_a_week);
INSERT INTO pet_video (id, video_url, like_count, pet_id, created_at, updated_at)
VALUES (20, 'https://video.com/video20.mp4', 200, 20, '2023-10-25 19:00:00', @after_a_week);

SET REFERENTIAL_INTEGRITY TRUE;
