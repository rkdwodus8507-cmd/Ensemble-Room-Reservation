-- ============================================================
-- 유저
-- ============================================================

-- 오너 유저 (업체 소유자)
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'owner_1', '업체오너', 'VENDOR', NOW(), NOW());

-- 테스트 유저 (앱 사용자, 고정 UUID)
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test_user_1', '테스트유저', 'USER', NOW(), NOW());

-- 추가 유저 (리뷰 다양화용)
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('aaaaaa11-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test_user_2', '기타리스트김씨', 'USER', NOW(), NOW());
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('aaaaaa22-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test_user_3', '드러머박씨', 'USER', NOW(), NOW());

-- ============================================================
-- 업체
-- ============================================================

-- 업체 1: 사운드박스 합주실 (APPROVED, 리뷰 많음)
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, thumbnail_url, created_at, updated_at) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '사운드박스 합주실', '02-1234-5678', '서울 강남구 테헤란로 12', '111-11-11111', 'APPROVED', '{"mon":{"open":"09:00","close":"23:00","closed":false},"tue":{"open":"09:00","close":"23:00","closed":false},"wed":{"open":"09:00","close":"23:00","closed":false},"thu":{"open":"09:00","close":"23:00","closed":false},"fri":{"open":"09:00","close":"23:00","closed":false},"sat":{"open":"10:00","close":"24:00","closed":false},"sun":{"open":"10:00","close":"22:00","closed":false}}', '{"주차","와이파이","음료"}', 'https://images.unsplash.com/photo-1511379938547-c1f69419868d?auto=format&fit=crop&w=800&q=80', NOW(), NOW());

-- 업체 2: 밴드랩 스튜디오 (APPROVED, 리뷰 적음)
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, thumbnail_url, created_at, updated_at) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '밴드랩 스튜디오', '02-9876-5432', '서울 마포구 와우산로 22', '222-22-22222', 'APPROVED', '{"mon":{"open":"09:00","close":"23:00","closed":false},"tue":{"open":"09:00","close":"23:00","closed":false},"wed":{"open":"09:00","close":"23:00","closed":false},"thu":{"open":"09:00","close":"23:00","closed":false},"fri":{"open":"09:00","close":"23:00","closed":false},"sat":{"open":"10:00","close":"24:00","closed":false},"sun":{"open":"10:00","close":"22:00","closed":false}}', '{"녹음","대기실"}', 'https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&w=800&q=80', NOW(), NOW());

-- 업체 3: 뮤직팩토리 (APPROVED, 리뷰 없음 — 신규 업체 케이스)
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, thumbnail_url, created_at, updated_at) VALUES ('cccccc11-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '뮤직팩토리', '02-5555-1234', '서울 서초구 반포대로 55', '333-33-33333', 'APPROVED', '{"mon":{"open":"10:00","close":"22:00","closed":false},"tue":{"open":"10:00","close":"22:00","closed":false},"wed":{"open":"10:00","close":"22:00","closed":false},"thu":{"open":"10:00","close":"22:00","closed":false},"fri":{"open":"10:00","close":"23:00","closed":false},"sat":{"open":"10:00","close":"23:00","closed":false},"sun":{"open":"12:00","close":"20:00","closed":false}}', '{"와이파이","에어컨","음료","주차"}', 'https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?auto=format&fit=crop&w=800&q=80', NOW(), NOW());

-- 업체 4: 잼스테이션 (PENDING — 관리자 승인 대기 케이스)
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, thumbnail_url, created_at, updated_at) VALUES ('cccccc22-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '잼스테이션', '02-7777-8888', '서울 영등포구 당산로 33', '444-44-44444', 'PENDING', '{"mon":{"open":"11:00","close":"23:00","closed":false},"tue":{"open":"11:00","close":"23:00","closed":false},"wed":{"open":"11:00","close":"23:00","closed":false},"thu":{"open":"11:00","close":"23:00","closed":false},"fri":{"open":"11:00","close":"24:00","closed":false},"sat":{"open":"11:00","close":"24:00","closed":false},"sun":{"open":"11:00","close":"22:00","closed":false}}', '{"녹음","드럼부스","와이파이"}', 'https://images.unsplash.com/photo-1519892300165-cb5542fb47c7?auto=format&fit=crop&w=800&q=80', NOW(), NOW());

-- ============================================================
-- 룸
-- ============================================================

-- 사운드박스 룸
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'A룸', 4, 15000, 1, 4, '[{"name":"드럼","brand":"Pearl"},{"name":"기타앰프","brand":"Marshall"},{"name":"베이스앰프","brand":"Ampeg"}]', '{}', true, 1, NOW(), NOW());
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'B룸', 6, 20000, 1, 4, '[{"name":"드럼","brand":"Yamaha"},{"name":"키보드","brand":"Roland"},{"name":"믹서","brand":"Mackie"}]', '{}', true, 2, NOW(), NOW());

-- 밴드랩 룸
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'A스튜디오', 4, 18000, 1, 4, '[{"name":"드럼","brand":"DW"},{"name":"기타앰프","brand":"Fender"}]', '{}', true, 1, NOW(), NOW());
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('22222222-2222-2222-2222-222222222222', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'B스튜디오', 6, 22000, 1, 4, '[{"name":"드럼","brand":"Tama"},{"name":"키보드","brand":"Nord"}]', '{}', true, 2, NOW(), NOW());

-- 뮤직팩토리 룸
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('11111111-1111-1111-1111-111111111122', 'cccccc11-cccc-cccc-cccc-cccccccccccc', '레코딩룸', 3, 25000, 2, 4, '[{"name":"드럼","brand":"Sonor"},{"name":"기타앰프","brand":"Vox"},{"name":"마이크","brand":"Shure"}]', '{}', true, 1, NOW(), NOW());
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('11111111-1111-1111-1111-111111111133', 'cccccc11-cccc-cccc-cccc-cccccccccccc', '합주룸', 5, 18000, 1, 4, '[{"name":"드럼","brand":"Pearl"},{"name":"기타앰프","brand":"Orange"},{"name":"베이스앰프","brand":"Hartke"}]', '{}', true, 2, NOW(), NOW());

-- 잼스테이션 룸 (PENDING 업체지만 룸은 미리 등록)
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('11111111-1111-1111-1111-111111111144', 'cccccc22-cccc-cccc-cccc-cccccccccccc', '잼룸 1', 4, 16000, 1, 3, '[{"name":"드럼","brand":"Mapex"},{"name":"기타앰프","brand":"Laney"}]', '{}', true, 1, NOW(), NOW());

-- ============================================================
-- 예약 (다양한 상태)
-- ============================================================

-- CONFIRMED: 다가오는 예약
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('33333333-3333-3333-3333-333333333333', 'R-20260322-TEST1', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-28', '14:00:00', '16:00:00', 2, 30000, 'CONFIRMED', NOW(), NOW());

-- COMPLETED: 이용 완료 (리뷰 미작성)
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444444', 'R-20260310-TEST2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '2026-03-10', '18:00:00', '20:00:00', 2, 36000, 'COMPLETED', NOW(), NOW());

-- COMPLETED: 이용 완료 (리뷰 작성됨)
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444455', 'R-20260305-TEST3', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-05', '10:00:00', '12:00:00', 2, 30000, 'COMPLETED', NOW(), NOW());

-- CANCELLED: 취소된 예약
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, cancelled_by, cancel_reason, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444466', 'R-20260315-TEST4', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-15', '19:00:00', '21:00:00', 2, 40000, 'CANCELLED', 'USER', '일정 변경으로 취소합니다', NOW(), NOW());

-- COMPLETED: 다른 유저의 완료 예약 (리뷰 작성됨)
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444477', 'R-20260308-USER2', 'aaaaaa11-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-08', '15:00:00', '17:00:00', 2, 30000, 'COMPLETED', NOW(), NOW());

INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444488', 'R-20260312-USER3', 'aaaaaa22-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-12', '13:00:00', '16:00:00', 3, 60000, 'COMPLETED', NOW(), NOW());

INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444499', 'R-20260301-USER2B', 'aaaaaa11-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '2026-03-01', '11:00:00', '13:00:00', 2, 36000, 'COMPLETED', NOW(), NOW());

-- ============================================================
-- 리뷰 (업체별 평점 다양화)
-- ============================================================

-- 사운드박스: 평균 ~4.3 (리뷰 3개)
INSERT INTO reviews (id, user_id, vendor_id, reservation_id, rating, content, created_at, updated_at) VALUES ('aa000001-0000-0000-0000-000000000001', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '44444444-4444-4444-4444-444444444455', 5, '장비 상태가 정말 좋고 방음도 완벽해요. 마샬 앰프 사운드 최고!', NOW(), NOW());
INSERT INTO reviews (id, user_id, vendor_id, reservation_id, rating, content, created_at, updated_at) VALUES ('aa000002-0000-0000-0000-000000000002', 'aaaaaa11-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '44444444-4444-4444-4444-444444444477', 4, '전반적으로 깔끔하고 좋았습니다. 주차가 편해서 짐 옮기기 좋아요.', NOW(), NOW());
INSERT INTO reviews (id, user_id, vendor_id, reservation_id, rating, content, created_at, updated_at) VALUES ('aa000003-0000-0000-0000-000000000003', 'aaaaaa22-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '44444444-4444-4444-4444-444444444488', 4, '드럼 상태 좋고 믹서도 깨끗해요. B룸 넓어서 6인 밴드도 여유 있었습니다.', NOW(), NOW());

-- 밴드랩: 평균 3.0 (리뷰 1개)
INSERT INTO reviews (id, user_id, vendor_id, reservation_id, rating, content, created_at, updated_at) VALUES ('aa000004-0000-0000-0000-000000000004', 'aaaaaa11-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '44444444-4444-4444-4444-444444444499', 3, '녹음 시설은 좋은데 대기실이 좀 좁아요. 가격 대비 보통입니다.', NOW(), NOW());

-- 뮤직팩토리: 리뷰 없음 (신규 업체)

-- ============================================================
-- 찜
-- ============================================================

INSERT INTO favorites (id, user_id, vendor_id, created_at) VALUES ('ffffff01-ffff-ffff-ffff-ffffffffffff', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccccc-cccc-cccc-cccc-cccccccccccc', NOW());
INSERT INTO favorites (id, user_id, vendor_id, created_at) VALUES ('ffffff02-ffff-ffff-ffff-ffffffffffff', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccccc11-cccc-cccc-cccc-cccccccccccc', NOW());

-- ============================================================
-- 알림 설정
-- ============================================================

INSERT INTO notification_settings (id, user_id, reservation_alert, review_alert, marketing_alert) VALUES ('55555555-5555-5555-5555-555555555555', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', true, true, false);
