-- 오너 유저 (업체 소유자)
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'owner_1', '업체오너', 'VENDOR', NOW(), NOW());

-- 테스트 유저 (앱 사용자, 고정 UUID)
INSERT INTO users (id, toss_user_id, nickname, role, created_at, updated_at) VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test_user_1', '테스트유저', 'USER', NOW(), NOW());

-- 업체 1: 사운드박스 합주실
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, created_at, updated_at) VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '사운드박스 합주실', '02-1234-5678', '서울 강남구 테헤란로 12', '111-11-11111', 'APPROVED', '{"mon":{"open":"09:00","close":"23:00","closed":false},"tue":{"open":"09:00","close":"23:00","closed":false},"wed":{"open":"09:00","close":"23:00","closed":false},"thu":{"open":"09:00","close":"23:00","closed":false},"fri":{"open":"09:00","close":"23:00","closed":false},"sat":{"open":"10:00","close":"24:00","closed":false},"sun":{"open":"10:00","close":"22:00","closed":false}}', '{"주차","와이파이","음료"}', NOW(), NOW());

-- 업체 2: 밴드랩 스튜디오
INSERT INTO vendors (id, owner_id, name, phone, address, business_number, status, operating_hours, amenities, created_at, updated_at) VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '밴드랩 스튜디오', '02-9876-5432', '서울 마포구 와우산로 22', '222-22-22222', 'APPROVED', '{"mon":{"open":"09:00","close":"23:00","closed":false},"tue":{"open":"09:00","close":"23:00","closed":false},"wed":{"open":"09:00","close":"23:00","closed":false},"thu":{"open":"09:00","close":"23:00","closed":false},"fri":{"open":"09:00","close":"23:00","closed":false},"sat":{"open":"10:00","close":"24:00","closed":false},"sun":{"open":"10:00","close":"22:00","closed":false}}', '{"녹음","대기실"}', NOW(), NOW());

-- 룸 (사운드박스)
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'A룸', 4, 15000, 1, 4, '[{"name":"드럼","brand":""},{"name":"기타앰프","brand":""},{"name":"베이스앰프","brand":""}]', '{}', true, 1, NOW(), NOW());
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'B룸', 6, 20000, 1, 4, '[{"name":"드럼","brand":""},{"name":"키보드","brand":""},{"name":"믹서","brand":""}]', '{}', true, 2, NOW(), NOW());

-- 룸 (밴드랩)
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'A스튜디오', 4, 18000, 1, 4, '[{"name":"드럼","brand":""},{"name":"기타앰프","brand":""}]', '{}', true, 1, NOW(), NOW());
INSERT INTO rooms (id, vendor_id, name, capacity, hourly_price, min_hours, max_hours, equipment, images, is_active, sort_order, created_at, updated_at) VALUES ('22222222-2222-2222-2222-222222222222', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'B스튜디오', 6, 22000, 1, 4, '[{"name":"드럼","brand":""},{"name":"키보드","brand":""}]', '{}', true, 2, NOW(), NOW());

-- 예약 (테스트 유저, CONFIRMED)
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('33333333-3333-3333-3333-333333333333', 'R-20260322-TEST1', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'cccccccc-cccc-cccc-cccc-cccccccccccc', '2026-03-28', '14:00:00', '16:00:00', 2, 30000, 'CONFIRMED', NOW(), NOW());

-- 예약 (테스트 유저, COMPLETED)
INSERT INTO reservations (id, reservation_number, user_id, room_id, vendor_id, date, start_time, end_time, duration_hours, total_price, status, created_at, updated_at) VALUES ('44444444-4444-4444-4444-444444444444', 'R-20260310-TEST2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '2026-03-10', '18:00:00', '20:00:00', 2, 36000, 'COMPLETED', NOW(), NOW());

-- 알림 설정 (테스트 유저)
INSERT INTO notification_settings (id, user_id, reservation_alert, review_alert, marketing_alert) VALUES ('55555555-5555-5555-5555-555555555555', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', true, true, false);
